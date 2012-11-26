/*
 * TableModel_Deltint.java
 * 
 * Created on Jul 12, 2007, 1:18:37 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package genDevs.jaxb.gui;

/**
 *
 * @author Saurabh
 */
public class TableModel_Deltint extends TableModelGen {

    private String[] colNames = {
        "S.No.","Start State", "Next State", "Timeout", "Outgoing Msg"
    };
    
    public TableModel_Deltint() {
        super();
        super.setColumnNames(colNames);
    }

}
