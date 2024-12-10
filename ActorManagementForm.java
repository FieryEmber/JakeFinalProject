package com.mycompany.customermanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ActorManagementForm extends JFrame {
    private JTable actorTable;
    private DefaultTableModel tableModel;
    private ActorDAO actorDAO = new ActorDAO();

    public ActorManagementForm() {
        setTitle("Actor Management");
        setSize(600, 400);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener (new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent evt) {
            new CustomerMgtMain().setVisible(true);
            dispose();
            }
        });

        // Initialize table and model
        tableModel = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name"}, 0);
        actorTable = new JTable(tableModel);
        loadActors();

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddActorDialog();
                loadActors();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editActor();
                loadActors();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteActor();
                loadActors();
            }
        });

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.setBackground(new java.awt.Color(0, 153, 153));
        panel.setFont(new java.awt.Font("Comic Sans MS", 0, 14));

        add(new JScrollPane(actorTable), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadActors() {
        tableModel.setRowCount(0);
        List<Actor> actors = actorDAO.getAllActors();
        for (Actor actor : actors) {
            tableModel.addRow(new Object[]{actor.getId(), actor.getFirstName(), actor.getLastName()});
        }
    }
    /*
    
    */

private void showAddActorDialog() {
        JTextField firstNameField = new JTextField(10);
        JTextField lastNameField = new JTextField(10);

        JPanel addPanel = new JPanel();
        addPanel.add(new JLabel("First Name:"));
        addPanel.add(firstNameField);
        addPanel.add(new JLabel("Last Name:"));
        addPanel.add(lastNameField);

        int result = JOptionPane.showConfirmDialog(null, addPanel, "Add New Actor", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();

            if (!firstName.isEmpty() && !lastName.isEmpty()) {
                Actor actor = new Actor();
                actor.setFirstName(firstName);
                actor.setLastName(lastName);
                actorDAO.addActor(actor);
                JOptionPane.showMessageDialog(this, "Actor added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

private void editActor() {
        int selectedRow = actorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a actor to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Retrieve selected actor's information
        int actorId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentFirstName = (String) tableModel.getValueAt(selectedRow, 1);
        String currentLastName = (String) tableModel.getValueAt(selectedRow, 2);

        // Create input fields pre-filled with current actor details
        JTextField firstNameField = new JTextField(currentFirstName, 10);
        JTextField lastNameField = new JTextField(currentLastName, 10);

        JPanel editPanel = new JPanel();
        editPanel.add(new JLabel("First Name:"));
        editPanel.add(firstNameField);
        editPanel.add(new JLabel("Last Name:"));
        editPanel.add(lastNameField);

        int result = JOptionPane.showConfirmDialog(null, editPanel, "Edit Actor", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String newFirstName = firstNameField.getText();
            String newLastName = lastNameField.getText();

            if (!newFirstName.isEmpty() && !newLastName.isEmpty()) {
                // Update actor object and database
                Actor actor = new Actor();
                actor.setId(actorId);
                actor.setFirstName(newFirstName);
                actor.setLastName(newLastName);

                actorDAO.updateActor(actor); // Calls update method in DAO
                JOptionPane.showMessageDialog(this, "Actor updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteActor() {
        int selectedRow = actorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an actor to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int actorId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentFirstName = (String) tableModel.getValueAt(selectedRow, 1);
        String currentLastName = (String) tableModel.getValueAt(selectedRow, 2);
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete \"" + currentFirstName + " " + currentLastName + "\"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            Actor actor = new Actor();
            actor.setFirstName(currentFirstName);
            actor.setLastName(currentLastName);
            
        try {
            actorDAO.deleteActor(actor, actorId);
            JOptionPane.showMessageDialog(this, "Actor deleted successfully!");
            }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}