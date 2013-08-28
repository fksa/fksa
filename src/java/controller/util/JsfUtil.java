package controller.util;

import java.security.MessageDigest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import org.primefaces.util.ComponentUtils;

public class JsfUtil {

    public static String replaceSQL(String s){
        s = s.replace("'", " ");
        s = s.replace('"', ' ');
        s = s.replace('`', ' ');
        s = s.replace('´', ' ');
        s = s.replace(';', ' ');
        s = s.replace(':', ' ');
        s = s.replace('\\', ' ');
        s = s.replace('/', ' ');
        s = s.replace('=', ' ');
        s = s.replace('#', ' ');
        s = s.replace('*', ' ');
        s = s.replace('%', ' ');
        s = s.replace('[', ' ');
        s = s.replace(']', ' ');
        s = s.replace('_', ' ');
        s = s.replace("-", "- ");
        s = s.replace("$", "$ ");
        s = s.replace("   ", " ");
        s = s.replace("  ", " ");
        return s;
     } 
    public static String formatDate(Date date)
    {
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
    

    public static String getHash(String message) {
        MessageDigest md;
        byte[] buffer, digest;
        String hash = "";

        try {
            buffer = message.getBytes("UTF-8");
            md = MessageDigest.getInstance("SHA1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        md.update(buffer);
        digest = md.digest();

        for (byte aux : digest) {
            int b = aux & 0xff;
            String s = Integer.toHexString(b);
            if (s.length() == 1)
                hash += "0";
            hash += s;
        }
        return hash;
    }

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "Seleccione...");
            i++;
        }
        try {
            for (Object x : entities) {
                items[i++] = new SelectItem(x, x.toString());
            }
        } catch (Exception e) {
        }
        return items;
    }

    public static SelectItem[] getSelectItems(List<?> entities) {
        int size = entities.size() + 1;
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        items[0] = new SelectItem(null, "Seleccione...");
        i++;
        try {
            for (Object x : entities) {
                items[i++] = new SelectItem(x, x.toString());
            }
        } catch (Exception e) {
        }
        return items;
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    //TODO: Revisar en donde mas se puede utilizar esta funcion
    public static String getClientControlValue(String controlId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent component = null;

        component = ComponentUtils.findComponent(facesContext.getViewRoot(), controlId);
        String value = ComponentUtils.getValueToRender(facesContext, component);
        return value;
    }

    public static Date addDaysToDate(Date date, int days) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.add(Calendar.DATE, days);
        Date newDate = c1.getTime();
        return newDate;
    }
    
    public static String rPad(String str, Integer length, char car) {
      return str
             + 
             String.format("%" + (length - str.length()) + "s", "")
                         .replace(" ", String.valueOf(car));
    }

    public static String lPad(String str, Integer length, char car) {
      return String.format("%" + (length - str.length()) + "s", "")
                   .replace(" ", String.valueOf(car)) 
             +
             str;
    }
}