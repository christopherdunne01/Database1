package chess;

import java.sql.*;
import java.io.*;

public class Main 
{
    static Connection conn; 
    static String user = "Chris";
    static String pass = "password";
    
        public static void main (String [] args)throws SQLException, IOException
        {

            CreateDatabase dB = new CreateDatabase(); 
            Tables tb = new Tables(); 
            Login log = new Login(); 
            Trigger trigg = new Trigger(); 
            Query callQuery = new Query(); 

            dB.createDB(); //create database with user
            log.activate(); //login with user = 'Chris' and password = 'password'
            tb.active(); //create Tables and populate Tables from text files
            trigg.createTrigger(); //Create and execute Trigger
            
            //connect to JDBC to ensure no code duplicates
            conn = DriverManager.getConnection ("jdbc:mysql://localhost/Chess", user, pass);
            conn.setAutoCommit(false);

            callQuery.runQuery();
        
        }
        
        public static Statement getQueryStatement() throws SQLException{
            Statement query = conn.createStatement();
            query.executeUpdate("USE Chess;");
            return query; 
        }
    
        public static void callCommit() throws SQLException{
            conn.commit(); 
        }
       
}
