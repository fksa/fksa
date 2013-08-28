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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author janus
 */
@Entity
@Table(name = "titulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Titulo.findAll", query = "SELECT t FROM Titulo t"),
    @NamedQuery(name = "Titulo.findById", query = "SELECT t FROM Titulo t WHERE t.id = :id"),
    @NamedQuery(name = "Titulo.findByNumeroAcciones", query = "SELECT t FROM Titulo t WHERE t.numeroAcciones = :numeroAcciones"),
    @NamedQuery(name = "Titulo.findByTituloNumero", query = "SELECT t FROM Titulo t WHERE t.tituloNumero = :tituloNumero")})
public class Titulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_acciones")
    private int numeroAcciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "titulo_numero")
    private int tituloNumero;
    @JoinColumn(name = "id_accionista", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Accionista idAccionista;
    @JoinColumn(name = "id_sas", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sas idSas;

    public Titulo(Accionista accionista, Sas sas){
        this.idAccionista = accionista;
        this.numeroAcciones = (accionista.totalPagos() - accionista.totalTitulos() + 1000) / 2000;
        this.idSas = sas;
    }
    
    public Titulo() {
    }

    public Titulo(Integer id) {
        this.id = id;
    }

    public Titulo(Integer id, int numeroAcciones, int tituloNumero) {
        this.id = id;
        this.numeroAcciones = numeroAcciones;
        this.tituloNumero = tituloNumero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroAcciones() {
        return numeroAcciones;
    }

    public void setNumeroAcciones(int numeroAcciones) {
        this.numeroAcciones = numeroAcciones;
    }

    public int getTituloNumero() {
        return tituloNumero;
    }

    public void setTituloNumero(int tituloNumero) {
        this.tituloNumero = tituloNumero;
    }

    public Accionista getIdAccionista() {
        return idAccionista;
    }

    public void setIdAccionista(Accionista idAccionista) {
        this.idAccionista = idAccionista;
    }

    public Sas getIdSas() {
        return idSas;
    }

    public void setIdSas(Sas idSas) {
        this.idSas = idSas;
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
        if (!(object instanceof Titulo)) {
            return false;
        }
        Titulo other = (Titulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Titulo[ id=" + id + " ]";
    }

}
