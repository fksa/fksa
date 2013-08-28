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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Edmundo
 */
@Entity
@Table(name = "sacrificio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sacrificio.findAll", query = "SELECT s FROM Sacrificio s"),
    @NamedQuery(name = "Sacrificio.findById", query = "SELECT s FROM Sacrificio s WHERE s.id = :id"),
    @NamedQuery(name = "Sacrificio.findByPesoSacrificio", query = "SELECT s FROM Sacrificio s WHERE s.pesoSacrificio = :pesoSacrificio"),
    @NamedQuery(name = "Sacrificio.findByPesoCanalCaliente", query = "SELECT s FROM Sacrificio s WHERE s.pesoCanalCaliente = :pesoCanalCaliente"),
    @NamedQuery(name = "Sacrificio.findByPesoVisceras", query = "SELECT s FROM Sacrificio s WHERE s.pesoVisceras = :pesoVisceras"),
    @NamedQuery(name = "Sacrificio.findByPesoPiel", query = "SELECT s FROM Sacrificio s WHERE s.pesoPiel = :pesoPiel"),
    @NamedQuery(name = "Sacrificio.findByPesoSebo", query = "SELECT s FROM Sacrificio s WHERE s.pesoSebo = :pesoSebo"),
    @NamedQuery(name = "Sacrificio.findByPesoCanalFrio", query = "SELECT s FROM Sacrificio s WHERE s.pesoCanalFrio = :pesoCanalFrio"),
    @NamedQuery(name = "Sacrificio.findByPesoCanalEntrega", query = "SELECT s FROM Sacrificio s WHERE s.pesoCanalEntrega = :pesoCanalEntrega"),
    @NamedQuery(name = "Sacrificio.findByObservaciones", query = "SELECT s FROM Sacrificio s WHERE s.observaciones = :observaciones")})
public class Sacrificio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso_sacrificio")
    private int pesoSacrificio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso_canal_caliente")
    private int pesoCanalCaliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso_visceras")
    private int pesoVisceras;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso_piel")
    private int pesoPiel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso_sebo")
    private int pesoSebo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso_canal_frio")
    private int pesoCanalFrio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso_canal_entrega")
    private int pesoCanalEntrega;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_animal", referencedColumnName = "id", insertable = true, updatable = false)
    @OneToOne(optional = false)
    private Animal animal;

    public Sacrificio(Animal animal) {
        this.animal = animal;
        this.pesoSacrificio = 0;
        this.pesoCanalCaliente = 0;
        this.pesoVisceras = 0;
        this.pesoPiel = 0;
        this.pesoSebo = 0;
        this.pesoCanalFrio = 0;
        this.pesoCanalEntrega = 0;
        this.observaciones = " ";
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Sacrificio() {
    }

    public Sacrificio(Integer id) {
        this.id = id;
    }

    public Sacrificio(Integer id, int pesoSacrificio, int pesoCanalCaliente, int pesoVisceras, int pesoPiel, int pesoSebo, int pesoCanalFrio, int pesoCanalEntrega, String observaciones) {
        this.id = id;
        this.pesoSacrificio = pesoSacrificio;
        this.pesoCanalCaliente = pesoCanalCaliente;
        this.pesoVisceras = pesoVisceras;
        this.pesoPiel = pesoPiel;
        this.pesoSebo = pesoSebo;
        this.pesoCanalFrio = pesoCanalFrio;
        this.pesoCanalEntrega = pesoCanalEntrega;
        this.observaciones = observaciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPesoSacrificio() {
        return pesoSacrificio;
    }

    public void setPesoSacrificio(int pesoSacrificio) {
        this.pesoSacrificio = pesoSacrificio;
    }

    public int getPesoCanalCaliente() {
        return pesoCanalCaliente;
    }

    public void setPesoCanalCaliente(int pesoCanalCaliente) {
        this.pesoCanalCaliente = pesoCanalCaliente;
    }

    public int getPesoVisceras() {
        return pesoVisceras;
    }

    public void setPesoVisceras(int pesoVisceras) {
        this.pesoVisceras = pesoVisceras;
    }

    public int getPesoPiel() {
        return pesoPiel;
    }

    public void setPesoPiel(int pesoPiel) {
        this.pesoPiel = pesoPiel;
    }

    public int getPesoSebo() {
        return pesoSebo;
    }

    public void setPesoSebo(int pesoSebo) {
        this.pesoSebo = pesoSebo;
    }

    public int getPesoCanalFrio() {
        return pesoCanalFrio;
    }

    public void setPesoCanalFrio(int pesoCanalFrio) {
        this.pesoCanalFrio = pesoCanalFrio;
    }

    public int getPesoCanalEntrega() {
        return pesoCanalEntrega;
    }

    public void setPesoCanalEntrega(int pesoCanalEntrega) {
        this.pesoCanalEntrega = pesoCanalEntrega;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        if (!(object instanceof Sacrificio)) {
            return false;
        }
        Sacrificio other = (Sacrificio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Sacrificio[ id=" + id + " ]";
    }
    
}
