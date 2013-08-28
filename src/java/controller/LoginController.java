/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.util.JsfUtil;
import entity.*;
import facade.GrupoFacade;
import facade.PerfilFacade;
import facade.UsuarioFacade;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
/**
 *
 * @author MER
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController {

    public String getValidarPermisos(){
        Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(request instanceof HttpServletRequest){ 
            String url = ((HttpServletRequest) request).getRequestURI();
            url =  url.substring(url.indexOf("/pages/") + 7);
            url =  url.substring(0, url.indexOf('/'));
            if (this.permisosTotales.contains(url)) return "false";
        }
        return "true";
    }

    private void generarMenuMap(){
        List<Modulo> modulos = new ArrayList();
        for (PerfilesUsuario p : usuario.getPerfilesUsuarioList()){
            this.ejbFacadePerfil.refresh(p.getPerfil());
            for (GruposPerfil g : p.getPerfil().getGruposPerfilList()){
                this.ejbFacadeGrupo.refresh(g.getGrupo());
                for (ModulosGrupo m : g.getGrupo().getModulosGrupoList()){
                    if (m.getModulo().getIdGrupoModulo()==null) modulos.add(0, m.getModulo());
                    else modulos.add(m.getModulo()); 
                }
            }
        }
        menuMap = new HashMap();
        for (Modulo m : modulos){
            if (m.getIdGrupoModulo()==null){
                if (!menuMap.containsKey(m))
                    menuMap.put(m,new ArrayList());
            }
            else{
                ArrayList modulosGrupo = (ArrayList)menuMap.get(m.getIdGrupoModulo());
                if (modulosGrupo!=null && !modulosGrupo.contains(m)) modulosGrupo.add(m);
            }
        }
    }
    
    public void generarMenu() {
        //if(menuModel!=null) return;
        generarMenuMap();
        this.permisosTotales = new ArrayList();
        menuModel = new DefaultMenuModel();
        Iterator itr = menuMap.entrySet().iterator();
	while (itr.hasNext()) {
            Map.Entry e = (Map.Entry)itr.next();
            ArrayList opciones = (ArrayList)e.getValue();
            Submenu submenu = new Submenu();
            submenu.setLabel(((Modulo)e.getKey()).getNombre());
            for ( Object o : opciones){
                Modulo m = (Modulo)o;
                MenuItem item = new MenuItem();
                item.setValue(m.getNombre());
                item.setUrl("/faces/pages/" + m.getUrl() + "/List.xhtml");
                submenu.getChildren().add(item);
                if (m.getPermisos()!=null && m.getPermisos().equals("SI"))
                    permisosTotales.add(m.getUrl());
            }
            menuModel.addSubmenu(submenu);
	}
    }  

    public MenuModel getMenuModel() {  
        return menuModel;  
    }

    /** Creates a new instance of LoginController */
    public LoginController() {
    }

    private String username;
    private String password;
    private Usuario usuario;
    @EJB private UsuarioFacade ejbFacade;
    @EJB private PerfilFacade ejbFacadePerfil;
    @EJB private GrupoFacade ejbFacadeGrupo;
    private MenuModel menuModel;
    private HashMap menuMap;
    private List<String> permisosTotales;
    
    protected Usuario getUsuario(){
        return usuario;
    }

    public String getUsername() {
        return (usuario!=null)?usuario.getNombre():username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        if (username != null && username.equals("admin") && password != null && password.equals("admin")) {
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid Credentials");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public String checkValidUser() {
/*        System.out.println("ConstantesJobs.getDirFacturas() = " + ConstantesJobs.getDirFacturas());
        if (ConstantesJobs.getDirFacturas() == null){
            Object ctx = FacesContext.getCurrentInstance().getExternalContext().getContext();
            if(ctx instanceof ServletContext)
                ConstantesJobs.setPath(((ServletContext) ctx).getRealPath(""));
        }
        System.out.println("ConstantesJobs.getDirFacturas() = " + ConstantesJobs.getDirFacturas());*/

        FacesMessage msg = null;
        String event = "";

        if (username != null && password != null) {
            username = JsfUtil.replaceSQL(username);
            usuario = ejbFacade.find(username);
            ejbFacade.refresh(usuario);
            if (usuario==null){
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario Inválido", "Invalid Credentials");
                event = "/pages/login";
            }
            else if (!JsfUtil.getHash(password).equals(usuario.getPwd())){
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Clave Inválida", "Invalid Credentials");
                event = "/pages/login";
            }
            else{
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                HttpSession session = (HttpSession) context.getSession(true);
                session.setAttribute("usuarioLogin", username);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", usuario.getNombre());
                event = "/pages/index";
                this.generarMenu();
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Invalid Credentials");
            event = "/pages/login";
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        return event;
    }

    public static HttpSession getSession() {
        ExternalContext extCon = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extCon.getSession(true);
        return session;
    }

    public String logout() {
        getSession().invalidate();
        return "/pages/login";
    }
}
