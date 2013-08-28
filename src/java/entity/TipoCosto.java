/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author FKSA
 */
@Entity
@Table(name = "tipo_costo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCosto.findAll", query = "SELECT t FROM TipoCosto t"),
    @NamedQuery(name = "TipoCosto.findById", query = "SELECT t FROM TipoCosto t WHERE t.id = :id"),
    @NamedQuery(name = "TipoCosto.findByNombre", query = "SELECT t FROM TipoCosto t WHERE t.nombre = :nombre")})
public class TipoCosto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipo")
    private List<CostoVenta> costoVentaList;

    public TipoCosto() {
    }

    public TipoCosto(Integer id) {
        this.id = id;
    }

    public TipoCosto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<CostoVenta> getCostoVentaList() {
        return costoVentaList;
    }

    public void setCostoVentaList(List<CostoVenta> costoVentaList) {
        this.costoVentaList = costoVentaList;
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
        if (!(object instanceof TipoCosto)) {
            return false;
        }
        TipoCosto other = (TipoCosto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
