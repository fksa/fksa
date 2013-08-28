/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Venta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.Constantes;

/**
 *
 * @author FKSA
 */
@Stateless
public class VentaFacade extends AbstractFacade<Venta> {
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VentaFacade() {
        super(Venta.class);
    }

    public List<Venta> findAllVentaCanales()
    {
        Query query = em.createNamedQuery("Venta.findByTipo");
        query.setParameter("tipo", Constantes.VENTA_EN_CANAL);
        return query.getResultList();
    }    
    
}
