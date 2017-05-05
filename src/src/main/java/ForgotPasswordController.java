import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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

public class ForgotPasswordController {
    @FXML private JFXTextField emailText;
    @FXML private JFXTextField usernameText;
    @FXML private Label statusText;
    @FXML private JFXButton sendEmail;
    @FXML private JFXButton backButton;

    void start(Stage stage)throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("/ForgotPassword.fxml"));
        Undecorator undecorator = new Undecorator(stage,(Region)home);
        undecorator.getStylesheets().add("skin/undecorator.css");

        Scene homeScene = new Scene(undecorator,620,450);
        homeScene.setFill(Color.TRANSPARENT);
        stage.setScene(homeScene);

        stage.show();
    }

    private void forgotPasswordLogic(){
        String email = emailText.getText();
        String username = usernameText.getText();
        DBConnect connect = new DBConnect();
        if(!emailText.getText().trim().isEmpty() && !usernameText.getText().isEmpty() && connect.verifyEmailAndUsername(email,username)){
            //Exista combinatia mail - username in baza de date
            int accountID = connect.getAccountID(username);
            String password = connect.getInfoFromColumn("password",accountID);
            SendMailTLS.sendAnEmail(email,username,password); //Trimiem email-ul
            statusText.setText("Parola a fost trimisa prin email !");
        }
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException ,InterruptedException{
        if(event.getSource() == backButton){
            Stage stage = (Stage) backButton.getScene().getWindow();
            //stage.setTitle("Chestionare Auto categoria B");
            LoginController home = new LoginController();
            home.start(stage);
        }
        if(event.getSource() == sendEmail){
            forgotPasswordLogic();
        }
    }
}
