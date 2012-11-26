/*
 * TableModelGen.java
 * 
 * Created on Jul 12, 2007, 1:06:43 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package genDevs.jaxb.gui;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Saurabh
 */
public class TableModelGen extends javax.swing.table.AbstractTableModel implements TableModelListener {
 
    private Object [][] data = {};
        
  private String[] columnNames={
  //              "State", "Timeout"
            };  
 
    public TableModelGen() {
        
    }
    
    public void setColumnNames(String[] names){
        this.columnNames = names;
    }

        public Object[][] getData(){
        return data;
    }
    public boolean isCellEditable(int row, int col)
        {
    	//fn updated on August 24, 2007
    	//ver 0.5.2
    	if(col==0)
    	return true;
    	else
    		return false;}
    
    public String getColumnName(int col) {
        return columnNames[col];
    }
    public void deleteRow(int row){
        //bpz 2007
        Object[][] newData = new Object[data.length-1][columnNames.length];
        for(int i=0; i<data.length; i++){
            if(i<row){
                newData[i] = data[i];
            }
            if(i==row)
                continue;
            if(i>row){
                    newData[i-1]=data[i];
            }
        }
        data = newData;
        this.fireTableRowsDeleted(row, row);
    }
    public void addRow(Object[] rowData){
        int dataRowLength = data.length;
        Object[][] newData = new Object[dataRowLength+1][columnNames.length];
        for(int i=0; i<data.length; i++){
            newData[i] = data[i];
        }
       newData[newData.length-1] = rowData; 
       data = newData;
       this.fireTableRowsInserted(data.length, data.length);
     
        
    }
    public void printTable(){
        System.out.println("DATA Set:");
        for(int i=0; i<data.length; i++){
            for(int j=0; j<data[i].length; j++){
                Object dataVal = getValueAt(i,j);
                if(dataVal==null){
                    System.out.print(" ");
                    continue;
                }
                else
                    System.out.print(dataVal.toString()+"\t");
            }
            System.out.println(" ");
        }
    }
    
    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    
     //Interface functions below
    public int getRowCount() {
       return data.length;
    }

    public int getColumnCount() {
         return columnNames.length;
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public void tableChanged(TableModelEvent e) {
        System.out.println("Delt Int table changed");
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModelGen model = (TableModelGen)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        model.setValueAt(data, row, column);
        // Do something with the data...
        model.printTable();
    }

}
