import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DBConnect {
    private Connection connection;
    private Statement statemenet;
    private ResultSet resultSet;

    public DBConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chestionareauto?serverTimezone=UTC&autoReconnect=true&useSSL=false","root","andrei123");
            statemenet = connection.createStatement();
        }catch (Exception exc){
            System.out.println("Error: "+exc);
        }
    }

    public void getData(){
        try{
            String query = "select * from TOKENS";
            resultSet = statemenet.executeQuery(query);
            System.out.println("Records from Database");
            while(resultSet.next()){
                String idTokens = resultSet.getString("idTOKENS");
                System.out.println("Token: " + idTokens);
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    public boolean findToken(String token){
        try{
            String query = "Select * from TOKENS where idTOKENS = '" + token + "'";
            resultSet = statemenet.executeQuery(query);
            if(resultSet == null)
                return false;
            else
                return true;
        }catch(Exception ex){
            System.out.println(ex);
        }
        return false;
    }
}
