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
import mysql.core.DepartureHistory;
public class DepartureHistoryTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int FIRST_COL = 0;
    private static final int LAST_COL = 1;
    private static final int ROOM_TYPE_COL = 2;
    private static final int NIGHTS_COL = 3;
    private static final int DATE_COL = 4;
    
    private String[] columnNames = {"First Name","Last Name","Room Type","Nights","Departure Date"};
    private ArrayList<DepartureHistory> departures;
    
    public DepartureHistoryTableModel(ArrayList<DepartureHistory> list){
        this.departures = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return departures.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        DepartureHistory departure = departures.get(row);
        
        switch(col){
            case FIRST_COL:
                return departure.getCustomerFirst();
            case LAST_COL:
                return departure.getCustomerLast();
            case ROOM_TYPE_COL:
                return departure.getRoomType();
            case NIGHTS_COL:
                return departure.getNumberNights();
            case DATE_COL:
                return departure.getStrDepart();
            case OBJECT_COL:
                return departure;
            default:
                return departure.getCustomerID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
