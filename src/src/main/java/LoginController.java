
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML private Button singIn;
    @FXML private Button useToken;
    @FXML private TextField username;

    @FXML private void handleButtonAction(){
        username.setText("I love JavaFX");
    }

    @FXML private void handleUseToken(ActionEvent event) throws IOException{
        if(event.getSource()==useToken) {
            Parent root = FXMLLoader.load(getClass().getResource("/UseToken.fxml"));
            Scene scene = new Scene(root,600,400);
            Stage stage=(Stage) useToken.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            stage.setScene(scene);
            stage.show();
        }
    }

}
