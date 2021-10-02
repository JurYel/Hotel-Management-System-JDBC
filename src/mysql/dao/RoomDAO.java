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
import java.util.ArrayList;
import java.io.FileInputStream;
import mysql.core.Room;

public class RoomDAO {
    private Connection myConn;
    
    public RoomDAO()throws SQLException{
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
    
    public void addRoom(Room room)throws SQLException{
        PreparedStatement pst = null;
        try{
            myConn.setAutoCommit(false);
            for(int i = 0;i<room.getQuantity();i++){
                
                if(room.getRoomTypes().equals("Single")){
                    pst = myConn.prepareStatement("INSERT INTO room (hotel_id,room_description,room_type,room_rate) VALUES(?,?,?,?)");
                    pst.setInt(1,room.getHotelID());
                    pst.setString(2,room.getRoomDesc());
                    pst.setString(3,room.getRoomTypes());
                    pst.setDouble(4,600);
                    pst.executeUpdate();
                }
                if(room.getRoomTypes().equals("Double")){
                    pst = myConn.prepareStatement("INSERT INTO room (hotel_id,room_description,room_type,room_rate) VALUES(?,?,?,?)");
                    pst.setInt(1,room.getHotelID());
                    pst.setString(2,room.getRoomDesc());
                    pst.setString(3,room.getRoomTypes());
                    pst.setDouble(4,1200);
                    pst.executeUpdate();
                }
                if(room.getRoomTypes().equals("Triple")){
                    pst = myConn.prepareStatement("INSERT INTO room (hotel_id,room_description,room_type,room_rate) VALUES(?,?,?,?)");
                    pst.setInt(1,room.getHotelID());
                    pst.setString(2,room.getRoomDesc());
                    pst.setString(3,room.getRoomTypes());
                    pst.setDouble(4,1800);
                    pst.executeUpdate();
                }
                if(room.getRoomTypes().equals("Quad")){
                    pst = myConn.prepareStatement("INSERT INTO room (hotel_id,room_description,room_type,room_rate) VALUES(?,?,?,?)");
                    pst.setInt(1,room.getHotelID());
                    pst.setString(2,room.getRoomDesc());
                    pst.setString(3,room.getRoomTypes());
                    pst.setDouble(4,2400);
                    pst.executeUpdate();;
                }
                if(room.getRoomTypes().equals("Queen")){
                    pst = myConn.prepareStatement("INSERT INTO room (hotel_id,room_description,room_type,room_rate) VALUES(?,?,?,?)");
                    pst.setInt(1,room.getHotelID());
                    pst.setString(2,room.getRoomDesc());
                    pst.setString(3,room.getRoomTypes());
                    pst.setDouble(4,1500);
                    pst.executeUpdate();
                }
                if(room.getRoomTypes().equals("King")){
                    pst = myConn.prepareStatement("INSERT INTO room (hotel_id,room_description,room_type,room_rate) VALUES(?,?,?,?)");
                    pst.setInt(1,room.getHotelID());
                    pst.setString(2,room.getRoomDesc());
                    pst.setString(3,room.getRoomTypes());
                    pst.setDouble(4,1500);
                    pst.executeUpdate();
                }
                if(room.getRoomTypes().equals("Twin")){
                    pst = myConn.prepareStatement("INSERT INTO room (hotel_id,room_description,room_type,room_rate) VALUES(?,?,?,?)");
                    pst.setInt(1,room.getHotelID());
                    pst.setString(2,room.getRoomDesc());
                    pst.setString(3,room.getRoomTypes());
                    pst.setDouble(4,2000);
                    pst.executeUpdate();
                }
            }
            myConn.commit();
            
        }
        catch(Exception exc){
            exc.printStackTrace();
            myConn.rollback();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void updateRoom(Room room)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE room SET hotel_id = ?,room_description = ?,room_type = ? WHERE room_id = ?");
            pst.setInt(1,room.getHotelID());
            pst.setString(2,room.getRoomDesc());
            pst.setString(3,room.getRoomTypes());
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void deleteRoom(int id)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("DELETE FROM room WHERE room_id = ?");
            pst.setInt(1,id);
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
        
    }
    
    public ArrayList<Room> getHotelRooms(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT room_id,rm.hotel_id,hotel_name,room_description,room_type,room_rate FROM room as rm, hotel as ht "
                    + "WHERE (rm.hotel_id) = (ht.hotel_id) AND  rm.hotel_id = ?");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Room room = convertRowToRoom(rs);
                list.add(room);
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
    
    
     
    public ArrayList<Room> getAllRooms()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT room_id,rm.hotel_id,hotel_name,room_description,room_type,room_rate FROM room as rm, hotel as ht "
                    + "WHERE rm.hotel_id = ht.hotel_id");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Room room = convertRowToRoom(rs);
                list.add(room);
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
    
    public ArrayList<Room> searchHotelRoom(int id,String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT room_id,rm.hotel_id,hotel_name,room_description,room_type,room_rate FROM room as rm, hotel as ht "
                    + "WHERE rm.hotel_id = ht.hotel_id AND rm.hotel_id = ? AND CONCAT(hotel_name,room_type) LIKE '%"+search+"%' ");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Room room = convertRowToRoom(rs);
                list.add(room);
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
    
    public ArrayList<Room> searchRoom(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT room_id,rm.hotel_id,hotel_name,room_description,room_type FROM room as rm, hotel as ht "
                    + "WHERE rm.hotel_id = ht.hotel_id AND CONCAT(hotel_name,room_type) LIKE '%"+search+"%' ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Room room = convertRowToRoom(rs);
                list.add(room);
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
    
    private Room convertRowToRoom(ResultSet rs )throws SQLException{
        int room_id = rs.getInt("room_id");
        int hotel_id = rs.getInt("rm.hotel_id");
        String hotel_name = rs.getString("hotel_name");
        String desc = rs.getString("room_description");
        String type = rs.getString("room_type");
        double rate = rs.getDouble("room_rate");
        
        Room room = new Room(hotel_name,desc,type);
        room.setRoomID(room_id);
        room.setHotelID(hotel_id);
        room.setRoomRate(rate);
        return room;
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
