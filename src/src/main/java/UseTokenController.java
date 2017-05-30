
import com.jfoenix.controls.JFXTextField;
import insidefx.undecorator.Undecorator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class UseTokenController {
    @FXML private Button backButton;
    @FXML private Button useToken;
    @FXML private JFXTextField textField;

    static String validToken;

    @FXML private Label MessageText;


    void start(Stage stage)throws IOException{
        Parent home = FXMLLoader.load(getClass().getResource("/UseToken.fxml"));
        Undecorator undecorator = new Undecorator(stage,(Region)home);
        undecorator.getStylesheets().add("skin/undecorator.css");

        Scene homeScene = new Scene(undecorator);
        homeScene.setFill(Color.TRANSPARENT);
        stage.setScene(homeScene);
        stage.show();
    }

    @FXML private void handleButtonAction(ActionEvent event)throws IOException{
        DBConnect connect = new DBConnect();
        String imputToken = textField.getText();
        if(imputToken.equals("")) {
            MessageText.setText("Please insert a token.");
        }
        if(!connect.findToken(imputToken)) {
            MessageText.setText("Your token is invalid, please try again.");
        }
        if(connect.findToken(imputToken)){
            validToken = imputToken;
            MessageText.setText("Your token is correct.");
            Stage stage = (Stage) useToken.getScene().getWindow();
            SignUpController signUpController = new SignUpController();
            signUpController.start(stage);
        }
        if(event.getSource()==backButton) {
            Stage stage = (Stage) backButton.getScene().getWindow();
            LoginController loginController = new LoginController();
            loginController.start(stage);
        }

    }
}
