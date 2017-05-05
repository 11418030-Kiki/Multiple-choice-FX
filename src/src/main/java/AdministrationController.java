import com.jfoenix.controls.JFXButton;
import insidefx.undecorator.Undecorator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministrationController {

    @FXML private JFXButton backButton ;

    void start(Stage stage)throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("/Administration.fxml"));
        Undecorator undecorator = new Undecorator(stage,(Region)home);
        undecorator.getStylesheets().add("skin/undecorator.css");

        Scene homeScene = new Scene(undecorator);
        homeScene.setFill(Color.TRANSPARENT);
        stage.setScene(homeScene);
        stage.show();
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException,InterruptedException{
        if(event.getSource() == backButton){
            Stage stage = (Stage) backButton.getScene().getWindow();
           // stage.setTitle("Chestionare Auto categoria B");
            HomeController home = new HomeController();
            home.start(stage);
        }
    }

}
