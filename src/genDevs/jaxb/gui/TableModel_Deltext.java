/*
 * TableModel_Deltext.java
 * 
 * Created on Jul 12, 2007, 2:16:04 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package genDevs.jaxb.gui;

/**
 *
 * @author Saurabh
 */
public class TableModel_Deltext extends TableModelGen{

    private String[] colNames = {
        "S.No", "Incoming Msg", "Start State", "Next State", "Rechedule", "Timeout", "Outgoing Msg"
    };
    
    public TableModel_Deltext() {
        super();
        super.setColumnNames(colNames);
    }

}
