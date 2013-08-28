package controller;

import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import controller.util.PrimeDataModel;
import entity.Accionista;
import entity.PagoAcciones;
import entity.Sas;
import entity.Titulo;
import facade.AccionistaFacade;
import java.io.Serializable;
import java.util.List;
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


@ManagedBean(name="accionistaController")
@SessionScoped
public class AccionistaController implements Serializable {


    private Accionista current;
    private PrimeDataModel items = null;
    @EJB private facade.AccionistaFacade ejbFacade;
    @EJB private facade.PagoAccionesFacade ejbFacadePago;
    @EJB private facade.TituloFacade ejbFacadeTitulo;
    @EJB private facade.SasFacade ejbFacadeSAS;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Accionista> filteredItems;

    public void createTitulo(){
        try {
            if (current.validar()){
                JsfUtil.addSuccessMessage("Accionista noo tiene Pagos pendientes de Título.");
                return;
            }
            Sas sas = null;
            if (current.getTituloList()!=null && !current.getTituloList().isEmpty()){
                sas = ((Titulo)current.getTituloList().get(0)).getIdSas();
            }
            Titulo titulo = new Titulo(current,sas);
            current.addTitulo(titulo);
            if (sas != null) {
                updateTitulo(titulo);
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    public void saveTitulo(RowEditEvent event){
        try {
            Titulo titulo = (Titulo) event.getObject();
            updateTitulo(titulo);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateTitulo(Titulo titulo){
        titulo.getIdSas().addTitulo(titulo);
        current.setEstado(Constantes.ESTADO_ACTIVO);
        ejbFacadeTitulo.create(titulo);
        ejbFacadeSAS.edit(titulo.getIdSas());
        getFacade().edit(current);
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TituloCreated"));
    }
    
    public void createPago(){
        try {
            PagoAcciones pago = new PagoAcciones(current);
            ejbFacadePago.create(pago);
            current.addPago(pago);
            current.setEstado(Constantes.ESTADO_PENDIENTE);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PagoAccionesCreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void updateCancel(RowEditEvent event){
    }

    public void updatePago(RowEditEvent event){
        try {
            PagoAcciones pagoAcciones = (PagoAcciones) event.getObject();
            pagoAcciones.update();
            ejbFacadePago.edit(pagoAcciones);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PagoAccionesUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public List<Accionista> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Accionista> filteredItems) {
        this.filteredItems = filteredItems;
    }
    
    public SelectItem[] getTituloOptions() {  
        return JsfUtil.getSelectItems(Constantes.getListTitulo(), true);
    }  

    public SelectItem[] getEstadoOptions() {  
        return JsfUtil.getSelectItems(Constantes.getListEstado(), true);
    }  
    
    public SelectItem[] getTiposIdentificacion() {  
        return JsfUtil.getSelectItems(Constantes.getListTipoIdentificacion(),false);
    }  

    public AccionistaController() {
    }

    public Accionista getSelected() {
        if (current == null) {
            current = new Accionista();
            //selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Accionista selected) {
        current = selected;
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    }

    private AccionistaFacade getFacade() {
        return ejbFacade;
    }
    
    /*public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(Integer.parseInt(ResourceBundle.getBundle("/Bundle").getString("Pagination"))) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public PrimeDataModel createPageDataModel() {
                    return new LazyPrimeDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                }
            };
        }
        return pagination;
    }*/

    public String prepareList() {
        recreateModel();
        return "List?faces-redirect=true";
    }

    public String prepareView() {
        current = (Accionista)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreate() {
        current = new Accionista();
        //selectedItemIndex = -1;
        //return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AccionistaCreated"));
            return null;//prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        //current = (Accionista)getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return null;// "Edit?faces-redirect=true";
    }

    public void update() {
        try {
            if (current.getId()==null){
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AccionistaCreated"));
            }
            else{
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AccionistaUpdated"));
            }
            recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        //return prepareList();
    }

    public String destroy() {
        current = (Accionista)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AccionistaDeleted"));
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
            //items = getPagination().createPageDataModel();
            items = new PrimeDataModel (this.getFacade().findAll());
        }
        //current = null;
        return items;
    }

    private void recreateModel() {
        items = null;
        //current = null;
   }

    private void recreatePagination() {
        pagination = null;
    }

    /*public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }*/

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass=Accionista.class)
    public static class AccionistaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AccionistaController controller = (AccionistaController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "accionistaController");
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
            if (object instanceof Accionista) {
                Accionista o = (Accionista) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+Accionista.class.getName());
            }
        }

    }

}
