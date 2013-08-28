package controller;

import controller.util.JsfUtil;
import controller.util.PrimeDataModel;
import entity.Sas;
import facade.SasFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;


@ManagedBean(name="sasController")
@SessionScoped
public class SasController implements Serializable {


    private Sas current;
    private PrimeDataModel items = null;
    @EJB private facade.SasFacade ejbFacade;
    private int totalCapitalSAS = 0;
    private int totalTitulos = 0;
    private int totalSuscritoSA = 0;
    private int totalPagadoSA = 0;
    
    public void clear(){
        current = null;
    }

    private void calcularTotales(List<Sas> list){
        for (Sas sas : list){
            totalCapitalSAS += sas.getCapitalSuscrito();
            totalTitulos += sas.getTotalEnTitulos();
            totalSuscritoSA += sas.getCapitalSuscritoSa();
            totalPagadoSA += sas.getCapitalPagadoSa();
        }
    }

    public int getTotalCapitalSAS() {
        return totalCapitalSAS;
    }

    public int getTotalTitulos() {
        return totalTitulos;
    }

    public int getTotalSuscritoSA() {
        return totalSuscritoSA;
    }

    public int getTotalPagadoSA() {
        return totalPagadoSA;
    }
    
    public SasController() {
    }

    public Sas getSelected() {
        if (current == null) {
            current = new Sas();
        }
        return current;
    }

    public void setSelected(Sas selected) {
        current = selected;
    }

    private SasFacade getFacade() {
        return ejbFacade;
    }

    public String prepareList() {
        return "List";
    }

    public String prepareView() {
        return "View";
    }

    public PrimeDataModel getItems() {
        if (items == null) {
            List<Sas> list = getFacade().findAll();
            calcularTotales(list);
            items = new PrimeDataModel(list);
        }
        return items;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass=Sas.class)
    public static class SasControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SasController controller = (SasController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sasController");
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
            if (object instanceof Sas) {
                Sas o = (Sas) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+Sas.class.getName());
            }
        }

    }

}
