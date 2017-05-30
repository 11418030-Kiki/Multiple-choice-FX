
import com.jfoenix.controls.*;
import insidefx.undecorator.Undecorator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static java.lang.System.exit;

public class LoginController extends Application {

    @FXML private JFXButton signIn;
    @FXML private JFXButton useToken;
    @FXML private JFXTextField username;
    @FXML private JFXPasswordField password;
    @FXML private Label textLabel;
    @FXML private JFXButton exitButton;
    @FXML private JFXButton forgotPasswordButton;


    static int idAccount_Current;

    @Override
    public void start(final Stage stage) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("/Login.fxml"));

        Undecorator undecorator = new Undecorator(stage, (Region) home);
        undecorator.getStylesheets().add("skin/undecorator.css");

        Scene homeScene = new Scene(undecorator);
        homeScene.setFill(Color.TRANSPARENT);

        stage.setScene(homeScene);
        stage.show();
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException {

        if (event.getSource() == useToken) {
            Stage stage = (Stage) useToken.getScene().getWindow();
            UseTokenController useTokenController = new UseTokenController();
            useTokenController.start(stage);
        }

        if (event.getSource() == exitButton) {
            exit(0);
        }

        if (event.getSource() == signIn || event.getSource() == username || event.getSource() == password) {
            DBConnect connect = new DBConnect();
            if (connect.verifyAccount(username.getText(), password.getText())) {
                Stage stage = (Stage) signIn.getScene().getWindow();
                HomeController home = new HomeController();
                home.start(stage);
            } else {
                textLabel.setText("Datele introduse sunt invalide");
                forgotPasswordButton.setVisible(true);
            }
        }
        if (event.getSource() == forgotPasswordButton) {
            Stage stage = (Stage) forgotPasswordButton.getScene().getWindow();
            ForgotPasswordController fpc = new ForgotPasswordController();
            fpc.start(stage);
        }

    }
}
