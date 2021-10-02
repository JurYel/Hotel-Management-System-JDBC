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
public class Customer {
    private int customer_id;
    private String email;
    private String customer_first;
    private String customer_last;
    private String phone_number;
    
    public Customer(String first,String last,String num,String email){
        super();
        this.customer_first = first;
        this.customer_last = last;
        this.phone_number = num;
        this.email = email;
    }
    
    public void setCustomerID(int id){
        this.customer_id = id;
    }
    
    public int getCustomerID(){
        return customer_id;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getCustomerFirst(){
        return customer_first;
    }
    
    public String getCustomerLast(){
        return customer_last;
    }
    
    public String getPhoneNumber(){
        return phone_number;
    }
}
