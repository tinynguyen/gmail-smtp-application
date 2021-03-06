/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import com.business.MyMail;
import com.entity.MailMessage;
import com.entity.SMTPServer;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author tiny
 */
public class MyEmail implements ActionListener {

    JLabel lFrom, lTo, lSubject, lServer, lUsername, lPassword, lMessage, lFileChooser;
    JTextField tfFrom, tfTo, tfSubject, tfUsername, tfFileChoose;
    JComboBox cbServer;
    JPasswordField pfPassword;
    JTextArea taMessage;
    JButton bSend, bChoose;
    JPanel pnlTop;
    JFileChooser fc;

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

        // Add all components to panel top 
        addComponentsToPanelTop();

        frame.add(pnlTop, BorderLayout.PAGE_START);
        frame.add(taMessage, BorderLayout.CENTER);
        frame.add(bSend, BorderLayout.PAGE_END);
    }

    private void addComponentsToPanelTop() {

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
        pnlTop.add(lFileChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
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

        gbc.gridx = 1;
        gbc.gridy = 6;
        pnlTop.add(tfFileChoose, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 5, 10);
        pnlTop.add(bChoose, gbc);
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
        lFileChooser = new JLabel("Attach File:");

        // Init all text field
        tfFrom = new JTextField(20);
        tfSubject = new JTextField(20);
        tfTo = new JTextField(20);
        tfUsername = new JTextField(20);
        tfFileChoose = new JTextField(10);

        // Init password field
        pfPassword = new JPasswordField();

        // Init combobox
        String[] server = {"smtp.gmail.com(SSL)", "smtp.gmail.com(TLS)"};
        cbServer = new JComboBox(server);

        // Init text area
        bSend = new JButton("Send E-Mail");
        bSend.addActionListener(this);
        bChoose = new JButton("Browse");
        bChoose.addActionListener(this);

        // Init text area
        taMessage = new JTextArea();
        taMessage.setRows(8);
        taMessage.setLineWrap(true);
        taMessage.setWrapStyleWord(true);

        // Init file choose
        fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == bSend) {
            String from = tfFrom.getText();
            String to = tfTo.getText();
            String subject = tfSubject.getText();
            String username = tfUsername.getText();
            String password = new String(pfPassword.getPassword());
            String message = taMessage.getText();
            String filePath = tfFileChoose.getText();

            boolean validate = validateInfo(from, to, subject, username, password, message);

            if (!validate) {
                return;
            }

            MailMessage mm = new MailMessage(from, message, subject, to);
            MyMail mMail = new MyMail();
            SMTPServer mailServer;
            boolean sent = false;

            if (cbServer.getSelectedItem().toString().equals("smtp.gmail.com(SSL)")) {
                mailServer = new SMTPServer("SSL", "465", "smtp.gmail.com");
                try {
                    sent = mMail.sendMail(mm, mMail.getMailSession(mailServer, username.split("@")[0], password), filePath);
                } catch (Exception ex) {
                    Logger.getLogger(MyEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("SSL");
            }
            if (cbServer.getSelectedItem().toString().equals("smtp.gmail.com(TLS)")) {
                mailServer = new SMTPServer("TLS", "587", "smtp.gmail.com");
                try {
                    sent = mMail.sendMail(mm, mMail.getMailSession(mailServer, username.split("@")[0], password),filePath);
                } catch (Exception ex) {
                    Logger.getLogger(MyEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("TLS");
            }
            if (sent) {
                JOptionPane.showMessageDialog(null, "Message sent to " + mm.getTo(), "Success Message", JOptionPane.INFORMATION_MESSAGE);
                tfFrom.setText("");
                tfTo.setText("");
                tfSubject.setText("");
                tfUsername.setText("");
                pfPassword.setText("");
                taMessage.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Error! Please try again!", "Fail Message", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (ae.getSource() == bChoose) {
            int choose = fc.showOpenDialog(null);
            if (choose == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                tfFileChoose.setText(selectedFile.getAbsolutePath());
            }
        }
    }

    // Check null infomation
    private boolean validateInfo(String from, String to, String subject, String username, String password, String message) {
        ArrayList<String> missInfoErrs = new ArrayList<>();
        ArrayList<String> invalidateInfoErrs = new ArrayList<>();

        // Check miss info of all field
        if (from.equals("")) {
            missInfoErrs.add("From address is not null!");
        }
        if (to.equals("")) {
            missInfoErrs.add("To address is not null!");
        }
        if (subject.equals("")) {
            missInfoErrs.add("Subject is not null!");
        }
        if (username.equals("")) {
            missInfoErrs.add("Username is not null!");
        }
        if (password.equals("")) {
            missInfoErrs.add("Password is not null!");
        }
        if (message.equals("")) {
            missInfoErrs.add("Message is not null!");
        }

        if (missInfoErrs.size() > 0) {
            String errors = "";
            for (int i = 0; i < missInfoErrs.size(); i++) {
                errors = (errors + missInfoErrs.get(i) + "\n");
            }
            JOptionPane.showMessageDialog(null, errors, "Missing Info", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Check validate info
        if (from.split("@").length < 2) {
            invalidateInfoErrs.add("Invalid From address!");
        }
        if (to.split("@").length < 2) {
            invalidateInfoErrs.add("Invalid To address!");
        }
        if (username.split("@").length < 2) {
            invalidateInfoErrs.add("Invalid Username!");
        }
        if (invalidateInfoErrs.size() > 0) {
            String errors = "";
            for (int i = 0; i < invalidateInfoErrs.size(); i++) {
                errors = (errors + invalidateInfoErrs.get(i) + "\n");
            }
            JOptionPane.showMessageDialog(null, errors + "Please check again!", "Invalid Data", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // If validate return true 
        return true;
    }
}
