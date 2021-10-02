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
import mysql.core.Booking;
import java.util.Date;
import java.sql.Timestamp;
import mysql.core.CustomerRecords;
import mysql.dao.CustomerRecordsDAO;

public class BookingDAO {
    private Connection myConn;
    private Booking theBooking;
    
    public BookingDAO()throws SQLException{
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
    
    public void bookRoom(Booking book)throws SQLException{
        PreparedStatement pst = null;
        try{
            myConn.setAutoCommit(false);
            pst = myConn.prepareStatement("INSERT INTO booking (customer_id,room_id,hotel_id,book_date) "
                    + "VALUES(?,?,?,?)");
            pst.setInt(1, book.getCustomerID());
            pst.setInt(2,book.getRoomID());
            pst.setInt(3,book.getHotelID());
            pst.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();
            
            /* Record Arrival */
            
            pst = myConn.prepareStatement("INSERT INTO arrival_history (customer_id,date_arrival) VALUES(?,?)");
            pst.setInt(1,book.getCustomerID());
            pst.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();
            
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
    
    public void passTime(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            ArrayList<Booking> bookings = this.getHotelBookings(id);
            for(int i =0;i<bookings.size();i++){
                String checkinStr = bookings.get(i).getLSCheckInDate();
                
                if(!(checkinStr.equals(" "))){
                    Date checkin_date = bookings.get(i).getCheckInDate();
                    Date curDate = new Date();
                    int inDay = checkin_date.getDate();
                    int curDay = curDate.getDate();
                    
                    if((curDay - inDay) > 0){
                        pst = myConn.prepareStatement("UPDATE booking SET number_nights = ? WHERE booking_id = ?");
                        pst.setInt(1,(curDay - inDay));
                        pst.setInt(2,bookings.get(i).getBookingID());
                        pst.executeUpdate();
                    }
                }
            }
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
    }
    
    public void checkInBookedRoom(Booking book)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE booking SET checkin_date = ? WHERE booking_id = ?");
            pst.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
            pst.setInt(2,book.getBookingID());
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void checkInRoom(Booking book)throws SQLException{
        PreparedStatement pst = null;
        try{
            myConn.setAutoCommit(false);
            pst = myConn.prepareStatement("INSERT INTO booking (customer_id,hotel_id,room_id,book_date,checkin_date) VALUES(?,?,?,?,?)");
            pst.setInt(1,book.getCustomerID());
            pst.setInt(2,book.getHotelID());
            pst.setInt(3,book.getRoomID());
            pst.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();
            
            /* Record Arrival */
            
            pst = myConn.prepareStatement("INSERT INTO arrival_history (customer_id,date_arrival) VALUES(?,?)");
            pst.setInt(1,book.getCustomerID());
            pst.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();
            
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
    
    public void checkOutRoom(Booking book)throws SQLException{
        PreparedStatement pst = null;
        try{
            myConn.setAutoCommit(false);
            Date curDate = new Date();
            Date checkin = book.getCheckInDate();
            
            int curDay = curDate.getDate();
            int inDay = checkin.getDate();
            int nights = (curDay - inDay);
            
            pst = myConn.prepareStatement("UPDATE booking SET checkout_date = ?,number_nights = ? WHERE booking_id = ?");
            pst.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
            pst.setInt(2,nights);
            pst.setInt(3,book.getBookingID());
            pst.executeUpdate();
            
            /* Record Departure */
            
            pst = myConn.prepareStatement("INSERT INTO departure_history (room_id,customer_id,date_departure,number_nights) VALUES(?,?,?,?)");
            pst.setInt(1,book.getRoomID());
            pst.setInt(2,book.getCustomerID());
            pst.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
            pst.setInt(4,nights);
            pst.executeUpdate();
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

    public void cancelBooking(int id)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("DELETE FROM booking WHERE booking_id = ?");
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
    
    public void updateBooking(Booking book)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE booking SET room_id = ?,book_date = ? WHERE booking_id = ?");
            pst.setInt(1,book.getRoomID());
            pst.setTimestamp(2,convertUtilToTimestamp(book.getBookDate()));
            pst.setInt(3,book.getBookingID());
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public double calculateRate(Booking book)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        double rate = 0;
        try{
            Date curDate = new Date();
            Date checkin = book.getCheckInDate();
            
            int curDay = curDate.getDate();
            int inDay = checkin.getDate();
            int nights = (curDay - inDay);
            
            if(book.getRoomType().equals("Single")){
                rate = (nights * 600);
            }
            if(book.getRoomType().equals("Double")){
                rate = (nights * 1200);
            }
            if(book.getRoomType().equals("Triple")){
                rate = (nights * 1800);
            }
            if(book.getRoomType().equals("Quad")){
                rate = (nights * 2400);
            }
            if(book.getRoomType().equals("Queen")){
                rate = (nights * 1500);
            }
            if(book.getRoomType().equals("King")){
                rate = (nights * 1500);
            }
            if(book.getRoomType().equals("Twin")){
                rate = (nights * 2000);
            }
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return rate;
    }
    
    public void recordData(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            CustomerRecordsDAO recordDAO = new CustomerRecordsDAO();
            ArrayList<Booking> checkouts = this.getHotelCheckOuts(id);
            ArrayList<CustomerRecords> records = recordDAO.getHotelRecords(id);
            
            /* Save to customer records */
            for(int i = 0;i<checkouts.size();i++){
                if(records.size() > 0){
                    for(int j = 0;j<records.size();j++){
                        if(!(checkouts.get(i).getBookingID() == records.get(j).getBookingID())){
                            pst = myConn.prepareStatement("INSERT INTO customer_records (booking_id,customer_id) VALUES(?,?)");
                            pst.setInt(1,checkouts.get(i).getBookingID());
                            pst.setInt(2,checkouts.get(i).getCustomerID());
                            pst.executeUpdate();
                        }
                    }
                    
                }
                
            }
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        
    }
    
    public ArrayList<Booking> refreshBookings()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Booking> list = new ArrayList<>();
        try{
            myConn.setAutoCommit(false);
            CustomerRecordsDAO recordDAO = new CustomerRecordsDAO();
            ArrayList<Booking> checkouts = this.getAllCheckedOuts();
            ArrayList<CustomerRecords> records = recordDAO.getAllRecords();
            
            /* Save to customer records */
            for(int i = 0;i<checkouts.size();i++){
                if(records.size() > 0){
                    for(int j = 0;j<records.size();j++){
                        if(!(checkouts.get(i).getBookingID() == records.get(j).getBookingID())){
                            pst = myConn.prepareStatement("INSERT INTO customer_records (booking_id,customer_id) VALUES(?,?)");
                            pst.setInt(1,checkouts.get(i).getBookingID());
                            pst.setInt(2,checkouts.get(i).getCustomerID());
                            pst.executeUpdate();
                        }
                    }
                    
                }
                
            }
            
            /* Hide all checkouts */
            pst = myConn.prepareStatement("SELECT bk.customer_id,bk.room_id,bk.hotel_id,hotel_name,customer_first,customer_last, "
                    + "room_type,book_date,checkin_date,checkout_date,number_nights FROM booking as bk,customer_detail as cu, room as rm,hotel as ht"
                    + "WHERE (bk.customer_id,bk.room_id,bk.hotel_id) = (cu.customer_id,rm.room_id,ht.hotel_id) ORDER BY book_date DESC");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getTimestamp("checkout_date") == null){
                    Booking book = convertRowToBooking(rs);
                    list.add(book);
                }
            }
            myConn.commit();
            return list;
            
        }
        catch(Exception exc){
            exc.printStackTrace();
            myConn.rollback();
        }
        finally{
            close(pst,rs);
        }
        return list;
    }
    
    public int getRoomID(int bookID)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
            pst = myConn.prepareStatement("SELECT room_id FROM booking WHERE booking_id = ?");
            pst.setInt(1,bookID);
            rs = pst.executeQuery();
            
            while(rs.next()){
                id = rs.getInt("room_id");
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
    
    public int getBookingIDByCustID(int cust_id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
            
            pst = myConn.prepareStatement("SELECT booking_id,checkin_date,checkout_date FROM booking WHERE customer_id = ? ");
            pst.setInt(1,cust_id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                if((rs.getTimestamp("checkin_date") == null) && (rs.getTimestamp("checkout_date") == null)){
                    id = rs.getInt("booking_id");
                }
                
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
    
    public int getBookingID(Date book_date)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
            
            pst = myConn.prepareStatement("SELECT booking_id FROM booking WHERE book_date = ?");
            pst.setTimestamp(1,convertUtilToTimestamp(book_date));
            rs = pst.executeQuery();
            
            while(rs.next()){
                id = rs.getInt("booking_id");
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
    
    public int getCustomerIDByRoomID(int roomID)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0 ;
        try{
            pst = myConn.prepareStatement("SELECT customer_id,checkin_date,checkou_date FROM booking WHERE room_id = ?");
            pst.setInt(1,roomID);
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getTimestamp("checkout_date")==null){
                    id = rs.getInt("customer_id");
                }
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
    
    public Date getCheckInDate(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        Date inDate = new Date();
        try{
            pst = myConn.prepareStatement("SELECT checkin_date FROM booking WHERE booking_id = ?");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                inDate = convertTimestampToUtil(rs.getTimestamp("checkin_date"));
            }
            return inDate;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return inDate;
    }
    
    public boolean checkIfRoomBooked(int room_id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT room_id,book_date,checkout_date FROM booking");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getDate("checkout_date") == null){
                    if(rs.getInt("room_id") == room_id){
                        return true;
                    }
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
    
    public boolean checkCustomerBooked(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT customer_id FROM booking");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getInt("customer_id") == id){
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
    
    public boolean checkHotelBookings(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT hotel_id FROM booking");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getInt("hotel_id") == id){
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
    
    public boolean checkIfBooked(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT book_date,checkout_date,checkin_date FROM booking WHERE booking_id = ?");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                if((rs.getTimestamp("book_date") != null) && (rs.getTimestamp("checkout_date") == null) && (rs.getTimestamp("checkin_date") == null)){
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
    
    public boolean checkIfBookingCheckedIn(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT book_date,checkout_date,checkin_date FROM booking WHERE booking_id = ?");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                if((rs.getTimestamp("book_date") != null) && (rs.getTimestamp("checkout_date") == null) && (rs.getTimestamp("checkin_date") != null)){
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
    
    public boolean checkIfCheckedIn(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT book_date,checkout_date,checkin_date FROM booking WHERE customer_id = ?");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                if((rs.getTimestamp("book_date") != null) && (rs.getTimestamp("checkout_date") == null) && (rs.getTimestamp("checkin_date") != null)){
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
    
    public boolean checkIfUserBooked(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT book_date,checkout_date,checkin_date FROM booking WHERE customer_id = ?");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                if((rs.getTimestamp("book_date") != null) && (rs.getTimestamp("checkout_date") == null) && (rs.getTimestamp("checkin_date") == null)){
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
    
    public ArrayList<Booking> getHotelCheckOuts(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Booking> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT booking_id,bk.customer_id,bk.room_id,bk.hotel_id,hotel_name,customer_first,customer_last, "
                    + "room_type,book_date,checkin_date,checkout_date,number_nights FROM booking as bk,customer_detail as cu, room as rm,hotel as ht "
                    + "WHERE (bk.customer_id,bk.room_id,bk.hotel_id) = (cu.customer_id,rm.room_id,ht.hotel_id) "
                    + "AND bk.hotel_id = ? ORDER BY book_date DESC");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                if((rs.getTimestamp("checkout_date") != null)){
                    Booking book = convertRowToBooking(rs);
                    list.add(book);
                }
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
    
    public ArrayList<Booking> getAllCheckedIns()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Booking> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT booking_id,bk.customer_id,bk.room_id,bk.hotel_id,hotel_name,customer_first,customer_last, "
                    + "room_type,book_date,checkin_date,checkout_date,number_nights FROM booking as bk,customer_detail as cu, room as rm,hotel as ht "
                    + "WHERE (bk.customer_id,bk.room_id,bk.hotel_id) = (cu.customer_id,rm.room_id,ht.hotel_id) ORDER BY book_date DESC");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if((rs.getTimestamp("checkout_date") == null) && (rs.getTimestamp("checkin_date")!= null)){
                    Booking book = convertRowToBooking(rs);
                    list.add(book);
                }
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
    
    public ArrayList<Booking> getAllCheckedOuts()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Booking> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT booking_id,bk.customer_id,bk.room_id,bk.hotel_id,hotel_name,customer_first,customer_last, "
                    + "room_type,book_date,checkin_date,checkout_date,number_nights FROM booking as bk,customer_detail as cu, room as rm,hotel as ht "
                    + "WHERE (bk.customer_id,bk.room_id,bk.hotel_id) = (cu.customer_id,rm.room_id,ht.hotel_id) ORDER BY book_date DESC");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getTimestamp("checkout_date")!=null){
                    Booking book = convertRowToBooking(rs);
                    list.add(book);
                }
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
    
    public ArrayList<Booking> getUserBookingDetails(int custID,int hotelID)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Booking> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT booking_id,bk.customer_id,bk.room_id,bk.hotel_id,hotel_name,customer_first,customer_last, "
                    + "room_type,book_date,checkin_date,checkout_date,number_nights FROM booking as bk,customer_detail as cu, room as rm,hotel as ht "
                    + "WHERE (bk.customer_id,bk.room_id,bk.hotel_id) = (cu.customer_id,rm.room_id,ht.hotel_id) AND (bk.customer_id,bk.hotel_id) = (?,?) ORDER BY book_date DESC");
            pst.setInt(1,custID);
            pst.setInt(2,hotelID);
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getTimestamp("checkout_date") == null){
                    Booking booking = convertRowToBooking(rs);
                    list.add(booking);
                }
                
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
        
    
    public ArrayList<Booking> getBookingDetails(int bookID)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Booking> list = new ArrayList<>();
        try{
           pst = myConn.prepareStatement("SELECT bk.customer_id,bk.room_id,bk.hotel_id,hotel_name,customer_first,customer_last, "
                    + "room_type,book_date,checkin_date,checkout_date,number_nights FROM booking as bk,customer_detail as cu, room as rm,hotel as ht"
                    + "WHERE (bk.customer_id,bk.room_id,bk.hotel_id) = (cu.customer_id,rm.room_id,ht.hotel_id) AND booking_id = ? ORDER BY book_date DESC");
            pst.setInt(1,bookID);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Booking booking = convertRowToBooking(rs);
                list.add(booking);
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
    
    public ArrayList<Booking> getHotelBookings(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Booking> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT booking_id,bk.customer_id,bk.room_id,bk.hotel_id,hotel_name,customer_first,customer_last, "
                    + "room_type,book_date,checkin_date,checkout_date,number_nights FROM booking as bk,customer_detail as cu, room as rm,hotel as ht "
                    + "WHERE (bk.customer_id,bk.room_id,bk.hotel_id) = (cu.customer_id,rm.room_id,ht.hotel_id) AND bk.hotel_id = ? ORDER BY book_date DESC");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Booking booking = convertRowToBooking(rs);
                list.add(booking);
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
    
    public ArrayList<Booking> getAllBookings()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Booking> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT booking_id,bk.customer_id,bk.room_id,bk.hotel_id,hotel_name,customer_first,customer_last, "
                    + "room_type,book_date,checkin_date,checkout_date,number_nights FROM booking as bk,customer_detail as cu, room as rm,hotel as ht "
                    + "WHERE (bk.customer_id,bk.room_id,bk.hotel_id) = (cu.customer_id,rm.room_id,ht.hotel_id) ORDER BY book_date DESC");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Booking booking = convertRowToBooking(rs);
                list.add(booking);
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
    
    public ArrayList<Booking> searchHotelBooking(int id,String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Booking> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT booking_id,bk.customer_id,bk.room_id,bk.hotel_id,hotel_name,customer_first,customer_last, "
                    + "room_type,book_date,checkin_date,checkout_date,number_nights FROM booking as bk,customer_detail as cu, room as rm,hotel as ht "
                    + "WHERE (bk.customer_id,bk.room_id,bk.hotel_id) = (cu.customer_id,rm.room_id,ht.hotel_id) "
                    + "AND CONCAT(hotel_name,customer_first,customer_last,room_type,book_date,checkin_date,checkout_date) LIKE '%"+search+"%'"
                    + "AND bk.hotel_id = ? ORDER BY book_date DESC");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Booking booking = convertRowToBooking(rs);
                list.add(booking);
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
    
    public ArrayList<Booking> searchBooking(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Booking> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT booking_id,bk.customer_id,bk.room_id,bk.hotel_id,hotel_name,customer_first,customer_last, "
                    + "room_type,book_date,checkin_date,checkout_date,number_nights FROM booking as bk,customer_detail as cu, room as rm,hotel as ht "
                    + "WHERE (bk.customer_id,bk.room_id,bk.hotel_id) = (cu.customer_id,rm.room_id,ht.hotel_id) "
                    + "AND CONCAT(hotel_name,customer_first,customer_last,room_type,book_date,checkin_date,checkout_date) LIKE '%"+search+"%' ORDER BY book_date DESC");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Booking booking = convertRowToBooking(rs);
                list.add(booking);
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
    
    private Timestamp convertUtilToTimestamp(Date uDate){
        Timestamp timestamp = new Timestamp(uDate.getTime());
        return timestamp;
    }
    
    private Date convertTimestampToUtil(Timestamp timestamp){
        Date uDate = new Date(timestamp.getTime());
        return uDate;
    }
    
    private Booking convertRowToBooking(ResultSet rs)throws SQLException{
        int book_id = rs.getInt("booking_id");
        int cust_id = rs.getInt("bk.customer_id");
        int room_id = rs.getInt("bk.room_id");
        int hotel_id = rs.getInt("bk.hotel_id");
        String hotel_name = rs.getString("hotel_name");
        String first = rs.getString("customer_first");
        String last = rs.getString("customer_last");
        String type = rs.getString("room_type");
        int nights  = rs.getInt("number_nights");
        
        Date book_date = new Date();
        Date checkin = new Date();
        Date checkout = new Date();
        boolean book = true;
        boolean checkedin = true;
        boolean checkedout = true;
        if(rs.getTimestamp("book_date")!=null){
            book = true;
        }
        else{
            book = false;
        }
        
        if(rs.getTimestamp("checkin_date")!=null){
            checkedin = true;
        }
        else{
            checkedin = false;
        }
        
        if(rs.getTimestamp("checkout_date")!=null){
            checkedout = true;
        }
        else{
            checkedout = false;
        }
        
        if((book == true) && (checkedin == false) && (checkedout == false)){
            book_date = convertTimestampToUtil(rs.getTimestamp("book_date"));
            theBooking = new Booking(hotel_name,first,last,type,book_date,null,null,nights);
            theBooking.setBookingID(book_id);
            theBooking.setCustomerID(cust_id);
            theBooking.setRoomID(room_id);
            theBooking.setHotelID(hotel_id);
        }
        if((book == true) && (checkedin == true) && (checkedout == false)){
            book_date = convertTimestampToUtil(rs.getTimestamp("book_date"));
            checkin = convertTimestampToUtil(rs.getTimestamp("checkin_date"));
            theBooking = new Booking(hotel_name,first,last,type,book_date,checkin,null,nights);
            theBooking.setBookingID(book_id);
            theBooking.setCustomerID(cust_id);
            theBooking.setRoomID(room_id);
            theBooking.setHotelID(hotel_id);
        }
        if((book == false) && (checkedin == true) && (checkedout = true)){
            checkout = convertTimestampToUtil(rs.getTimestamp("checkout_date"));
            checkin = convertTimestampToUtil(rs.getTimestamp("checkin_date"));
            theBooking = new Booking(hotel_name,first,last,type,null,checkin,checkout,nights);
            theBooking.setBookingID(book_id);
            theBooking.setCustomerID(cust_id);
            theBooking.setRoomID(room_id);
            theBooking.setHotelID(hotel_id);
        }
        if((book == true) && (checkedin = true) && (checkedout == true)){
            book_date = convertTimestampToUtil(rs.getTimestamp("book_date"));
            checkout = convertTimestampToUtil(rs.getTimestamp("checkout_date"));
            checkin = convertTimestampToUtil(rs.getTimestamp("checkin_date"));
            theBooking = new Booking(hotel_name,first,last,type,book_date,checkin,checkout,nights);
            theBooking.setBookingID(book_id);
            theBooking.setCustomerID(cust_id);
            theBooking.setRoomID(room_id);
            theBooking.setHotelID(hotel_id);
        }
        
        return theBooking;
        
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
