/*
 * TableModel_StateTA.java
 * 
 * Created on Jul 12, 2007, 1:13:06 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package genDevs.jaxb.gui;

/**
 *
 * @author Saurabh
 */
public class TableModel_StateTA extends TableModelGen{

     private String[] columnNames={
                "State", "Timeout"
            };  
 
    public TableModel_StateTA() {
        super();
        super.setColumnNames(columnNames);
    }

    
    
}
