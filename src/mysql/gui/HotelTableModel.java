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
import mysql.core.Hotel;

public class HotelTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int HOTEL_NAME_COL = 0;
    private static final int MANAGER_COL = 1;
    private static final int STREET_COL = 2;
    private static final int BARANGAY_COL = 3;
    private static final int CITY_COL = 4;
    private static final int PHONE_COL = 5;
    
    private String[] columnNames = {"Hotel Name","Hotel Manager","Street","Barangay","City","Phone Number"};
    private ArrayList<Hotel> hotels;
    
    public HotelTableModel(ArrayList<Hotel> list){
        this.hotels = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return hotels.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        Hotel hotel = hotels.get(row);
        
        switch(col){
            case HOTEL_NAME_COL:
                return hotel.getHotelName();
            case MANAGER_COL:
                return hotel.getManager();
            case STREET_COL:
                return hotel.getStreet();
            case BARANGAY_COL:
                return hotel.getBarangay();
            case CITY_COL:
                return hotel.getCity();
            case PHONE_COL:
                return hotel.getPhoneNumber();
            case OBJECT_COL:
                return hotel;
            default:
                return hotel.getHotelID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
