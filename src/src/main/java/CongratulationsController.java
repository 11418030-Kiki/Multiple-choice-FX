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

    public static String resultText ;

    @FXML private void initialize(){
        resultMessage.setText("Ai fost declarat " + resultText);
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException{
        if(event.getSource() == tryAgainButton){

            Parent incepeIar = FXMLLoader.load(getClass().getResource("/Chestionar.fxml"));
            Scene scene = new Scene(incepeIar,1024,768);
            Stage stage = (Stage)tryAgainButton.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            stage.setScene(scene);
            stage.show();

        }
        if(event.getSource() == goHomeButton){

            Parent goHome = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            Scene scene = new Scene(goHome,600,400);
            Stage stage = (Stage) goHomeButton.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            stage.setScene(scene);
            stage.show();

        }
    }




}