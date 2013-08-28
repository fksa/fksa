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
 * @author janus
 */
@Entity
@Table(name = "pago_acciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoAcciones.findAll", query = "SELECT p FROM PagoAcciones p"),
    @NamedQuery(name = "PagoAcciones.findById", query = "SELECT p FROM PagoAcciones p WHERE p.id = :id"),
    @NamedQuery(name = "PagoAcciones.findByFecha", query = "SELECT p FROM PagoAcciones p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "PagoAcciones.findByNumero", query = "SELECT p FROM PagoAcciones p WHERE p.numero = :numero"),
    @NamedQuery(name = "PagoAcciones.findByValor", query = "SELECT p FROM PagoAcciones p WHERE p.valor = :valor"),
    @NamedQuery(name = "PagoAcciones.findByComisionBancaria", query = "SELECT p FROM PagoAcciones p WHERE p.comisionBancaria = :comisionBancaria"),
    @NamedQuery(name = "PagoAcciones.findByValorNeto", query = "SELECT p FROM PagoAcciones p WHERE p.valorNeto = :valorNeto"),
    @NamedQuery(name = "PagoAcciones.findByObservaciones", query = "SELECT p FROM PagoAcciones p WHERE p.observaciones = :observaciones")})
public class PagoAcciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 20)
    @Column(name = "numero")
    private String numero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private int valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "comision_bancaria")
    private int comisionBancaria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_neto")
    private int valorNeto;
    @Size(max = 40)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_accionista", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Accionista idAccionista;

    public PagoAcciones(Accionista accionista){
        this.idAccionista = accionista;
        this.fecha = new Date();
        this.valor = this.comisionBancaria = this.valorNeto = 0;
    }
    
    public PagoAcciones() {
    }

    public PagoAcciones(Integer id) {
        this.id = id;
    }

    public PagoAcciones(Integer id, Date fecha, int valor, int comisionBancaria, int valorNeto) {
        this.id = id;
        this.fecha = fecha;
        this.valor = valor;
        this.comisionBancaria = comisionBancaria;
        this.valorNeto = valorNeto;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getComisionBancaria() {
        return comisionBancaria;
    }

    public void setComisionBancaria(int comisionBancaria) {
        this.comisionBancaria = comisionBancaria;
    }

    public int getValorNeto() {
        return valorNeto;
    }

    public void setValorNeto(int valorNeto) {
        this.valorNeto = valorNeto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        if (!(object instanceof PagoAcciones)) {
            return false;
        }
        PagoAcciones other = (PagoAcciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PagoAcciones[ id=" + id + " ]";
    }

    public void update() {
        this.valorNeto = this.valor - this.comisionBancaria;
    }
    
}
