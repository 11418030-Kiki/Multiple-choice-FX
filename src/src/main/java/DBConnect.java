import javax.swing.plaf.nimbus.State;
import java.sql.*;


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
            if(!resultSet.next())
                return false;
            else
                return true;
        }catch(Exception ex){
            System.out.println(ex);
        }
        return false;
    }

    public void removeToken(String token){
        try{
            String query = "DELETE FROM TOKENS WHERE idTOKENS = '" + token + "'";
             statemenet.executeUpdate(query);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    public boolean verifyUsernameAndPassword(String username,String email){ //Daca returneaza true , nu exista useru sau mailu in baza de date si se poate face cont
        try{
            String query = "SELECT * FROM ACCOUNTS WHERE USERNAME = '"+username+"' OR EMAIL = '"+email+"'";
            resultSet = statemenet.executeQuery(query);
            if(!resultSet.next()) {
                return true;
            }
            else
                return false;

        }catch(Exception ex){
            System.out.println(ex);
        }
        return false;
    }

    public void createAccount(String username,String password,String email){
        try{
            Integer number = 0;
            String queryForCount = "SELECT COUNT(*) FROM ACCOUNTS;";
            resultSet = statemenet.executeQuery(queryForCount);
            while(resultSet.next())
                number=resultSet.getInt(1);
            ++number;
            String query = "INSERT INTO ACCOUNTS VALUES"+"("+number+","+"'"+username+"'"+","+"'"+password+"'"+","+"'"+email+"')";
            statemenet.executeUpdate(query);
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
}
