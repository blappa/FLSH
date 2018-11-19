/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.projection;

import java.util.Comparator;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author ZALEWO
 */
public class PvMatiereModel extends ListDataModel<PvMatiere> implements SelectableDataModel<PvMatiere> {

    public PvMatiereModel(List<PvMatiere> data) {
        super(data);
        this.data=data;
    }

     private List<PvMatiere> data; 

    @Override
    public Object getRowKey(PvMatiere t) {
     return t.getNoteId();
    }

    @Override
    public PvMatiere getRowData(String string) {
        for (PvMatiere pvMatiere : this) {
            if(pvMatiere.getNoteId().toString().equals(string))
                return pvMatiere;
        }
  
       return null;
    }
}
