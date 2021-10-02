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
import java.util.Date;
public class DepartureHistory {
    private int departure_id;
    private int room_id;
    private int customer_id;
    private String customer_first;
    private String customer_last;
    private String room_type;
    private Date date_depart;
    private String str_depart;
    private int nights;
    
    public DepartureHistory(String first,String last,String type,Date date,int nts){
        super();
        this.customer_first = first;
        this.customer_last = last;
        this.room_type = type;
        this.date_depart = date;
        this.nights = nts;
    }
    
    public void setDepartureID(int id){
        this.departure_id = id;
    }
    
    public void setRoomID(int id){
        this.room_id = id;
    }
    
    public void setCustomerID(int id){
        this.customer_id = id;
    }
    
    public void setStrDepart(String date){
        this.str_depart = date;
    }
    
    public int getDepartureID(){
        return departure_id;
    }
    
    public int getRoomID(){
        return room_id;
    }
    
    public int getCustomerID(){
        return customer_id;
    }
    
    public String getCustomerFirst(){
        return customer_first;
    }
    
    public String getCustomerLast(){
        return customer_last;
    }
    
    public String getRoomType(){
        return room_type;
    }
    
    public int getNumberNights(){
        return nights;
    }
    
    public String getStrDepart(){
        return str_depart;
    }
    
    public Date getDateDeparture(){
        return date_depart;
    }
}
    

