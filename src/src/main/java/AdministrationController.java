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
import java.util.UUID;

public class AdministrationController {

    @FXML private JFXButton backButton ;
    @FXML private JFXButton addQuestionButton;
    @FXML private JFXButton generateTokensButton;
    @FXML private JFXButton editQuestionsButton;

    private void generateTokens(){
        DBConnect connect = new DBConnect();
        for(int i = 0 ; i < 10 ; i ++) {
            String uuid = UUID.randomUUID().toString();
                connect.insertToken(uuid);
            }
        }

    void start(Stage stage)throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("/Administration.fxml"));
        Undecorator undecorator = new Undecorator(stage,(Region)home);
        undecorator.getStylesheets().add("skin/undecorator.css");

        Scene homeScene = new Scene(undecorator,620,430);
        homeScene.setFill(Color.TRANSPARENT);
        stage.setScene(homeScene);
        stage.show();
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException,InterruptedException{
        if(event.getSource() == backButton){
            Stage stage = (Stage) backButton.getScene().getWindow();
            HomeController home = new HomeController();
            home.start(stage);
        }

        else if (event.getSource() == addQuestionButton) {
            Stage stage = (Stage) addQuestionButton.getScene().getWindow();
            AddNewQuestionController add = new AddNewQuestionController();
            add.start(stage);
        }
        else if (event.getSource() == generateTokensButton){
            generateTokens();
        }
        else if(event.getSource() == editQuestionsButton){
            Stage stage = (Stage) backButton.getScene().getWindow();
            EditQuestionController editQuestionController = new EditQuestionController();
            editQuestionController.start(stage);
        }


    }

}
