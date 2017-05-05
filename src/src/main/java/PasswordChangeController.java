import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import insidefx.undecorator.Undecorator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordChangeController {

    @FXML private JFXButton backButton;
    @FXML private JFXButton changeButton;
    @FXML private Label resultLabel;
    @FXML private JFXPasswordField oldPasswordField;
    @FXML private JFXPasswordField newPasswordField;
    @FXML private JFXPasswordField confirmNewPasswordField;

    private void changePasswordLogic(){
        DBConnect connect = new DBConnect();
        if(oldPasswordField.getText().equals(connect.getInfoFromColumn("password",LoginController.idAccount_Current))){
            //Daca parola old este aia din baza de date, mergem mai departe
            if(!newPasswordField.getText().trim().isEmpty() && !confirmNewPasswordField.getText().trim().isEmpty() && newPasswordField.getText().equals(confirmNewPasswordField.getText()) && newPasswordField.getText().matches("^[ ]{9},{16}")){
                //Daca parola nu este introdusa si parola noua corespunde cu confirmarea, scriem in baza de date si afisam
                connect.changePassword(newPasswordField.getText(),LoginController.idAccount_Current);
                resultLabel.setText("Parola a fost schimbata cu success !");
            }
            else{
                resultLabel.setText("Parola introdusa este invalida !");
            }
        }
        else{
            resultLabel.setText("Parola veche nu este corecta !");
        }
    }


    void start(Stage stage)throws IOException{
        Parent home = FXMLLoader.load(getClass().getResource("/PasswordChange.fxml"));
        Undecorator undecorator = new Undecorator(stage,(Region)home);
        undecorator.getStylesheets().add("skin/undecorator.css");
        Scene homeScene = new Scene(undecorator,620,430);
        homeScene.setFill(Color.TRANSPARENT);
        stage.setScene(homeScene);
        stage.show();
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException ,InterruptedException{

        if(event.getSource() == backButton){
            Stage stage = (Stage) backButton.getScene().getWindow();
            //stage.setTitle("Chestionare Auto categoria B");
            AccountController accountController = new AccountController();
            accountController.start(stage);
        }
        else if (event.getSource() == changeButton){
            changePasswordLogic();
        }

    }

}
