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
import mysql.core.Hotel;

public class HotelDAO {
    private Connection myConn;
    
    public HotelDAO()throws SQLException{
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
    
    public void registerHotel(Hotel hotel)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO hotel(hotel_name,street,barangay,city,phone_number) VALUES(?,?,?,?,?)");
            pst.setString(1,hotel.getHotelName());
            pst.setString(2,hotel.getStreet());
            pst.setString(3,hotel.getBarangay());
            pst.setString(4,hotel.getCity());
            pst.setString(5,hotel.getPhoneNumber());
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void updateHotel(Hotel hotel)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE hotel SET hotel_name = ?,street = ?,barangay = ?,city = ?,phone_number = ? WHERE hotel_id = ?");
            pst.setString(1,hotel.getHotelName());
            pst.setString(2,hotel.getStreet());
            pst.setString(3,hotel.getBarangay());
            pst.setString(4,hotel.getCity());
            pst.setString(5,hotel.getPhoneNumber());
            pst.setInt(6,hotel.getHotelID());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void deleteHotel(int hotel_id)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("DELETE FROM hotel WHERE hotel_id = ?");
            pst.setInt(1,hotel_id);
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
        
    }
    
    public int getHotelID(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
            pst = myConn.prepareStatement("SELECT hotel_id FROM hotel WHERE hotel_name = ?");
            pst.setString(1,name);
            rs = pst.executeQuery();
            
            while(rs.next()){
                id = rs.getInt("hotel_id");
            }
            return id;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return id;
    }
    
    public boolean checkHotelName(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT hotel_name FROM hotel");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getString("hotel_name").equals(name)){
                    return true;
                }
            }
            return false;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return false;
    }
    
    public ArrayList<Hotel> getAllHotels()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Hotel> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT ht.hotel_id,hotel_name,emp_first,emp_last,street,barangay,city,ht.phone_number "
                    + "FROM hotel as ht, employee as emp WHERE (emp.hotel_id,emp_position) = (ht.hotel_id,'Hotel Manager') ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Hotel hotel = convertRowToHotel(rs);
                list.add(hotel);
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
    
    public ArrayList<Hotel> searchHotel(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Hotel> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT ht.hotel_id,hotel_name,emp_first,emp_last,street,barangay,city,ht.phone_number "
                    + "FROM hotel as ht, employee as emp WHERE (emp.hotel_id,emp_position) = (ht.hotel_id,'Hotel Manager') "
                    + "AND CONCAT(hotel_name,emp_first,emp_last) LIKE '%"+search+"%' ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Hotel hotel = convertRowToHotel(rs);
                list.add(hotel);
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
    
    private Hotel convertRowToHotel(ResultSet rs)throws SQLException{
        int hotel_id = rs.getInt("ht.hotel_id");
        String name = rs.getString("hotel_name");
        String mngr = rs.getString("emp_first") + " " + rs.getString("emp_last");
        String street = rs.getString("street");
        String brgy = rs.getString("barangay");
        String city = rs.getString("city");
        String number = rs.getString("ht.phone_number");
        
        Hotel hotel = new Hotel(name,mngr,street,brgy,city,number);
        hotel.setHotelID(hotel_id);
        return hotel;
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
