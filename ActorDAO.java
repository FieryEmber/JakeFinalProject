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

public class ActorDAO {
    public List<Actor> getAllActors() {
        List<Actor> actors = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM actor");
            while (rs.next()) {
                Actor actor = new Actor();
                actor.setId(rs.getInt("actor_id"));
                actor.setFirstName(rs.getString("first_name"));
                actor.setLastName(rs.getString("last_name"));
                actors.add(actor);
            }
        } catch (SQLException e) {
            System.out.println("Error. Details: " + e.getMessage());
        }
        return actors;
    }

    public void addActor(Actor actor) {
        String query = "INSERT INTO actor (first_name, last_name) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, actor.getFirstName());
            stmt.setString(2, actor.getLastName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error. Details: " + e.getMessage());
        }
    }

    public void updateActor(Actor actor) {
        String query = "UPDATE actor SET first_name = ?, last_name = ? WHERE actor_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, actor.getFirstName());
            stmt.setString(2, actor.getLastName());
            stmt.setInt(3, actor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error. Details: " + e.getMessage());
        }
    }

    public void deleteActor(Actor actor, int id) throws SQLException{
        String query = "DELETE FROM actor WHERE actor_id = ?";
        
        //Tries to delete selected actor, gives error window on failure
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            // MySQL error code for foreign key constraint violation is 1451 so we check for that specific code here and display a different error message if it matches
            if (e.getErrorCode() == 1451) { 
                throw new SQLException("Cannot delete \"" + actor.getFirstName() + " " + actor.getLastName() + "\" because there are existing dependencies linked to this actor");
            }
            else {
                throw new SQLException("Error. Details: " + e.getMessage(), e);
            }
        }
    }
}