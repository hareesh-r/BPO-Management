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
    private static JLabel currId;
    private static JTextField userName,currMessageField, currIdField, currNameField, currAddressField, currPhoneNumberField,currPriceField,currLocationField,currQuantityField;
    private static JPasswordField password;
    private static JButton executeOption;
    private static JRadioButton addRadio , updateRadio , deleteRadio;
    private static String currAddressValue,currLocationValue, currPhoneNumberValue,currNameValue,currMessageValue;
    private static Integer currPriceValue,currIdValue,currQuantityValue;
    private static final Color black = new Color(0,0,0),white = new Color(255,255,255),green = new Color(132, 224, 158);

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static class Customer {

        String connectionName,connectionPassword;

        Customer(String connectionName,String connectionPassword){
            this.connectionName = connectionName;
            this.connectionPassword = connectionPassword;
        }
        private void insert(int id, String name, String address, String phoneNumber) throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", connectionName, connectionPassword);
            Statement stmt = con.createStatement();
            String insert = "INSERT INTO "+ "customer" +" VALUES("+id+",'"+name+"','"+address+"','"+phoneNumber+"')";
            stmt.execute(insert);
            con.close();
            System.out.println("Successfully Inserted customer data with id:"+id);
        }
        private void update(int id, String name, String address, String phoneNumber) throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", connectionName, connectionPassword);
            Statement stmt = con.createStatement();
            String update = "UPDATE "+ "customer" +" SET NAME='"+name+"' ,address='"+address+"' ,phoneno='"+phoneNumber+"' WHERE idcustomer="+id;
            stmt.execute(update);
            con.close();
            System.out.println("Successfully Updated customer data with id:"+id);
        }
        private void delete(int id) throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", connectionName, connectionPassword);
            Statement stmt = con.createStatement();
            String delete = "DELETE FROM "+ "customer" +" WHERE idcustomer="+id;
            stmt.execute(delete);
            con.close();
            System.out.println("Successfully Deleted customer data with id:"+id);
        }
        private void display() throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", connectionName, connectionPassword);
            Statement stmt = con.createStatement();
            String display = "SELECT * FROM "+ "customer";
            ResultSet res = stmt.executeQuery(display);

            while(res.next()){
                int idcustomer = res.getInt("idcustomer");
                String name = res.getString("name");
                String address = res.getString("address");
                String phoneno = res.getString("phoneno");

                System.out.println(idcustomer+"\t"+name+"\t"+address+"\t"+phoneno);
            }
            con.close();
            System.out.println("Data of all customers retrieved Successful...");
        }
    }

    public static class Order {

        String connectionName, connectionPassword;
        Random random = new Random();
        int randomOrderID = random.nextInt(1000);

        Order(String connectionName,String connectionPassword){
            this.connectionName = connectionName;
            this.connectionPassword = connectionPassword;
        }
        private void place(int quantity,String item) throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", connectionName, connectionPassword);
            Statement stmt = con.createStatement();
            String insert = "INSERT INTO stock.order VALUES("+randomOrderID+","+quantity+","+"'"+item+"')";
            stmt.execute(insert);
            con.close();
            System.out.println("Order placed Successfully with order id:"+randomOrderID+"...");
        }
        private void cancel(int orderid) throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", connectionName, connectionPassword);
            Statement stmt = con.createStatement();
            String insert = "DELETE FROM stock.order WHERE orderid="+orderid;
            stmt.execute(insert);
            con.close();
            System.out.println("Order cancelled Successfully with order id:"+orderid+"...");
        }
        private void display() throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", connectionName, connectionPassword);
            Statement stmt = con.createStatement();
            String display = "SELECT * FROM stock.order";
            ResultSet res = stmt.executeQuery(display);

            while(res.next()){
                int orderid = res.getInt("orderid");
                int quantity = res.getInt("quantity");
                String item = res.getString("item");

                System.out.println(orderid+"\t"+quantity+"\t"+item);
            }
            con.close();
            System.out.println("Data of all orders retrieved Successful...");
        }
    }

    public static class Product{

        String connectionName,connectionPassword;

        Product(String connectionName,String connectionPassword){
            this.connectionName = connectionName;
            this.connectionPassword = connectionPassword;
        }
        private void add(int id,String productName,int price,String location) throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", connectionName, connectionPassword);
            Statement stmt = con.createStatement();
            String insert = "INSERT INTO PRODUCT VALUES("+id+",'"+productName+"',"+price+",'"+location+"')";
            stmt.execute(insert);
            con.close();
            System.out.println("Successfully Inserted product data with id:"+id);
        }
        private void update(int id,String productName,int price,String location) throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", connectionName, connectionPassword);
            Statement stmt = con.createStatement();
            String update = "UPDATE PRODUCT SET PRODUCTNAME='"+productName+"' ,price="+price+" ,location='"+location+"' WHERE productid="+id;
            stmt.execute(update);
            con.close();
            System.out.println("Successfully Updated product data with id:"+id);
        }
        private void delete(int id) throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", connectionName, connectionPassword);
            Statement stmt = con.createStatement();
            String update = "DELETE FROM PRODUCT WHERE PRODUCTID="+id;
            stmt.execute(update);
            con.close();
            System.out.println("Successfully Deleted product data with id:"+id);
        }
    }

    public static void orderProduct(String userName){

        frame.remove(panel);
        frame.setSize(650,350);

        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(null);
        orderPanel.setBackground(black);

        JLabel welcomeUser = new JLabel("Welcome " + userName);
        welcomeUser.setBounds(10,20,180,25);
        welcomeUser.setForeground(white);
        orderPanel.add(welcomeUser);

        JRadioButton placeRadio = new JRadioButton("Place Order !");
        placeRadio.setBounds(10,50,100,30);
        placeRadio.setBackground(black);
        placeRadio.setForeground(white);
        placeRadio.setFocusPainted(false);

        JRadioButton cancelRadio = new JRadioButton("Cancel an order of a product !");
        cancelRadio.setBounds(10,80,200,30);
        cancelRadio.setBackground(black);
        cancelRadio.setForeground(white);
        cancelRadio.setFocusPainted(false);

        JRadioButton displayRadio=new JRadioButton("Display the orders made");
        displayRadio.setBounds(10,110,200,30);
        displayRadio.setBackground(black);
        displayRadio.setForeground(white);
        displayRadio.setFocusPainted(false);

        currId = new JLabel("Enter Id");
        currId.setBounds(235,50,150,30);
        currId.setForeground(white);

        currIdField = new JTextField(100);
        currIdField.setBounds(450,50,150,30);
        orderPanel.add(currIdField);

        currName = new JLabel("Enter Product/Item name");
        currName.setBounds(235,80,250,30);
        currName.setForeground(white);

        currNameField = new JTextField(100);
        currNameField.setBounds(450,80,150,30);
        orderPanel.add(currNameField);

        JLabel currQuantity = new JLabel("Enter quantity you want to order");
        currQuantity.setBounds(235,110,200,30);
        currQuantity.setForeground(white);

        currQuantityField = new JTextField(100);
        currQuantityField.setBounds(450,110,150,30);
        orderPanel.add(currQuantityField);

        ButtonGroup bg=new ButtonGroup();
        bg.add(placeRadio);
        bg.add(cancelRadio);
        bg.add(displayRadio);

        executeOption=new JButton("Execute !");
        executeOption.setBounds(230,250,180,30);
        executeOption.setBackground(green);
        executeOption.setForeground(black);
        executeOption.setFocusPainted(false);
        executeOption.addActionListener(e -> {

            currIdValue = Integer.valueOf(currIdField.getText());
            currNameValue = currNameField.getText();
            currQuantityValue = Integer.valueOf(currQuantityField.getText());

            Order order = new Order(connectionName,connectionPassword);
            if(placeRadio.isSelected()){
                try {
                    order.place(currQuantityValue,currNameValue);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.out.println("User Data Added! "+currIdValue+" , "+currNameValue+" , "+currQuantityValue);
            }else if(cancelRadio.isSelected()){
                try {
                    order.cancel(currIdValue);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.out.println("User Data Updated! "+currIdValue+" , "+currNameValue+" , "+currQuantityValue);
            }else if(displayRadio.isSelected()){
                try {
                    order.display();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        orderPanel.add(currId);
        orderPanel.add(currName);
        orderPanel.add(currQuantity);

        orderPanel.add(placeRadio);
        orderPanel.add(cancelRadio);
        orderPanel.add(displayRadio);
        orderPanel.add(executeOption);

        frame.add(orderPanel);
        frame.setVisible(true);
    }
    private static void customerLogin(String username){
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

            Customer customer = new Customer(connectionName,connectionPassword);

            if(addRadio.isSelected()){
//                try {
//                    customer.insert(currIdValue,currNameValue,currAddressValue, currPhoneNumberValue);
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
                System.out.println("User Data Added! "+currMessageValue+" , "+currNameValue+" , "+currAddressValue+" , "+ currPhoneNumberValue);
            }else if(updateRadio.isSelected()){
//                try {
//                    customer.update(currIdValue,currNameValue,currAddressValue, currPhoneNumberValue);
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
                System.out.println("User Data Updated! "+currMessageValue+" , "+currNameValue+" , "+currAddressValue+" , "+ currPhoneNumberValue);
            }else if(deleteRadio.isSelected()){
//                try {
//                    customer.delete(currIdValue);
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
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

    private static void adminLogin(){

        frame.remove(panel);
        frame.setSize(800,500);

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

        ArrayList<String> complaintsPhnoList = new ArrayList<>();
        complaintsPhnoList.add("9176969473");
        complaintsPhnoList.add("9276969473");
        complaintsPhnoList.add("9136969473");
        complaintsPhnoList.add("9174969473");
        complaintsPhnoList.add("9176569473");

        ArrayList<String> complaintsEmailList = new ArrayList<>();
        complaintsEmailList.add("hareeshprogrammera@gmail.com");
        complaintsEmailList.add("hareeshprogrammerb@gmail.com");
        complaintsEmailList.add("hareeshprogrammerc@gmail.com");
        complaintsEmailList.add("hareeshprogrammerd@gmail.com");
        complaintsEmailList.add("hareeshprogrammere@gmail.com");

        int counter = 20;
        for(int i=0;i<complaintsPhnoList.size();i++){

            JLabel phoneNumber = new JLabel("Phone Number : ");
            phoneNumber.setBounds(10,60+counter,100,25);
            phoneNumber.setForeground(white);
            adminPanel.add(phoneNumber);

            JLabel currPhoneNumber = new JLabel(complaintsPhnoList.get(i));
            currPhoneNumber.setBounds(110,60+counter,180,25);
            currPhoneNumber.setForeground(white);
            adminPanel.add(currPhoneNumber);

            JLabel email = new JLabel("Email ID : ");
            email.setBounds(180,60+counter,100,25);
            email.setForeground(white);
            adminPanel.add(email);

            JLabel currEmail = new JLabel(complaintsEmailList.get(i));
            currEmail.setBounds(240,60+counter,250,25);
            currEmail.setForeground(white);
            adminPanel.add(currEmail);
            counter+=20;
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
                adminLogin();

            } else {
                System.out.println("User Logged in!");
                customerLogin(usernameText);
            }

        });

        panel.add(loginButton);
        frame.setVisible(true);
    }
}

