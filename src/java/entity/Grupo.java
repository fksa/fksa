/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g"),
    @NamedQuery(name = "Grupo.findById", query = "SELECT g FROM Grupo g WHERE g.id = :id"),
    @NamedQuery(name = "Grupo.findByNombre", query = "SELECT g FROM Grupo g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "Grupo.findByDescripcion", query = "SELECT g FROM Grupo g WHERE g.descripcion = :descripcion")})
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<ModulosGrupo> modulosGrupoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<GruposPerfil> gruposPerfilList;

    public Grupo() {
    }

   public void removePerfil(GruposPerfil p){
        this.gruposPerfilList.remove(p);
    }

    public boolean containPerfil(Perfil perfil){
        for (GruposPerfil p : this.gruposPerfilList)
            if (p.getPerfil().equals(perfil)) return true;
        return false;
    }

    public void addPerfil(Perfil p){
        if (this.gruposPerfilList==null) this.gruposPerfilList = new ArrayList();
        this.gruposPerfilList.add(new GruposPerfil( this, p));
    }

   public void removeModulo(ModulosGrupo m){
        this.modulosGrupoList.remove(m);
    }

    public boolean containModulo(Modulo modulo){
        for (ModulosGrupo m : this.modulosGrupoList)
            if (m.getModulo().equals(modulo)) return true;
        return false;
    }

    public void addModulo(Modulo m){
        if (this.modulosGrupoList==null) this.modulosGrupoList = new ArrayList();
        this.modulosGrupoList.add(new ModulosGrupo( this, m));
    }

    public Grupo(Integer id) {
        this.id = id;
    }

    public Grupo(Integer id, String nombre) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<ModulosGrupo> getModulosGrupoList() {
        return modulosGrupoList;
    }

    public void setModulosGrupoList(List<ModulosGrupo> modulosGrupoList) {
        this.modulosGrupoList = modulosGrupoList;
    }

    @XmlTransient
    public List<GruposPerfil> getGruposPerfilList() {
        return gruposPerfilList;
    }

    public void setGruposPerfilList(List<GruposPerfil> gruposPerfilList) {
        this.gruposPerfilList = gruposPerfilList;
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
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Grupo: " + this.nombre;
    }
    
}
