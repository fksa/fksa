/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author janus
 */
@Entity
@Table(name = "contrato_suscripcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratoSuscripcion.findAll", query = "SELECT c FROM ContratoSuscripcion c"),
    @NamedQuery(name = "ContratoSuscripcion.findById", query = "SELECT c FROM ContratoSuscripcion c WHERE c.id = :id"),
    @NamedQuery(name = "ContratoSuscripcion.findByAccionesSuscritas", query = "SELECT c FROM ContratoSuscripcion c WHERE c.accionesSuscritas = :accionesSuscritas")})
public class ContratoSuscripcion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "acciones_suscritas")
    private int accionesSuscritas;
    @JoinColumn(name = "id_accionista", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Accionista idAccionista;

    public ContratoSuscripcion() {
    }

    public ContratoSuscripcion(Integer id) {
        this.id = id;
    }

    public ContratoSuscripcion(Integer id, int accionesSuscritas) {
        this.id = id;
        this.accionesSuscritas = accionesSuscritas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAccionesSuscritas() {
        return accionesSuscritas;
    }

    public void setAccionesSuscritas(int accionesSuscritas) {
        this.accionesSuscritas = accionesSuscritas;
    }

    public Accionista getIdAccionista() {
        return idAccionista;
    }

    public void setIdAccionista(Accionista idAccionista) {
        this.idAccionista = idAccionista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContratoSuscripcion)) {
            return false;
        }
        ContratoSuscripcion other = (ContratoSuscripcion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ContratoSuscripcion[ id=" + id + " ]";
    }
    
}
