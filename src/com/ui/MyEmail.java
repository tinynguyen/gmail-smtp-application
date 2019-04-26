/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import com.entity.SMTPServer;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author tiny
 */
public class MyEmail implements ActionListener {

    JLabel lFrom, lTo, lSubject, lServer, lUsername, lPassword, lMessage;
    JTextField tfFrom, tfTo, tfSubject, tfUsername;
    JComboBox<SMTPServer> cbServer;
    JPasswordField pfPassword;
    JTextArea taMessage;
    JButton bSend;
    JPanel pnlTop;
    
    GridBagConstraints gbc = new GridBagConstraints();

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Send E-Mail Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentsToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void addComponentsToPane(Container frame) {

        // Set frame layout
        frame.setLayout(new BorderLayout());
        // Init components
        initComponents();

        addComponents();

        frame.add(pnlTop, BorderLayout.PAGE_START);
        frame.add(taMessage, BorderLayout.CENTER);
        frame.add(bSend, BorderLayout.PAGE_END);
    }

    private void addComponents() {

        // Set layout for panel top
        pnlTop.setLayout(new GridBagLayout());

        // Set position for all components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // All labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 0);
        pnlTop.add(lFrom, gbc);

        gbc.insets = new Insets(0, 10, 5, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlTop.add(lTo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlTop.add(lSubject, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlTop.add(lServer, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        pnlTop.add(lUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        pnlTop.add(lPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        pnlTop.add(lMessage, gbc);

        // All text field
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 30, 5, 10);
        pnlTop.add(tfFrom, gbc);

        gbc.insets = new Insets(0, 30, 5, 10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlTop.add(tfTo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        pnlTop.add(tfSubject, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        pnlTop.add(cbServer, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        pnlTop.add(tfUsername, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        pnlTop.add(pfPassword, gbc);

        // Add text area
    }

    private void initComponents() {
        // Init top panel
        pnlTop = new JPanel();

        // Init all label
        lFrom = new JLabel("From:");
        lTo = new JLabel("To:");
        lSubject = new JLabel("Subject:");
        lServer = new JLabel("SMTP Server:");
        lUsername = new JLabel("Username:");
        lPassword = new JLabel("Password:");
        lMessage = new JLabel("Message:");

        // Init all text field
        tfFrom = new JTextField(20);
        tfSubject = new JTextField(20);
        tfTo = new JTextField(20);
        tfUsername = new JTextField(20);

        // Init password field
        pfPassword = new JPasswordField();

        // Init combobox
        cbServer = new JComboBox<>();

        // Init text area
        bSend = new JButton("Send E-Mail");

        // Init text area
        taMessage = new JTextArea();
        taMessage.setRows(8);
        taMessage.setLineWrap(true);
        taMessage.setWrapStyleWord(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
    }

}
