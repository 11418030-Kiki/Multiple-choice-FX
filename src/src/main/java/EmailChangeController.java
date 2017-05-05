import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
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

public class EmailChangeController {
    @FXML private JFXTextField actualMailText;
    @FXML private JFXTextField newMailText;
    @FXML private JFXButton changeMailButton;
    @FXML private JFXButton backButton;
    @FXML private JFXRadioButton termsButton;
    @FXML private Label messageLabel;

    void start(Stage stage)throws IOException{
        Parent home = FXMLLoader.load(getClass().getResource("/EmailChange.fxml"));
        Undecorator undecorator = new Undecorator(stage,(Region)home);
        undecorator.getStylesheets().add("skin/undecorator.css");

        Scene homeScene = new Scene(undecorator,620,430);
        homeScene.setFill(Color.TRANSPARENT);
        stage.setScene(homeScene);
        stage.show();
    }

    @FXML private void initialize() throws InterruptedException{
        DBConnect connect = new DBConnect();
        actualMailText.setPromptText(connect.getInfoFromColumn("email",LoginController.idAccount_Current));
        actualMailText.setDisable(true);
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException ,InterruptedException{
        if(event.getSource() == backButton){
            Stage stage = (Stage) backButton.getScene().getWindow();
            //stage.setTitle("Chestionare Auto categoria B");
            AccountController accountController = new AccountController();
            accountController.start(stage);
        }
        if(event.getSource() == changeMailButton){
            if(!newMailText.getText().trim().isEmpty() && termsButton.isSelected() && newMailText.getText().contains("@")){
                DBConnect connect = new DBConnect();
                connect.changeEmail(newMailText.getText(),LoginController.idAccount_Current);
                messageLabel.setText("Success !");
            }
        }
    }
}
