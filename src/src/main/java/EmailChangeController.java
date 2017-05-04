import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmailChangeController {
    @FXML private JFXTextField actualMailText;
    @FXML private JFXTextField newMailText;
    @FXML private JFXButton changeMailButton;
    @FXML private JFXButton backButton;

    void start(Stage stage)throws IOException{
        Parent home = FXMLLoader.load(getClass().getResource("/EmailChange.fxml"));
        Scene homeScene = new Scene(home, 600, 400);
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
            stage.setTitle("Chestionare Auto categoria B");
            AccountController accountController = new AccountController();
            accountController.start(stage);
        }
    }
}
