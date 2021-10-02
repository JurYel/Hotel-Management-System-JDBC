/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql.gui;

/**
 *
 * @author Jur Yel
 */
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import mysql.core.Customer;

public class CustomerTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int FIRSTNAME_COL = 0;
    private static final int LASTNAME_COL = 1;
    private static final int PHONE_NUMBER_COL = 2;
    private static final int EMAIL_COL = 3;
    
    private String[] columnNames = {"First Name","Last Name","Phone Number","Email"};
    private ArrayList<Customer> customers;
    
    public CustomerTableModel(ArrayList<Customer> list){
        this.customers = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return customers.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        Customer customer = customers.get(row);
        
        switch(col){
            case FIRSTNAME_COL:
                return customer.getCustomerFirst();
            case LASTNAME_COL:
                return customer.getCustomerLast();
            case PHONE_NUMBER_COL:
                return customer.getPhoneNumber();
            case EMAIL_COL:
                return customer.getEmail();
            case OBJECT_COL:
                return customer;
            default:
                return customer.getCustomerID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
