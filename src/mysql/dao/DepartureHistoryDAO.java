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
import java.util.Properties;
import java.util.ArrayList;
import java.io.FileInputStream;
import mysql.core.DepartureHistory;

public class DepartureHistoryDAO {
    private Connection myConn;
    
    public DepartureHistoryDAO()throws SQLException{
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
    
    public void recordDeparture(DepartureHistory depart)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO departure_history (room_id,customer_id,date_departure,number_nights) VALUES(?,?,?,?)");
            pst.setInt(1,depart.getRoomID());
            pst.setInt(2,depart.getCustomerID());
            pst.setDate(3,new java.sql.Date(System.currentTimeMillis()));
            pst.setInt(4,depart.getNumberNights());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public ArrayList<DepartureHistory> getAllDepartures()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<DepartureHistory> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT departure_id,dep.room_id,dep.customer_id,room_type,customer_first,customer_last, "
                    + "DATE_FORMAT(date_departure,'%M %e, %Y - %l:%i %p') as date_departure, number_nights "
                    + "FROM departure_history as dep,customer_detail as cu,room as rm WHERE (dep.room_id,dep.customer_id) = (rm.room_id,cu.customer_id) ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                DepartureHistory departure = convertRowToDeparture(rs);
                list.add(departure);
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
    
    public ArrayList<DepartureHistory> searchDeparture(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<DepartureHistory> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT departure_id,dep.room_id,dep.customer_id,room_type,customer_first,customer_last, "
                    + "DATE_FORMAT(date_departure,'%M %e, %Y - %l:%i %p') as date_departure, number_nights "
                    + "FROM departure_history as dep,customer_detail as cu,room as rm WHERE (dep.room_id,dep.customer_id) = (rm.room_id,cu.customer_id) "
                    + "AND CONCAT(room_type,customer_first,customer_last) LIKE '%"+search+"%' ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                DepartureHistory departure = convertRowToDeparture(rs);
                list.add(departure);
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
    
    private DepartureHistory convertRowToDeparture(ResultSet rs)throws SQLException{
        int depart_id = rs.getInt("departure_id");
        int room_id = rs.getInt("dep.room_id");
        int customer_id = rs.getInt("dep.customer_id");
        String first = rs.getString("customer_first");
        String last = rs.getString("customer_last");
        String type = rs.getString("room_type");
        String date_depart = rs.getString("date_departure");
        int nights = rs.getInt("number_nights");
        
        DepartureHistory depart = new DepartureHistory(first,last,type,null,nights);
        depart.setCustomerID(customer_id);
        depart.setDepartureID(depart_id);
        depart.setRoomID(room_id);
        depart.setStrDepart(date_depart);
        
        return depart;
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
