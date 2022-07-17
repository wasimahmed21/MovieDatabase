package MovieDatabaseProject;


import java.sql.Connection;        //needed for connection
import java.sql.DriverManager;     // for establishing connection and loading
import java.sql.PreparedStatement; //this represents a precompiled SQL statement.
import java.sql.ResultSet;        //representing a database result set, which is usually generated by executing a statement 
import java.sql.SQLException;     //database access error or other errors represented using the getMessage() 
import java.sql.Statement;        //executing a SQL statement


// we can just import java.sql.*;(which includes everything)


//Establishing the connection
// Do remember the library sqlite-jdbc to be downloaded 

public class Movies {

	private Connection connect() { 
		Connection conn=null;
			
		try
        {
            Class.forName("org.sqlite.JDBC");
            conn=DriverManager.getConnection("jdbc:sqlite:movies.db");
            conn.setAutoCommit(false);
        }
        catch(SQLException | ClassNotFoundException e)
        {
            System.err.println(e.getClass().getName()+":"+e.getMessage());
            System.exit(0);
        }
        return conn;

			}
			
		
	public void createTable() {
		String sql= "CREATE TABLE MOVIES " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           TEXT    NOT NULL, " + 
                " ACTOR          TEXT     NOT NULL, " +
                " ACTRESS        TEXT     NOT NULL, " + 
                " DIRECTOR        TEXT     NOT NULL),"+
                " YEAROFRELEASE  CHAR(4))"; 
		   try
		   {
			   Connection conn=this.connect();
			   Statement st=conn.createStatement();
			   st.execute(sql);
		   }
		   catch(SQLException e) {
			   System.out.println(e.getMessage());
		   }
		}
	
	
	
	public void insert(int id,String name,String actor,String actress,String director,String year) {
		String info ="INSERT INTO MOVIES(ID,NAME,ACTOR,ACTRESS,DIRECTOR,YEAROFRELEASE) VALUES(?,?,?,?,?,?)"; 
		try {
			Connection conn=this.connect();
			
			PreparedStatement pst=conn.prepareStatement(info);
            pst.setInt(1,id);
            pst.setString(2,name);
            pst.setString(3,actor);
            pst.setString(4,actress);
            pst.setString(5,name);
            pst.setString(6,year);
            pst.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
//the movies data in the table, you need to query the movie details (name, actor, actress, director, year of release) using a SELECT statement	
	
	public void selectAll() 
    {

        try
        {
            Connection conn =this.connect();
            Statement st = conn.createStatement();
            ResultSet rs= st.executeQuery("SELECT * FROM MOVIES;");
            while(rs.next())
            {
                int id=rs.getInt("ID");
                String name=rs.getString("NAME");
                String actor=rs.getString("ACTOR");
                String actress=rs.getString("ACTRESS");
                String year=rs.getString("YEAROFRELEASE");
                String director=rs.getString("DIRECTOR");
                
                System.out.println("MOVIE ID = "+id);
                System.out.println("MOVIE NAME = "+name);
                System.out.println("ACTOR NAME = "+actor);
                System.out.println("ACTRESS NAME = "+actress);
                System.out.println("DIRECTOR NAME = "+director);
                System.out.println("YEAR OF RELEASE = "+year);
                System.out.println();
            }
            rs.close();
            st.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
	
	
//as well as use a query with parameter like actor name to select movies based on the actor's name.

    public void selectparam() 
    {

        try
        {
            Connection conn =this.connect();
            Statement st = conn.createStatement();
            ResultSet rs= st.executeQuery("SELECT NAME FROM MOVIES WHERE ACTOR='YASH':");
            System.out.println("The movies in which Yash is a Actor: ");
            while(rs.next())
            {
                String name=rs.getString("NAME");
                System.out.println(name);
            }
            rs.close();
            st.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
	
	
	
	public static void main(String[] args) {
		Movies list =new Movies(); //creating an object of the class Movies
        list.createTable();
        list.insert(1,"VIKRAM","KAMAL HASAN","AGENT TINA","LOKESH KANAGARAJ","2022");
        list.insert(2,"NAYAGAN","KAMAL HASSAN","SARANYA","MANI RATHNAM","1987");
        list.insert(3,"KGF2","YASH","SRINIDHI","PRASHANTH","2022");
        list.insert(4,"AYAN","SURIYA","TAMANNAH","K V ANAND","2009");
        list.selectAll();
        list.selectparam();
	}

}