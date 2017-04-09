
import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.omg.PortableInterceptor.HOLDING;

import java.io.IOException;

import static java.lang.System.exit;

public class LoginController {
    @FXML private JFXButton singIn;
    @FXML private JFXButton useToken;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label textLabel;
    @FXML private JFXButton exitButton;

    public static int idAccount_Current;

    @FXML private void handleButtonAction(ActionEvent event) throws IOException{

        if(event.getSource()==useToken) {
            Parent root = FXMLLoader.load(getClass().getResource("/UseToken.fxml"));
            Scene scene = new Scene(root,600,400);
            Stage stage=(Stage) useToken.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            stage.setScene(scene);
            stage.show();
        }

        if(event.getSource()==exitButton){
            exit(0);
        }

        if(event.getSource()==singIn) {
            DBConnect connect = new DBConnect();
            if (connect.verifyAccount(username.getText(), password.getText()) == true){
                Stage stage = (Stage)singIn.getScene().getWindow();
                stage.setTitle("Chestionare Auto categoria B");
                HomeController home = new HomeController();
                home.start(stage);
            }
            else{
                textLabel.setText("Datele introduse sunt invalide");
            }
        }
    }
}
