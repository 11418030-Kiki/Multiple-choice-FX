import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController { // implements Initializable {
    @FXML private  Label firstName;
    @FXML private  Label lastName;
    @FXML private MenuItem profileInfo;
    @FXML private MenuItem exitButtonBar;
    @FXML private JFXButton logoutButton;
    @FXML private JFXButton incepeChestionar;


    public void start(Stage stage)throws IOException{
        Parent home = FXMLLoader.load(getClass().getResource("/Home.fxml"));
        Scene homeScene = new Scene(home, 600, 400);
        stage.setScene(homeScene);
        stage.show();
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException{
        if(event.getSource() == incepeChestionar){
            Parent chestionar = FXMLLoader.load(getClass().getResource("/Chestionar.fxml"));
            Scene scene = new Scene(chestionar,1024,768);
            Stage stage=(Stage) incepeChestionar.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            stage.setScene(scene);
            stage.show();
        }
    }
}
