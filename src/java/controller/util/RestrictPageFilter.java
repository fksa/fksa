/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

//import general.ConstantesVOM;
//import controller.ContratoController;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MER
 */
public class RestrictPageFilter implements Filter {

    FilterConfig fc;
    private String timeoutPage = "faces/pages/login.xhtml";

    public void init(FilterConfig filterConfig) throws ServletException {
        fc = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String pageRequested = req.getRequestURI().toString();
        if (session == null) {
            session = req.getSession(true); // will create a new session
            String timeoutUrl = req.getContextPath() + "/" + getTimeoutPage();
            resp.sendRedirect(timeoutUrl);
        } else if ((session.getAttribute("usuarioLogin") == null) && (!pageRequested.contains("login.xhtml"))) {
            String timeoutUrl = req.getContextPath() + "/" + getTimeoutPage();
            resp.sendRedirect(timeoutUrl);
        } else {
            /*
            if ( pageRequested.contains(ConstantesVOM.CONTRATO_MODULO_COMERCIALES) || pageRequested.contains(ConstantesVOM.CONTRATO_MODULO_GESTION) ){
                ContratoController contratoController = (ContratoController) session.getAttribute("contratoController");
                if (contratoController != null ){
                    contratoController.setPendientes(pageRequested.contains(ConstantesVOM.CONTRATO_MODULO_COMERCIALES));
                    contratoController.recreateModel();
                }
            }
            * */
            chain.doFilter(request, response); // continue filtering
        }        
    }

    public void destroy() {
    }

    public String getTimeoutPage() {
        return timeoutPage;
    }
}
