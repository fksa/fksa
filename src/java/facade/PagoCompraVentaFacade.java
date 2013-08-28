/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.PagoCompraVenta;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author FKSA
 */
@Stateless
public class PagoCompraVentaFacade extends AbstractFacade<PagoCompraVenta> {
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagoCompraVentaFacade() {
        super(PagoCompraVenta.class);
    }
    
}
