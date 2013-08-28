/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author FKSA
 */
@Entity
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c ORDER BY c.id DESC"),
    @NamedQuery(name = "Compra.findById", query = "SELECT c FROM Compra c WHERE c.id = :id"),
    @NamedQuery(name = "Compra.findByFechaCompra", query = "SELECT c FROM Compra c WHERE c.fechaCompra = :fechaCompra"),
    @NamedQuery(name = "Compra.findByValor", query = "SELECT c FROM Compra c WHERE c.valor = :valor"),
    @NamedQuery(name = "Compra.findBySaldo", query = "SELECT c FROM Compra c WHERE c.saldo = :saldo"),
    @NamedQuery(name = "Compra.findByFechaSaldo", query = "SELECT c FROM Compra c WHERE c.fechaSaldo = :fechaSaldo"),
    @NamedQuery(name = "Compra.findByObservaciones", query = "SELECT c FROM Compra c WHERE c.observaciones = :observaciones")})
public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_compra")
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private int valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "saldo")
    private int saldo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_saldo")
    @Temporal(TemporalType.DATE)
    private Date fechaSaldo;
    @Size(max = 100)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_vendedor", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vendedor idVendedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCompra")
    private List<PagoCompraVenta> pagoCompraVentaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCompra")
    private List<Animal> animalList;

    public Compra() {
    }

    public Compra(Integer id) {
        this.id = id;
    }

    public Compra(Integer id, Date fechaCompra, int valor, int saldo, Date fechaSaldo) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.valor = valor;
        this.saldo = saldo;
        this.fechaSaldo = fechaSaldo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Date getFechaSaldo() {
        return fechaSaldo;
    }

    public void setFechaSaldo(Date fechaSaldo) {
        this.fechaSaldo = fechaSaldo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Vendedor getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Vendedor idVendedor) {
        this.idVendedor = idVendedor;
    }

    @XmlTransient
    public List<PagoCompraVenta> getPagoCompraVentaList() {
        return pagoCompraVentaList;
    }

    public void setPagoCompraVentaList(List<PagoCompraVenta> pagoCompraVentaList) {
        this.pagoCompraVentaList = pagoCompraVentaList;
    }

    @XmlTransient
    public List<Animal> getAnimalList() {
        return animalList;
    }

    public void setAnimalList(List<Animal> animalList) {
        this.animalList = animalList;
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
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.idVendedor + " :: " + this.id;
    }

    public void addAnimal(Animal animal) {
        animalList.add(animal);
    }

    public void updateValores() {
        valor  = 0;
        for (Animal animal : this.animalList){
            valor += animal.getPesoCompra() * animal.getValorKgCompra();
        }
        saldo = valor;
        for (PagoCompraVenta pago : this.pagoCompraVentaList){
            saldo -= pago.getValor();
        }
    }

    public void addPago(PagoCompraVenta pago) {
        this.pagoCompraVentaList.add(pago);
    }

    public void removeAnimal(Animal animal) {
        animalList.remove(animal);
    }

    public void removePago(PagoCompraVenta pago) {
        pagoCompraVentaList.remove(pago);
    }
    
}
