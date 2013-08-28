package util;

import java.util.*;

/**
 *
 * @author EDMUNDO
 */
public class Constantes {
    
    public static final String ESTADO_ACTIVO = "ACTIVO";
    public static final String ESTADO_INACTIVO = "INACTIVO";
    public static final String ESTADO_PENDIENTE = "PENDIENTE";
    
    public static final String TITULO_SI = "SI";
    public static final String TITULO_NO = "NO";
    
    public static final String TIPO_IDENTIFICACION_CEDULA = "CC";
    public static final String TIPO_IDENTIFICACION_PASAPORTE = "PAS";
    public static final String TIPO_IDENTIFICACION_NIT = "NIT";
    
    public static final String VENTA_EN_PIE = "GANADO EN PIE";
    public static final String VENTA_EN_CANAL = "CARNE EN CANAL";

    private static final List<String> listEstado;
    private static final List<String> listTitulo;
    private static final List<String> listTipoIdentificacion;
    private static final List<String> listTipoVenta;

    static {
        List<String> list = new ArrayList();
        list.add(ESTADO_ACTIVO);
        list.add(ESTADO_INACTIVO);
        list.add(ESTADO_PENDIENTE);
        listEstado = Collections.unmodifiableList(list);
        list = new ArrayList();
        list.add(TITULO_SI);
        list.add(TITULO_NO);
        listTitulo = Collections.unmodifiableList(list);
        list = new ArrayList();
        list.add(TIPO_IDENTIFICACION_CEDULA);
        list.add(TIPO_IDENTIFICACION_NIT);
        listTipoIdentificacion = Collections.unmodifiableList(list);
        list = new ArrayList();
        list.add(VENTA_EN_PIE);
        list.add(VENTA_EN_CANAL);
        listTipoVenta = Collections.unmodifiableList(list);
    }
    
    public static List<String> getListEstado() {
        return listEstado;
    }

    public static List<String> getListTitulo() {
        return listTitulo;
    }

    public static List<String> getListTipoIdentificacion() {
        return listTipoIdentificacion;
    }

    public static List<String> getListTipoVenta() {
        return listTipoVenta;
    }
       
}
