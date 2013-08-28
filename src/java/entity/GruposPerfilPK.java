/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author EDMUNDO
 */
@Embeddable
public class GruposPerfilPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_grupo")
    private int idGrupo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_perfil")
    private int idPerfil;

    public GruposPerfilPK() {
    }

    public GruposPerfilPK(int idGrupo, int idPerfil) {
        this.idGrupo = idGrupo;
        this.idPerfil = idPerfil;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idGrupo;
        hash += (int) idPerfil;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GruposPerfilPK)) {
            return false;
        }
        GruposPerfilPK other = (GruposPerfilPK) object;
        if (this.idGrupo != other.idGrupo) {
            return false;
        }
        if (this.idPerfil != other.idPerfil) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GruposPerfilPK[ idGrupo=" + idGrupo + ", idPerfil=" + idPerfil + " ]";
    }
    
}
