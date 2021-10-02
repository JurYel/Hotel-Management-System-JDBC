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
import java.sql.Timestamp;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.ArrayList;
import mysql.core.ArrivalHistory;

public class ArrivalHistoryDAO {
    private Connection myConn;
    
    public ArrivalHistoryDAO()throws SQLException{
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
    
    public void recordArrival(ArrivalHistory arrival)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO arrival_history (customer_id,date_arrival) VALUES(?,?)");
            pst.setInt(1,arrival.getCustomerID());
            pst.setDate(2,new java.sql.Date(System.currentTimeMillis()));
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public ArrayList<ArrivalHistory> getAllArrivals()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<ArrivalHistory> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT arrival_id,arv.customer_id,customer_first,customer_last, "
                    + "DATE_FORMAT(date_arrival,'%M %e, %Y - %l:%i %p') as date_arrival FROM arrival_history as arv, customer_detail as cu "
                    + "WHERE arv.customer_id = cu.customer_id");
            rs = pst.executeQuery();
            
            while(rs.next()){
                ArrivalHistory arrival = convertRowToArrival(rs);
                list.add(arrival);
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
    
    public ArrayList<ArrivalHistory> searchArrival(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<ArrivalHistory> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT arrival_id,arv.customer_id,customer_first,customer_last, "
                    + "DATE_FORMAT(date_arrival,'%M %e, %Y - %l:%i %p') as date_arrival FROM arrival_history as arv, customer_detail as cu "
                    + "WHERE arv.customer_id = cu.customer_id AND CONCAT(customer_first,customer_last) LIKE '%"+search+"%' ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                ArrivalHistory arrival = convertRowToArrival(rs);
                list.add(arrival);
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
    
    private ArrivalHistory convertRowToArrival(ResultSet rs)throws SQLException{
        int arrival_id = rs.getInt("arrival_id");
        int customer_id = rs.getInt("arv.customer_id");
        String customer_first = rs.getString("customer_first");
        String customer_last = rs.getString("customer_last");
        String date_arrival = rs.getString("date_arrival");
        
        ArrivalHistory arrival = new ArrivalHistory(customer_first,customer_last,null);
        arrival.setArrivalID(arrival_id);
        arrival.setCustomerID(customer_id);
        arrival.setStrArrival(date_arrival);
        
        return arrival;
        
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
