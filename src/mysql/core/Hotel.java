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
public class Hotel {
    private int hotel_id;
    private String manager;
    private String hotel_name;
    private String street;
    private String barangay;
    private String city;
    private String phone_number;
    private int employees;
    
    public Hotel(String hotel,String mgr,String str,String brgy,String ct,String num){
        super();
        this.hotel_name = hotel;
        this.manager = mgr;
        this.street = str;
        this.barangay = brgy;
        this.city = ct;
        this.phone_number = num;
    }
    
    public void setHotelID(int id){
        this.hotel_id = id;
    }
    
    public int getHotelID(){
        return hotel_id;
    }
    
    public void setEmployees(int num){
        this.employees = num;
    }
    
    public String getManager(){
        return manager;
    }
    
    public String getHotelName(){
        return hotel_name;
    }
    
    public String getStreet(){
        return street;
    }
    
    public String getBarangay(){
        return barangay;
    }
    
    public String getCity(){
        return city;
    }
    
    public String getPhoneNumber(){
        return phone_number;
    }
    
    public int getEmployees(){
        return employees;
    }
    
    public String getAddress(){
        return String.format("%s %s, %s",street,barangay,city);
    }
}
