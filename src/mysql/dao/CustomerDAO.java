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
import mysql.core.Customer;

public class CustomerDAO {
    private Connection myConn;
    
    public CustomerDAO()throws SQLException{
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
    
    public void addCustomer(Customer customer)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO customer_detail (customer_first,customer_last,email,phone_number) VALUES(?,?,?,?)");
            pst.setString(1,customer.getCustomerFirst());
            pst.setString(2,customer.getCustomerLast());
            pst.setString(3,customer.getEmail());
            pst.setString(4,customer.getPhoneNumber());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void updateCustomer(Customer customer)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE customer_detail SET customer_first = ?,customer_last = ?,email = ?,phone_number = ? WHERE customer_id = ?");
            pst.setString(1,customer.getCustomerFirst());
            pst.setString(2,customer.getCustomerLast());
            pst.setString(3,customer.getEmail());
            pst.setString(4,customer.getPhoneNumber());
            pst.setInt(5,customer.getCustomerID());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void deleteCustomer(int id)throws SQLException{
        PreparedStatement pst = null;
        try{
            myConn.setAutoCommit(false);
            
            /* Delete from Arrival History */
            
            pst = myConn.prepareStatement("DELETE FROM arrival_history WHERE customer_id = ?");
            pst.setInt(1,id);
            pst.executeUpdate();
            
            /* Delete from Departure History */
            
            pst = myConn.prepareStatement("DELETE FROM departure_history WHERE customer_id = ?");
            pst.setInt(1,id);
            pst.executeUpdate();
            
            /* Delete from Customer Table */
            
            pst = myConn.prepareStatement("DELETE FROM customer_detail WHERE customer_id = ?");
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
    
    public int getCustomerID(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
            pst = myConn.prepareStatement("SELECT customer_id,customer_first,customer_last FROM customer_detail");
            rs = pst.executeQuery();
            
            while(rs.next()){
                String cust = String.format("%s %s",rs.getString("customer_first"),rs.getString("customer_last"));
                if(cust.equals(name)){
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
    
    public boolean checkCustomerName(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
             pst = myConn.prepareStatement("SELECT customer_id,customer_first,customer_last FROM customer_detail");
            rs = pst.executeQuery();
            
            while(rs.next()){
                String cust = String.format("%s %s",rs.getString("customer_first"),rs.getString("customer_last"));
                if(cust.equals(name)){
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
    
    public ArrayList<Customer> getAllCustomers()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Customer> list = new ArrayList<>();
        try{
             pst = myConn.prepareStatement("SELECT customer_id,customer_first,customer_last,email,phone_number "
                    + "FROM customer_detail ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Customer customer = convertRowToCustomer(rs);
                list.add(customer);
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
    
    public ArrayList<Customer> searchCustomer(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Customer> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT customer_id,customer_first,customer_last,email,phone_number "
                    + "FROM customer_detail WHERE CONCAT(customer_first,customer_last) LIKE '%"+search+"%'");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Customer customer = convertRowToCustomer(rs);
                list.add(customer);
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
    
    private Customer convertRowToCustomer(ResultSet rs)throws SQLException{
        int custID = rs.getInt("customer_id");
        String email = rs.getString("email");
        String first = rs.getString("customer_first");
        String last = rs.getString("customer_last");
        String phone = rs.getString("phone_number");
        
        Customer customer = new Customer(first,last,phone,email);
        customer.setCustomerID(custID);
        return customer;
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
