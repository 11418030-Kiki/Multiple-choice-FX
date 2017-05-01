
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UseTokenController {
    @FXML private Button backButton;
    @FXML private Button useToken;
    @FXML private JFXTextField textField;

    public static String validToken;

    @FXML private Label MessageText;

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
            Parent root = FXMLLoader.load(getClass().getResource("/SignUp.fxml"));
            Scene scene = new Scene(root,600,400);
            Stage stage=(Stage) useToken.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            stage.setScene(scene);
            stage.show();
        }
        if(event.getSource()==backButton) {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(root,600,400);
            Stage stage=(Stage) backButton.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            stage.setScene(scene);
            stage.show();
        }

    }
}
