package chess;

import java.sql.*;
import java.io.*;

public class CreateDatabase {
    
    protected void createDB() throws SQLException, IOException
        {   


            try
            {
                Class.forName("com.mysql.jdbc.Driver");    
            }catch (ClassNotFoundException a){
                System.out.println("Could not load driver."); 
            }

                System.out.println("Connecting to localhost...");


            try
            {
                Connection conn = DriverManager.getConnection ("jdbc:mysql://localhost/?user=root");//log into php
                System.out.println("Connection Successful!!");

                conn.setAutoCommit(false);

                System.out.println("Creating the database");

                Statement s = conn.createStatement();

                //Create Database Chess
                s.executeUpdate("CREATE DATABASE IF NOT EXISTS Chess"); 
                System.out.println("DB Created");

                //Create User 'Chris' on the 'localhost' with all Privlidges Granted
                s.executeUpdate("CREATE USER 'Chris'@'localhost' IDENTIFIED BY 'password'");
                s.executeUpdate("GRANT ALL PRIVILEGES ON *.* TO 'Chris'@'localhost' WITH GRANT OPTION");

                System.out.println("User Created");


                conn.commit();
                conn.close();

            }catch(Error b)
            {
                System.out.println("Error has Occoured"+b); 
            }
    
    }//end method createDB

}// end class CreateDatabase

    
    
    
    
    