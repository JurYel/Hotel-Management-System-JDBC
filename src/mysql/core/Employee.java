/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql.core;

/**
 *
 * @author Jur Yel
 */
public class Employee {
    private String staff_id;
    private String email;
    private String emp_first;
    private String emp_last;
    private String phone_number;
    private int hotel_id;
    private String hotel_name;
    private String emp_pos;
    private String password;
    
    public Employee(String stfID, String hotel,String first,String last,String phone,String email,String pos){
        super();
        this.staff_id = stfID;
        this.hotel_name = hotel;
        this.emp_first = first;
        this.emp_last = last;
        this.phone_number = phone;
        this.email = email;
        this.emp_pos = pos;
    }
    
    public void setStaffID(String id){
        this.staff_id = id;
    }
    
    public void setHotelID(int id){
        this.hotel_id = id;
    }
    
    public void setPassword(String pass){
        this.password = pass;
    }
    
    public String getStaffID(){
        return staff_id;
    }
    
    public int getHotelID(){
        return hotel_id;
    }
    
    public String getHotelName(){
        return hotel_name;
    }
    
    public String getFirstName(){
        return emp_first;
    }
    
    public String getLastName(){
        return emp_last;
    }
    
    public String getPhoneNumber(){
        return phone_number;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getPosition(){
        return emp_pos;
    }
    
    public String getPassword(){
        return password;
    }
}
