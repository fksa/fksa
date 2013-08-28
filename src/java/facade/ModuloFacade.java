/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.Modulo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author EDMUNDO
 */
@Stateless
public class ModuloFacade {
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    public void create(Modulo modulo) {
        em.persist(modulo);
    }

    public void edit(Modulo modulo) {
        em.merge(modulo);
    }

    public void remove(Modulo modulo) {
        em.remove(em.merge(modulo));
    }

    public Modulo find(Object id) {
        return em.find(Modulo.class, id);
    }

    public List<Modulo> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Modulo.class));
        return em.createQuery(cq).getResultList();
    }

    public List<Modulo> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Modulo.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Modulo> rt = cq.from(Modulo.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
