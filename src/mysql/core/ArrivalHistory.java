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
public class ArrivalHistory {
    private int arrival_id;
    private int customer_id;
    private String customer_first;
    private String customer_last;
    private Date arrival_date;
    private String str_arrival;
    
    public ArrivalHistory(String first,String last,Date arrival){
        super();
        this.customer_first = first;
        this.customer_last = last;
        this.arrival_date = arrival;
    }
    
    public void setArrivalID(int id){
        this.arrival_id = id;
    }
    
    public void setCustomerID(int id){
        this.customer_id = id;
    }
    
    public void setStrArrival(String date){
        this.str_arrival = date;
    }
    
    public int getArrivalID(){
        return arrival_id;
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
    
    public Date getArrivalDate(){
        return arrival_date;
    }
    
    public String getStrArrival(){
        return str_arrival;
    }
}
