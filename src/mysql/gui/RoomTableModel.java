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
import mysql.core.Room;
import javax.swing.table.AbstractTableModel;
public class RoomTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int ROOM_DESC_COL = 0;
    private static final int ROOM_TYPE_COL = 1;
    private static final int ROOM_RATE_COL = 2;
    
    private String[] columnNames = {"Room Description","Room Type","Room Rate"};
    private ArrayList<Room> rooms;
    
    public RoomTableModel(ArrayList<Room> list){
        this.rooms = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return rooms.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row, int col){
        Room room = rooms.get(row);
        
        switch(col){
            case ROOM_DESC_COL:
                return room.getRoomDesc();
            case ROOM_TYPE_COL:
                return room.getRoomTypes();
            case ROOM_RATE_COL:
                return room.getRoomRate();
            case OBJECT_COL:
                return room;
            default:
                return room.getRoomID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
