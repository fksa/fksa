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
@Table(name = "perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p"),
    @NamedQuery(name = "Perfil.findById", query = "SELECT p FROM Perfil p WHERE p.id = :id"),
    @NamedQuery(name = "Perfil.findByNombre", query = "SELECT p FROM Perfil p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Perfil.findByDescripcion", query = "SELECT p FROM Perfil p WHERE p.descripcion = :descripcion")})
public class Perfil implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil")
    private List<PerfilesUsuario> perfilesUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil")
    private List<GruposPerfil> gruposPerfilList;

    public Perfil() {
    }

    public void removeUsuario(PerfilesUsuario u){
        this.perfilesUsuarioList.remove(u);
    }

    public boolean containUsuario(Usuario usuario){
        for (PerfilesUsuario p : this.perfilesUsuarioList)
            if (p.getUsuario().equals(usuario)) return true;
        return false;
    }

    public void addUsuario(Usuario u){
        if (this.perfilesUsuarioList==null) this.perfilesUsuarioList = new ArrayList();
        this.perfilesUsuarioList.add(new PerfilesUsuario( u, this));
    }

    public void removeGrupo(GruposPerfil g){
        this.gruposPerfilList.remove(g);
    }

    public boolean containGrupo(Grupo grupo){
        for (GruposPerfil p : this.gruposPerfilList)
            if (p.getGrupo().equals(grupo)) return true;
        return false;
    }

    public void addGrupo(Grupo g){
        if (this.gruposPerfilList==null) this.gruposPerfilList = new ArrayList();
        this.gruposPerfilList.add(new GruposPerfil( g, this));
    }

    public Perfil(Integer id) {
        this.id = id;
    }

    public Perfil(Integer id, String nombre) {
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
    public List<PerfilesUsuario> getPerfilesUsuarioList() {
        return perfilesUsuarioList;
    }

    public void setPerfilesUsuarioList(List<PerfilesUsuario> perfilesUsuarioList) {
        this.perfilesUsuarioList = perfilesUsuarioList;
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
        if (!(object instanceof Perfil)) {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Perfil: " + this.nombre;
    }
    
}
