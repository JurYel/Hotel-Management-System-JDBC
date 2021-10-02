/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql.dao;

/**
 *
 * @author Jur Yel
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.util.ArrayList;
import mysql.core.Payment;
import java.util.Date;

public class PaymentDAO {
    private Connection myConn;
    
    public PaymentDAO()throws SQLException{
        try{
            Properties props = new Properties();
            props.load(new FileInputStream("sql/hotel_system.properties"));
            
            String user = props.getProperty("user");
            String pass = props.getProperty("password");
            String dburl = props.getProperty("dburl");
            
            myConn = DriverManager.getConnection(dburl,user,pass);
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
    }
    
    public void addPayment(Payment pay)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO payment (customer_id,booking_id,card_number,amount,payment_date,payment_type) VALUES(?,?,?,?,?,?)");
            pst.setInt(1,pay.getCustomerID());
            pst.setInt(2,pay.getBookingID());
            pst.setInt(3,pay.getCardNumber());
            pst.setDouble(4,pay.getAmount());
            pst.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            pst.setString(6,pay.getPaymentType());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public ArrayList<Payment> getHotelPayments(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Payment> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT payment_id,pay.customer_id,pay.booking_id,hotel_name,customer_first,customer_last,card_number, "
                    + "number_nights,amount,DATE_FORMAT(payment_date,'%M %e, %Y') as payment_date,payment_type "
                    + "FROM payment as pay,customer_detail as cu,hotel as ht,booking as bk "
                    + "WHERE (pay.customer_id,pay.booking_id,bk.hotel_id) = (cu.customer_id,bk.booking_id,ht.hotel_id) AND pay.hotel_id = ?");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Payment payment = convertRowToPayment(rs);
                list.add(payment);
            }
            return list;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return list;
    }
    
    public ArrayList<Payment> getAllPayments()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Payment> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT payment_id,pay.customer_id,pay.booking_id,hotel_name,customer_first,customer_last,card_number, "
                    + "number_nights,amount,DATE_FORMAT(payment_date,'%M %e, %Y') as payment_date,payment_type "
                    + "FROM payment as pay,customer_detail as cu,hotel as ht,booking as bk "
                    + "WHERE (pay.customer_id,pay.booking_id,bk.hotel_id) = (cu.customer_id,bk.booking_id,ht.hotel_id) ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Payment payment = convertRowToPayment(rs);
                list.add(payment);
            }
            return list;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return list;
    }
    
    public ArrayList<Payment> searchHotelPayment(int id,String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Payment> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT payment_id,pay.customer_id,pay.booking_id,hotel_name,customer_first,customer_last,card_number, "
                    + "number_nights,amount,DATE_FORMAT(payment_date,'%M %e, %Y') as payment_date,payment_type "
                    + "FROM payment as pay,customer_detail as cu,hotel as ht,booking as bk "
                    + "WHERE (pay.customer_id,pay.booking_id,bk.hotel_id) = (cu.customer_id,bk.booking_id,ht.hotel_id) AND pay.hotel_id = ? "
                    + "AND CONCAT(hotel_name,customer_first,customer_last,card_number) LIKE '%"+search+"%' ");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Payment payment = convertRowToPayment(rs);
                list.add(payment);
            }
            return list;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return list;
    }
    
    public ArrayList<Payment> searchPayment(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Payment> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT payment_id,pay.customer_id,pay.booking_id,hotel_name,customer_first,customer_last,card_number, "
                    + "number_nights,amount,DATE_FORMAT(payment_date,'%M %e, %Y') as payment_date,payment_type "
                    + "FROM payment as pay,customer_detail as cu,hotel as ht,booking as bk "
                    + "WHERE (pay.customer_id,pay.booking_id,bk.hotel_id) = (cu.customer_id,bk.booking_id,ht.hotel_id) "
                    + "AND CONCAT(hotel_name,customer_first,customer_last,card_number) LIKE '%"+search+"%' ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Payment payment = convertRowToPayment(rs);
                list.add(payment);
            }
            return list;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return list;
    }
    
    private Payment convertRowToPayment(ResultSet rs)throws SQLException{
        int payment_id = rs.getInt("payment_id");
        int customer_id = rs.getInt("pay.customer_id");
        int booking_id = rs.getInt("pay.booking_id");
        String hotel_name = rs.getString("hotel_name");
        int nights = rs.getInt("number_nights");
        String first = rs.getString("customer_first");
        String last = rs.getString("customer_last");
        int card = rs.getInt("card_number");
        String paydate = rs.getString("payment_date");
        double amount = rs.getDouble("amount");
        String type = rs.getString("payment_type");
        
        Payment payment = new Payment(hotel_name,first,last,nights,card,amount,null,type);
        payment.setPaymentID(payment_id);
        payment.setCustomerID(customer_id);
        payment.setBookingID(booking_id);
        payment.setStrPayDate(paydate);
        
        return payment;
    }
    
    private void close(PreparedStatement pst,ResultSet rs)throws SQLException{
        if(pst!=null){
            pst.close();
        }
        if(rs!=null){
            rs.close();
        }
    }
}
