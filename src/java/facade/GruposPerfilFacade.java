/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.GruposPerfil;
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
public class GruposPerfilFacade {
    @PersistenceContext(unitName = "FrigoCaguan")
    private EntityManager em;

    public void create(GruposPerfil gruposPerfil) {
        em.persist(gruposPerfil);
    }

    public void edit(GruposPerfil gruposPerfil) {
        em.merge(gruposPerfil);
    }

    public void remove(GruposPerfil gruposPerfil) {
        em.remove(em.merge(gruposPerfil));
    }

    public GruposPerfil find(Object id) {
        return em.find(GruposPerfil.class, id);
    }

    public List<GruposPerfil> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(GruposPerfil.class));
        return em.createQuery(cq).getResultList();
    }

    public List<GruposPerfil> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(GruposPerfil.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<GruposPerfil> rt = cq.from(GruposPerfil.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
