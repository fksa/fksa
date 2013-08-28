/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.Grupo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author EDMUNDO
 */
@Stateless
public class GrupoFacade extends AbstractFacade<Grupo>{
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoFacade() {
        super(Grupo.class);
    }

}
