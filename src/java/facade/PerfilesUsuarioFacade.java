/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.PerfilesUsuario;
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
public class PerfilesUsuarioFacade {
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    public void create(PerfilesUsuario perfilesUsuario) {
        em.persist(perfilesUsuario);
    }

    public void edit(PerfilesUsuario perfilesUsuario) {
        em.merge(perfilesUsuario);
    }

    public void remove(PerfilesUsuario perfilesUsuario) {
        em.remove(em.merge(perfilesUsuario));
    }

    public PerfilesUsuario find(Object id) {
        return em.find(PerfilesUsuario.class, id);
    }

    public List<PerfilesUsuario> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(PerfilesUsuario.class));
        return em.createQuery(cq).getResultList();
    }

    public List<PerfilesUsuario> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(PerfilesUsuario.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<PerfilesUsuario> rt = cq.from(PerfilesUsuario.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
