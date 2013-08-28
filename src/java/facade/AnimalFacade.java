/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Animal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author FKSA
 */
@Stateless
public class AnimalFacade extends AbstractFacade<Animal> {
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnimalFacade() {
        super(Animal.class);
    }

    public List<Animal> findAllDisp()
    {
        Query query = em.createNamedQuery("Animal.findAll");
        return query.getResultList();
    }    
    
}
