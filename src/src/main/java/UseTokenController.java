
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
    @FXML private TextField textField;

    @FXML private Label MessageText;

    @FXML private void handleTokenAction(){
        DBConnect connect = new DBConnect();
        String imputToken = textField.getText();
        if(imputToken.equals(""))
            MessageText.setText("Please insert a token.");
        if(connect.findToken(imputToken)==false)
            MessageText.setText("Your token is invalid, please try again.");
        else if(connect.findToken(imputToken) == true){
            MessageText.setText("Your token is correct.");
        }

    }

    @FXML private void handleBackButton(ActionEvent event) throws IOException{
            if(event.getSource()==backButton) {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(root,600,400);
            Stage stage=(Stage) useToken.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            stage.setScene(scene);

            stage.show();
        }
    }
}
