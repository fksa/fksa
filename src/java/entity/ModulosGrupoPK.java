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
public class ModulosGrupoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_grupo")
    private int idGrupo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_modulo")
    private int idModulo;

    public ModulosGrupoPK() {
    }

    public ModulosGrupoPK(int idGrupo, int idModulo) {
        this.idGrupo = idGrupo;
        this.idModulo = idModulo;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idGrupo;
        hash += (int) idModulo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModulosGrupoPK)) {
            return false;
        }
        ModulosGrupoPK other = (ModulosGrupoPK) object;
        if (this.idGrupo != other.idGrupo) {
            return false;
        }
        if (this.idModulo != other.idModulo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ModulosGrupoPK[ idGrupo=" + idGrupo + ", idModulo=" + idModulo + " ]";
    }
    
}
