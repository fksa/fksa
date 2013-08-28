package controller;

import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import controller.util.PrimeDataModel;
import entity.Grupo;
import entity.GruposPerfil;
import entity.Modulo;
import entity.ModulosGrupo;
import entity.Perfil;
import facade.GrupoFacade;
import facade.ModuloFacade;
import facade.PerfilFacade;
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

@ManagedBean (name="grupoController")
@SessionScoped
public class GrupoController {

    private Grupo current;
    private PrimeDataModel items = null;
    private PrimeDataModel itemsModulo = null;
    private PrimeDataModel itemsPerfil = null;
    @EJB private facade.GrupoFacade ejbFacade;
    @EJB private facade.ModuloFacade ejbFacadeModulo;
    @EJB private facade.PerfilFacade ejbFacadePerfil;
    @EJB private facade.ModulosGrupoFacade ejbFacadeModulosGrupo;
    @EJB private facade.GruposPerfilFacade ejbFacadeGruposPerfil;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Modulo[] modulosArray = null;
    private Perfil[] perfilesArray = null;
    private ModulosGrupo selectedModulo;
    private GruposPerfil selectedPerfil;

    public GruposPerfil getSelectedPerfil() {
        return selectedPerfil;
    }

    public void setSelectedPerfil(GruposPerfil selectedPerfil) {
        this.selectedPerfil = selectedPerfil;
    }

    public ModulosGrupo getSelectedModulo() {
        return selectedModulo;
    }

    public void setSelectedModulo(ModulosGrupo selectedModulo) {
        this.selectedModulo = selectedModulo;
    }

    public void removeModulo(){
         try {
            this.ejbFacadeModulosGrupo.remove(selectedModulo);
            current.removeModulo(selectedModulo);
            getFacade().edit(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ModuloDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    public void removePerfil(){
         try {
            this.ejbFacadeGruposPerfil.remove(selectedPerfil);
            current.removePerfil(selectedPerfil);
            getFacade().edit(current);
            this.ejbFacadePerfil.refresh(selectedPerfil.getPerfil());
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PerfilDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void addPerfiles(){
        for (int i = 0; i < perfilesArray.length; i++){
            if (!current.containPerfil(perfilesArray[i]))
                current.addPerfil(perfilesArray[i]);
        }
        recreateModel();
    }

    public void addModulos(){
        for (int i = 0; i < modulosArray.length; i++){
            if (!current.containModulo(modulosArray[i]))
                current.addModulo(modulosArray[i]);
        }
        recreateModel();
    }

    public Perfil[] getPerfilesArray() {
        return perfilesArray;
    }

    public void setPerfilesArray(Perfil[] perfilesArray) {
        this.perfilesArray = perfilesArray;
    }
    
    public Modulo[] getModulosArray() {
        return modulosArray;
    }

    public void setModulosArray(Modulo[] modulosArray) {
        this.modulosArray = modulosArray;
    }

    public ModuloFacade getEjbFacadeModulo() {
        return ejbFacadeModulo;
    }

    public PerfilFacade getEjbFacadePerfil() {
        return ejbFacadePerfil;
    }

    public GrupoController() {
    }

    public Grupo getSelected() {
        if (current == null) {
            current = new Grupo();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Grupo grupo) {
       current = grupo;
       selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    }

    private GrupoFacade getFacade() {
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
        current = (Grupo)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Grupo();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GrupoCreated"));
            return "Edit";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Grupo)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String prepareEditForView() {
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            for (GruposPerfil gp : current.getGruposPerfilList()){
                this.ejbFacadePerfil.refresh(gp.getPerfil());
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GrupoUpdated"));
            recreateModel();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GrupoDeleted"));
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
            for (GruposPerfil p : current.getGruposPerfilList())
                list.remove(p.getPerfil());
            itemsPerfil = new PrimeDataModel(list);
        }
        return itemsPerfil;
    }
    
    public PrimeDataModel getItemsModulo() {
        if (itemsModulo == null) {
            List<Modulo> list = this.getEjbFacadeModulo().findAll();
            for (ModulosGrupo m : current.getModulosGrupoList())
                list.remove(m.getModulo());
            itemsModulo = new PrimeDataModel(list);
        }
        return itemsModulo;
    }
    
    private void recreateModel() {
        items = null;
        itemsModulo = null;
        modulosArray = null;
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

    @FacesConverter(forClass=Grupo.class)
    public static class GrupoControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GrupoController controller = (GrupoController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "grupoController");
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
            if (object instanceof Grupo) {
                Grupo o = (Grupo) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+GrupoController.class.getName());
            }
        }

    }

}
