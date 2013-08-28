/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author EDMUNDO
 */
@Entity
@Table(name = "modulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modulo.findAll", query = "SELECT m FROM Modulo m"),
    @NamedQuery(name = "Modulo.findById", query = "SELECT m FROM Modulo m WHERE m.id = :id"),
    @NamedQuery(name = "Modulo.findByNombre", query = "SELECT m FROM Modulo m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Modulo.findByUrl", query = "SELECT m FROM Modulo m WHERE m.url = :url"),
    @NamedQuery(name = "Modulo.findByPermisos", query = "SELECT m FROM Modulo m WHERE m.permisos = :permisos")})
public class Modulo implements Serializable {
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
    @Size(max = 300)
    @Column(name = "url")
    private String url;
    @Size(max = 2)
    @Column(name = "permisos")
    private String permisos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modulo")
    private List<ModulosGrupo> modulosGrupoList;
    @OneToMany(mappedBy = "idGrupoModulo")
    private List<Modulo> moduloList;
    @JoinColumn(name = "id_grupo_modulo", referencedColumnName = "id")
    @ManyToOne
    private Modulo idGrupoModulo;

    public Modulo() {
    }

    public Modulo(Integer id) {
        this.id = id;
    }

    public Modulo(Integer id, String nombre) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    @XmlTransient
    public List<ModulosGrupo> getModulosGrupoList() {
        return modulosGrupoList;
    }

    public void setModulosGrupoList(List<ModulosGrupo> modulosGrupoList) {
        this.modulosGrupoList = modulosGrupoList;
    }

    @XmlTransient
    public List<Modulo> getModuloList() {
        return moduloList;
    }

    public void setModuloList(List<Modulo> moduloList) {
        this.moduloList = moduloList;
    }

    public Modulo getIdGrupoModulo() {
        return idGrupoModulo;
    }

    public void setIdGrupoModulo(Modulo idGrupoModulo) {
        this.idGrupoModulo = idGrupoModulo;
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
        if (!(object instanceof Modulo)) {
            return false;
        }
        Modulo other = (Modulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Modulo[ id=" + id + " ]";
    }
    
}
