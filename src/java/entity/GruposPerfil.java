/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EDMUNDO
 */
@Entity
@Table(name = "grupos_perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GruposPerfil.findAll", query = "SELECT g FROM GruposPerfil g"),
    @NamedQuery(name = "GruposPerfil.findByIdGrupo", query = "SELECT g FROM GruposPerfil g WHERE g.gruposPerfilPK.idGrupo = :idGrupo"),
    @NamedQuery(name = "GruposPerfil.findByIdPerfil", query = "SELECT g FROM GruposPerfil g WHERE g.gruposPerfilPK.idPerfil = :idPerfil")})
public class GruposPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GruposPerfilPK gruposPerfilPK;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Perfil perfil;
    @JoinColumn(name = "id_grupo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Grupo grupo;

    public GruposPerfil() {
    }

    public GruposPerfil(Grupo idGrupo, Perfil idPerfil) {
        this.gruposPerfilPK = new GruposPerfilPK(idGrupo.getId(), idPerfil.getId());
        this.grupo = idGrupo;
        this.perfil = idPerfil;
    }

    public GruposPerfil(GruposPerfilPK gruposPerfilPK) {
        this.gruposPerfilPK = gruposPerfilPK;
    }

    public GruposPerfil(int idGrupo, int idPerfil) {
        this.gruposPerfilPK = new GruposPerfilPK(idGrupo, idPerfil);
    }

    public GruposPerfilPK getGruposPerfilPK() {
        return gruposPerfilPK;
    }

    public void setGruposPerfilPK(GruposPerfilPK gruposPerfilPK) {
        this.gruposPerfilPK = gruposPerfilPK;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gruposPerfilPK != null ? gruposPerfilPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GruposPerfil)) {
            return false;
        }
        GruposPerfil other = (GruposPerfil) object;
        if ((this.gruposPerfilPK == null && other.gruposPerfilPK != null) || (this.gruposPerfilPK != null && !this.gruposPerfilPK.equals(other.gruposPerfilPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GruposPerfil[ gruposPerfilPK=" + gruposPerfilPK + " ]";
    }
    
}
