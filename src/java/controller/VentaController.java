package controller;

import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import controller.util.PrimeDataModel;
import entity.Animal;
import entity.CostoVenta;
import entity.PagoCompraVenta;
import entity.ProductoVenta;
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
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;
import util.Constantes;

@ManagedBean(name = "ventaController")
@SessionScoped
public class VentaController implements Serializable {

    private Venta current;
    private PrimeDataModel items = null;
    @EJB private facade.VentaFacade ejbFacade;
    @EJB private facade.AnimalFacade ejbFacadeAnimal;
    @EJB private facade.PagoCompraVentaFacade ejbFacadePago;
    @EJB private facade.CostoVentaFacade ejbFacadeCosto;
    @EJB private facade.ProductoVentaFacade ejbFacadeProducto;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Animal[] animalesArray = null;
    //private PrimeDataModel itemsAnimal;

    public void removeCosto(CostoVenta costo){
        try {
            current.removeCosto(costo);
            current.updateValores();
            ejbFacadeCosto.remove(costo);
            this.update();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    public void removeProducto(ProductoVenta producto){
        try {
            current.removeProducto(producto);
            current.updateValores();
            ejbFacadeProducto.remove(producto);
            this.update();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    public void removePago(PagoCompraVenta pago){
        try {
            current.removePago(pago);
            current.updateValores();
            ejbFacadePago.remove(pago);
            this.update();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    public void removeAnimal(Animal animal){
        try {
            current.removeAnimal(animal);
            current.updateValores();
            animal.setIdVenta(null);
            ejbFacadeAnimal.edit(animal);
            this.update();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    public PrimeDataModel getItemsAnimal() {
        this.recreateModel();
        return new PrimeDataModel(ejbFacadeAnimal.findAllDisp());
    }

    public Animal[] getAnimalesArray() {
        return animalesArray;
    }

    public void setAnimalesArray(Animal[] animalesArray) {
        this.animalesArray = animalesArray;
    }

    public SelectItem[] getTipoVenta() {  
        return JsfUtil.getSelectItems(Constantes.getListTipoVenta(), true);
    }  
    
    public void addAnimales(){
        try {
            for (Animal animal : animalesArray){
                current.addAnimal(animal);
                ejbFacadeAnimal.edit(animal);
            }
            current.updateValores();
            this.update();
        } catch (Exception e) {
            System.out.println(e.getClass());
            JsfUtil.addErrorMessage(e, "La Venta aún no ha sido creada.");
        }
    }
    
    public void saveAnimal(RowEditEvent event){
        try {
            Animal animal = (Animal) event.getObject();
            animal.updateTotalVenta();
            current.updateValores();
            this.update();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void createProducto(){
        try {
            ProductoVenta producto = new ProductoVenta(current);
            ejbFacadeProducto.create(producto);
            current.addProducto(producto);
            current.updateValores();
            this.update();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "La Venta aún no ha sido creada.");
        }
    }
    
    public void saveProducto(RowEditEvent event){
        try {
            ProductoVenta producto = (ProductoVenta) event.getObject();
            producto.updateTotal();
            //ejbFacadeProducto.edit(producto);
            current.updateValores();
            this.update();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void createCosto(){
        try {
            CostoVenta costo = new CostoVenta(current);
            ejbFacadeCosto.create(costo);
            current.addCosto(costo);
            current.updateValores();
            this.update();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "La Venta aún no ha sido creada.");
        }
    }
    
    public void saveCosto(RowEditEvent event){
        try {
            CostoVenta costo = (CostoVenta) event.getObject();
            //ejbFacadeCosto.edit(costo);
            costo.updateTotal();
            current.updateValores();
            this.update();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void createPago(){
        try {
            PagoCompraVenta pago = new PagoCompraVenta(current);
            ejbFacadePago.create(pago);
            current.addPago(pago);
            current.updateValores();
            this.update();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "La Venta aún no ha sido creada.");
        }
    }
    
    public void savePago(RowEditEvent event){
        try {
            PagoCompraVenta pago = (PagoCompraVenta) event.getObject();
            ejbFacadePago.edit(pago);
            current.updateValores();
            this.update();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public VentaController() {
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
                    return new PrimeDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
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

    public void prepareCreate() {
        current = new Venta();
        this.recreateModel();
        //selectedItemIndex = -1;
        //return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VentaCreated"));
            return null;//prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
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

    public String destroy() {
        //current = (Venta) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VentaDeleted"));
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
        animalesArray = null;
        return items;
    }

    private void recreateModel() {
        items = null;
        //itemsAnimal = null;
        animalesArray = null;
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

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Venta.class)
    public static class VentaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VentaController controller = (VentaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ventaController");
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
