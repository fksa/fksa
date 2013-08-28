package controller;

import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import controller.util.PrimeDataModel;
import entity.Animal;
import entity.Venta;
import facade.VentaFacade;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "ventaCanalesController")
@SessionScoped
public class VentaCanalesController implements Serializable {

    private Venta current;
    private PrimeDataModel items = null;
    @EJB private facade.VentaFacade ejbFacade;
    @EJB private facade.AnimalFacade ejbFacadeAnimal;
    //@EJB private facade.SacrificioFacade ejbFacadeSacrificio;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public void createSacrificio(){
        for ( Animal a : current.getAnimalList() ){
            if ( a.getSacrificio() == null ) {
                a.addSacrificio();
                ejbFacadeAnimal.edit(a);
            }
        }
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VentaUpdated"));
    }        
    
    public void saveAnimal(RowEditEvent event){
        try {
            Animal animal = (Animal) event.getObject();
            //animal.updateTotalVenta();
            //current.updateValores();
            //this.update();
            ejbFacadeAnimal.edit(animal);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnimalUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public VentaCanalesController() {
    }

    public Venta getSelected() {
        if (current == null) {
            current = new Venta();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Venta selected) {
        current = selected;
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    }

    private VentaFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(Integer.parseInt(ResourceBundle.getBundle("/Bundle").getString("Pagination"))) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public PrimeDataModel createPageDataModel() {
                    return new PrimeDataModel(getFacade().findAllVentaCanales());
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Venta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareEdit() {
        current = (Venta) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public void update() {
        try {
            if (current.getId() == null) {
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VentaCreated"));
            } else {
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VentaUpdated"));
            }
            this.recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public PrimeDataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
        //itemsAnimal = null;
        //current = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    @FacesConverter(forClass = Venta.class)
    public static class VentaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VentaCanalesController controller = (VentaCanalesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ventaCanalesController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Venta) {
                Venta o = (Venta) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Venta.class.getName());
            }
        }
    }
}
