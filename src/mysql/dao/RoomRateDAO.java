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
import mysql.core.RoomRates;

public class RoomRateDAO {
    private Connection myConn;
    
    public RoomRateDAO()throws SQLException{
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
    
    public void addRate(RoomRates rate)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO room_rates (room_type,room_rate) VALUES(?,?)");
            pst.setString(1, rate.getRoomType());
            pst.setDouble(2, rate.getRoomRate());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public ArrayList<RoomRates> getAllRoomRates()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<RoomRates> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT room_id,room_type,room_rate FROM room_rates");
            rs = pst.executeQuery();
            
            while(rs.next()){
                RoomRates rate = convertRowToRoomRate(rs);
                list.add(rate);
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
    
    public ArrayList<RoomRates> searchRate(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<RoomRates> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT room_id,room_type,room_rate FROM room_rates WHERE CONCAT(room_type,room_rate) LIKE '%"+search+"%' ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                RoomRates rate = convertRowToRoomRate(rs);
                list.add(rate);
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
    
    private RoomRates convertRowToRoomRate(ResultSet rs)throws SQLException{
        int id = rs.getInt("room_id");
        String type = rs.getString("room_type");
        double rate = rs.getDouble("room_rate");
        
        RoomRates room_rate = new RoomRates(type,rate);
        room_rate.setRoomID(id);
        
        return room_rate;
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
