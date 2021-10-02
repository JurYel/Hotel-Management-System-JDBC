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
import mysql.core.CustomerRecords;

public class CustomerRecordsTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int HOTEL_COL = 0;
    private static final int FIRST_COL = 1;
    private static final int LAST_COL = 2;
    private static final int BOOK_DATE_COL = 3;
    private static final int CHECKIN_DATE_COL = 4;
    private static final int CHECKOUT_DATE_COL = 5;
    
    private String[] columnNames = {"Hotel Name","First Name", "Last Name","Book Date","Check In Date","Check Out Date"};
    private ArrayList<CustomerRecords> records;
    
    public CustomerRecordsTableModel(ArrayList<CustomerRecords> list){
        this.records = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return records.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        CustomerRecords record = records.get(row);
        
        switch(col){
            case HOTEL_COL:
                return record.getHotelName();
            case FIRST_COL:
                return record.getFirstName();
            case LAST_COL:
                return record.getLastName();
            case BOOK_DATE_COL:
                return record.getStrBook();
            case CHECKIN_DATE_COL:
                return record.getStrCheckIn();
            case CHECKOUT_DATE_COL:
                return record.getStrCheckOut();
            case OBJECT_COL:
                return record;
            default:
                return record.getRecordID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
    
}
