/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FKSA
 */
@Entity
@Table(name = "pago_compra_venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoCompraVenta.findAll", query = "SELECT p FROM PagoCompraVenta p"),
    @NamedQuery(name = "PagoCompraVenta.findById", query = "SELECT p FROM PagoCompraVenta p WHERE p.id = :id"),
    @NamedQuery(name = "PagoCompraVenta.findByValor", query = "SELECT p FROM PagoCompraVenta p WHERE p.valor = :valor"),
    @NamedQuery(name = "PagoCompraVenta.findByObservaciones", query = "SELECT p FROM PagoCompraVenta p WHERE p.observaciones = :observaciones")})
public class PagoCompraVenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private int valor;
    @Size(max = 100)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_compra", referencedColumnName = "id")
    @ManyToOne
    private Compra idCompra;
    @JoinColumn(name = "id_venta", referencedColumnName = "id")
    @ManyToOne
    private Venta idVenta;

    public PagoCompraVenta(Compra compra) {
        this.idCompra = compra;
        this.fecha = new Date();
        this.valor = compra.getSaldo();
    }
    
    public PagoCompraVenta(Venta venta) {
        this.idVenta = venta;
        this.fecha = new Date();
        this.valor = venta.getSaldo();
    }

    public PagoCompraVenta() {
    }

    public PagoCompraVenta(Integer id) {
        this.id = id;
    }

    public PagoCompraVenta(Integer id, int valor, String observaciones) {
        this.id = id;
        this.valor = valor;
        this.observaciones = observaciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Compra getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Compra idCompra) {
        this.idCompra = idCompra;
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
        if (!(object instanceof PagoCompraVenta)) {
            return false;
        }
        PagoCompraVenta other = (PagoCompraVenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PagoCompraVenta[ id=" + id + " ]";
    }
    
}
