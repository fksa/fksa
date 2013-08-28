/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;  

/**
 *
 * @author EDMUNDO
 */
public class LazyPrimeDataModel<T> extends LazyDataModel<T> implements SelectableDataModel<T>{

    private List<T> datasource;
    public LazyPrimeDataModel() {  
    }  
  
    public LazyPrimeDataModel(List<T> datasource) {  
        this.datasource = datasource;  
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
            Logger.getLogger(LazyPrimeDataModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(LazyPrimeDataModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LazyPrimeDataModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(LazyPrimeDataModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LazyPrimeDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;       
    }

    @Override  
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {  
        List<T> data = new ArrayList<T>();  
  
        //filter  
        for(T t : datasource) {  
            boolean match = true;  
  
            for(Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {  
                try {  
                    String filterProperty = it.next();  
                    String filterValue = filters.get(filterProperty);  
                    String fieldValue = String.valueOf(t.getClass().getField(filterProperty).get(t));  
  
                    if(filterValue == null || fieldValue.startsWith(filterValue)) {  
                        match = true;  
                    }  
                    else {  
                        match = false;  
                        break;  
                    }  
                } catch(Exception e) {  
                    match = false;  
                }   
            }  
  
            if(match) {  
                data.add(t);  
            }  
        }  
  
        //sort  
        if(sortField != null) {  
            Collections.sort(data, new LazySorter(sortField, sortOrder));  
        }  
  
        //rowCount  
        int dataSize = data.size();  
        this.setRowCount(dataSize);  
  
        //paginate  
        if(dataSize > pageSize) {  
            try {  
                return data.subList(first, first + pageSize);  
            }  
            catch(IndexOutOfBoundsException e) {  
                return data.subList(first, first + (dataSize % pageSize));  
            }  
        }  
        else {  
            return data;  
        }  
    }  
    
}