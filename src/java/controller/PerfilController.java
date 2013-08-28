package controller;

import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import controller.util.PrimeDataModel;
import entity.*;
import facade.GrupoFacade;
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
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

@ManagedBean (name="perfilController")
@SessionScoped
public class PerfilController {

    private Perfil current;
    private PrimeDataModel items = null;
    private PrimeDataModel itemsGrupo = null;
    private PrimeDataModel itemsUsuario = null;
    @EJB private facade.PerfilFacade ejbFacade;
    @EJB private facade.GruposPerfilFacade ejbFacadeGruposPerfil;
    @EJB private facade.GrupoFacade ejbFacadeGrupo;
    @EJB private facade.UsuarioFacade ejbFacadeUsuario;
    @EJB private facade.PerfilesUsuarioFacade ejbFacadePerfilesUsuario;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Grupo[] gruposArray = null;
    private Usuario[] usuariosArray = null;
    private GruposPerfil selectedGrupo;
    private PerfilesUsuario selectedUsuario;

    public GruposPerfil getSelectedGrupo() {
        return selectedGrupo;
    }

    public void setSelectedGrupo(GruposPerfil selectedGrupo) {
        this.selectedGrupo = selectedGrupo;
    }

    public PerfilesUsuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedModulo(PerfilesUsuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public void removeUsuario(){
         try {
            this.ejbFacadePerfilesUsuario.remove(selectedUsuario);
            current.removeUsuario(selectedUsuario);
            getFacade().edit(current);
            this.getEjbFacadeUsuario().refresh(selectedUsuario.getUsuario());
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return;
    }
    
    public void removeGrupo(){
         try {
            this.ejbFacadeGruposPerfil.remove(selectedGrupo);
            current.removeGrupo(selectedGrupo);
            getFacade().edit(current);
            this.ejbFacadeGrupo.refresh(selectedGrupo.getGrupo());
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GrupoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return;
    }

    public void addUsuarios(){
        for (int i = 0; i < usuariosArray.length; i++){
            if (!current.containUsuario(usuariosArray[i]))
                current.addUsuario(usuariosArray[i]);
        }
        recreateModel();
        return;
    }

    public void addGrupos(){
        for (int i = 0; i < gruposArray.length; i++){
            if (!current.containGrupo(gruposArray[i]))
                current.addGrupo(gruposArray[i]);
        }
        recreateModel();
        return;
    }

    public Usuario[] getUsuariosArray() {
        return usuariosArray;
    }

    public void setUsuariosArray(Usuario[] usuariosArray) {
        this.usuariosArray = usuariosArray;
    }
    
    public Grupo[] getGruposArray() {
        return gruposArray;
    }

    public void setGruposArray(Grupo[] gruposArray) {
        this.gruposArray = gruposArray;
    }

    public UsuarioFacade getEjbFacadeUsuario() {
        return ejbFacadeUsuario;
    }

    public GrupoFacade getEjbFacadeGrupo() {
        return ejbFacadeGrupo;
    }

    public PerfilController() {
    }

    public Perfil getSelected() {
        if (current == null) {
            current = new Perfil();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Perfil perfil) {
       current = perfil;
       selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    }

    private PerfilFacade getFacade() {
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
        current = (Perfil)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Perfil();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PerfilCreated"));
            return "Edit";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Perfil)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String prepareEditForView() {
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            for (PerfilesUsuario pu : current.getPerfilesUsuarioList()){
                this.getEjbFacadeUsuario().refresh(pu.getUsuario());
            }
            for (GruposPerfil gp : current.getGruposPerfilList()){
                this.ejbFacadeGrupo.refresh(gp.getGrupo());
            }
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PerfilUpdated"));
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PerfilDeleted"));
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

    public PrimeDataModel getItemsUsuario() {
        if (itemsUsuario == null) {
            List<Usuario> list = this.getEjbFacadeUsuario().findAll();
            for (PerfilesUsuario u : current.getPerfilesUsuarioList())
                list.remove(u.getUsuario());
            itemsUsuario = new PrimeDataModel(list);
        }
        return itemsUsuario;
    }
    
    public PrimeDataModel getItemsGrupo() {
        if (itemsGrupo == null) {
            List<Grupo> list = this.getEjbFacadeGrupo().findAll();
            for (GruposPerfil g : current.getGruposPerfilList())
                list.remove(g.getGrupo());
            itemsGrupo = new PrimeDataModel(list);
        }
        return itemsGrupo;
    }
    
    private void recreateModel() {
        items = null;
        itemsGrupo = null;
        gruposArray = null;
        itemsUsuario = null;
        usuariosArray = null;
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

    @FacesConverter(forClass=Perfil.class)
    public static class PerfilControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PerfilController controller = (PerfilController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "perfilController");
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
            if (object instanceof Perfil) {
                Perfil o = (Perfil) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+PerfilController.class.getName());
            }
        }

    }

}
