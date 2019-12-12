/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oso_app;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author gemss
 */
public class config {
    
    Connection con;
    Statement st;
    ResultSet rs;
    
    
    public Connection configDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String user = "gemss";
            String pass = "r00t";
            
            con = DriverManager.getConnection("jdbc:mysql://localhost/javaxx?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC", user, pass);
            st = con.createStatement();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Koneksi Gagal : " + e);
        }    
        return con;
    }
    
}
