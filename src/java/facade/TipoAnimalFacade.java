/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.TipoAnimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author FKSA
 */
@Stateless
public class TipoAnimalFacade extends AbstractFacade<TipoAnimal> {
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoAnimalFacade() {
        super(TipoAnimal.class);
    }
    
}
