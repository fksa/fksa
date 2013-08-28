/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.text.DecimalFormat;
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
import util.Constantes;

/**
 *
 * @author janus
 */
@Entity
@Table(name = "accionista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accionista.findAll", query = "SELECT a FROM Accionista a"),
    @NamedQuery(name = "Accionista.findById", query = "SELECT a FROM Accionista a WHERE a.id = :id"),
    @NamedQuery(name = "Accionista.findByNombre", query = "SELECT a FROM Accionista a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Accionista.findByTipoIdentificacion", query = "SELECT a FROM Accionista a WHERE a.tipoIdentificacion = :tipoIdentificacion"),
    @NamedQuery(name = "Accionista.findByIdentificacion", query = "SELECT a FROM Accionista a WHERE a.identificacion = :identificacion"),
    @NamedQuery(name = "Accionista.findByLugarExpedicion", query = "SELECT a FROM Accionista a WHERE a.lugarExpedicion = :lugarExpedicion"),
    @NamedQuery(name = "Accionista.findByTelefono", query = "SELECT a FROM Accionista a WHERE a.telefono = :telefono"),
    @NamedQuery(name = "Accionista.findByTelefono2", query = "SELECT a FROM Accionista a WHERE a.telefono2 = :telefono2"),
    @NamedQuery(name = "Accionista.findByDepartamento", query = "SELECT a FROM Accionista a WHERE a.departamento = :departamento"),
    @NamedQuery(name = "Accionista.findByCiudad", query = "SELECT a FROM Accionista a WHERE a.ciudad = :ciudad"),
    @NamedQuery(name = "Accionista.findByDireccion", query = "SELECT a FROM Accionista a WHERE a.direccion = :direccion"),
    @NamedQuery(name = "Accionista.findByVereda", query = "SELECT a FROM Accionista a WHERE a.vereda = :vereda"),
    @NamedQuery(name = "Accionista.findByTituloEntregado", query = "SELECT a FROM Accionista a WHERE a.tituloEntregado = :tituloEntregado"),
    @NamedQuery(name = "Accionista.findByCartaFirmada", query = "SELECT a FROM Accionista a WHERE a.cartaFirmada = :cartaFirmada"),
    @NamedQuery(name = "Accionista.findByEstado", query = "SELECT a FROM Accionista a WHERE a.estado = :estado")})
public class Accionista implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "lugar_expedicion")
    private String lugarExpedicion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 21)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 21)
    @Column(name = "telefono_2")
    private String telefono2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "departamento")
    private String departamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ciudad")
    private String ciudad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 100)
    @Column(name = "vereda")
    private String vereda;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "titulo_entregado")
    private String tituloEntregado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "carta_firmada")
    private String cartaFirmada;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccionista")
    private List<Titulo> tituloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccionista")
    private List<PagoAcciones> pagoAccionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccionista")
    private List<ContratoSuscripcion> contratoSuscripcionList;
    
    public Accionista() {
        this.estado = Constantes.ESTADO_PENDIENTE;
        this.tipoIdentificacion = Constantes.TIPO_IDENTIFICACION_CEDULA;
        this.tituloEntregado = this.cartaFirmada = Constantes.TITULO_NO;
    }

    public Accionista(Integer id) {
        this.id = id;
    }

    public Accionista(Integer id, String nombre, String tipoIdentificacion, String identificacion, String lugarExpedicion, String telefono, String departamento, String ciudad, String direccion, String tituloEntregado, String cartaFirmada, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.tipoIdentificacion = tipoIdentificacion;
        this.identificacion = identificacion;
        this.lugarExpedicion = lugarExpedicion;
        this.telefono = telefono;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.tituloEntregado = tituloEntregado;
        this.cartaFirmada = cartaFirmada;
        this.estado = estado;
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

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }
    
    public String getStrIdentificacion(){
        try{
            String ret = tipoIdentificacion + " ";
            ret += (tipoIdentificacion != null && tipoIdentificacion.equals(Constantes.TIPO_IDENTIFICACION_CEDULA))? new DecimalFormat("###,###,###,###").format(Integer.parseInt(identificacion)):identificacion;
            return ret;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return "";
        }
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getVereda() {
        return vereda;
    }

    public void setVereda(String vereda) {
        this.vereda = vereda;
    }

    public String getTituloEntregado() {
        return tituloEntregado;
    }

    public void setTituloEntregado(String tituloEntregado) {
        this.tituloEntregado = tituloEntregado;
    }

    public String getCartaFirmada() {
        return cartaFirmada;
    }

    public void setCartaFirmada(String cartaFirmada) {
        this.cartaFirmada = cartaFirmada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Titulo> getTituloList() {
        return tituloList;
    }

    public void setTituloList(List<Titulo> tituloList) {
        this.tituloList = tituloList;
    }

    @XmlTransient
    public List<PagoAcciones> getPagoAccionesList() {
        return pagoAccionesList;
    }

    public void setPagoAccionesList(List<PagoAcciones> pagoAccionesList) {
        this.pagoAccionesList = pagoAccionesList;
    }

    @XmlTransient
    public List<ContratoSuscripcion> getContratoSuscripcionList() {
        return contratoSuscripcionList;
    }

    public void setContratoSuscripcionList(List<ContratoSuscripcion> contratoSuscripcionList) {
        this.contratoSuscripcionList = contratoSuscripcionList;
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
        if (!(object instanceof Accionista)) {
            return false;
        }
        Accionista other = (Accionista) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getStrIdentificacion() + " :: " + getNombre() + " :: " + getId();
    }

    public void addPago() {
        this.pagoAccionesList.add(new PagoAcciones(this));
    }
    
    public void addPago(PagoAcciones pagoAcciones) {
        this.pagoAccionesList.add(pagoAcciones);
    }
    
    public void addTitulo(Titulo titulo) {
        this.tituloList.add(titulo);
    }

    public int totalPagos(){
        try{
            int total = 0;
            for (PagoAcciones p : this.pagoAccionesList ){
                total += p.getValorNeto();
            }
            return total;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return -1;
        }
    }

    public int totalSuscrito(){
        try{
            int total = 0;
            for (ContratoSuscripcion c : this.contratoSuscripcionList ){
                total += c.getAccionesSuscritas() * 2000;
            }
            return total;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return -1;
        }
    }

    public int totalTitulos(){
        try{
            int total = 0;
            for (Titulo t : this.tituloList ){
                total += t.getNumeroAcciones() * 2000;
            }
            return total;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return -1;
        }
    }
    
    public boolean validar(){
        return (totalPagos() == totalTitulos());
    }
    
    public boolean validarEntregaTitulo(){
        return tituloEntregado.equals(Constantes.TITULO_SI);
    }

}
