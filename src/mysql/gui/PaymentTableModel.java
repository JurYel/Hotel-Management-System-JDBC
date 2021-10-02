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
import mysql.core.Payment;

public class PaymentTableModel extends AbstractTableModel {

    public static final int OBJECT_COL = -1;
    private static final int HOTEL_COL = 0;
    private static final int FIRST_COL = 1;
    private static final int LAST_COL = 2;
    private static final int CARD_NUMBER_COL = 3;
    private static final int NIGHTS_COL = 4;
    private static final int AMOUNT_COL = 5;
    private static final int PAYDATE_COL = 6;
    private static final int PAYMENT_TYPE_COL = 7;

    private String[] columnNames = {"Hotel Name", "First Name", "Last Name", "Card Number", "Nights Stayed", "Amount", "Payment Date", "Payment Type"};
    private ArrayList<Payment> payments;

    public PaymentTableModel(ArrayList<Payment> list) {
        this.payments = list;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return payments.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Payment payment = payments.get(row);

        switch (col) {
            case HOTEL_COL:
                return payment.getHotelName();
            case FIRST_COL:
                return payment.getCustomerFirst();
            case LAST_COL:
                return payment.getCustomerLast();
            case CARD_NUMBER_COL:
                return payment.getCardNumber();
            case NIGHTS_COL:
                return payment.getNumberNights();
            case AMOUNT_COL:
                return payment.getAmount();
            case PAYDATE_COL:
                return payment.getStrPayDate();
            case PAYMENT_TYPE_COL:
                return payment.getPaymentType();
            case OBJECT_COL:
                return payment;
            default:
                return payment.getPaymentID();

        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }

}
