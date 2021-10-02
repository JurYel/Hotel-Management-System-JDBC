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
import mysql.core.Booking;
public class BookingTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int HOTEL_COL = 0;
    private static final int FIRST_COL = 1;
    private static final int LAST_COL = 2;
    private static final int ROOM_TYPE_COL = 3;
    private static final int BOOK_DATE_COL = 4;
    private static final int CHECKIN_DATE_COL = 5;
    private static final int CHECKOUT_DATE_COL = 6;
    private static final int NIGHTS_COL = 7;
    
    private String[] columnNames = {"Hotel Name","First Name","Last Name","Room Type","Book Date","Check In Date","Check Out Date","Nights Stayed"};
    private ArrayList<Booking> bookings;
    
    public BookingTableModel(ArrayList<Booking> list){
        this.bookings = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return bookings.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        Booking book = bookings.get(row);
        
        switch(col){
            case HOTEL_COL:
                return book.getHotelName();
            case FIRST_COL:
                return book.getCustomerFirst();
            case LAST_COL:
                return book.getCustomerLast();
            case ROOM_TYPE_COL:
                return book.getRoomType();
            case BOOK_DATE_COL:
                return book.getLSBookDate();
            case CHECKIN_DATE_COL:
                return book.getLSCheckInDate();
            case CHECKOUT_DATE_COL:
                return book.getLSCheckOutDate();
            case NIGHTS_COL:
                return book.getNumberNights();
            case OBJECT_COL:
                return book;
            default:
                return book.getBookingID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
