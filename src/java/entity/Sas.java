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
import javax.persistence.Id;
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
 * @author janus
 */
@Entity
@Table(name = "sas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sas.findAll", query = "SELECT s FROM Sas s"),
    @NamedQuery(name = "Sas.findById", query = "SELECT s FROM Sas s WHERE s.id = :id"),
    @NamedQuery(name = "Sas.findByNombre", query = "SELECT s FROM Sas s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Sas.findByNit", query = "SELECT s FROM Sas s WHERE s.nit = :nit"),
    @NamedQuery(name = "Sas.findByDomicilio", query = "SELECT s FROM Sas s WHERE s.domicilio = :domicilio"),
    @NamedQuery(name = "Sas.findByDireccion", query = "SELECT s FROM Sas s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "Sas.findByDocumentoNumero", query = "SELECT s FROM Sas s WHERE s.documentoNumero = :documentoNumero"),
    @NamedQuery(name = "Sas.findByFechaConstitucion", query = "SELECT s FROM Sas s WHERE s.fechaConstitucion = :fechaConstitucion"),
    @NamedQuery(name = "Sas.findByFechaInscripcion", query = "SELECT s FROM Sas s WHERE s.fechaInscripcion = :fechaInscripcion"),
    @NamedQuery(name = "Sas.findByNumeroInscripcion", query = "SELECT s FROM Sas s WHERE s.numeroInscripcion = :numeroInscripcion"),
    @NamedQuery(name = "Sas.findByLibroInscripcion", query = "SELECT s FROM Sas s WHERE s.libroInscripcion = :libroInscripcion"),
    @NamedQuery(name = "Sas.findByMatricula", query = "SELECT s FROM Sas s WHERE s.matricula = :matricula"),
    @NamedQuery(name = "Sas.findByNombreRepresentante", query = "SELECT s FROM Sas s WHERE s.nombreRepresentante = :nombreRepresentante"),
    @NamedQuery(name = "Sas.findByIdentificacionRepresentante", query = "SELECT s FROM Sas s WHERE s.identificacionRepresentante = :identificacionRepresentante"),
    @NamedQuery(name = "Sas.findByCapitalSuscrito", query = "SELECT s FROM Sas s WHERE s.capitalSuscrito = :capitalSuscrito"),
    @NamedQuery(name = "Sas.findByCapitalPagado", query = "SELECT s FROM Sas s WHERE s.capitalPagado = :capitalPagado"),
    @NamedQuery(name = "Sas.findByCapitalAutorizado", query = "SELECT s FROM Sas s WHERE s.capitalAutorizado = :capitalAutorizado"),
    @NamedQuery(name = "Sas.findByCapitalSuscritoSa", query = "SELECT s FROM Sas s WHERE s.capitalSuscritoSa = :capitalSuscritoSa"),
    @NamedQuery(name = "Sas.findByCapitalPagadoSa", query = "SELECT s FROM Sas s WHERE s.capitalPagadoSa = :capitalPagadoSa"),
    @NamedQuery(name = "Sas.findByTotalEnTitulos", query = "SELECT s FROM Sas s WHERE s.totalEnTitulos = :totalEnTitulos")})
public class Sas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nit")
    private String nit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "domicilio")
    private String domicilio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "documento_numero")
    private String documentoNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_constitucion")
    @Temporal(TemporalType.DATE)
    private Date fechaConstitucion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numero_inscripcion")
    private String numeroInscripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "libro_inscripcion")
    private String libroInscripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_representante")
    private String nombreRepresentante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "identificacion_representante")
    private String identificacionRepresentante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capital_suscrito")
    private int capitalSuscrito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capital_pagado")
    private int capitalPagado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capital_autorizado")
    private int capitalAutorizado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capital_suscrito_sa")
    private int capitalSuscritoSa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capital_pagado_sa")
    private int capitalPagadoSa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_en_titulos")
    private int totalEnTitulos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "consecutivo_titulo")
    private int consecutivoTitulo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSas")
    private List<Titulo> tituloList;

    public Sas() {
    }

    public Sas(Integer id) {
        this.id = id;
    }

    public Sas(Integer id, String nombre, String nit, String domicilio, String direccion, String documentoNumero, Date fechaConstitucion, Date fechaInscripcion, String numeroInscripcion, String libroInscripcion, String matricula, String nombreRepresentante, String identificacionRepresentante, int capitalSuscrito, int capitalPagado, int capitalAutorizado, int capitalSuscritoSa, int capitalPagadoSa, int totalEnTitulos) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.domicilio = domicilio;
        this.direccion = direccion;
        this.documentoNumero = documentoNumero;
        this.fechaConstitucion = fechaConstitucion;
        this.fechaInscripcion = fechaInscripcion;
        this.numeroInscripcion = numeroInscripcion;
        this.libroInscripcion = libroInscripcion;
        this.matricula = matricula;
        this.nombreRepresentante = nombreRepresentante;
        this.identificacionRepresentante = identificacionRepresentante;
        this.capitalSuscrito = capitalSuscrito;
        this.capitalPagado = capitalPagado;
        this.capitalAutorizado = capitalAutorizado;
        this.capitalSuscritoSa = capitalSuscritoSa;
        this.capitalPagadoSa = capitalPagadoSa;
        this.totalEnTitulos = totalEnTitulos;
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDocumentoNumero() {
        return documentoNumero;
    }

    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    public Date getFechaConstitucion() {
        return fechaConstitucion;
    }

    public void setFechaConstitucion(Date fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getNumeroInscripcion() {
        return numeroInscripcion;
    }

    public void setNumeroInscripcion(String numeroInscripcion) {
        this.numeroInscripcion = numeroInscripcion;
    }

    public String getLibroInscripcion() {
        return libroInscripcion;
    }

    public void setLibroInscripcion(String libroInscripcion) {
        this.libroInscripcion = libroInscripcion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getIdentificacionRepresentante() {
        return identificacionRepresentante;
    }

    public void setIdentificacionRepresentante(String identificacionRepresentante) {
        this.identificacionRepresentante = identificacionRepresentante;
    }

    public int getCapitalSuscrito() {
        return capitalSuscrito;
    }

    public void setCapitalSuscrito(int capitalSuscrito) {
        this.capitalSuscrito = capitalSuscrito;
    }

    public int getCapitalPagado() {
        return capitalPagado;
    }

    public void setCapitalPagado(int capitalPagado) {
        this.capitalPagado = capitalPagado;
    }

    public int getCapitalAutorizado() {
        return capitalAutorizado;
    }

    public void setCapitalAutorizado(int capitalAutorizado) {
        this.capitalAutorizado = capitalAutorizado;
    }

    public int getCapitalSuscritoSa() {
        return capitalSuscritoSa;
    }

    public void setCapitalSuscritoSa(int capitalSuscritoSa) {
        this.capitalSuscritoSa = capitalSuscritoSa;
    }

    public int getCapitalPagadoSa() {
        return capitalPagadoSa;
    }

    public void setCapitalPagadoSa(int capitalPagadoSa) {
        this.capitalPagadoSa = capitalPagadoSa;
    }

    public int getTotalEnTitulos() {
        return totalEnTitulos;
    }

    public void setTotalEnTitulos(int totalEnTitulos) {
        this.totalEnTitulos = totalEnTitulos;
    }

    public int getConsecutivoTitulo() {
        return consecutivoTitulo;
    }

    public void setConsecutivoTitulo(int consecutivoTitulo) {
        this.consecutivoTitulo = consecutivoTitulo;
    }

    @XmlTransient
    public List<Titulo> getTituloList() {
        return tituloList;
    }

    public void setTituloList(List<Titulo> tituloList) {
        this.tituloList = tituloList;
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
        if (!(object instanceof Sas)) {
            return false;
        }
        Sas other = (Sas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre; 
    }
    
    public void addTitulo(Titulo titulo){
        titulo.setTituloNumero(consecutivoTitulo);
        tituloList.add(titulo);
        totalEnTitulos += titulo.getNumeroAcciones() * 2000;
        consecutivoTitulo ++;
    }

    public int getDisponible(){
        return getCapitalSuscrito() - getTotalEnTitulos();
    }

}
