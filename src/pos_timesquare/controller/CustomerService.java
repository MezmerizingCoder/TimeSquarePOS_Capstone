/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_timesquare.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pos_timesquare.controller.DatabaseConnection.getConnection;
import pos_timesquare.model.Customer;
import pos_timesquare.model.User;

/**
 *
 * @author Acer
 */
public class CustomerService {
    
    Connection conn = null;
    PreparedStatement pst;
    ResultSet rs;
    
    public CustomerService(){
        Connection conn = getConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, "Customer", null);
            if (tables.next()) {
                System.out.println("Exist");
                
                if(!dbmd.getColumns(null, null, "Customer", "name").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Customer ADD name TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Customer", "address").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Customer ADD address TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Customer", "birthDate").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Customer ADD birthDate TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Customer", "contactNum").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Customer ADD contactNum TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Customer", "gender").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Customer ADD gender TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Customer", "image").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Customer ADD image TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Customer", "membershipDate").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Customer ADD membershipDate TEXT"; 
                    stmt.executeUpdate(sql);
                }
                if(!dbmd.getColumns(null, null, "Customer", "status").next()){
                    Statement stmt = conn.createStatement();
                    String sql = "ALTER TABLE Customer ADD status TEXT"; 
                    stmt.executeUpdate(sql);
                }
                
            }
            else {
                System.out.println("Not exist");
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Customer(" +
                   "id INTEGER NOT NULL UNIQUE," +
                   " name TEXT, " + 
                   " address TEXT, " +
                   " membershipDate TEXT, " +
                   " image TEXT, " +
                   " gender TEXT, " +
                   " birthdate TEXT, " +
                   " contactNum TEXT, " +
                   " status TEXT, " +
                   "PRIMARY KEY(id AUTOINCREMENT))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");  
                conn.close();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<Customer> getAllCustomer(){
        
        Connection conn = getConnection();
        List<Customer> customers = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Customer");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(rs.getString("id")));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setMembershipDate(rs.getString("membershipDate"));
                customer.setImage(rs.getString("image"));
                customer.setGender(rs.getString("gender"));
                customer.setBirthDate(rs.getString("birthdate"));
                customer.setStatus(rs.getString("status"));
                customer.setContactNum(rs.getString("contactNum"));
                
                customers.add(customer);
            }
            conn.close();
            return customers;
            
        } catch (SQLException ex) {
            return null;
        }
    
    }
    
    public List<Customer> getAllCustomerByName(String name){
        
        Connection conn = getConnection();
        List<Customer> customers = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Customer WHERE name LIKE '" + name + "%' AND status != 'Deleted'");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(rs.getString("id")));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setMembershipDate(rs.getString("membershipDate"));
                customer.setImage(rs.getString("image"));
                customer.setGender(rs.getString("gender"));
                customer.setBirthDate(rs.getString("birthdate"));
                customer.setStatus(rs.getString("status"));
                customer.setContactNum(rs.getString("contactNum"));
                
                customers.add(customer);
            }
            conn.close();
            return customers;
            
        } catch (SQLException ex) {
            return null;
        }
    
    }
    
    public List<Customer> getAllDeletedCustomer(){
        
        Connection conn = getConnection();
        List<Customer> customers = new ArrayList<>();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Customer WHERE status == 'Deleted'");
            rs = pst.executeQuery();
            
            
            while(rs.next()){
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(rs.getString("id")));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setMembershipDate(rs.getString("membershipDate"));
                customer.setImage(rs.getString("image"));
                customer.setGender(rs.getString("gender"));
                customer.setBirthDate(rs.getString("birthdate"));
                customer.setStatus(rs.getString("status"));
                customer.setContactNum(rs.getString("contactNum"));
                
                customers.add(customer);
            }
            conn.close();
            return customers;
            
        } catch (SQLException ex) {
            return null;
        }
    
    }
    
    public Customer getCustomerById(int id){
        
        Connection conn = getConnection();
        
        try {
            System.out.println("Getting data");
            pst = conn.prepareStatement("SELECT * FROM Customer WHERE id ==" + id);
            rs = pst.executeQuery();
            
            
            Customer customer = new Customer();
            customer.setId(Integer.parseInt(rs.getString("id")));
            customer.setName(rs.getString("name"));
            customer.setAddress(rs.getString("address"));
            customer.setMembershipDate(rs.getString("membershipDate"));
            customer.setImage(rs.getString("image"));
            customer.setGender(rs.getString("gender"));
            customer.setBirthDate(rs.getString("birthdate"));
            customer.setStatus(rs.getString("status"));
            customer.setContactNum(rs.getString("contactNum"));
                
            conn.close();
            return customer;
            
        } catch (SQLException ex) {
            return null;
        }
    
    }
    
    public int addCustomer(Customer customer){
        try {
            Connection conn = getConnection();
            
            pst = conn.prepareStatement("INSERT INTO Customer VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            pst.setString(1, null);
            pst.setString(2, customer.getName());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getMembershipDate());
            pst.setString(5, customer.getImage());
            pst.setString(6, customer.getGender());
            pst.setString(7, customer.getBirthDate());
            pst.setString(8, customer.getContactNum());
            pst.setString(9, customer.getStatus());
            
            pst.executeUpdate();
            
            
            ResultSet rs  = pst.getGeneratedKeys();
            
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            
            conn.close();
            
            
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
    }
    
    public void updateCustomer(int id, Customer customer){
        try {
            Connection conn = getConnection();
            pst = conn.prepareStatement("UPDATE Customer SET name=?, address=?, membershipDate=?, image=?, gender=?, birthdate=?, contactNum=?, status=?  WHERE id = " + id);
            
            pst.setString(1, customer.getName());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getMembershipDate());
            pst.setString(4, customer.getImage());
            pst.setString(5, customer.getGender());
            pst.setString(6, customer.getBirthDate());
            pst.setString(7, customer.getContactNum());
            pst.setString(8, customer.getStatus());
            
            pst.executeUpdate();
            
            System.out.println("Update Success");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
