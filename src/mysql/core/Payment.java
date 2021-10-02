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
public class Payment {
    private int payment_id;
    private int customer_id;
    private int booking_id;
    private String hotel_name;
    private int nights;
    private String customer_first;
    private String customer_last;
    private int card_number;
    private double amount;
    private Date payment_date;
    private String str_paydate;
    private String payment_type;
    
    public Payment(String hotel,String first,String last,int nts,int num,double amt,Date paydate,String type){
        super();
        this.hotel_name = hotel;
        this.customer_first = first;
        this.customer_last = last;
        this.card_number = num;
        this.amount = amt;
        this.payment_date = paydate;
        this.nights = nts;
        this.payment_type = type;
    }
    
    public void setPaymentID(int id){
        this.payment_id = id;
    }
    
    public void setCustomerID(int id){
        this.customer_id = id;
    }
    
    public void setBookingID(int id){
        this.booking_id = id;
    }
    
    public void setStrPayDate(String date){
        this.str_paydate = date;
    }
    
    public int getPaymentID(){
        return payment_id;
    }
    
    public int getCustomerID(){
        return customer_id;
    }
    
    public int getBookingID(){
        return booking_id;
    }
    
    public String getStrPayDate(){
        return str_paydate;
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
    
    public int getCardNumber(){
        return card_number;
    }
    
    public int getNumberNights(){
        return nights;
    }
    
    public double getAmount(){
        return amount;
    }
    
    public Date getPaymentDate(){
        return payment_date;
    }
    
    public String getPaymentType(){
        return payment_type;
    }
    
   
}
