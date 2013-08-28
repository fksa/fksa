/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import util.Constantes;

/**
 *
 * @author FKSA
 */
@Entity
@Table(name = "venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
    @NamedQuery(name = "Venta.findById", query = "SELECT v FROM Venta v WHERE v.id = :id"),
    @NamedQuery(name = "Venta.findByFechaVenta", query = "SELECT v FROM Venta v WHERE v.fechaVenta = :fechaVenta"),
    @NamedQuery(name = "Venta.findByTipo", query = "SELECT v FROM Venta v WHERE v.tipo = :tipo"),
    @NamedQuery(name = "Venta.findByTotalVenta", query = "SELECT v FROM Venta v WHERE v.totalVenta = :totalVenta"),
    @NamedQuery(name = "Venta.findByTotalCosto", query = "SELECT v FROM Venta v WHERE v.totalCosto = :totalCosto"),
    @NamedQuery(name = "Venta.findByUtilidad", query = "SELECT v FROM Venta v WHERE v.utilidad = :utilidad"),
    @NamedQuery(name = "Venta.findBySaldo", query = "SELECT v FROM Venta v WHERE v.saldo = :saldo"),
    @NamedQuery(name = "Venta.findByFechaSaldo", query = "SELECT v FROM Venta v WHERE v.fechaSaldo = :fechaSaldo"),
    @NamedQuery(name = "Venta.findByObservaciones", query = "SELECT v FROM Venta v WHERE v.observaciones = :observaciones")})
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_venta")
    @Temporal(TemporalType.DATE)
    private Date fechaVenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_venta")
    private int totalVenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_costo")
    private int totalCosto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "utilidad")
    private int utilidad;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVenta")
    private List<CostoVenta> costoVentaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVenta")
    private List<ProductoVenta> productoVentaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVenta")
    private List<PagoCompraVenta> pagoCompraVentaList;
    @JoinColumn(name = "id_comprador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Comprador idComprador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVenta")
    private List<Animal> animalList;
    @Transient
    private int costoAnimales = -1;

    public int getCostoAnimales() {
        if (costoAnimales < 0 && animalList != null){
            costoAnimales = 0;
            for (Animal animal : this.animalList) {
                costoAnimales += animal.getValorTotalCompra();
            }
        }
        return costoAnimales;
    }

    public Venta() {
    }

    public Venta(Integer id) {
        this.id = id;
    }

    public Venta(Integer id, Date fechaVenta, String tipo, int totalVenta, int totalCosto, int utilidad, int saldo, Date fechaSaldo) {
        this.id = id;
        this.fechaVenta = fechaVenta;
        this.tipo = tipo;
        this.totalVenta = totalVenta;
        this.totalCosto = totalCosto;
        this.utilidad = utilidad;
        this.saldo = saldo;
        this.fechaSaldo = fechaSaldo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(int totalVenta) {
        this.totalVenta = totalVenta;
    }

    public int getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(int totalCosto) {
        this.totalCosto = totalCosto;
    }

    public int getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(int utilidad) {
        this.utilidad = utilidad;
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

    @XmlTransient
    public List<CostoVenta> getCostoVentaList() {
        return costoVentaList;
    }

    public void setCostoVentaList(List<CostoVenta> costoVentaList) {
        this.costoVentaList = costoVentaList;
    }

    @XmlTransient
    public List<ProductoVenta> getProductoVentaList() {
        return productoVentaList;
    }

    public void setProductoVentaList(List<ProductoVenta> productoVentaList) {
        this.productoVentaList = productoVentaList;
    }

    @XmlTransient
    public List<PagoCompraVenta> getPagoCompraVentaList() {
        return pagoCompraVentaList;
    }

    public void setPagoCompraVentaList(List<PagoCompraVenta> pagoCompraVentaList) {
        this.pagoCompraVentaList = pagoCompraVentaList;
    }

    public Comprador getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(Comprador idComprador) {
        this.idComprador = idComprador;
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
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return (idComprador != null ) ? idComprador.getNombre() + " :: " + this.getId() : "";
    }

    public void updateValores() {
        costoAnimales = 0;
        totalVenta  = 0;
        totalCosto  = 0;
        for (Animal animal : this.animalList){
            totalVenta += animal.getValorTotalVenta();
            costoAnimales += animal.getValorTotalCompra();
        }
        totalCosto += costoAnimales;
        for (CostoVenta costo : this.costoVentaList){
            totalCosto += costo.getValorTotal();
        }
        for (ProductoVenta producto : this.productoVentaList){
            totalVenta += producto.getValorTotal();
        }
        utilidad = totalVenta - totalCosto;
        saldo = totalVenta;
        for (PagoCompraVenta pago : this.pagoCompraVentaList){
            saldo -= pago.getValor();
        }
    }

    public void addPago(PagoCompraVenta pago) {
        this.pagoCompraVentaList.add(pago);
    }

    public void addAnimales(Animal[] animalesArray) {
        for (Animal animal : animalesArray){
            animal.setIdVenta(this);
            if (this.tipo.equals(Constantes.VENTA_EN_PIE)){
                animal.setPesoVenta(animal.getPesoCompra());
                animal.setValorKgVenta(animal.getValorKgCompra()+100);
            }
            else{
                animal.setPesoVenta(0);
                animal.setValorKgVenta(0);
            }
            animal.updateTotalVenta();
        }
    }
    
    public void addAnimal(Animal animal) {
        animal.setIdVenta(this);
        if (this.tipo.equals(Constantes.VENTA_EN_PIE)){
            animal.setPesoVenta(animal.getPesoCompra());
            animal.setValorKgVenta(animal.getValorKgCompra()+100);
        }
        else{
            animal.setPesoVenta(0);
            animal.setValorKgVenta(0);
        }
        animal.updateTotalVenta();
        this.animalList.add(animal);
    }
    
    public boolean addSacrificio(){
        boolean ret = (animalList.isEmpty() || animalList.get(0).getSacrificio()==null);
        System.out.println(ret);
        return ret;
        //return (animalList.isEmpty() || animalList.get(0).getSacrificio()==null);
    }

    public void addProducto(ProductoVenta producto) {
        this.productoVentaList.add(producto);
    }

    public void addCosto(CostoVenta costo) {
        this.costoVentaList.add(costo);
    }

    public void removeAnimal(Animal animal) {
        animalList.remove(animal);
    }

    public void removePago(PagoCompraVenta pago) {
        pagoCompraVentaList.remove(pago);
    }

    public void removeCosto(CostoVenta costo) {
        this.costoVentaList.remove(costo);
    }
    
    public void removeProducto(ProductoVenta producto){
        this.productoVentaList.remove(producto);
    }
    
    public int getSemana() {
        if (fechaVenta == null) return -1;
        Calendar c = Calendar.getInstance();
        c.setTime(fechaVenta);
        return c.get( Calendar.WEEK_OF_YEAR );
    }

}
