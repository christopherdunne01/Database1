package chess;

import java.sql.*;

public class Trigger {
     
    private String user = "Chris";
    private String pass = "password"; 
    
    protected void createTrigger() throws SQLException
    {
        
        try
        { 
            Connection connb = DriverManager.getConnection("jdbc:mysql://localhost/Chess", user, pass); 
            connb.setAutoCommit(false);
            
            Statement trigger = connb.createStatement(); 
            
            System.out.println("Creating Trigger...");
            
            //When new Game is implemented it also corresponds with the Matches table. 
            trigger.executeUpdate("CREATE TRIGGER ImplementNewGame AFTER INSERT ON Game FOR EACH ROW INSERT INTO Matches (MatchDate) VALUES (New.DatePlayed)"); 
            
            connb.commit(); 
            
            System.out.println("Successfully created"); 
            
            connb.close(); 
            
        }catch(Exception e)
        {
            System.out.println("Error" +e);
        }  
    
    }//end CreateTrigger
    
}// end class Trigger
