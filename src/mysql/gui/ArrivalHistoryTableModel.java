/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql.gui;

/**
 *
 * @author Jur Yel
 */
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import mysql.core.ArrivalHistory;

public class ArrivalHistoryTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int FIRST_COL = 0;
    private static final int LAST_COL = 1;
    private static final int DATE_ARRIVAL_COL = 2;
    
    private String[] columnNames = {"First Name","Last Name","Date Arrival"};
    private ArrayList<ArrivalHistory> arrivals;
    
    public ArrivalHistoryTableModel(ArrayList<ArrivalHistory> list){
        this.arrivals = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return arrivals.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        ArrivalHistory arrival = arrivals.get(row);
        
        switch(col){
            case FIRST_COL:
                return arrival.getCustomerFirst();
            case LAST_COL:
                return arrival.getCustomerLast();
            case DATE_ARRIVAL_COL:
                return arrival.getStrArrival();
            case OBJECT_COL:
                return arrival;
            default:
                return arrival.getArrivalID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
