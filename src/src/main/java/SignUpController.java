import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import insidefx.undecorator.Undecorator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class SignUpController {

    @FXML private JFXButton backButton;
    @FXML private JFXButton signUpButton;
    @FXML private JFXTextField usernameText;
    @FXML private JFXTextField emailText;
    @FXML private JFXPasswordField passwordText;
    @FXML private JFXPasswordField passwordRepeatText;
    @FXML private JFXRadioButton termsButton;
    @FXML private JFXTextField firstNameText;
    @FXML private JFXTextField lastNameText;

    private ArrayList<Integer> questionsList = new ArrayList<>();
    private LinkedList<Integer> questionsLinkedList = new LinkedList<>();


    public void start(final Stage stage) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("/SignUp.fxml"));

        Undecorator undecorator = new Undecorator(stage, (Region) home);
        undecorator.getStylesheets().add("skin/undecorator.css");

        Scene homeScene = new Scene(undecorator);
        homeScene.setFill(Color.TRANSPARENT);

        stage.setScene(homeScene);
        stage.show();
    }


    @FXML public void handleBackAction(ActionEvent event) throws IOException {
        if (event.getSource() == backButton) {
            Stage stage = (Stage) backButton.getScene().getWindow();
            LoginController loginController = new LoginController();
            loginController.start(stage);
        }
    }

    @FXML private void handleSignUpButton(ActionEvent event) throws IOException {
        if (event.getSource() == signUpButton) {
            DBConnect connect = new DBConnect();
            if (connect.verifyUsernameAndPassword(usernameText.getText(), emailText.getText())) {
                if (!passwordText.getText().equals("") && passwordText.getText().equals(passwordRepeatText.getText()) && !firstNameText.getText().equals("") && !lastNameText.getText().equals("")) {
                    //Parolele introduse exista si sunt identice in ambele TextField
                    //Deci avem toate campurile , mai trebuie sa acceptam termenii si conditiile
                    if (termsButton.isSelected()) {
                        //Avem tot ce ne trebuie , putem sa creeam contul nou si sa stergem tokenul folosit pentru creeare.
                        connect.createAccount(usernameText.getText(), passwordText.getText(), emailText.getText(), firstNameText.getText(), lastNameText.getText());
                        connect.removeToken(UseTokenController.validToken);

                        for (int i = 1; i <= connect.getCountFromSQL("questions"); ++i) {
                            questionsList.add(i);
                        }

                        for (int i = 1; i <= connect.getCountFromSQL("questions"); ++i) {
                            questionsList.add(i);
                        }

                        Collections.shuffle(questionsList);

                        questionsLinkedList.addAll(questionsList);
                        /* Am creat un LinkedList cu intrebarile pentru mediul de invatare al accountului creat si il serializam in baza de date pentru a deserializa obiectul
                        * Linked List la fiecare logare in account.*/
                        LoginController.idAccount_Current = connect.getCountFromSQL("accounts");

                        Connection conn;
                        try{
                            conn = DBConnect.getConnection();
                            conn.setAutoCommit(false);

                            DBConnect.writeJavaObject(conn,questionsLinkedList);
                            conn.commit();

                        }catch (Exception ex){
                            ex.printStackTrace();
                        }


                        System.out.println("Your account has been created.");

                        Stage stage = (Stage) backButton.getScene().getWindow();
                        LoginController loginController = new LoginController();
                        loginController.start(stage);

                    } else {
                        System.out.println("Termenii nu sunt acceptati");
                    }
                } else {
                    System.out.println("Parola nu exista sau nu corespunde sau nu ai introdus firstName si lastName");
                }
            } else {
                System.out.println("Usernameul sau emailul exista in baza de date");
            }
        }
    }
}