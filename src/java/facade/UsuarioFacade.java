/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author EDMUNDO
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario>{
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario find(String usuario) {
        Query cons = em.createNamedQuery("Usuario.findByUsuario");
        cons.setParameter("usuario", usuario);
        Object obj = cons.getSingleResult();
        return (Usuario)obj;
    }
    
}
