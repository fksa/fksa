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
@Table(name = "perfiles_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PerfilesUsuario.findAll", query = "SELECT p FROM PerfilesUsuario p"),
    @NamedQuery(name = "PerfilesUsuario.findByIdUsuario", query = "SELECT p FROM PerfilesUsuario p WHERE p.perfilesUsuarioPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "PerfilesUsuario.findByIdPerfil", query = "SELECT p FROM PerfilesUsuario p WHERE p.perfilesUsuarioPK.idPerfil = :idPerfil")})
public class PerfilesUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PerfilesUsuarioPK perfilesUsuarioPK;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Perfil perfil;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public PerfilesUsuario() {
    }

    public PerfilesUsuario(PerfilesUsuarioPK perfilesUsuarioPK) {
        this.perfilesUsuarioPK = perfilesUsuarioPK;
    }

    public PerfilesUsuario(int idUsuario, int idPerfil) {
        this.perfilesUsuarioPK = new PerfilesUsuarioPK(idUsuario, idPerfil);
    }

    public PerfilesUsuario(Usuario idUsuario, Perfil idPerfil) {
        this.perfilesUsuarioPK = new PerfilesUsuarioPK(idUsuario.getId(), idPerfil.getId());
        this.usuario = idUsuario;
        this.perfil = idPerfil;
    }

    public PerfilesUsuarioPK getPerfilesUsuarioPK() {
        return perfilesUsuarioPK;
    }

    public void setPerfilesUsuarioPK(PerfilesUsuarioPK perfilesUsuarioPK) {
        this.perfilesUsuarioPK = perfilesUsuarioPK;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perfilesUsuarioPK != null ? perfilesUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerfilesUsuario)) {
            return false;
        }
        PerfilesUsuario other = (PerfilesUsuario) object;
        if ((this.perfilesUsuarioPK == null && other.perfilesUsuarioPK != null) || (this.perfilesUsuarioPK != null && !this.perfilesUsuarioPK.equals(other.perfilesUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PerfilesUsuario[ perfilesUsuarioPK=" + perfilesUsuarioPK + " ]";
    }
    
}
