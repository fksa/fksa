/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FKSA
 */
@Entity
@Table(name = "animal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Animal.findAll", query = "SELECT a FROM Animal a WHERE a.idVenta IS NULL"),
    @NamedQuery(name = "Animal.findById", query = "SELECT a FROM Animal a WHERE a.id = :id"),
    @NamedQuery(name = "Animal.findByNumeroFksa", query = "SELECT a FROM Animal a WHERE a.numeroFksa = :numeroFksa"),
    @NamedQuery(name = "Animal.findByPesoCompra", query = "SELECT a FROM Animal a WHERE a.pesoCompra = :pesoCompra"),
    @NamedQuery(name = "Animal.findByValorKgCompra", query = "SELECT a FROM Animal a WHERE a.valorKgCompra = :valorKgCompra"),
    @NamedQuery(name = "Animal.findByValorTotalCompra", query = "SELECT a FROM Animal a WHERE a.valorTotalCompra = :valorTotalCompra"),
    @NamedQuery(name = "Animal.findByPesoVenta", query = "SELECT a FROM Animal a WHERE a.pesoVenta = :pesoVenta"),
    @NamedQuery(name = "Animal.findByValorKgVenta", query = "SELECT a FROM Animal a WHERE a.valorKgVenta = :valorKgVenta"),
    @NamedQuery(name = "Animal.findByValorTotalVenta", query = "SELECT a FROM Animal a WHERE a.valorTotalVenta = :valorTotalVenta")})
public class Animal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_fksa")
    private int numeroFksa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso_compra")
    private int pesoCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_kg_compra")
    private int valorKgCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_total_compra")
    private int valorTotalCompra;
    @Column(name = "peso_venta")
    private Integer pesoVenta;
    @Column(name = "valor_kg_venta")
    private Integer valorKgVenta;
    @Column(name = "valor_total_venta")
    private Integer valorTotalVenta;
    @JoinColumn(name = "id_venta", referencedColumnName = "id")
    @ManyToOne
    private Venta idVenta;
    @JoinColumn(name = "id_compra", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Compra idCompra;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoAnimal idTipo;
    //@JoinColumn(name = "id_animal", referencedColumnName = "id")
    //@OneToOne(cascade = CascadeType.ALL, optional = false)
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "animal")
    private Sacrificio sacrificio;

    public Sacrificio getSacrificio() {
        return sacrificio;
    }

    public void setSacrificio(Sacrificio sacrificio) {
        this.sacrificio = sacrificio;
    }

    public Animal(Compra compra) {
        this.idCompra = compra;
        this.numeroFksa = 0;
        this.pesoCompra = 400;
        this.valorKgCompra = 2000;
        this.valorTotalCompra = pesoCompra * valorKgCompra;
    }

    public Animal() {
    }

    public Animal(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroFksa() {
        return numeroFksa;
    }

    public void setNumeroFksa(int numeroFksa) {
        this.numeroFksa = numeroFksa;
    }

    public int getPesoCompra() {
        return pesoCompra;
    }

    public void setPesoCompra(int pesoCompra) {
        this.pesoCompra = pesoCompra;
    }

    public int getValorKgCompra() {
        return valorKgCompra;
    }

    public void setValorKgCompra(int valorKgCompra) {
        this.valorKgCompra = valorKgCompra;
    }

    public int getValorTotalCompra() {
        return valorTotalCompra;
    }

    public void setValorTotalCompra(int valorTotalCompra) {
        this.valorTotalCompra = valorTotalCompra;
    }

    public Integer getPesoVenta() {
        return pesoVenta;
    }

    public void setPesoVenta(Integer pesoVenta) {
        this.pesoVenta = pesoVenta;
    }

    public Integer getValorKgVenta() {
        return valorKgVenta;
    }

    public void setValorKgVenta(Integer valorKgVenta) {
        this.valorKgVenta = valorKgVenta;
    }

    public Integer getValorTotalVenta() {
        return valorTotalVenta;
    }

    public void setValorTotalVenta(Integer valorTotalVenta) {
        this.valorTotalVenta = valorTotalVenta;
    }

    public Venta getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Venta idVenta) {
        this.idVenta = idVenta;
    }

    public Compra getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Compra idCompra) {
        this.idCompra = idCompra;
    }

    public TipoAnimal getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoAnimal idTipo) {
        this.idTipo = idTipo;
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
        if (!(object instanceof Animal)) {
            return false;
        }
        Animal other = (Animal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.idTipo + " :: " + this.numeroFksa;
    }

    public void updateTotal() {
        valorTotalCompra  = pesoCompra * valorKgCompra;
    }

    public void updateTotalVenta() {
        valorTotalVenta  = pesoVenta * valorKgVenta;
    }

    public void addSacrificio() {
        this.sacrificio = new Sacrificio(this);
    }

}
