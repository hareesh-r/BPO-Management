package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;



class Main  implements ActionListener{
    private static final String connectionName = "root";
    private static final String connectionPassword = "password";
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel currName;
    private static JTextField userName,currMessageField, currNameField, currAddressField, currPhoneNumberField;
    private static JPasswordField password;
    private static JButton executeOption;
    private static JRadioButton addRadio , updateRadio , deleteRadio;
    private static String currAddressValue, currPhoneNumberValue,currNameValue,currMessageValue;
    private static final Color black = new Color(245,40,145),white = new Color(255,255,255),green = new Color(0, 0, 0);
    public static ArrayList<String> complaintId,complaintList,complaintsPhnoList,complaintsEmailList;

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    private static boolean isResolved(String currPhoneNumberValue) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bpo", connectionName, connectionPassword);
        Statement stmt = con.createStatement();
        String display = "SELECT * FROM bpo where phno = '"+currPhoneNumberValue+"' ;";
        ResultSet res = stmt.executeQuery(display);

        while(res.next()){
            String currStatus = res.getString("status");
            System.out.println(currStatus);
            if(currStatus.equals("new")){
                con.close();
                return false;
            }else{
                con.close();
                return true;
            }
        }
        return false;
    }
    private static void customerLogin(String username) throws SQLException {

        frame.remove(panel);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(null);
        userPanel.setBackground(black);

        JLabel welcomeUser = new JLabel("Welcome " + username);
        welcomeUser.setBounds(10,20,180,25);
        welcomeUser.setForeground(white);
        userPanel.add(welcomeUser);

        addRadio=new JRadioButton("Raise complaint");
        addRadio.setBounds(10,50,150,30);
        addRadio.setBackground(black);
        addRadio.setForeground(white);
        addRadio.setFocusPainted(false);

        updateRadio=new JRadioButton("Check Status");
        updateRadio.setBounds(10,80,150,30);
        updateRadio.setBackground(black);
        updateRadio.setForeground(white);
        updateRadio.setFocusPainted(false);

        deleteRadio=new JRadioButton("Delete complaint");
        deleteRadio.setBounds(10,110,150,30);
        deleteRadio.setBackground(black);
        deleteRadio.setForeground(white);
        deleteRadio.setFocusPainted(false);

        currName = new JLabel("Enter Name");
        currName.setBounds(160,50,150,30);
        currName.setForeground(white);

        currNameField = new JTextField(100);
        currNameField.setBounds(300,50,150,30);
        userPanel.add(currNameField);

        JLabel currAddress = new JLabel("Enter E-mail address");
        currAddress.setBounds(160,80,150,30);
        currAddress.setForeground(white);

        currAddressField = new JTextField(100);
        currAddressField.setBounds(300,80,150,30);
        userPanel.add(currAddressField);

        JLabel currPhoneNumber = new JLabel("Enter Phone Number");
        currPhoneNumber.setBounds(160,110,150,30);
        currPhoneNumber.setForeground(white);

        currPhoneNumberField = new JTextField(100);
        currPhoneNumberField.setBounds(300,110,150,30);
        userPanel.add(currPhoneNumberField);

        JLabel currMessage = new JLabel("Enter your complaint");
        currMessage.setBounds(160,140,150,30);
        currMessage.setForeground(white);

        currMessageField = new JTextField(100);
        currMessageField.setBounds(300,140,150,60);
        userPanel.add(currMessage);

        ButtonGroup bg=new ButtonGroup();
        bg.add(addRadio);
        bg.add(updateRadio);
        bg.add(deleteRadio);

        executeOption=new JButton("Apply");
        executeOption.setBounds(135,250,180,30);
        executeOption.setBackground(green);
        executeOption.setForeground(black);
        executeOption.setFocusPainted(false);
        executeOption.addActionListener( e -> {

            currNameValue = currNameField.getText();
            currAddressValue = currAddressField.getText();
            currPhoneNumberValue = currPhoneNumberField.getText();
            currMessageValue = currMessageField.getText();

            if(addRadio.isSelected()){
                int randId = 0 + (int)(Math.random() * ((4000 - 0) + 1));
                Connection con = null;
                try {
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bpo", connectionName, connectionPassword);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                Statement stmt = null;
                try {
                    stmt = con.createStatement();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                String update = "INSERT INTO bpo VALUES('"+Integer.toString(randId)+"','"+currPhoneNumberValue+"','"+currAddressValue+"','"+currMessageValue+"','new');";
                try {
                    stmt.execute(update);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                System.out.println("User Complaint Added! "+currMessageValue+" , "+currNameValue+" , "+currAddressValue+" , "+ currPhoneNumberValue);
            }else if(updateRadio.isSelected()){
                try {
                    if(isResolved(currPhoneNumberValue)){
                        System.out.println("Resolved");
                    }else{
                        System.out.println("Pending");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }else if(deleteRadio.isSelected()){

               System.out.println("User Data Deleted! "+currMessageValue+" , "+currNameValue+" , "+currAddressValue+" , "+ currPhoneNumberValue);
            }
        });

        userPanel.add(currName);
        userPanel.add(currAddress);
        userPanel.add(currPhoneNumber);
        userPanel.add(currMessageField);

        userPanel.add(addRadio);
        userPanel.add(updateRadio);
        userPanel.add(deleteRadio);
        userPanel.add(executeOption);

        frame.setSize(480,350);
        frame.add(userPanel);
        frame.setVisible(true);
    }

    private static void getComplaints(ArrayList id,ArrayList phno,ArrayList mail,ArrayList complaint) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bpo", connectionName, connectionPassword);
        Statement stmt = con.createStatement();
        String display = "SELECT * FROM "+ "bpo where status = 'new' ;";
        ResultSet res = stmt.executeQuery(display);

        while(res.next()){
            String currId = res.getString("id");
            String currPhno = res.getString("phno");
            String currEmail = res.getString("email");
            String currComplaint = res.getString("complaint");
            String currStatus = res.getString("status");

            System.out.println(currId+"\t"+currPhno+"\t"+currEmail+"\t"+currComplaint+"\t"+currStatus);
            id.add(currId);
            phno.add(currPhno);
            mail.add(currEmail);
            complaint.add(currComplaint);
            id.add(currId);
        }
        con.close();
    }

    private static void markResolve(String id) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bpo", connectionName, connectionPassword);
        Statement stmt = con.createStatement();
        String update = "UPDATE bpo SET status = 'resolved' WHERE id="+id+";";
        stmt.execute(update);
        con.close();
        System.out.println("Successfully resolved id:"+id);
    }

    private static void markSpam(String id) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bpo", connectionName, connectionPassword);
        Statement stmt = con.createStatement();
        String update = "UPDATE bpo SET status = 'spam' WHERE id="+id+";";
        stmt.execute(update);
        con.close();
        System.out.println("Successfully resolved id:"+id);
    }

    private static void adminLogin() throws SQLException {

        frame.remove(panel);
        frame.setSize(1000,700);

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(null);
        adminPanel.setBackground(black);

        JLabel welcomeAdmin = new JLabel("Welcome Admin !");
        welcomeAdmin.setBounds(10,20,180,25);
        welcomeAdmin.setForeground(white);
        adminPanel.add(welcomeAdmin);

        JLabel complainTitle = new JLabel("Current Complaints");
        complainTitle.setBounds(10,50,180,25);
        complainTitle.setForeground(white);
        adminPanel.add(complainTitle);

        complaintId = new ArrayList<>();
        complaintsPhnoList = new ArrayList<>();
        complaintsEmailList = new ArrayList<>();
        complaintList = new ArrayList<>();

        getComplaints(complaintId,complaintsPhnoList,complaintsEmailList,complaintList);

        int counter = 50;
        for(int i=0;i<complaintsPhnoList.size();i++){

            String id = complaintId.get(i);

            JLabel phoneNumber = new JLabel("Phone Number : ");
            phoneNumber.setBounds(10,60+counter,100,25);
            phoneNumber.setForeground(white);
            adminPanel.add(phoneNumber);

            JLabel currPhoneNumber = new JLabel(complaintsPhnoList.get(i));
            currPhoneNumber.setBounds(110,60+counter,180,25);
            currPhoneNumber.setForeground(white);
            adminPanel.add(currPhoneNumber);

            JLabel email = new JLabel("Email ID : ");
            email.setBounds(10,90+counter,100,25);
            email.setForeground(white);
            adminPanel.add(email);

            JLabel currEmail = new JLabel(complaintsEmailList.get(i));
            currEmail.setBounds(80,90+counter,250,25);
            currEmail.setForeground(white);
            adminPanel.add(currEmail);

            JLabel complain = new JLabel("Complain : ");
            complain.setBounds(10,120+counter,100,25);
            complain.setForeground(white);
            adminPanel.add(complain);

            JLabel currComplain = new JLabel(complaintList.get(i));
            currComplain.setBounds(80,120+counter,250,25);
            currComplain.setForeground(white);
            adminPanel.add(currComplain);

            JRadioButton resolveRadio = new JRadioButton("Resolved");
            resolveRadio.setBounds(500,60+counter,100,25);
            resolveRadio.setBackground(black);
            resolveRadio.setForeground(white);
            resolveRadio.setFocusPainted(false);

            JRadioButton spamRadio = new JRadioButton("Spam");
            spamRadio.setBounds(500,90+counter,100,25);
            spamRadio.setBackground(black);
            spamRadio.setForeground(white);
            spamRadio.setFocusPainted(false);

            JButton resolve=new JButton("Execute");
            resolve.setBounds(500,120+counter,100,25);
            resolve.setBackground(green);
            resolve.setForeground(black);
            resolve.setFocusPainted(false);
            resolve.addActionListener( e -> {

                if(resolveRadio.isSelected()){
                    try {
                        markResolve(id);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else if(spamRadio.isSelected()){
                    try {
                        markSpam(id);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            ButtonGroup bg=new ButtonGroup();
            bg.add(resolveRadio);
            bg.add(spamRadio);

            adminPanel.add(resolveRadio);
            adminPanel.add(spamRadio);
            adminPanel.add(resolve);

            counter+=130;
        }

        frame.add(adminPanel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {

        frame = new JFrame("BPO Management");
        panel = new JPanel();
        frame.setSize(470,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel);
        panel.setBackground(black);
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10,20,80,25);
        userLabel.setForeground(white);
        panel.add(userLabel);

        userName = new JTextField(100);
        userName.setBounds(100,20,160,25);
        panel.add(userName);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10,50,80,25);
        passwordLabel.setForeground(white);
        panel.add(passwordLabel);

        password = new JPasswordField(100);
        password.setBounds(100,50,160,25);
        panel.add(password);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(170,80,90,25);
        loginButton.isDefaultButton();
        loginButton.setBackground(green);
        loginButton.setForeground(black);

        loginButton.addActionListener(new Main());
        loginButton.addActionListener(e -> {

            String usernameText = userName.getText();
            String passwordText = String.valueOf(password.getPassword());
            if (usernameText.equals("admin") && passwordText.equals("admin")) {
                System.out.println("Admin Logged in!");
                try {
                    adminLogin();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            } else {
                System.out.println("User Logged in!");
                try {
                    customerLogin(usernameText);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });

        panel.add(loginButton);
        frame.setVisible(true);
    }
}

