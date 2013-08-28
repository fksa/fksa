/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.ContratoSuscripcion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author janus
 */
@Stateless
public class ContratoSuscripcionFacade extends AbstractFacade<ContratoSuscripcion> {
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContratoSuscripcionFacade() {
        super(ContratoSuscripcion.class);
    }
    
}
