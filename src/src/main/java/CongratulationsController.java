import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CongratulationsController {

    @FXML private Label resultMessage;
    @FXML private JFXButton tryAgainButton;
    @FXML private JFXButton goHomeButton;

    static String resultText ;

    @FXML private void initialize(){
        resultMessage.setText("Ai fost declarat " + resultText);
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException{
        if(event.getSource() == tryAgainButton){

            Stage stage = (Stage)tryAgainButton.getScene().getWindow();
            ChestionarController home = new ChestionarController();
            home.start(stage);

        }
        if(event.getSource() == goHomeButton){

            Parent goHome = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            Scene scene = new Scene(goHome,600,400);
            Stage stage = (Stage) goHomeButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
    }

}
