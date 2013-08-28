package controller;

import entity.Modulo;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import controller.util.PrimeDataModel;
import facade.ModuloFacade;

import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

@ManagedBean (name="moduloController")
@SessionScoped
public class ModuloController {

    private Modulo current;
    private PrimeDataModel items = null;
    @EJB private facade.ModuloFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String sinUrl = "true";

    public SelectItem[] getItemsAvailablePermisos() {
        SelectItem[] opciones = new SelectItem[2];
        opciones[0] = new SelectItem("NO", "Consulta");
        opciones[1] = new SelectItem("SI", "Acceso Total");
        return opciones;
    }
           
    public String getSinUrl() {
        return sinUrl;
    }

    public void handleGrupoChange() {
        sinUrl = current.getIdGrupoModulo()==null?"true":"false";
    }

    public ModuloController() {
    }

    public void setSelected(Modulo modulo) {
       current = modulo;
       selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    }

    public Modulo getSelected() {
        if (current == null) {
            current = new Modulo();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ModuloFacade getFacade() {
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
                    return new PrimeDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
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
        current = (Modulo)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Modulo();
        sinUrl = "true";
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ModuloCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Modulo)getItems().getRowData();
        sinUrl = current.getIdGrupoModulo()==null?"true":"false";
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            if (current.getIdGrupoModulo()==null){current.setUrl(null);current.setPermisos(null);}
            getFacade().edit(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ModuloUpdated"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        if (current.getModuloList().size()==0){
            performDestroy();
            recreateModel();
        }
        else JsfUtil.addSuccessMessage("El módulo no se puede eliminar.");
        return null;
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ModuloDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count-1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex+1}).get(0);
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

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return this.getSelectItemsGrupos(ejbFacade.findAll());
    }

    private SelectItem[] getSelectItemsGrupos(List<Modulo> entities) {
        ArrayList selArrayList = new  ArrayList();
        selArrayList.add(new SelectItem(null, "Ninguno"));
        try {
            for (Modulo x : entities) {
                if (x.getIdGrupoModulo()==null)
                    selArrayList.add(new SelectItem(x, x.getNombre()));
            }
        } catch (Exception e) {}
        return (SelectItem[])selArrayList.toArray(new SelectItem[selArrayList.size()]);
    }


    @FacesConverter(forClass=Modulo.class)
    public static class ModuloControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ModuloController controller = (ModuloController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "moduloController");
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
            if (object instanceof Modulo) {
                Modulo o = (Modulo) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+ModuloController.class.getName());
            }
        }

    }

}
