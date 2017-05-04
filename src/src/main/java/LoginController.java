
import com.jfoenix.controls.*;
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
    @FXML private JFXButton signIn;
    @FXML private JFXButton useToken;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label textLabel;
    @FXML private JFXButton exitButton;
    @FXML private JFXButton forgotPasswordButton;

    public static int idAccount_Current;

    void start(Stage stage)throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        Scene homeScene = new Scene(home, 600, 400);
        stage.setScene(homeScene);
        stage.show();
    }

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

        if(event.getSource()==signIn) {
            DBConnect connect = new DBConnect();
            if (connect.verifyAccount(username.getText(), password.getText()) == true){
                Stage stage = (Stage)signIn.getScene().getWindow();
                stage.setTitle("Chestionare Auto categoria B");
                HomeController home = new HomeController();
                home.start(stage);
            }
            else{
                textLabel.setText("Datele introduse sunt invalide");
                forgotPasswordButton.setVisible(true);
            }
        }
        if(event.getSource() == forgotPasswordButton){
            Stage stage = (Stage) forgotPasswordButton.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            ForgotPasswordController fpc = new ForgotPasswordController();
            fpc.start(stage);
        }
    }
}
