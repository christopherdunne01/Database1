package chess;

import java.sql.*;
import java.io.Console;

import java.util.Scanner; 

public class Login {
    
    private String user, pass;  
    
    protected void activate() throws SQLException
    {
        this.chessLeagueLogin();
    }
    
    private void chessLeagueLogin() throws SQLException 
    {
      
        try
        {
            
           Scanner sc = new Scanner(System.in);
            System.out.println("Please enter the username: ");
            user = sc.next();
            System.out.println("Please enter the password: ");
            pass = sc.next(); 
           
            Connection log = DriverManager.getConnection("jdbc:mysql://localhost/Chess", user, pass);
            
            System.out.println("Both Correct");
            
            log.close();

        }catch(Error C)
        {
            System.out.println("Error with username and password");
            
           
        }
       
    }//end chessLeagueLogin
    
}//end Login
