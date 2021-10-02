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
import mysql.core.Employee;

public class EmployeeTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int HOTEL_NAME_COL = 0;
    private static final int STAFF_ID_COL = 1;
    private static final int FIRST_NAME_COL = 2;
    private static final int LAST_NAME_COL = 3;
    private static final int POSITION_COL = 4;
    private static final int PHONE_NUMBER_COL = 5;
    private static final int EMAIL_COL = 6;
    
    private String[] columnNames = {"Hotel Name","Staff ID","First Name","Last Name","Position","Phone Number","Email"};
    private ArrayList<Employee> employees;
    
    public EmployeeTableModel(ArrayList<Employee> list){
        this.employees = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return employees.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        Employee employee = employees.get(row);
        
        switch(col){
            case HOTEL_NAME_COL:
                return employee.getHotelName();
            case STAFF_ID_COL:
                return employee.getStaffID();
            case FIRST_NAME_COL:
                return employee.getFirstName();
            case LAST_NAME_COL:
                return employee.getLastName();
            case POSITION_COL:
                return employee.getPosition();
            case PHONE_NUMBER_COL:
                return employee.getPhoneNumber();
            case EMAIL_COL:
                return employee.getEmail();
            case OBJECT_COL:
                return employee;
            default: 
                return employee.getStaffID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
