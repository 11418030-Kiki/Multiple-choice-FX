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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class AccountController {


    @FXML private PieChart pieChart;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private JFXButton backButton;
    @FXML private JFXButton changePasswordButton;
    @FXML private JFXButton changeEmailButton;
    @FXML private JFXButton resetLearningButton;

    private ArrayList<Integer> questionsList = new ArrayList<>();
    private LinkedList<Integer> questionsLinkedList = new LinkedList<>();


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
            HomeController home = new HomeController();
            home.start(stage);
        }
        else if (event.getSource() == changePasswordButton){
            Stage stage = (Stage)changePasswordButton.getScene().getWindow();
            PasswordChangeController passchange = new PasswordChangeController();
            passchange.start(stage);
        }
        else if(event.getSource() == changeEmailButton){
            Stage stage = (Stage)changeEmailButton.getScene().getWindow();
            EmailChangeController emailChange = new EmailChangeController();
            emailChange.start(stage);
        }
        else if(event.getSource() == resetLearningButton){
            DBConnect connect = new DBConnect();
            for (int i = 1; i <= connect.getCountFromSQL("questions"); ++i) {
                questionsList.add(i);
            }

            for (int i = 1; i <= connect.getCountFromSQL("questions"); ++i) {
                questionsList.add(i);
            }

            Collections.shuffle(questionsList);

            questionsLinkedList.addAll(questionsList);


            Connection conn;
            try{
                conn = DBConnect.getConnection();
                conn.setAutoCommit(false);

                DBConnect.writeJavaObject(conn,questionsLinkedList);
                conn.commit();

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }
}
