/*
   DBClass ofera toate metodele necesare conexiunii cu baza de date MySQL.
   Pentru a le accesa e nevoie de a crea cate un obiect DBConnect pe unde ai nevoie si asta nu e ok
   Probabil metodele astea or sa fie statice si eventual mutate in alta clasa, asta cu DBConnect are nume
   cam nefericit.
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.sql.PreparedStatement;


public class DBConnect {
    private Statement statemenet;
    private ResultSet resultSet;

    private static final String WRITE_OBJECT_SQL = "UPDATE accounts SET object_value = ? WHERE idACCOUNT = ?";
    private static final String READ_OBJECT_SQL = "SELECT object_value FROM accounts WHERE idACCOUNT = ?";

    static Connection getConnection() throws Exception {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/chestionareauto?serverTimezone=UTC&autoReconnect=true&useSSL=false";
        String username = "root";
        String password = "andrei123";
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    DBConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chestionareauto?serverTimezone=UTC&autoReconnect=true&useSSL=false", "root", "andrei123");
            statemenet = connection.createStatement();
        }catch (Exception exc){
            System.out.println("Error: "+exc);
        }
    }

    void getData(){
        try{
            String query = "select * from TOKENS LIMIT 1";
            resultSet = statemenet.executeQuery(query);
            System.out.println("Records from Database");
            while(resultSet.next()){
                String idTokens = resultSet.getString("idTOKENS");
                System.out.println("Token: " + idTokens);
            }
        }catch(Exception ex){ex.printStackTrace();}
    }

    boolean findToken(String token){
        try{
            String query = "Select * from TOKENS where idTOKENS = '" + token + "'";
            resultSet = statemenet.executeQuery(query);
            return resultSet.next();
        }catch(Exception ex){ex.printStackTrace();}
        return false;
    }

    void removeToken(String token){
        try{
            String query = "DELETE FROM TOKENS WHERE idTOKENS = '" + token + "'";
             statemenet.executeUpdate(query);
        }catch(Exception ex){ex.printStackTrace();}
    }

    boolean verifyUsernameAndPassword(String username,String email){ //Daca returneaza true , nu exista useru sau mailu in baza de date si se poate face cont
        try{
            String query = "SELECT * FROM ACCOUNTS WHERE USERNAME = '"+username+"' OR EMAIL = '"+email+"'";
            resultSet = statemenet.executeQuery(query);
            return !resultSet.next();
        }catch(Exception ex){ex.printStackTrace();}
        return false;
    }

    void createAccount(String username,String password,String email,String firstName,String lastName){
        try{
            Integer number = 0;
            String queryForCount = "SELECT COUNT(*) FROM ACCOUNTS;";
            resultSet = statemenet.executeQuery(queryForCount);
            while(resultSet.next())
                number=resultSet.getInt(1);
            ++number;
            String query = "INSERT INTO ACCOUNTS (idACCOUNT,username,password,email,firstname,lastname) VALUES"+"("+number+","+"'"+username+"'"+","+"'"+password+"'"+","+"'"+email+"'"+","+"'"+firstName+"'"+","+"'"+lastName+"'"+")";
            statemenet.executeUpdate(query);
        }catch(SQLException ex){ex.printStackTrace();}
    }

    boolean verifyAccount(String username,String password){
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
        }catch (Exception ex){ ex.printStackTrace(); }

        return false;
    }

    String getInfoFromColumn(String column,int idAccount){
        try{
            String query = "SELECT "+column+" from ACCOUNTS WHERE idAccount = "+idAccount;
            resultSet = statemenet.executeQuery(query);
            if(!resultSet.next())
                return null;
            else
                return resultSet.getString(1);
        }catch(Exception ex){ex.printStackTrace();}
        return null;
    }

    boolean verifyAnswer(int idQuiz,String answer){
        try{
            String query = "SELECT * FROM questions WHERE idQuestion = "+idQuiz+" and raspunsCorect = '" +answer+ "'";
            resultSet = statemenet.executeQuery(query);
            return resultSet.next();
        }catch(Exception ex){ex.printStackTrace();}
        return false;
    }

    String getInfoFromQuestions(String column,Integer idQuiz){
        try{
            String query = "SELECT "+column+" from QUESTIONS WHERE idQUESTION = "+idQuiz.toString();
            resultSet = statemenet.executeQuery(query);
            if(!resultSet.next())
                return null;
            else
                return resultSet.getString(1);
        }catch (Exception ex){ex.printStackTrace();}
        return null;
    }

    Integer getCountFromSQL(String tableName){
        try{
            String query = "SELECT COUNT(*) FROM "+ tableName;
            resultSet = statemenet.executeQuery(query);
            if(!resultSet.next())
                return -1;
            else
            {
                return Integer.parseInt(resultSet.getString(1));
            }
        }catch (Exception ex){ex.printStackTrace();}
        return -1;
    }

    void getImageFromSQL(int idQuiz,ImageView imageView){
        try{
            byte[] fileBytes;
            String query = "SELECT imagine FROM QUESTIONS WHERE idQuestion = " + idQuiz;
            resultSet = statemenet.executeQuery(query);
            if(resultSet.next()){
                fileBytes = resultSet.getBytes(1);
                Image image = new Image(new ByteArrayInputStream(fileBytes));
                imageView.setImage(image);
            }

        }catch (Exception ex){
            //System.out.println(ex);
        }
    }

    static void writeJavaObject(Connection connect, Object object) throws Exception{
        //String className = object.getClass().getName();
        PreparedStatement pstmt = connect.prepareStatement(WRITE_OBJECT_SQL,Statement.RETURN_GENERATED_KEYS);

        pstmt.setObject(1,object);
        pstmt.setInt(2,LoginController.idAccount_Current);
        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        if(rs.next()){
            rs.getInt(1);
        }

        rs.close();
        pstmt.close();
    }

    static Object readJavaObject(Connection conn,int id)throws Exception{
        PreparedStatement pstmt = conn.prepareStatement(READ_OBJECT_SQL);
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        Object object = rs.getObject(1);
        //String className = object.getClass().getName();

        rs.close();
        pstmt.close();
        return object;
    }

    void setContorStatus(String column,int value,int id) {
        try {
            String query = "UPDATE accounts SET " + column + " = " + value + " WHERE idACCOUNT = " + id;
            statemenet.executeUpdate(query);
        }catch(Exception ex){ex.printStackTrace();}
    }

    void changePassword(String newPassword,int idAccount){
        try{
            String query = "UPDATE accounts SET password = '" + newPassword + "' WHERE idACCOUNT = " + idAccount ;
            statemenet.executeUpdate(query);
        }catch(SQLException ex) {ex.printStackTrace();}
    }

    void changeEmail(String newEmail,int idAccount){
        try{
            String query = "UPDATE accounts SET email = '" + newEmail + "' WHERE idACCOUNT = " + idAccount;
            statemenet.executeUpdate(query);
        }catch (Exception ex){ex.printStackTrace();}
    }

    boolean verifyEmailAndUsername(String email,String username){
        try{
            String query = "SELECT * FROM accounts WHERE email = '" + email + "' AND username = '" + username + "' ";
            resultSet = statemenet.executeQuery(query);
            return resultSet.next();
        }catch (Exception ex){ex.printStackTrace();}
        return false;
    }

    int getAccountID(String username){
        try{
            String query = "SELECT idACCOUNT FROM ACCOUNTS WHERE username = '" + username + "'";
            resultSet = statemenet.executeQuery(query);
            if(!resultSet.next())
                return -1;
            else return resultSet.getInt(1);
        }catch (Exception ex) {ex.printStackTrace();}
        return -1;
    }

    void insertQuestion(String question , String ansA, String ansB, String ansC, String correct ,Object ... args){
        try {
            if(args != null) {
                PreparedStatement pre = getConnection().prepareStatement("INSERT INTO questions (idQuestion,intrebareText,varianta1Text,varianta2Text,varianta3Text,raspunsCorect,imagine)" +
                        " VALUES (?,?,?,?,?,?,?)");
                pre.setInt(1, (getCountFromSQL("questions") + 1));
                pre.setString(2, question);
                pre.setString(3, ansA);
                pre.setString(4, ansB);
                pre.setString(5, ansC);
                pre.setString(6, correct);

                FileInputStream image  = (FileInputStream)args[0];
                File imgFile = (File)args[1];

                pre.setBinaryStream(7,(InputStream)image,(int)imgFile.length());

                pre.executeUpdate();
            }
            else{
                PreparedStatement pre = getConnection().prepareStatement("INSERT INTO questions (idQuestion,intrebareText,varianta1Text,varianta2Text,varianta3Text,raspunsCorect) VALUES" +
                        "(?,?,?,?,?,?)");
                pre.setInt(1, (getCountFromSQL("questions") + 1));
                pre.setString(2, question);
                pre.setString(3, ansA);
                pre.setString(4, ansB);
                pre.setString(5, ansC);
                pre.setString(6, correct);
                pre.executeUpdate();


            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void insertToken(String token){
        try{
            PreparedStatement pre = getConnection().prepareStatement("INSERT INTO tokens VALUES (?)");
            pre.setString(1,token);
            pre.executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    void editAQuestion(String intrebareText,String varianta1,String varianta2,String varianta3,String raspunsCorect,String idQuiz,Object ... args){

        try{

            if(args == null) {
                PreparedStatement pre = getConnection().prepareStatement("UPDATE questions SET intrebareText = ? , varianta1Text = ?" +
                        ", varianta2Text = ? , varianta3Text = ? , raspunsCorect = ? WHERE idQuestion = ?");
                pre.setString(1,intrebareText);
                pre.setString(2,varianta1);
                pre.setString(3,varianta2);
                pre.setString(4,varianta3);
                pre.setString(5,raspunsCorect);
                pre.setInt(6,Integer.parseInt(idQuiz));
                pre.executeUpdate();
            }
            else {
                PreparedStatement pre = getConnection().prepareStatement("UPDATE questions SET intrebareText = ? , varianta1Text = ?" +
                        ", varianta2Text = ? , varianta3Text = ? , raspunsCorect = ? ,imagine = ? WHERE idQuestion = ?");
                pre.setString(1,intrebareText);
                pre.setString(2,varianta1);
                pre.setString(3,varianta2);
                pre.setString(4,varianta3);
                pre.setString(5,raspunsCorect);
                FileInputStream image  = (FileInputStream)args[0];
                File imgFile = (File)args[1];


                pre.setBinaryStream(6,(InputStream)image,(int)imgFile.length());
                pre.setInt(7,Integer.parseInt(idQuiz));

                pre.executeUpdate();
            }

        }catch (Exception ex){ex.printStackTrace();}

    }


}
