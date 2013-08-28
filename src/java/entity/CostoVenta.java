/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FKSA
 */
@Entity
@Table(name = "costo_venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CostoVenta.findAll", query = "SELECT c FROM CostoVenta c"),
    @NamedQuery(name = "CostoVenta.findById", query = "SELECT c FROM CostoVenta c WHERE c.id = :id"),
    @NamedQuery(name = "CostoVenta.findByValorUnitario", query = "SELECT c FROM CostoVenta c WHERE c.valorUnitario = :valorUnitario"),
    @NamedQuery(name = "CostoVenta.findByUnidades", query = "SELECT c FROM CostoVenta c WHERE c.unidades = :unidades"),
    @NamedQuery(name = "CostoVenta.findByValorTotal", query = "SELECT c FROM CostoVenta c WHERE c.valorTotal = :valorTotal"),
    @NamedQuery(name = "CostoVenta.findByObservaciones", query = "SELECT c FROM CostoVenta c WHERE c.observaciones = :observaciones")})
public class CostoVenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_unitario")
    private int valorUnitario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "unidades")
    private int unidades;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_total")
    private int valorTotal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoCosto idTipo;
    @JoinColumn(name = "id_venta", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Venta idVenta;

    public CostoVenta(Venta venta) {
        this.idVenta = venta;
        this.valorUnitario = 0;
        this.unidades = 1;
        this.valorTotal = 0;
        this.observaciones = " ";
    }

    public CostoVenta() {
    }

    public CostoVenta(Integer id) {
        this.id = id;
    }

    public CostoVenta(Integer id, int valorUnitario, int unidades, int valorTotal, String observaciones) {
        this.id = id;
        this.valorUnitario = valorUnitario;
        this.unidades = unidades;
        this.valorTotal = valorTotal;
        this.observaciones = observaciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(int valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public TipoCosto getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoCosto idTipo) {
        this.idTipo = idTipo;
    }

    public Venta getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Venta idVenta) {
        this.idVenta = idVenta;
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
        if (!(object instanceof CostoVenta)) {
            return false;
        }
        CostoVenta other = (CostoVenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CostoVenta[ id=" + id + " ]";
    }

    public void updateTotal() {
        valorTotal  = unidades * valorUnitario;
    }
    
}
