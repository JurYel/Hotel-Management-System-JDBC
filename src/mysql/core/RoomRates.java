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
public class RoomRates {
    private int room_id;
    private String room_type;
    private double room_rate;
    
    public RoomRates(String type,double rate){
        super();
        this.room_type = type;
        this.room_rate = rate;
    }
    
    public void setRoomID(int id){
        this.room_id = id;
    }
    
    public int getRoomID(){
        return room_id;
    }
    
    public String getRoomType(){
        return room_type;
    }
    
    public double getRoomRate(){
        return room_rate;
    }
}
