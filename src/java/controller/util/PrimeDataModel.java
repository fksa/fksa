/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author EDMUNDO
 */
public class PrimeDataModel<T> extends ListDataModel<T> implements SelectableDataModel<T>{

    public PrimeDataModel(List<T> data) {  
        super(data);  
    }

    @Override
    public Object getRowKey(T t) {
        return getId(t);
    }

    @Override
    public T getRowData(String rowKey) {
        List<T> list = (List<T>) getWrappedData();  
          
        for(T t : list) {  
            if(this.getId(t).equals(new Integer(rowKey)))  
                return t;  
        }  
          
        return null;  
    }
    
    private Integer getId(T t){
        try {
            return (Integer) t.getClass().getMethod("getId").invoke(t, null);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PrimeDataModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PrimeDataModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PrimeDataModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PrimeDataModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PrimeDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;       
    }

}