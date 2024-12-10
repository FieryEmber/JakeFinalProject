/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.customermanagementsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LoginForm extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private Map<String, String> userPasswords;

    public LoginForm() {
        setTitle("Admin Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userField = new JTextField(15);
        passField = new JPasswordField(15);
        loginButton = new JButton("Login");
        
        //Hash Map stores combinations of usernames and passwords to be checked by the login
        userPasswords = new HashMap<>();
        userPasswords.put("root", "Likeaboss360");
        userPasswords.put("jalbohn22", "LikeaBOSS360");
        userPasswords.put("pthompso", "Rivercat2020!");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String pass = new String(passField.getPassword());
      
                //Checks if the Hash Map contains a valid user/password combination
                if (userPasswords.containsKey(user) && userPasswords.get(user).equals(pass)) {
                    new CustomerMgtMain().setVisible(true);
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

