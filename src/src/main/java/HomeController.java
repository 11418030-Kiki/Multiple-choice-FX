import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import insidefx.undecorator.Undecorator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;

public class HomeController {

    @FXML private JFXButton logoutButton;
    @FXML private JFXButton incepeChestionar;
    @FXML private JFXButton mediuInvatareButton;
    @FXML private JFXButton accountInfo;
    @FXML private Label firstName;
    @FXML private Label lastName;
    @FXML private JFXDatePicker currentDate;
    @FXML private JFXTimePicker timePicker;
    @FXML private JFXButton adminButton;
    @FXML private JFXToggleButton musicToggle;


    private static boolean isStarted = false;


    private void playMusic(String song) {
        final URL resource = getClass().getResource("" + "mediaResources/" + song + ".mp3");
        final Media media = new Media(resource.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }


    @FXML
    private void initialize() {

        DBConnect connect = new DBConnect();

        if (Integer.parseInt(connect.getInfoFromColumn("admin", LoginController.idAccount_Current)) != 0) {
            adminButton.setVisible(true);
        }

        firstName.setText(connect.getInfoFromColumn("firstname", LoginController.idAccount_Current));
        lastName.setText(connect.getInfoFromColumn("lastname", LoginController.idAccount_Current));
        LocalDate today = LocalDate.now();
        LocalTime todayTime = LocalTime.now();
        currentDate.setValue(today);
        currentDate.setEditable(false); //Nu poate fi modificat
        currentDate.setMouseTransparent(true); //Nu mai vede mouseu
        currentDate.setDefaultColor(Paint.valueOf("red"));
        timePicker.setValue(todayTime);
        timePicker.setEditable(false);
        timePicker.setMouseTransparent(true);


    }

    public void start(final Stage stage) throws IOException {


        final URL resource = getClass().getResource("" + "mediaResources/nfs_home.mp3");
        final Media media = new Media(resource.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);

        Parent home = FXMLLoader.load(getClass().getResource("/Home.fxml"));

        Undecorator undecorator = new Undecorator(stage,(Region)home);
        undecorator.getStylesheets().add("skin/undecorator.css");


        Scene homeScene = new Scene(undecorator);

        homeScene.setFill(Color.TRANSPARENT);

        stage.setScene(homeScene);
        stage.show();

        if(!isStarted) {
            //mediaPlayer.play();
            isStarted = true;
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == incepeChestionar) {
            Stage stage = (Stage) incepeChestionar.getScene().getWindow();
            ChestionarController home = new ChestionarController();
            home.start(stage);
        } else if (event.getSource() == logoutButton) {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            LoginController loginController = new LoginController();
            loginController.start(stage);
        } else if (event.getSource() == mediuInvatareButton) {
            Stage stage = (Stage) mediuInvatareButton.getScene().getWindow();
            MediuInvatareController mediu = new MediuInvatareController();
            mediu.start(stage);
        } else if (event.getSource() == accountInfo) {
            Stage stage = (Stage) accountInfo.getScene().getWindow();
            AccountController accountController = new AccountController();
            accountController.start(stage);
        } else if (event.getSource() == adminButton) {
            Stage stage = (Stage) adminButton.getScene().getWindow();
            AdministrationController administrationController = new AdministrationController();
            administrationController.start(stage);
        }
    }
}
