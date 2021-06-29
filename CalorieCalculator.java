/*
Fall_2020_-_APJ_-_Group_Project_Code
Calories Calculator app
App that helps people live their normal life!

Team:
U1910046 Vladimir Khvan
u1910063 Bakhrom Usmanov
U1910100 Farrukh Asatov
*/
import java.sql.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class CalorieCalculator {

    // declaration of gui elements

    //declaring labels
    static JLabel jlb1, jlb2, jlb3, jlb4, jlb5, jlb6, jlb7, jlb8, jlb9, jlb10, jlb11, jlb12, jlb13, jlb14, jlb15, jlb16,
            jlb17, jlb18, jlb19, jlb20, jlb21, jlb22, jlb23, jlb24, jlb25, jlb26, jlb27, jlb28, jlb29, jlb30, jlb31,
            jlb32, jlb33;

    //declaring textfields
    static JTextField jtf1, jtf4, jtf6, jtf7;

    //declaring passwordfields
    static JPasswordField jtf2, jtf3, jtf5, jtf8, jtf9;

    //declaring buttons
    static JButton jbt1, jbt2, jbt3, jbt4, jbt5, jbt6, jbt7, jbt8, jbt9, jbt10, jbt11, jbt12, jbt13, jbt14, jbt15,
            jbt16;
    //declaring sliders
    static JSlider jsr1, jsr2, jsr3, jsr4, jsr5, jsr6;

    //declaring radio buttons
    static JRadioButton jrbtn1, jrbtn2;

    static String username; // username of client
    static String userPassword, userPassword2, date; // user password
    static int height, weight, age, sex, goal, userID, fitDays, numberOfDays;
    static double calories, percentOfSuccessfulDays, normCalories;

    // initializing resultset
    static ResultSet rset;

    public static void main(String[] args) {

        // DATABASE

        try {

            // Allocate a database 'Connection' object
            Connection conn = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/world?autoReconnect=true&useSSL=false", "root", "1234");

            // Allocate a 'Statement' object in the Connection
            Statement statement = conn.createStatement();

            boolean checkerDB = true; // checks if database initialized (true for yes, false for no)

            // gets list of databases
            ResultSet catalogSet = conn.getMetaData().getCatalogs();

            // initializing database part

            // loop to check if there is need database
            while (catalogSet.next()) {
                // takes 1 database name
                String databaseName = catalogSet.getString(1);

                // condition statement
                if (databaseName.equals("caloriescalculator")) {
                    checkerDB = false;
                    // switch database to needed
                    statement.executeUpdate("use CaloriesCalculator");
                }
            }

            // initialize database and tables if it was not initialized
            if (checkerDB) {
                // create database
                statement.executeUpdate("create database CaloriesCalculator");
                // switch database
                statement.executeUpdate("use CaloriesCalculator");
                // create table user
                statement.executeUpdate(
                        "create table user (userID int(5) not null auto_increment primary key, username varchar(35), userPassword char(30), age int(3),	height int(3),	weight int(3),	sex tinyint(1),	calories int(5), goal tinyint(1), fitDays tinyint(3), todayDate char(30), numberOfDays int(4), normCalories double(8,3));");
                // create table goal
                statement.executeUpdate("create table goal (goal tinyint(1), description char(20));");
                // create table sex
                statement.executeUpdate("create table sex (sex tinyint(1), description char(20));");
                // insert default values in goal and sex tables
                statement.executeUpdate("insert into goal values(1,'Lose weight');");
                statement.executeUpdate("insert into goal values(2,'Keep weight');");
                statement.executeUpdate("insert into goal values(3,'Gain weight');");
                statement.executeUpdate("insert into sex values(1,'Male');");
                statement.executeUpdate("insert into sex values(2,'Female');");
            }

            // GUI
            //creating a Registration Frame
            JFrame frameRegistation = new JFrame("Calories Calculator"); //registration frame
            //creating a Main Frame
            JFrame frameMain = new JFrame("Calories Calculator");
            //creating a Login In Frame
            JFrame frameLogin = new JFrame("Calories Calculator");
            //creating a Profile Frame
            JFrame frameProfile = new JFrame("Calories Calculator");
            //creating a Dayly Consumption Frame
            JFrame frameConsumption = new JFrame("Calories Calculator");
            //creating a Dashboard Frame
            JFrame frameDashboard = new JFrame("Calories Calculator");
            //creating an Update Frame
            JFrame frameUpdate = new JFrame("Calories Calculator");

            // initializing elements in Update Frame
            // Text fields Creation
            jtf7 = new JTextField("");
            jtf7.setDocument(new JTextFieldLimit(20));
            jtf7.setBounds(200, 87, 220, 30);
            jtf7.setFont(new Font("Arial", Font.BOLD, 15));

            jtf8 = new JPasswordField("");
            jtf8.setDocument(new JTextFieldLimit(20));
            jtf8.setBounds(200, 127, 220, 30);
            jtf8.setFont(new Font("Arial", Font.BOLD, 23));

            jtf9 = new JPasswordField("");
            jtf9.setDocument(new JTextFieldLimit(20));
            jtf9.setBounds(200, 167, 220, 30);
            jtf9.setFont(new Font("Arial", Font.BOLD, 23));

            // Sliders Creation
            //creating age slider
            jsr4 = new JSlider(0, 100, 50);
            jsr4.setBounds(200, 207, 220, 30);
            //creating height slider
            jsr5 = new JSlider(0, 220, 110);
            jsr5.setBounds(200, 247, 220, 30);
            //creating weight slider
            jsr6 = new JSlider(0, 160, 80);
            jsr6.setBounds(200, 287, 220, 30);

            // Listeners for sliders
            //listener for age slider
            jsr4.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    jlb23.setText("New Age: " + jsr4.getValue());
                }
            });
            //listener for height slider
            jsr5.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    jlb24.setText("New Height: " + jsr5.getValue() + "cm");
                }
            });
            //listener for weight slider
            jsr6.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    jlb25.setText("New Weight: " + jsr6.getValue() + "kg");
                }
            });
            //combo box for the goal
            JComboBox<String> jcbo2 = new JComboBox<String>(
                    new String[] { "Gain Weight", "Lose Weight", "Keep Weight" });
            jcbo2.setBounds(200, 320, 220, 40);
            jcbo2.setFont(new Font("Arial", Font.PLAIN, 16));

            // Labels Creation
            // Updating Profile label
            jlb19 = new JLabel("Updating Profile");
            jlb19.setBounds(100, 15, 400, 50);
            jlb19.setFont(new Font("Arial", Font.BOLD, 35));
            // New Username label
            jlb20 = new JLabel("New Username: ");
            jlb20.setBounds(80, 80, 220, 40);
            jlb20.setFont(new Font("Arial", Font.PLAIN, 16));
            // Old Password label
            jlb21 = new JLabel("Old Password: ");
            jlb21.setBounds(80, 120, 220, 40);
            jlb21.setFont(new Font("Arial", Font.PLAIN, 16));
            // New Password label
            jlb22 = new JLabel("New Password: ");
            jlb22.setBounds(80, 160, 220, 40);
            jlb22.setFont(new Font("Arial", Font.PLAIN, 16));
            // New Age label
            jlb23 = new JLabel("New Age: " + jsr4.getValue());
            jlb23.setBounds(100, 200, 220, 40);
            jlb23.setFont(new Font("Arial", Font.PLAIN, 16));
            // New Height label
            jlb24 = new JLabel("New Height: " + jsr5.getValue() + " cm");
            jlb24.setBounds(50, 240, 220, 40);
            jlb24.setFont(new Font("Arial", Font.PLAIN, 16));
            // New Weight label
            jlb25 = new JLabel("New Weight: " + jsr6.getValue() + " kg");
            jlb25.setBounds(55, 280, 220, 40);
            jlb25.setFont(new Font("Arial", Font.PLAIN, 16));
            // New Goal label
            jlb26 = new JLabel("New Goal: ");
            jlb26.setBounds(120, 320, 220, 40);
            jlb26.setFont(new Font("Arial", Font.PLAIN, 16));

            jlb30 = new JLabel("");
            jlb30.setBounds(120, 440, 220, 40);
            jlb30.setFont(new Font("Arial", Font.PLAIN, 16));

            // Creation of buttons
            // Submit button
            jbt15 = new JButton("Submit");
            jbt15.setBounds(80, 390, 130, 40);
            jbt15.setFont(new Font("Arial", Font.BOLD, 16));

            jbt15.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        // if there is error then value=true
                        boolean errorCatch = false;
                        //get data from database
                        rset = statement.executeQuery("select userID, username from user");

                        // get username
                        username = jtf7.getText();

                        //check if text field is empty
                        if (username.isEmpty()) {
                            errorCatch = true;
                            jlb30.setText("Set username.");
                        }

                        // checks for duplicates
                        while (rset.next()) {
                            //skip loop when faces the itself
                            if (userID == Integer.parseInt(rset.getString("userID"))) {
                                continue;
                            }

                            if (rset.getString("username").toLowerCase().equals(username.toLowerCase())) {
                                errorCatch = true;
                                jlb30.setText("Change username.");
                            }
                        }

                        userPassword = new String(jtf8.getPassword());
                        userPassword2 = new String(jtf9.getPassword());

                        //chekcs if textfield is empty
                        if (userPassword.isEmpty()) {
                            jlb30.setText("Problem with password");
                            errorCatch = true;
                        }
                        //chekcs if textfield is empty
                        if (userPassword2.isEmpty()) {
                            userPassword2 = userPassword;
                        }
                        //get data from database
                        rset = statement.executeQuery("select userPassword from user where userID = '" + userID + "'");
                        //set cursor
                        rset.next();

                        //check if password correct
                        if (!rset.getString("userPassword").toLowerCase().equals(userPassword.toLowerCase())) {
                            jlb30.setText("Problem with password");
                            errorCatch = true;
                        }

                        //get values
                        age = jsr4.getValue();
                        height = jsr5.getValue();
                        weight = jsr6.getValue();

                        if (jcbo2.getSelectedItem().toString().equals("Lose Weight")) {
                            goal = 1;
                        } else if (jcbo2.getSelectedItem().toString().equals("Keep Weight")) {
                            goal = 2;
                        } else if (jcbo2.getSelectedItem().toString().equals("Gain Weight")) {
                            goal = 3;
                        }

                        if (!errorCatch) {
                            //set fields to default
                            jtf7.setText("");
                            jtf8.setText("");
                            jtf9.setText("");
                            jsr4.setValue(50);
                            jsr5.setValue(160);
                            jsr6.setValue(65);
                            //change frame
                            frameUpdate.setVisible(false);
                            frameDashboard.setVisible(true);
                            //update values in table
                            statement.executeUpdate("update user set username = '" + username + "', userPassword = '"
                                    + userPassword2 + "', age = '" + age + "', height = '" + height + "', weight = '"
                                    + weight + "',goal = '" + goal + "' where userID = " + userID);
                        }
                        errorCatch = false;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
            // Back button
            jbt16 = new JButton("Back");
            jbt16.setBounds(275, 390, 130, 40);
            jbt16.setFont(new Font("Arial", Font.BOLD, 16));

            jbt16.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frameUpdate.setVisible(false);
                    frameDashboard.setVisible(true);
                }
            });

            // initializing Dashboard frame elements
            // Creation of labels
            // Creation of Dashboard label
            jlb17 = new JLabel("Dashboard of " + username);
            jlb17.setBounds(85, 100, 400, 40);
            jlb17.setFont(new Font("Arial", Font.BOLD, 30));
            // Creation of Success Percentage label
            jlb18 = new JLabel("Success Percentage 100% ");
            jlb18.setBounds(60, 140, 400, 40);
            jlb18.setFont(new Font("Arial", Font.BOLD, 30));
            // Creation of Consumtion kkal label
            jlb32 = new JLabel("Consumtion kkal: ");
            jlb32.setBounds(60, 180, 400, 40);
            jlb32.setFont(new Font("Arial", Font.BOLD, 30));

            // Creation of buttons
            // Creation of Back button
            jbt13 = new JButton("Back");
            jbt13.setBounds(110, 340, 270, 40);
            jbt13.setFont(new Font("Arial", Font.BOLD, 25));

            jbt13.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frameDashboard.setVisible(false);
                    frameProfile.setVisible(true);
                }
            });
            // Creation of Change User Info button
            jbt14 = new JButton("Change User Info");
            jbt14.setBounds(110, 270, 270, 40);
            jbt14.setFont(new Font("Arial", Font.BOLD, 25));

            jbt14.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        // get set of 'username's from mysql
                        rset = statement.executeQuery(
                                "select userID, username, age, height, weight, goal from user where username = '"
                                        + username + "'");

                        rset.next();

                        //get values from set
                        userID = Integer.parseInt(rset.getString("userID"));
                        age = Integer.parseInt(rset.getString("age"));
                        height = Integer.parseInt(rset.getString("height"));
                        weight = Integer.parseInt(rset.getString("weight"));
                        goal = Integer.parseInt(rset.getString("goal"));

                        //set values for update frame
                        if (goal == 1) {
                            jcbo2.setSelectedItem("Lose Weight");
                        } else if (goal == 2) {
                            jcbo2.setSelectedItem("Keep Weight");
                        } else if (goal == 3) {
                            jcbo2.setSelectedItem("Gain Weight");
                        }

                        jtf7.setText(username);
                        jsr4.setValue(age);
                        jsr5.setValue(height);
                        jsr6.setValue(weight);

                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    //change frames
                    frameDashboard.setVisible(false);
                    frameUpdate.setVisible(true);
                }
            });

            // Text fields Creation
            JTextField jtf6 = new JTextField("");
            jtf6.setBounds(225, 200, 220, 40);
            jtf6.setFont(new Font("Arial", Font.BOLD, 30));

            // initializing Consumption Frame elements
            // Creation of labels
            // Creation of Daily Consumption label
            jlb15 = new JLabel("Daily Consumption");
            jlb15.setBounds(90, 100, 400, 40);
            jlb15.setFont(new Font("Arial", Font.BOLD, 35));
            // Creation of Eaten kkal label
            jlb33 = new JLabel("Eaten kkal: ");
            jlb33.setBounds(90, 140, 400, 40);
            jlb33.setFont(new Font("Arial", Font.BOLD, 25));
            // Creation of Kilocalories label
            jlb16 = new JLabel("Kilocalories: ");
            jlb16.setBounds(30, 200, 270, 40);
            jlb16.setFont(new Font("Arial", Font.BOLD, 30));

            // Creation of buttons
            // Creation of Back button
            jbt11 = new JButton("Back");
            jbt11.setBounds(110, 340, 270, 40);
            jbt11.setFont(new Font("Arial", Font.BOLD, 30));

            jbt11.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frameConsumption.setVisible(false);
                    frameProfile.setVisible(true);
                }
            });
            // Creation of Submit button
            jbt12 = new JButton("Submit");
            jbt12.setBounds(110, 270, 270, 40);
            jbt12.setFont(new Font("Arial", Font.BOLD, 30));

            jbt12.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    try {

                        // if there is error then value=true
                        boolean errorCatch = false;
                        double caloriesBefore;

                        // get username
                        calories = Double.parseDouble(jtf6.getText());

                        //checks if text field is empty
                        if (jtf6.toString().isEmpty()) {
                            errorCatch = true;
                            jtf6.setText("" + -calories);
                        }

                        //calories cannot be less or equal to 0
                        if (calories <= 0) {
                            errorCatch = true;
                            jtf6.setText("" + -calories);
                        }

                        //get data from database
                        rset = statement.executeQuery(
                                "select calories, todayDate, numberOfDays,fitDays, normCalories from user");
                        //place cursor
                        rset.next();

                        //get data from set
                        calories += Double.parseDouble(rset.getString("calories"));
                        caloriesBefore = Double.parseDouble(rset.getString("calories"));
                        date = rset.getString("todayDate");

                        //checks if time is equal
                        if (!java.time.LocalDate.now().toString().equals(date)) {
                            numberOfDays = Integer.parseInt(rset.getString("numberOfDays")) + 1;
                        }

                        //checks fitting in calories frames and substract fit day
                        if (normCalories - 300 < caloriesBefore && caloriesBefore < normCalories + 300
                                && calories >= normCalories + 300) {
                            statement.executeUpdate(
                                    "update user set fitDays = " + (Integer.parseInt(rset.getString("fitDays")) - 1)
                                            + " where username = '" + username + "'");
                        }

                        //checks fitting in calories frames and add fit day
                        if (normCalories - 300 < calories && calories < normCalories + 300) {
                            if (!(normCalories - 300 < caloriesBefore && caloriesBefore < normCalories + 300)) {
                                statement.executeUpdate(
                                        "update user set fitDays = " + (Integer.parseInt(rset.getString("fitDays")) + 1)
                                                + " where username = '" + username + "'");
                            }
                        }

                        //checks number of days
                        if (!java.time.LocalDate.now().toString().equals(date)) {
                            statement.executeUpdate("update user set todayDate = '"
                                    + java.time.LocalDate.now().toString() + "', numberOfDays = " + numberOfDays
                                    + " where username = '" + username + "'");
                            calories = Double.parseDouble(jtf6.getText());
                        }

                        if (!errorCatch) {
                            //change frame
                            frameConsumption.setVisible(false);
                            frameProfile.setVisible(true);

                            //change current frame
                            jlb33.setText("Today kkal: " + calories);

                            statement.executeUpdate(
                                    "update user set calories = " + calories + " where username = '" + username + "'");
                        }
                        errorCatch = false;

                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                }
            });

            jtf6.addKeyListener(new KeyAdapter() {
                //catch key input
                public void keyPressed(KeyEvent ke) {
                    //validates digits and backspace input
                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar() == KeyEvent.VK_BACK_SPACE
                            || ke.getKeyChar() == KeyEvent.VK_DELETE) {
                        //set text field editable when digit or backspace was pressed
                        jtf6.setEditable(true);
                    } else {
                        //set text field non-editable when digit or backspace was pressed
                        jtf6.setEditable(false);
                    }
                }
            });

            // initializing elements in Main Frame
            // Creation of labels
            // Creation of Welcome label
            jlb10 = new JLabel("Welcome!");
            jlb10.setBounds(148, 100, 200, 40);
            jlb10.setFont(new Font("Arial", Font.BOLD, 40));

            // Creation of buttons
            // Creation of Sign Up button
            jbt3 = new JButton("Sign Up");
            jbt3.setBounds(130, 200, 220, 40);
            jbt3.setFont(new Font("Arial", Font.BOLD, 30));

            jbt3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //change frame
                    frameMain.setVisible(false);
                    frameRegistation.setVisible(true);
                }
            });
            // Creation of Sign In button
            jbt4 = new JButton("Sign In");
            jbt4.setBounds(130, 270, 220, 40);
            jbt4.setFont(new Font("Arial", Font.BOLD, 30));

            jbt4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frameMain.setVisible(false);
                    frameLogin.setVisible(true);
                }
            });
            // Creation of Quit button
            jbt5 = new JButton("Quit");
            jbt5.setBounds(130, 340, 220, 40);
            jbt5.setFont(new Font("Arial", Font.BOLD, 30));

            jbt5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        //close all connections and statements on exit
                        if(conn != null){conn.close();};
                        if(statement != null){statement.close();};
                        if(rset != null){rset.close();};
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            });

            // initializing elements in Profile Frame
            // Creation of labels
            // Creation of Welcome label
            jlb14 = new JLabel("Welcome,");
            jlb14.setBounds(100, 100, 400, 40);
            jlb14.setFont(new Font("Arial", Font.BOLD, 35));
            // Creation of label
            jlb31 = new JLabel("");
            jlb31.setBounds(100, 140, 400, 40);
            jlb31.setFont(new Font("Arial", Font.BOLD, 35));

            // Creation of buttons
            // Creation of Add Info button
            jbt7 = new JButton("Add Info");
            jbt7.setBounds(110, 200, 270, 40);
            jbt7.setFont(new Font("Arial", Font.BOLD, 30));

            jbt7.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    try{
                        //get set of data
                        rset = statement.executeQuery(
                                "select calories from user where username = '"
                                        + username +"'");

                        rset.next();

                        //get data from set
                        calories = Integer.parseInt(rset.getString("calories"));
                        //prepare next frame
                        jlb33.setText("Today kkal: " + calories);

                        //change frame
                        frameProfile.setVisible(false);
                        frameConsumption.setVisible(true);
                    } catch (Exception e5){
                        e5.printStackTrace();
                    }
                }
            });
            // Creation of Weekly Insights button
            jbt8 = new JButton("Weekly Insights");
            jbt8.setBounds(110, 270, 270, 40);
            jbt8.setFont(new Font("Arial", Font.BOLD, 30));

            jbt8.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    try{
                        //get set of data
                        rset = statement.executeQuery(
                                "select normCalories, numberOfDays, fitDays from user where username = '"
                                     + username +"'");
                        //put cursor
                        rset.next();

                        //get data from set
                        fitDays = Integer.parseInt(rset.getString("fitDays"));
                        numberOfDays = Integer.parseInt(rset.getString("numberOfDays"));
                        normCalories = Double.parseDouble(rset.getString("normCalories"));

                        //checks for division by 0
                        if(numberOfDays != 0){
                            //calculate percent of successful days
                            percentOfSuccessfulDays = (Double.valueOf(fitDays)/Double.valueOf(numberOfDays))*100;
                            jlb18.setText("Success Percentage: "+ percentOfSuccessfulDays);
                        }

                        //prepare next frame
                        jlb32.setText("Needed calories:" + normCalories);
                    } catch (Exception e5){
                        e5.printStackTrace();
                    }

                    frameProfile.setVisible(false);
                    frameDashboard.setVisible(true);
                }
            });
            // Creation of Back button
            jbt9 = new JButton("Back");
            jbt9.setBounds(110, 340, 270, 40);
            jbt9.setFont(new Font("Arial", Font.BOLD, 30));

            jbt9.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //change frames
                    frameProfile.setVisible(false);
                    frameLogin.setVisible(true);
                }
            });

            // initializing elements in Login Frame
            // Text fields Creation

            jtf4 = new JTextField("");
            jtf4.setDocument(new JTextFieldLimit(20));
            jtf4.setBounds(215, 200, 220, 30);
            jtf4.setFont(new Font("Arial", Font.BOLD, 20));

            jtf5 = new JPasswordField("");
            jtf5.setDocument(new JTextFieldLimit(20));
            jtf5.setBounds(215, 270, 220, 30);
            jtf5.setFont(new Font("Arial", Font.BOLD, 25));

            // Creation of labels
            // Creation of Signing In label
            jlb11 = new JLabel("Signing In");
            jlb11.setBounds(155, 100, 400, 40);
            jlb11.setFont(new Font("Arial", Font.BOLD, 35));

            // Creation of Username label
            jlb12 = new JLabel("Username: ");
            jlb12.setBounds(50, 200, 270, 40);
            jlb12.setFont(new Font("Arial", Font.BOLD, 30));

            // Creation of Password label
            jlb13 = new JLabel("Password: ");
            jlb13.setBounds(52, 270, 270, 40);
            jlb13.setFont(new Font("Arial", Font.BOLD, 30));

            // Creation of label
            jlb28 = new JLabel("");
            jlb28.setBounds(52, 460, 270, 40);
            jlb28.setFont(new Font("Arial", Font.BOLD, 15));

            // Creation of label
            jlb29 = new JLabel("");
            jlb29.setBounds(52, 500, 270, 40);
            jlb29.setFont(new Font("Arial", Font.BOLD, 15));

            // Creation of buttons
            // Creation of Back button
            jbt2 = new JButton("Back");
            jbt2.setBounds(110, 400, 270, 40);
            jbt2.setFont(new Font("Arial", Font.BOLD, 30));

            jbt2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frameLogin.setVisible(false);
                    frameMain.setVisible(true);
                }
            });

            // Creation of buttons
            // Creation of Submit button
            jbt10 = new JButton("Submit");
            jbt10.setBounds(110, 340, 270, 40);
            jbt10.setFont(new Font("Arial", Font.BOLD, 30));

            jbt10.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try{

                        //if there is error then value=true
                        boolean errorCatch = false;
                        boolean isUsername = false, isPassword = false; //if values are true -> username and password are ok

                        //set labels to default
                        jlb28.setText("");
                        jlb29.setText("");

                        //get username
                        username = jtf4.getText();

                        //get set of 'username's from mysql
                        rset = statement.executeQuery("select username from user");

                        //checks for duplicates
                        while(rset.next()){
                            if(rset.getString("username").toLowerCase().equals(username.toLowerCase())){
                                isUsername=true;
                            }
                        }
                        //if username does not fits requirements show it
                        if(!isUsername){
                            jlb29.setText("Username is incorrect.");
                        }

                        //check if username is empty
                        if(username.isEmpty()){
                            errorCatch=true;
                            jlb29.setText("Set username.");
                        }

                        userPassword = new String(jtf5.getPassword());

                        rset = statement.executeQuery("select userPassword from user where username = '"+username+"'");

                        //checks password
                        while(rset.next()){
                            if(rset.getString("userPassword").toLowerCase().equals(userPassword.toLowerCase())){
                                isPassword=true;
                            }
                        }

                        //if password does not fits requirements show it
                        if(!isPassword){
                            jlb28.setText("Password is incorrect.");
                        }

                        //check if password is empty
                        if(userPassword.isEmpty()){
                            errorCatch=true;
                            jlb28.setText("Set password.");
                        }

                        //checks errors
                        if(!errorCatch && isPassword && isUsername){
                            //change frames
                            frameLogin.setVisible(false);
                            frameProfile.setVisible(true);
                            //bring fields to default
                            jtf4.setText("");
                            jtf5.setText("");
                            //set dashboard
                            jlb31.setText(username);
                            jlb17.setText("Dashboard of " +username);
                        }
                        errorCatch = false;

                    } catch (Exception e2){
                        e2.printStackTrace();
                    }
                }
            });

            // initializing elements in Registation Frame
            // Text fields Creation
            jtf1 = new JTextField("");
            jtf1.setDocument(new JTextFieldLimit(20));
            jtf1.setBounds(170, 87, 220, 30);
            jtf1.setFont(new Font("Arial", Font.BOLD, 15));

            jtf2 = new JPasswordField("");
            jtf2.setDocument(new JTextFieldLimit(20));
            jtf2.setBounds(170, 127, 220, 30);
            jtf2.setFont(new Font("Arial", Font.BOLD, 23));

            jtf3 = new JPasswordField("");
            jtf3.setDocument(new JTextFieldLimit(20));
            jtf3.setBounds(170, 167, 220, 30);
            jtf3.setFont(new Font("Arial", Font.BOLD, 23));

            // Sliders Creation

            // Age Sliders Creation
            jsr1 = new JSlider(0, 100, 50);
            jsr1.setBounds(170, 207, 220, 30);

            // Height Sliders Creation
            jsr2 = new JSlider(100, 220, 160);
            jsr2.setBounds(170, 247, 220, 30);

            // Weight Sliders Creation
            jsr3 = new JSlider(30, 160, 65);
            jsr3.setBounds(170, 287, 220, 30);

            //listeners that makes sliders interactive
            jsr1.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    jlb6.setText("Age: " + jsr1.getValue());
                }
            });
            jsr2.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    jlb5.setText("Height: " + jsr2.getValue() + "cm");
                }
            });
            jsr3.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    jlb7.setText("Weight: " + jsr3.getValue() + "kg");
                }
            });

            // Labels Creation
            // Registration Label Creation
            jlb1 = new JLabel("Registration");
            jlb1.setBounds(122, 15, 250, 50);
            jlb1.setFont(new Font("Arial", Font.BOLD, 40));
            //  Username Label Creation
            jlb2 = new JLabel("Username: ");
            jlb2.setBounds(80, 80, 220, 40);
            jlb2.setFont(new Font("Arial", Font.PLAIN, 16));
            // Password Label Creation
            jlb3 = new JLabel("Password: ");
            jlb3.setBounds(80, 120, 220, 40);
            jlb3.setFont(new Font("Arial", Font.PLAIN, 16));
            // Confirm Password Label Creation
            jlb4 = new JLabel("Confirm Password: ");
            jlb4.setBounds(23, 160, 220, 40);
            jlb4.setFont(new Font("Arial", Font.PLAIN, 16));
            // Age Label Creation
            jlb6 = new JLabel("Age: " + jsr1.getValue());
            jlb6.setBounds(100, 200, 220, 40);
            jlb6.setFont(new Font("Arial", Font.PLAIN, 16));
            // Height Label Creation
            jlb5 = new JLabel("Height: " + jsr2.getValue() + " cm");
            jlb5.setBounds(50, 240, 220, 40);
            jlb5.setFont(new Font("Arial", Font.PLAIN, 16));
            // Weight Label Creation
            jlb7 = new JLabel("Weight: " + jsr3.getValue() + " kg");
            jlb7.setBounds(55, 280, 220, 40);
            jlb7.setFont(new Font("Arial", Font.PLAIN, 16));
            // Gender Label Creation
            jlb9 = new JLabel("Gender: ");
            jlb9.setBounds(100, 320, 220, 40);
            jlb9.setFont(new Font("Arial", Font.PLAIN, 16));
            // Goal Label Creation
            jlb8 = new JLabel("Goal: ");
            jlb8.setBounds(120, 360, 220, 40);
            jlb8.setFont(new Font("Arial", Font.PLAIN, 16));
            // Label Creation
            jlb27 = new JLabel("");
            jlb27.setBounds(50, 420, 300, 200);
            jlb27.setFont(new Font("Arial", Font.PLAIN, 16));

            // Creation of Checkboxes

            ButtonGroup radioBox = new ButtonGroup();
            // Creation of Male RadioButton
            jrbtn1 = new JRadioButton("Male");
            jrbtn1.setBounds(300, 320, 110, 40);
            jrbtn1.setFont(new Font("Arial", Font.PLAIN, 16));
            // Creation of Female RadioButton
            jrbtn2 = new JRadioButton("Female ");
            jrbtn2.setBounds(190, 320, 110, 40);
            jrbtn2.setFont(new Font("Arial", Font.PLAIN, 16));
            // Adding RadioButtons
            radioBox.add(jrbtn1);
            radioBox.add(jrbtn2);

            // Creation of Goal combo box

            JComboBox<String> jcbo1 = new JComboBox<String>(
                    new String[] { "Gain Weight", "Lose Weight", "Keep Weight" });
            jcbo1.setBounds(170, 360, 220, 40);
            jcbo1.setFont(new Font("Arial", Font.PLAIN, 16));

            // Creation of buttons
            // Creation of Submit button
            jbt1 = new JButton("Submit");
            jbt1.setBounds(80, 420, 130, 40);
            jbt1.setFont(new Font("Arial", Font.BOLD, 16));

            //adding listener to submit button in registration pane
            jbt1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    try{
                        //if there is error then value=true
                        boolean errorCatch = false;

                        //get username
                        username = jtf1.getText();

                        //get set of 'username's from mysql
                        rset = statement.executeQuery("select username from user");

                        //checks if username field is empty
                        if(username.isEmpty()){
                            errorCatch=true;
                            jlb27.setText("Set username.");
                        }

                        //checks for duplicates
                        while(rset.next()){
                            if(rset.getString("username").toLowerCase().equals(username.toLowerCase())){
                                errorCatch=true;
                                jlb27.setText("Change username.");
                            }
                        }

                        userPassword = new String(jtf2.getPassword());
                        userPassword2 =  new String(jtf3.getPassword());

                        //checks similarity of passwords and if they are empty
                        if(!userPassword.equals(userPassword2) || userPassword.isEmpty() || userPassword2.isEmpty()){
                            jlb27.setText("Problem with password");
                            errorCatch=true;
                        }

                        age = jsr1.getValue();
                        height = jsr2.getValue();
                        weight = jsr3.getValue();

                        if(jrbtn1.isSelected()){
                            sex = 1;
                        } else if(jrbtn2.isSelected()){
                            sex = 2;
                        }

                        //getting goal value
                        if(jcbo1.getSelectedItem().toString().equals("Lose Weight")) {
                            goal = 1;
                        } else if(jcbo1.getSelectedItem().toString().equals("Keep Weight")) {
                            goal = 2;
                        } else if(jcbo1.getSelectedItem().toString().equals("Gain Weight")) {
                            goal = 3;
                        }
                        //checks if radio button is pressed
                        if(!jrbtn1.isSelected() && !jrbtn2.isSelected()){
                            jlb27.setText("Choose radio button");
                            errorCatch = true;
                        }


                        if(!errorCatch){
                            //set all fields to default condition
                            jtf1.setText("");
                            jtf2.setText("");
                            jtf3.setText("");
                            jsr1.setValue(50);
                            jsr2.setValue(160);
                            jsr3.setValue(65);
                            radioBox.clearSelection();

                            //change frame
                            frameRegistation.setVisible(false);
                            frameProfile.setVisible(true);

                            //calculate norm calories per day
                            if(sex == 1){
                                normCalories = (10*weight + 6.25*height - 5*age + 5)*1.375;
                            } else {
                                normCalories = (10*weight + 6.25*height - 5*age - 161)*1.375;
                            }

                            if(goal == 1){
                                normCalories -= 400;
                            } else if(goal == 3){
                                normCalories += 400;
                            }

                            //insert values in mysql
                            statement.executeUpdate("insert into user(username,userPassword,age,height,weight,sex,goal,calories,numberOfDays, fitDays, normCalories) values('"
                                    +username+"','"+userPassword+"',"+age+","+height+","+weight+","+sex+","+goal+", 0, 0, 0, " + normCalories + ");");

                            //sets username in dashboard frame
                            jlb31.setText(username);
                            jlb17.setText("Dashboard of " +username);
                        }
                        errorCatch = false;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            });
            // Creation of Back button
            jbt6 = new JButton("Back");
            jbt6.setBounds(275, 420, 130, 40);
            jbt6.setFont(new Font("Arial", Font.BOLD, 16));

            jbt6.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frameRegistation.setVisible(false);
                    frameMain.setVisible(true);
                }
            });

            //Adding all elements to the Update Frame

            //adding textfields
            frameUpdate.add(jtf7);
            frameUpdate.add(jtf8);
            frameUpdate.add(jtf9);

            //adding labels
            frameUpdate.add(jlb19);
            frameUpdate.add(jlb20);
            frameUpdate.add(jlb21);
            frameUpdate.add(jlb22);
            frameUpdate.add(jlb23);
            frameUpdate.add(jlb24);
            frameUpdate.add(jlb25);
            frameUpdate.add(jlb26);
            frameUpdate.add(jlb30);


            //adding sliders
            frameUpdate.add(jsr4);
            frameUpdate.add(jsr5);
            frameUpdate.add(jsr6);

            //adding buttons
            frameUpdate.add(jbt15);
            frameUpdate.add(jbt16);


            //adding comboboxes
            frameUpdate.add(jcbo2);

            //setting frame settins
            frameUpdate.setSize(500,650 );
            frameUpdate.setLocation(500,75);
            frameUpdate.setLayout(null);
            frameUpdate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameUpdate.setVisible(false);

            //adding elements to the Consumption frame
            //adding labels to frame
            frameConsumption.add(jlb15);
            frameConsumption.add(jlb16);
            frameConsumption.add(jlb33);

            //adding textfields
            frameConsumption.add(jtf6);


            //adding buttons to frame
            frameConsumption.add(jbt11);
            frameConsumption.add(jbt12);

            //setting frame settings
            frameConsumption.setSize(500,650 );
            frameConsumption.setLocation(500,75);
            frameConsumption.setLayout(null);
            frameConsumption.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameConsumption.setVisible(false);

            //adding elements to the Login frame
            //adding labels to frame
            frameLogin.add(jlb13);
            frameLogin.add(jlb11);
            frameLogin.add(jlb12);
            frameLogin.add(jlb28);
            frameLogin.add(jlb29);

            //adding textfields
            frameLogin.add(jtf4);
            frameLogin.add(jtf5);


            //adding buttons to frame
            frameLogin.add(jbt2);
            frameLogin.add(jbt10);

            //setting frame settings
            frameLogin.setSize(500,650 );
            frameLogin.setLocation(500,75);
            frameLogin.setLayout(null);
            frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameLogin.setVisible(false);

            //adding elements to the Dashboard frame
            //adding labels to frame
            frameDashboard.add(jlb17);
            frameDashboard.add(jlb18);
            frameDashboard.add(jlb32);

            //adding buttons to frame
            frameDashboard.add(jbt13);
            frameDashboard.add(jbt14);

            //setting frame settings
            frameDashboard.setSize(500,650 );
            frameDashboard.setLocation(500,75);
            frameDashboard.setLayout(null);
            frameDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameDashboard.setVisible(false);


            //Adding all elements to the Registation Frame
            //adding textfields
            frameRegistation.add(jtf1);
            frameRegistation.add(jtf2);
            frameRegistation.add(jtf3);

            //adding labels
            frameRegistation.add(jlb1);
            frameRegistation.add(jlb2);
            frameRegistation.add(jlb3);
            frameRegistation.add(jlb4);
            frameRegistation.add(jlb5);
            frameRegistation.add(jlb6);
            frameRegistation.add(jlb7);
            frameRegistation.add(jlb8);
            frameRegistation.add(jlb9);
            frameRegistation.add(jlb27);

            //adding sliders
            frameRegistation.add(jsr1);
            frameRegistation.add(jsr2);
            frameRegistation.add(jsr3);

            //adding checkboxes
            frameRegistation.add(jrbtn1);
            frameRegistation.add(jrbtn2);

            //adding comboboxes
            frameRegistation.add(jcbo1);

            //adding submit button
            frameRegistation.add(jbt1);
            frameRegistation.add(jbt6);

            //setting frame settins
            frameRegistation.setSize(500,650);
            frameRegistation.setLocation(500,75);
            frameRegistation.setLayout(null);
            frameRegistation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameRegistation.setVisible(false);

            //adding elements to the Main frame
            //adding labels to frame
            frameMain.add(jlb10);

            //adding buttons
            frameMain.add(jbt3);
            frameMain.add(jbt4);
            frameMain.add(jbt5);

            //setting frame settings
            frameMain.setSize(500,650 );
            frameMain.setLocation(500,75);
            frameMain.setLayout(null);
            frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameMain.setVisible(true);


            //adding elements to the Profile frame
            //adding labels to frame
            frameProfile.add(jlb14);
            frameProfile.add(jlb31);

            //adding buttons to frame
            frameProfile.add(jbt7);
            frameProfile.add(jbt8);
            frameProfile.add(jbt9);

            //setting frame settings
            frameProfile.setSize(500,650 );
            frameProfile.setLocation(500,75);
            frameProfile.setLayout(null);
            frameProfile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameProfile.setVisible(false);

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


//limits number of characters in JTextField
class JTextFieldLimit extends PlainDocument {
    private int limit;//number of characters in field

    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
        if (str == null) return;

        //after every input checks length of string and compares it to limit
        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}