import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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


public class AddNewQuestionController {

    @FXML private JFXTextField questionField;
    @FXML private JFXTextField answerA;
    @FXML private JFXTextField answerB;
    @FXML private JFXTextField answerC;
    @FXML private JFXTextField correctAnswer;


    @FXML private JFXButton saveButton ;
    @FXML private JFXButton resetButton ;
    @FXML private JFXButton backButton ;


    void start(Stage stage)throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("/AddNewQuestion.fxml"));
        Undecorator undecorator = new Undecorator(stage,(Region)home);
        undecorator.getStylesheets().add("skin/undecorator.css");
        Scene homeScene = new Scene(undecorator,630,470);
        homeScene.setFill(Color.TRANSPARENT);
        stage.setScene(homeScene);
        stage.show();
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException,InterruptedException {
        if (event.getSource() == backButton) {
            Stage stage = (Stage) backButton.getScene().getWindow();
            AdministrationController administration = new AdministrationController();
            administration.start(stage);
        }

        else if (event.getSource() == resetButton){
            questionField.setText("");
            answerA.setText("");
            answerB.setText("");
            answerC.setText("");
            correctAnswer.setText("");

        }
        else if(event.getSource() == saveButton){
            if(!questionField.getText().trim().isEmpty() && !answerA.getText().trim().isEmpty() &&
                    !answerB.getText().trim().isEmpty() && !answerC.getText().trim().isEmpty() &&
                    !correctAnswer.getText().trim().isEmpty()){
                DBConnect connect = new DBConnect();
                connect.insertQuestion(questionField.getText(),answerA.getText(),answerB.getText(),answerC.getText(),
                correctAnswer.getText());
            }
        }
    }
}
