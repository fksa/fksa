/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.Perfil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author EDMUNDO
 */
@Stateless
public class PerfilFacade extends AbstractFacade<Perfil>{
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PerfilFacade() {
        super(Perfil.class);
    }

}