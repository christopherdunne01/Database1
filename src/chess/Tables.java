package chess;

import java.io.IOException;
import java.sql.*;

public class Tables {
    
    private String user ="Chris";
    private String pass = "password"; 
    
    protected void active() throws SQLException, IOException
    {
        this.createTables();
        this.populateTables(); 
    }
    
    protected void createTables() throws SQLException, IOException
    {
        
        try
        {
            Connection connb = DriverManager.getConnection("jdbc:mysql://localhost/Chess", user, pass); 
            connb.setAutoCommit(false);
            
            System.out.println("Creating tables...");
            Statement l = connb.createStatement();
            
            //Create Tables 
            l.executeUpdate("CREATE TABLE IF NOT EXISTS Player(PlayerName CHAR (25) NOT NULL PRIMARY KEY, DateOfBirth DATE, FIDERating SMALLINT, FideTitle VARCHAR (25), ClubName VARCHAR(25) NOT NULL)");
            l.executeUpdate("CREATE TABLE IF NOT EXISTS Club(ClubName VARCHAR(25) NOT NULL PRIMARY KEY, Address VARCHAR(30), DateFormed DATE)");  
            l.executeUpdate("CREATE TABLE IF NOT EXISTS Game(GameID VARCHAR(4) NOT NULL PRIMARY KEY, DatePlayed DATE, BoardNumber TINYINT, Score VARCHAR(7), MatchID INT(4), WhitePlayer CHAR(25), BlackPlayer CHAR(25))");
            l.executeUpdate("CREATE TABLE IF NOT EXISTS Matches(MatchID INT(3) NOT NULL PRIMARY KEY, MatchDate DATE, Venue VARCHAR(25), Score VARCHAR(7), WinningClub VARCHAR(25), LosingClub VARCHAR(25))");
            
            //Create Foreign Keys
            l.executeUpdate("ALTER TABLE Player ADD CONSTRAINT fk_ClubName FOREIGN KEY(ClubName) REFERENCES Club(ClubName)"); 
            l.executeUpdate("ALTER TABLE Game ADD CONSTRAINT fk_MatchID FOREIGN KEY(MatchId) REFERENCES Matches(MatchID)"); 
            
            connb.commit(); 
            
            System.out.println("Tables and Foreign Keys Succesfully Created!");
            
            connb.close(); //closes connection
            
        }catch(Exception p)
            {
                System.out.println("Error when Creating Tables and adding Foreign Keys."+p);
            } 
    }//end createTables
    
    private void populateTables() throws SQLException
    {
        try
        {
         Connection connf = DriverManager.getConnection("jdbc:mysql://localhost/Chess", user ,pass); 
          connf.setAutoCommit(false);   
          
          Statement x = connf.createStatement(); 
          
          //set Foreign Key checks and populate Tables from Text File. 
          x.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");  
          x.executeUpdate("LOAD DATA LOCAL INFILE '/Users/ChristopherDunne/Desktop/Player.txt' INTO TABLE Player(PlayerName,DateOfBirth,FIDERating,FideTitle,ClubName)");
          x.executeUpdate("SET FOREIGN_KEY_CHECKS = 1"); 
          
          x.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");  
          x.executeUpdate("LOAD DATA LOCAL INFILE '/Users/ChristopherDunne/Desktop/Club.txt' INTO TABLE Club(ClubName,Address,DateFormed)"); 
          x.executeUpdate("SET FOREIGN_KEY_CHECKS = 1"); 
          
          x.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");  
          x.executeUpdate("LOAD DATA LOCAL INFILE '/Users/ChristopherDunne/Desktop/Game.txt' INTO TABLE Game(GameID,DatePlayed,BoardNumber,Score,MatchID,WhitePlayer,BlackPlayer)"); 
          x.executeUpdate("SET FOREIGN_KEY_CHECKS = 1"); 

          x.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");  
          x.executeUpdate("LOAD DATA LOCAL INFILE '/Users/ChristopherDunne/Desktop/Matches.txt' INTO TABLE Matches(MatchID,MatchDate,Venue,Score,WinningClub,LosingClub)"); 
          x.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");   
          
          connf.commit(); 
          
          System.out.println("Tables popualted");
          
          connf.close(); 
          
        }catch(Exception x){
          System.out.println("Error when Populating Tables with data from Text File."+x); 
        }
          
    }//end populateTables

}//end Class Tables
