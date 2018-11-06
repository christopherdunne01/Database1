package chess;

import java.io.IOException; 
import java.sql.*;
import java.util.Scanner; 

public class Query {
    
    private final String user = ("Chris");
    private final String pass = ("password");
    
    Scanner sc = new Scanner(System.in); 
    
    protected void runQuery() throws SQLException, IOException
    {
        this.ask();   //call ask method which then calls query method
    }
    
     protected void ask() throws SQLException
    {
        System.out.println("Enter 1 if you want to perform a stored proedure. Enter 2 to teminate the program: ");
        
        
        int answer = sc.nextInt(); 
        
        if(answer < 1 || answer > 2 )
        {
            System.out.println("Incorrect Number");
            this.ask();   
        }
        
        switch(answer)
        {
            case 1:
                this.queries();     
                break;
           
            case 2:
                System.out.println("System Successfully Terminated");
                System.exit(0);
                break; 
        }//end Switch 
        
    }//end ask
         
   
    private void queries() throws SQLException
    {
       
        int redo =1; 
        while(redo ==1 )
        {
             
        System.out.println("Please enter the number of what query you want:");
        System.out.println("1: Get PlayerName and FIDERating");
        System.out.println("2: Get Venue and DatePlayed at Venue");
        System.out.println("3: Get Winning Club where they scored over or equal to 1-0");
        System.out.println("4: Count number of players grouped to CoolChessers");
        System.out.println("5: Group ClubName that has won more than once");
        System.out.println("6: Enter Club's Name and get MatchID, MatchDate & Score");
        System.out.println("7: Get the Players who have above the average FIDERating for all Players");
        
        Scanner scan = new Scanner(System.in); 
        int input = scan.nextInt();
        
        if(input <1 || input > 8)
        {
            System.out.println("Invalid Number");      
        }
         
        String Data; 
        
        switch (input)
            {  
            case 1: this.getNameandFide();
                break;   
                
              case 2:
                  this.getVenueandDatePlayed();
                  break;
                               
              case 3:  
                  this.getWinningClubScoredAboveOne();
                  break;
                        
                    
              case 4:
                  this.countNumberofPlayersinCoolChessers();   
                  break;        
            
              case 5:      
                  this.countNumberofTimesifClubWonMoreThanOnce();
                  break;
                     
                  
             case 6: Data = getData("Enter ClubName:");
                    this.enterClubNameandGetMatchIDMatchDateandScore(Data);
                    break; 
                     
             case 7:
                   this.getPlayerNameWhoHasAboveAverageFIDE();
                   break;       
            }
            
            System.out.println("Would you like another Query? : \n - Yes : Press One \n - No: Press Two" ); 
            redo = scan.nextInt();
        }//end while loop
        
    }//end queries
    
    
    
        public String getData(String Prompt){
        String data; 
            System.out.println("");
            System.out.println(Prompt);//prompt user for input
            data = sc.next(); //scan input
        return data; 
        }
        
        //retieve each players name and FIDERating
        public void getNameandFide () throws SQLException{
            
            Statement query = Main.getQueryStatement();//call statement and USE Chess; 

            //SQL Statement. 
            ResultSet firstOutput=query.executeQuery("Select PlayerName, FIDERating FROM chess.PLAYER;");
            System.out.println("Output: ");
            
             while(firstOutput.next()) 
             {//get First String (PlayerName) & Second String(FIDERating)
             System.out.println(firstOutput.getString(1) + " " + firstOutput.getString(2));
             }
             Main.callCommit(); 
         }//end method
        
        
        //Get the venue of match and the DatePlayed
        public void getVenueandDatePlayed() throws SQLException{

        Statement query = Main.getQueryStatement();
        ResultSet secondOutput=query.executeQuery("Select Venue, DatePlayed FROM Game, Matches WHERE Matches.MatchID = Game.MatchID;");
           
            System.out.println("Output: ");
            
             while(secondOutput.next()) 
             {
             System.out.println(secondOutput.getString(1) + " " + secondOutput.getString(2));
             }
            Main.callCommit();
        }//end method
           
        //Get Winning Club that has achieved above 1-0
        public void getWinningClubScoredAboveOne() throws SQLException{
                
            Statement query = Main.getQueryStatement();
        
            ResultSet thirdOutput=query.executeQuery("SELECT WinningClub, MatchDate FROM MATCHES WHERE Score >= 1-0");
            System.out.println("Ouput: ");
           
             while(thirdOutput.next()) 
            {
             System.out.println(thirdOutput.getString(1) + " " + thirdOutput.getString(2));
            }
             Main.callCommit();
        }//end method
        
        //Count players in CoolChessers
        public void countNumberofPlayersinCoolChessers() throws SQLException{
            
            Statement query = Main.getQueryStatement();
            
            ResultSet fourthOutput=query.executeQuery("SELECT COUNT(PlayerName) FROM Player WHERE ClubName = 'CoolChessers' GROUP BY ClubName");
             System.out.println("Output: ");
            
             while(fourthOutput.next()) 
             {
             System.out.println("CoolChessers: " +fourthOutput.getString(1) +  " " );
             }
             Main.callCommit();
        }//end method
           
        //Count clubs that have won more than once
        public void countNumberofTimesifClubWonMoreThanOnce() throws SQLException{
                
            Statement query = Main.getQueryStatement();
            
            ResultSet fifthOutput=query.executeQuery("SELECT WinningClub, COUNT(WinningClub) FROM Matches GROUP BY WinningClub HAVING COUNT(WinningClub)>1;");
            System.out.println("Results: ");
            
             while(fifthOutput.next()) 
             {
             System.out.println(fifthOutput.getString(1) +  " " + fifthOutput.getString(2) +  " " );
             }
             Main.callCommit();
        }//end method
           
        //input clubname and retrieve MatchId, Match Date and Score
        public  void enterClubNameandGetMatchIDMatchDateandScore(String club) throws SQLException {
              
             Statement query = Main.getQueryStatement();

    
             ResultSet sixthOutput=query.executeQuery("SELECT MatchID, MatchDate, Score FROM MATCHES WHERE MatchID IN (SELECT MatchID FROM MATCHES WHERE WinningClub = '" + club + "');");
            
             System.out.println("Output: ");
             
             while(sixthOutput.next()) 
             {
               System.out.println(sixthOutput.getString(1) +"  " + sixthOutput.getString(2) + "    " + sixthOutput.getString(3) + "    ");
             }
            Main.callCommit();
        }//end method
        
        //List player names who has abover average FIDERating
        public  void getPlayerNameWhoHasAboveAverageFIDE() throws SQLException {
            
             Statement query = Main.getQueryStatement();
    
             ResultSet seventhOutput=query.executeQuery("SELECT PlayerName FROM Player WHERE FIDERating > (SELECT AVG(FIDERating) From Player);");
             System.out.println("Output: ");
         
             while(seventhOutput.next()) 
             {
                System.out.println(seventhOutput.getString(1) +"  ");
             }
            Main.callCommit();
       }//end method  
    
}//end class Query
