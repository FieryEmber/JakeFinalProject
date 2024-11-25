/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.customermanagementsystem;

/**
 *
 * @author PTHOMPSO
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error in connection");
        }
        return customers;
    }

    public void addCustomer(Customer customer) {
        String query = "INSERT INTO customer (first_name, last_name, email) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Connection error");
        }
    }

    public void updateCustomer(Customer customer) {
        String query = "UPDATE customer SET first_name = ?, last_name = ?, email = ? WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setInt(4, customer.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in connection");
        }
    }

    public void deleteCustomer(int id) {
        String query = "DELETE FROM customer WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in connection");
        }
    }
}
