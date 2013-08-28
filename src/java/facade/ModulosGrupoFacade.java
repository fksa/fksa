/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.ModulosGrupo;
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
public class ModulosGrupoFacade {
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    public void create(ModulosGrupo modulosGrupo) {
        em.persist(modulosGrupo);
    }

    public void edit(ModulosGrupo modulosGrupo) {
        em.merge(modulosGrupo);
    }

    public void remove(ModulosGrupo modulosGrupo) {
        em.remove(em.merge(modulosGrupo));
    }

    public ModulosGrupo find(Object id) {
        return em.find(ModulosGrupo.class, id);
    }

    public List<ModulosGrupo> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(ModulosGrupo.class));
        return em.createQuery(cq).getResultList();
    }

    public List<ModulosGrupo> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(ModulosGrupo.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<ModulosGrupo> rt = cq.from(ModulosGrupo.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
