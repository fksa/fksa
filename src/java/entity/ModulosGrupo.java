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
@Table(name = "modulos_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModulosGrupo.findAll", query = "SELECT m FROM ModulosGrupo m"),
    @NamedQuery(name = "ModulosGrupo.findByIdGrupo", query = "SELECT m FROM ModulosGrupo m WHERE m.modulosGrupoPK.idGrupo = :idGrupo"),
    @NamedQuery(name = "ModulosGrupo.findByIdModulo", query = "SELECT m FROM ModulosGrupo m WHERE m.modulosGrupoPK.idModulo = :idModulo")})
public class ModulosGrupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ModulosGrupoPK modulosGrupoPK;
    @JoinColumn(name = "id_modulo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Modulo modulo;
    @JoinColumn(name = "id_grupo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Grupo grupo;

    public ModulosGrupo() {
    }

    public ModulosGrupo(ModulosGrupoPK modulosGrupoPK) {
        this.modulosGrupoPK = modulosGrupoPK;
    }

    public ModulosGrupo(Grupo idGrupo, Modulo idModulo) {
        this.modulosGrupoPK = new ModulosGrupoPK(idGrupo.getId(), idModulo.getId());
        this.grupo = idGrupo;
        this.modulo = idModulo;
    }

    public ModulosGrupo(int idGrupo, int idModulo) {
        this.modulosGrupoPK = new ModulosGrupoPK(idGrupo, idModulo);
    }

    public ModulosGrupoPK getModulosGrupoPK() {
        return modulosGrupoPK;
    }

    public void setModulosGrupoPK(ModulosGrupoPK modulosGrupoPK) {
        this.modulosGrupoPK = modulosGrupoPK;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
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
        hash += (modulosGrupoPK != null ? modulosGrupoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModulosGrupo)) {
            return false;
        }
        ModulosGrupo other = (ModulosGrupo) object;
        if ((this.modulosGrupoPK == null && other.modulosGrupoPK != null) || (this.modulosGrupoPK != null && !this.modulosGrupoPK.equals(other.modulosGrupoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ModulosGrupo[ modulosGrupoPK=" + modulosGrupoPK + " ]";
    }
    
}
