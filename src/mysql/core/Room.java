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
public class Room {
    private int room_id;
    private int hotel_id;
    private String hotel_name;
    private String room_desc;
    private String room_type;
    private double room_rate;
    private int quantity;
    
    public Room(String hotel,String desc,String type){
        super();
        this.hotel_name = hotel;
        this.room_desc = desc;
        this.room_type = type;
    }
    
    public void setHotelID(int id){
        this.hotel_id = id;
    }
    
    public void setRoomID(int id){
        this.room_id = id;
    }
    
    public void setQuantity(int qty){
        this.quantity = qty;
    }
    
    public void setRoomRate(double rate){
        this.room_rate = rate;
    }
    
    public int getRoomID(){
        return room_id;
    }
    
    public int getHotelID(){
        return hotel_id;
    }
    
    public String getHotelName(){
        return hotel_name;
    }
    
    public String getRoomDesc(){
        return room_desc;
    }
    
    public String getRoomTypes(){
        return room_type;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    public double getRoomRate(){
        return room_rate;
    }
}
