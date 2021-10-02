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
import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;
import mysql.core.Employee;
import mysql.core.EncryptionPassword;

public class EmployeeDAO {
    private Connection myConn;
    
    public EmployeeDAO()throws SQLException{
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
    
    
    public void hireEmployee(Employee emp)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO employee (staff_id,hotel_id,emp_first,emp_last,phone_number,email,emp_position,password) "
                    + "VALUES(?,?,?,?,?,?,?,?)");
            pst.setString(1,emp.getStaffID());
            pst.setInt(2,emp.getHotelID());
            pst.setString(3,emp.getFirstName());
            pst.setString(4,emp.getLastName());
            pst.setString(5,emp.getPhoneNumber());
            pst.setString(6, emp.getEmail());
            pst.setString(7,emp.getPosition());
            pst.setString(8,EncryptionPassword.hashPassword(emp.getLastName().toUpperCase().getBytes(), "MD5"));
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void registerEmployee(Employee emp)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO employee (staff_id,hotel_id,emp_first,emp_last,phone_number,email,emp_position,password) "
                    + "VALUES(?,?,?,?,?,?,?,?)");
            pst.setString(1,emp.getStaffID());
            pst.setInt(2,emp.getHotelID());
            pst.setString(3,emp.getFirstName());
            pst.setString(4,emp.getLastName());
            pst.setString(5,emp.getPhoneNumber());
            pst.setString(6, emp.getEmail());
            pst.setString(7,emp.getPosition());
            pst.setString(8,EncryptionPassword.hashPassword(emp.getPassword().getBytes(), "MD5"));
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void updateEmployee(Employee emp)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE employee SET hotel_id = ?,emp_first = ?,emp_last = ?,phone_number = ?,email = ?,emp_position = ? "
                    + "WHERE staff_id = ?");
            pst.setInt(1,emp.getHotelID());
            pst.setString(2,emp.getFirstName());
            pst.setString(3,emp.getLastName());
            pst.setString(4, emp.getPhoneNumber());
            pst.setString(5,emp.getEmail());
            pst.setString(6,emp.getPosition());
            pst.setString(7,emp.getStaffID());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void fireEmployee(String empID)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("DELETE FROM employee WHERE staff_id = ?");
            pst.setString(1,empID);
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public boolean checkIfManager(String id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT emp_position FROM employee WHERE staff_id = ?");
            pst.setString(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                String pos = rs.getString("emp_position");
                if(pos.equals("Hotel Manager")){
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
    
    public boolean checkHotelEmployees(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT hotel_id FROM employee");
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
        
    
    public boolean checkIfStaff(String id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT emp_position FROM employee WHERE staff_id = ?");
            pst.setString(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                String pos = rs.getString("emp_position");
                if(pos.equals("Hotel Employee")){
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
    
    public boolean checkEmployeeName(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT emp_first,emp_last FROM employee");
            rs = pst.executeQuery();
            
            while(rs.next()){
                String theName = String.format("%s %s",rs.getString("emp_first"),rs.getString("emp_last"));
                if(theName.equals(name)){
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
    
    public boolean checkEmployeePassword(String id,String pass)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT password FROM employee WHERE staff_id = ?");
            pst.setString(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                if((rs.getString("password").equals(EncryptionPassword.hashPassword(pass.getBytes(),"MD5"))) 
                        || rs.getString("password").equals(pass)){
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
    
    public boolean checkEmployeeID(String id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT staff_id FROM employee");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getString("staff_id").equals(id)){
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
    
    public ArrayList<Employee> getHotelEmployees(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Employee> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT staff_id,emp.hotel_id,hotel_name,emp_first,emp_last,emp.phone_number,email,emp_position "
                    + "FROM employee as emp, hotel as ht WHERE emp.hotel_id = ht.hotel_id AND emp.hotel_id = ?");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Employee employee = convertRowToEmployee(rs);
                list.add(employee);
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
    
    public ArrayList<Employee> getAllEmployees()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Employee> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT staff_id,emp.hotel_id,hotel_name,emp_first,emp_last,emp.phone_number,email,emp_position "
                    + "FROM employee as emp, hotel as ht WHERE emp.hotel_id = ht.hotel_id");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Employee employee = convertRowToEmployee(rs);
                list.add(employee);
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
    
    public ArrayList<Employee> searchHotelEmployee(int id,String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Employee> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT staff_id, emp.hotel_id,hotel_name,emp_first,emp_last,emp.phone_number,email,emp_position "
                    + "FROM employee as emp, hotel as ht WHERE emp.hotel_id = ht.hotel_id AND emp.hotel_id = ?"
                    + " AND CONCAT(staff_id,hotel_name,emp_first,emp_last) LIKE '%"+search+"%'");
            pst.setInt(1,id);
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                Employee employee = convertRowToEmployee(rs);
                list.add(employee);
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
    
    public ArrayList<Employee> searchEmployee(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Employee> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT staff_id, emp.hotel_id,hotel_name,emp_first,emp_last,emp.phone_number,email,emp_position "
                    + "FROM employee as emp, hotel as ht WHERE emp.hotel_id = ht.hotel_id AND CONCAT(staff_id,hotel_name,emp_first,emp_last) LIKE '%"+search+"%'");
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                Employee employee = convertRowToEmployee(rs);
                list.add(employee);
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
    
    private Employee convertRowToEmployee(ResultSet rs)throws SQLException{
        String staff_id = rs.getString("staff_id");
        int hotel_id = rs.getInt("emp.hotel_id");
        String hotel_name = rs.getString("hotel_name");
        String email = rs.getString("email");
        String first = rs.getString("emp_first");
        String last  = rs.getString("emp_last");
        String phone_number = rs.getString("emp.phone_number");
        String emp_pos = rs.getString("emp_position");
        
        Employee employee = new Employee(staff_id,hotel_name,first,last,phone_number,email,emp_pos);
        employee.setHotelID(hotel_id);
        
        return employee;
        
    }
    
    private void close(PreparedStatement pst,ResultSet rs)throws SQLException{
        if(pst!=null){
            pst.close();
        }
        if(rs !=null){
            rs.close();
        }
    }
}
