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
public class Booking {
    private int booking_id;
    private int customer_id;
    private int hotel_id;
    private int room_id;
    private String hotel_name;
    private String customer_first;
    private String customer_last;
    private String room_type;
    private Date book_date;
    private String str_bookdate;
    private Date checkin_date;
    private String str_checkin;
    private Date checkout_date;
    private String str_checkout;
    private int number_nights;
    
    public Booking(String hotel,String first,String last,String type,Date book,Date checkin,Date checkout,int nights){
        super();
        this.hotel_name = hotel;
        this.customer_first = first;
        this.customer_last = last;
        this.room_type = type;
        this.book_date = book;
        this.checkin_date = checkin;
        this.checkout_date = checkout;
        this.number_nights = nights;
    }
    
    public void setBookingID(int id){
        this.booking_id = id;
    }
    
    public void setCustomerID(int id){
        this.customer_id = id;
    }
    
    public void setRoomID(int id){
        this.room_id = id;
    }
    
    public void setHotelID(int id){
        this.hotel_id = id;
    }
    
    public void setStrBookDate(String date){
        this.str_bookdate = date;
    }
    
    public void setStrCheckInDate(String date){
        this.str_checkin = date;
    }
    
    public void setStrCheckOutDate(String date){
        this.str_checkout = date;
    }
    
    public int getBookingID(){
        return booking_id;
    }
    
    public int getHotelID(){
        return hotel_id;
    }
    
    public int getCustomerID(){
        return customer_id;
    }
    
    public int getRoomID(){
        return room_id;
    }
    
    public String getHotelName(){
        return hotel_name;
    }
    
    public String getCustomerFirst(){
        return customer_first;
    }
    
    public String getCustomerLast(){
        return customer_last;
    }
    
    public String getRoomType(){
        return room_type;
    }
    
    public String getStrBookDate(){
        return str_bookdate;
    }
    
    public String getStrCheckInDate(){
        return str_checkin;
    }
    
    public String getStrCheckOutDate(){
        return str_checkout;
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
    
    public String getLSBookDate(){
        if(book_date == null){
            return " ";
        }
        else{
            return book_date.toLocaleString();
        }
    }
    
    public String getLSCheckInDate(){
        if(checkin_date == null){
            return " ";
        }
        else{
            return checkin_date.toLocaleString();
        }
    }
    
    public String getLSCheckOutDate(){
        if(checkout_date == null){
            return " ";
        }
        else{
            return checkout_date.toLocaleString();
        }
    }
    
    public int getNumberNights(){
        return number_nights;
    }
}
