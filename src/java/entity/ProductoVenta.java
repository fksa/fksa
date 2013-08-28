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
@Table(name = "producto_venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoVenta.findAll", query = "SELECT p FROM ProductoVenta p"),
    @NamedQuery(name = "ProductoVenta.findById", query = "SELECT p FROM ProductoVenta p WHERE p.id = :id"),
    @NamedQuery(name = "ProductoVenta.findByValorUnitario", query = "SELECT p FROM ProductoVenta p WHERE p.valorUnitario = :valorUnitario"),
    @NamedQuery(name = "ProductoVenta.findByUnidades", query = "SELECT p FROM ProductoVenta p WHERE p.unidades = :unidades"),
    @NamedQuery(name = "ProductoVenta.findByValorTotal", query = "SELECT p FROM ProductoVenta p WHERE p.valorTotal = :valorTotal"),
    @NamedQuery(name = "ProductoVenta.findByObservaciones", query = "SELECT p FROM ProductoVenta p WHERE p.observaciones = :observaciones")})
public class ProductoVenta implements Serializable {
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
    private double unidades;
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
    private TipoProducto idTipo;
    @JoinColumn(name = "id_venta", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Venta idVenta;

    public ProductoVenta(Venta venta) {
        this.idVenta = venta;
        this.valorUnitario = 0;
        this.unidades = 1;
        this.valorTotal = 0;
        this.observaciones = " ";
    }

    public ProductoVenta() {
    }

    public ProductoVenta(Integer id) {
        this.id = id;
    }

    public ProductoVenta(Integer id, int valorUnitario, int unidades, int valorTotal, String observaciones) {
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

    public double getUnidades() {
        return unidades;
    }

    public void setUnidades(double unidades) {
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

    public TipoProducto getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoProducto idTipo) {
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
        if (!(object instanceof ProductoVenta)) {
            return false;
        }
        ProductoVenta other = (ProductoVenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductoVenta[ id=" + id + " ]";
    }

    public void updateTotal() {
        System.out.println(unidades + " X " + valorUnitario + " = " + valorTotal);
        valorTotal = (int) (unidades * (double) valorUnitario);
        System.out.println(unidades + " X " + valorUnitario + " = " + valorTotal);
    }
    
}
