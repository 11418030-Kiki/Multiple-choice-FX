import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class HomeController { // implements Initializable {
    @FXML private MenuItem profileInfo;
    @FXML private MenuItem exitButtonBar;
    @FXML private JFXButton logoutButton;
    @FXML private JFXButton incepeChestionar;
    @FXML private JFXButton mediuInvatareButton;
    @FXML private Label firstName;
    @FXML private Label lastName;
    @FXML private JFXDatePicker currentDate;

    @FXML private void initialize(){
        DBConnect connect = new DBConnect();
        firstName.setText(connect.getInfoFromColumn("firstname",LoginController.idAccount_Current));
        lastName.setText(connect.getInfoFromColumn("lastname",LoginController.idAccount_Current));
        LocalDate today = LocalDate.now();
        currentDate.setValue(today);
        currentDate.setEditable(false); //Nu poate fi modificat
        currentDate.setMouseTransparent(true); //Nu mai vede mouseu
        currentDate.setDefaultColor(Paint.valueOf("red"));
    }

    public void start(Stage stage)throws IOException{
        Parent home = FXMLLoader.load(getClass().getResource("/Home.fxml"));
        Scene homeScene = new Scene(home, 600, 400);
        stage.setScene(homeScene);
        stage.show();
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException{
        if(event.getSource() == incepeChestionar){
            Stage stage = (Stage)incepeChestionar.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            ChestionarController home = new ChestionarController();
            home.start(stage);
        }
        else if(event.getSource() == logoutButton){
            Parent login = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(login,600,400);
            Stage stage = (Stage)logoutButton.getScene().getWindow();
            stage.setTitle("Chestionare auto categoria B");
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource() == mediuInvatareButton){
            Stage stage = (Stage)mediuInvatareButton.getScene().getWindow();
            stage.setTitle("Chestionare Auto categoria B");
            MediuInvatareController mediu = new MediuInvatareController();
            mediu.start(stage);
        }
    }
}
