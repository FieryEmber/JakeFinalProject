/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.customermanagementsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginForm() {
        setTitle("Admin Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userField = new JTextField(15);
        passField = new JPasswordField(15);
        loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String pass = new String(passField.getPassword());
                
                //FIXME: Make an array to check for multiple login combinations
                if (user.equals("pthompso") && pass.equals("Rivercat2020!")) {
                    new CustomerManagementForm();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login!");
                }
            }
        });

        addComponents();
        setVisible(true);
    }

    private void addComponents() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(loginButton);
        add(panel);
    }
}

