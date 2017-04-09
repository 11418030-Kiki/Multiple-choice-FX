import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SingUpController {

    @FXML private JFXButton backButton;
    @FXML private JFXButton singUpButton;
    @FXML private JFXTextField usernameText;
    @FXML private JFXTextField emailText;
    @FXML private JFXPasswordField passwordText;
    @FXML private JFXPasswordField passwordRepeatText;
    @FXML private JFXRadioButton termsButton;
    @FXML private JFXTextField firstNameText;
    @FXML private JFXTextField lastNameText;

    @FXML
    private void handleBackAction(ActionEvent event) throws IOException {
        if(event.getSource()==backButton){
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(root,600,400);
            Stage stage = (Stage)backButton.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            stage.setScene(scene);
        }
    }
    @FXML
    private void handleSingUpButton(ActionEvent event) throws IOException{
        if(event.getSource()==singUpButton){
            DBConnect connect = new DBConnect();
            if (connect.verifyUsernameAndPassword(usernameText.getText(),emailText.getText()) == true)
            {
                if(passwordText.getText().equals("")==false && passwordText.getText().equals(passwordRepeatText.getText()) && firstNameText.getText().equals("")==false
                        && lastNameText.getText().equals("")==false){
                    //Parolele introduse exista si sunt identice in ambele TextField
                    //Deci avem toate campurile , mai trebuie sa acceptam termenii si conditiile
                    if(termsButton.isSelected())
                    {
                        //Avem tot ce ne trebuie , putem sa creeam contul nou si sa stergem tokenul folosit pentru creeare.
                        connect.createAccount(usernameText.getText(),passwordText.getText(),emailText.getText(),firstNameText.getText(),lastNameText.getText());
                        connect.removeToken(UseTokenController.validToken);
                        System.out.println("Your account has been created.");
                    }
                    else{
                        System.out.println("Termenii nu sunt acceptati");
                    }
                }
                else {
                    System.out.println("Parola nu exista sau nu corespunde sau nu ai introdus firstName si lastName");
                }
            }
            else{
                System.out.println("Usernameul sau emailul exista in baza de date");
            }
        }
    }
}
