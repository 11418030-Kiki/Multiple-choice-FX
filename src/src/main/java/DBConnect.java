/**
 *  DBClass ofera toate metodele necesare conexiunii cu baza de date MySQL.
 *  Pentru a le accesa e nevoie de a crea cate un obiect DBConnect pe unde ai nevoie si asta nu e ok
 *  Probabil metodele astea or sa fie statice si eventual mutate in alta clasa, asta cu DBConnect are nume
 *  cam nefericit.
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javax.imageio.ImageIO;
import javafx.scene.image.PixelReader;
import java.io.ByteArrayInputStream;
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

    public void createAccount(String username,String password,String email,String firstName,String lastName){
        try{
            Integer number = 0;
            String queryForCount = "SELECT COUNT(*) FROM ACCOUNTS;";
            resultSet = statemenet.executeQuery(queryForCount);
            while(resultSet.next())
                number=resultSet.getInt(1);
            ++number;
            String query = "INSERT INTO ACCOUNTS VALUES"+"("+number+","+"'"+username+"'"+","+"'"+password+"'"+","+"'"+email+"'"+","+"'"+firstName+"'"+","+"'"+lastName+"'"+")";
            statemenet.executeUpdate(query);
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }

    public boolean verifyAccount(String username,String password){
        try{
            String query = "SELECT * FROM ACCOUNTS WHERE USERNAME = '"+username+"'" + "AND PASSWORD = '"+password+"'";
            resultSet = statemenet.executeQuery(query);
            if(!resultSet.next()){
                return false;
            }
            else
            {
                LoginController.idAccount_Current = resultSet.getInt(1);
                return true;
            }
        }catch (Exception ex){ System.out.println(ex); }

        return false;
    }

    public String getInfoFromColumn(String column,int value){
        try{
            String query = "SELECT "+column+" from ACCOUNTS WHERE idAccount = "+value;
            resultSet = statemenet.executeQuery(query);
            if(!resultSet.next())
                return null;
            else
                return resultSet.getString(1);
        }catch(Exception ex){
            System.out.println(ex);
        }
        return null;
    }

    public boolean verifyAnswer(int idQuiz,String answer){
        try{
            String query = "SELECT * FROM questions WHERE idQuestion = "+idQuiz+" and raspunsCorect = '" +answer+ "'";
            resultSet = statemenet.executeQuery(query);
            if(!resultSet.next())
                return false;
            else{
                return true;
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return false;
    }

    public String getInfoFromQuestions(String column,Integer idQuiz){
        try{
            String query = "SELECT "+column+" from QUESTIONS WHERE idQUESTION = "+idQuiz.toString();
            resultSet = statemenet.executeQuery(query);
            if(!resultSet.next())
                return null;
            else
                return resultSet.getString(1);
        }catch (Exception ex){
            System.out.println(ex);
        }
        return null;
    }

    public Integer getCountFromSQL(String tableName){
        try{
            String query = "SELECT COUNT(*) FROM "+ tableName;
            resultSet = statemenet.executeQuery(query);
            if(!resultSet.next())
                return -1;
            else
            {
                Integer x = Integer.parseInt(resultSet.getString(1));
                return x;
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
        return -1;
    }

    public void getImageFromSQL(int idQuiz,ImageView imageView){
        try{
            byte[] fileBytes;
            String query = "SELECT imagine FROM QUESTIONS WHERE idQuestion = " + idQuiz;
            resultSet = statemenet.executeQuery(query);
            if(resultSet.next()){
                fileBytes = resultSet.getBytes(1);
                Image image = new Image(new ByteArrayInputStream(fileBytes));
                imageView.setImage(image);
            }
            else{
                return;
            }
        }catch (Exception ex){
            //System.out.println(ex);
        }
    }
}
