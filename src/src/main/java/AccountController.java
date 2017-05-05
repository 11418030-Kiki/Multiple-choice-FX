import com.jfoenix.controls.JFXButton;
import insidefx.undecorator.Undecorator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.URL;

public class AccountController {


    @FXML private PieChart pieChart;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private JFXButton backButton;
    @FXML private JFXButton changePasswordButton;
    @FXML private JFXButton changeEmailButton;

    void start(Stage stage)throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("/Account.fxml"));
        Undecorator undecorator = new Undecorator(stage,(Region)home);
        undecorator.getStylesheets().add("skin/undecorator.css");

        Scene homeScene = new Scene(undecorator);
        homeScene.setFill(Color.TRANSPARENT);
        stage.setScene(homeScene);
        stage.show();
    }



    @FXML private void initialize() throws InterruptedException {
        DBConnect connect = new DBConnect();

        double chestionareAdmis = Double.parseDouble(connect.getInfoFromColumn("chestionareAdmis",LoginController.idAccount_Current));
        double chestionareRespins = Double.parseDouble(connect.getInfoFromColumn("chestionareRespins",LoginController.idAccount_Current));


        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Chestionare Admis",chestionareAdmis),
                new PieChart.Data("Chestionare Respins",chestionareRespins));

        pieChart.setData(list);





        firstNameLabel.setText("Prenume: " + connect.getInfoFromColumn("firstname", LoginController.idAccount_Current));
        lastNameLabel.setText("Nume: " + connect.getInfoFromColumn("lastname",LoginController.idAccount_Current));



    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException ,InterruptedException{

        if(event.getSource() == backButton){
            Stage stage = (Stage) backButton.getScene().getWindow();
            //stage.setTitle("Chestionare Auto categoria B");
            HomeController home = new HomeController();
            home.start(stage);
        }
        else if (event.getSource() == changePasswordButton){
            Stage stage = (Stage)changePasswordButton.getScene().getWindow();
            //stage.setTitle("Chestionare Auto categoria B - Schimba Parola");
            PasswordChangeController passchange = new PasswordChangeController();
            passchange.start(stage);
        }
        else if(event.getSource() == changeEmailButton){
            Stage stage = (Stage)changeEmailButton.getScene().getWindow();
           // stage.setTitle("Chestionare Auto categoria B - Schimba Email");
            EmailChangeController emailChange = new EmailChangeController();
            emailChange.start(stage);
        }

    }
}
