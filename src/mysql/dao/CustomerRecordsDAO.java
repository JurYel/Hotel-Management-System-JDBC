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
import mysql.core.CustomerRecords;
import java.util.Date;
import java.sql.Timestamp;

public class CustomerRecordsDAO {
    private Connection myConn;
    
    public CustomerRecordsDAO()throws SQLException{
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
    
    public ArrayList<CustomerRecords> getHotelRecords(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<CustomerRecords> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT record_id,cr.customer_id,cr.booking_id,hotel_name,customer_first,customer_last, "
                    + "DATE_FORMAT(book_date,'%M %e, %Y - %l:%i %p') as book_date, DATE_FORMAT(checkin_date,'%M %e, %Y - %l:%i %p') as checkin_date, "
                    + "DATE_FORMAT(checkout_date,'%M %e, %Y - %l:%i %p') as checkout_date FROM customer_records as cr, booking as bk, hotel as ht, customer_detail as cu "
                    + "WHERE (cr.customer_id,cr.booking_id,bk.hotel_id) = (cu.customer_id,bk.booking_id,ht.hotel_id) AND bk.hotel_id = ?");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                CustomerRecords record = convertRowToRecord(rs);
                list.add(record);
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
    
    public ArrayList<CustomerRecords> getAllRecords()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<CustomerRecords> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT record_id,cr.customer_id,cr.booking_id,hotel_name,customer_first,customer_last, "
                    + "DATE_FORMAT(book_date,'%M %e, %Y - %l:%i %p') as book_date, DATE_FORMAT(checkin_date,'%M %e, %Y - %l:%i %p') as checkin_date, "
                    + "DATE_FORMAT(checkout_date,'%M %e, %Y - %l:%i %p') as checkout_date FROM customer_records as cr, booking as bk, hotel as ht, customer_detail as cu "
                    + "WHERE (cr.customer_id,cr.booking_id,bk.hotel_id) = (cu.customer_id,bk.booking_id,ht.hotel_id)");
            rs = pst.executeQuery();
            
            while(rs.next()){
                CustomerRecords record = convertRowToRecord(rs);
                list.add(record);
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
    
    public ArrayList<CustomerRecords> searchHotelRecord(int id,String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<CustomerRecords> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT record_id,cr.customer_id,cr.booking_id,hotel_name,customer_first,customer_last, "
                    + "DATE_FORMAT(book_date,'%M %e, %Y - %l:%i %p') as book_date, DATE_FORMAT(checkin_date,'%M %e, %Y - %l:%i %p') as checkin_date, "
                    + "DATE_FORMAT(checkout_date,'%M %e, %Y - %l:%i %p') as checkout_date FROM customer_records as cr, booking as bk, hotel as ht, customer_detail as cu "
                    + "WHERE (cr.customer_id,cr.booking_id,bk.hotel_id) = (cu.customer_id,bk.booking_id,ht.hotel_id) AND bk.hotel_id = ? "
                    + "AND CONCAT(hotel_name,customer_first,customer_last) LIKE '%"+search+"%'");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                CustomerRecords record = convertRowToRecord(rs);
                list.add(record);
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
    
    public ArrayList<CustomerRecords> searchRecord(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<CustomerRecords> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT record_id,cr.customer_id,cr.booking_id,hotel_name,customer_first,customer_last, "
                    + "DATE_FORMAT(book_date,'%M %e, %Y - %l:%i %p') as book_date, DATE_FORMAT(checkin_date,'%M %e, %Y - %l:%i %p') as checkin_date, "
                    + "DATE_FORMAT(checkout_date,'%M %e, %Y - %l:%i %p') as checkout_date FROM customer_records as cr, booking as bk, hotel as ht, customer_detail as cu "
                    + "WHERE (cr.customer_id,cr.booking_id,bk.hotel_id) = (cu.customer_id,bk.booking_id,ht.hotel_id) "
                    + "AND CONCAT(hotel_name,customer_first,customer_last) LIKE '%"+search+"%'");
            rs = pst.executeQuery();
            
            while(rs.next()){
                CustomerRecords record = convertRowToRecord(rs);
                list.add(record);
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
    
    private CustomerRecords convertRowToRecord(ResultSet rs)throws SQLException{
        int record_id = rs.getInt("record_id");
        int customer_id = rs.getInt("cr.customer_id");
        int booking_id = rs.getInt("cr.booking_id");
        String hotel = rs.getString("hotel_name");
        String first = rs.getString("customer_first");
        String last = rs.getString("customer_last");
        String book = rs.getString("book_date");
        String checkin = rs.getString("checkin_date");
        String checkout = rs.getString("checkout_date");
        
        CustomerRecords records = new CustomerRecords(hotel,first,last,null,null,null);
        records.setRecordID(record_id);
        records.setCustomerID(customer_id);
        records.setBookingID(booking_id);
        
        return records;
    }
    
    private void close(PreparedStatement pst, ResultSet rs)throws SQLException{
        if(pst!=null){
            pst.close();
        }
        if(rs!=null){
            rs.close();
        }
    }
}
