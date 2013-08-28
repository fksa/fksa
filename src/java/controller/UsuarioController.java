package controller;

import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import controller.util.PrimeDataModel;
import entity.Perfil;
import entity.PerfilesUsuario;
import entity.Usuario;
import facade.PerfilFacade;
import facade.UsuarioFacade;
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

@ManagedBean (name="usuarioController")
@SessionScoped
public class UsuarioController {

    private Usuario current;
    private PrimeDataModel items = null;
    private PrimeDataModel itemsPerfil = null;
    @EJB private facade.UsuarioFacade ejbFacade;
    @EJB private facade.PerfilesUsuarioFacade ejbFacadePerfilesUsuario;
    @EJB private facade.PerfilFacade ejbFacadePerfil;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Perfil[] perfilesArray = null;
    private PerfilesUsuario selectedPerfil = null;

    public PerfilesUsuario getSelectedPerfil() {
        return selectedPerfil;
    }

    public void setSelectedPerfil(PerfilesUsuario selectedPerfil) {
        this.selectedPerfil = selectedPerfil;
    }

    public void removePerfil(){
         try {
            this.ejbFacadePerfilesUsuario.remove(selectedPerfil);
            current.removePerfil(selectedPerfil);
            getFacade().edit(current);
            this.ejbFacadePerfil.refresh(selectedPerfil.getPerfil());
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PerfilDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return;
    }

    public void addPerfiles(){
        for (int i = 0; i < perfilesArray.length; i++){
            if (!current.containPerfil(perfilesArray[i]))
                current.addPerfil(perfilesArray[i]);
        }
        recreateModel();
        return;
    }

    public Perfil[] getPerfilesArray() {
        return perfilesArray;
    }

    public void setPerfilesArray(Perfil[] perfilesArray) {
        this.perfilesArray = perfilesArray;
    }
    
    public PerfilFacade getEjbFacadePerfil() {
        return ejbFacadePerfil;
    }

    public UsuarioController() {
    }

    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Usuario usuario) {
       current = usuario;
       selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    }
    private UsuarioFacade getFacade() {
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
        current = (Usuario)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Usuario();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setPwd(JsfUtil.getHash(current.getPwd()));
            getFacade().create(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
            return "Edit";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Usuario)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String prepareEditForView() {
        return "Edit";
    }

    public void updatePwd(){
        try {
            current.setPwd(JsfUtil.getHash(current.getPwd()));
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    public String update() {
        try {
            getFacade().edit(current);
            for (PerfilesUsuario pu : current.getPerfilesUsuarioList()){
                this.getEjbFacadePerfil().refresh(pu.getPerfil());
            }
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        performDestroy();
        recreateModel();
        recreatePagination();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
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

    public PrimeDataModel getItemsPerfil() {
        if (itemsPerfil == null) {
            List<Perfil> list = this.getEjbFacadePerfil().findAll();
            for (PerfilesUsuario p : current.getPerfilesUsuarioList())
                list.remove(p.getPerfil());
            itemsPerfil = new PrimeDataModel(list);
        }
        return itemsPerfil;
    }
    
    private void recreateModel() {
        items = null;
        itemsPerfil = null;
        perfilesArray = null;
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

    @FacesConverter(forClass=Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getUsuario());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+UsuarioController.class.getName());
            }
        }

    }

}
