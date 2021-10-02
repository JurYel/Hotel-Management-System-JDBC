/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql.core;

/**
 *
 * @author Jur Yel
 */
import java.util.Date;
public class CustomerRecords {
    private int record_id;
    private int customer_id;
    private int booking_id;
    private String hotel_name;
    private String customer_first;
    private String customer_last;
    private Date book_date;
    private Date checkin_date;
    private Date checkout_date;
    private String str_book;
    private String str_checkin;
    private String str_checkout;
    
    public CustomerRecords(String hotel,String first,String last,Date book,Date in,Date out){
        super();
        this.hotel_name = hotel;
        this.customer_first = first;
        this.customer_last = last;
        this.book_date = book;
        this.checkin_date = in;
        this.checkout_date = out;
    }
    
    public void setRecordID(int id){
        this.record_id = id;
    }
    
    public void setCustomerID(int id){
        this.customer_id = id;
    }
    
    public void setBookingID(int id){
        this.booking_id = id;
    }
    
    public void setBookDate(String book){
        this.str_book = book;
    }
    
    public void setCheckInDate(String in){
        this.str_checkin = in;
    }
    
    public void setCheckOutDate(String out){
        this.str_checkout = out;
    }
    
    public int getRecordID(){
        return record_id;
    }
    
    public int getCustomerID(){
        return customer_id;
    }
    
    public int getBookingID(){
        return booking_id;
    }
    
    public String getHotelName(){
        return hotel_name;
    }
    
    public String getFirstName(){
        return customer_first;
    }
    
    public String getLastName(){
        return customer_last;
    }
    
    public Date getBookDate(){
        return book_date;
    }
    
    public Date getCheckInDate(){
        return checkin_date;
    }
    
    public Date getCheckOutDate(){
        return checkout_date;
    }
    
    public String getStrBook(){
        return str_book;
    }
    
    public String getStrCheckIn(){
        return str_checkin;
    }
    
    public String getStrCheckOut(){
        return str_checkout;
    }
}
