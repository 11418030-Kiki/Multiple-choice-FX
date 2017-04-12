import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;


public class ChestionarController {

    @FXML private Label timeLabel;
    @FXML private JFXButton sendAnswer;
    @FXML private JFXButton exitButton;

    @FXML private Label quizText;

    @FXML private JFXRadioButton answerA;
    @FXML private JFXRadioButton answerB;
    @FXML private JFXRadioButton answerC;

    private String answer  = "";
    private Integer correctAnswers = 0;
    private Integer wrongAnswers = 0;

    public static Integer idQuiz = 1;

    private void checkAnswer() { //O mica functie care verifica ce buton ai apasat
        if (answerA.isSelected()) {
            answer+="a";
        }
        if (answerB.isSelected()) {
            answer+="b";
        }
        if (answerC.isSelected()) {
            answer+="c";
        }
    }

    @FXML private void initialize() throws InterruptedException {
        //Aici trebuie sa initializam o intrebare , apoi cand dai click pe buton;
        DBConnect connect = new DBConnect();
        quizText.setText(connect.getInfoFromQuestions("intrebareText",idQuiz));
        answerA.setText(connect.getInfoFromQuestions("varianta1Text",idQuiz));
        answerB.setText(connect.getInfoFromQuestions("varianta2Text",idQuiz));
        answerC.setText(connect.getInfoFromQuestions("varianta3Text",idQuiz));

    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == sendAnswer) {  //Apasam pe buton , verificam ce am bifat
            checkAnswer();  //Verificam ce am bifat si adaugam in string-ul answer , asta o sa il verfiicam cu raspunsu din baza de date
            DBConnect connect = new DBConnect();
            if (connect.verifyAnswer(idQuiz, answer)==true) {  //Verificam daca ce am introdus este corect;
                correctAnswers++;
                System.out.println("Ai raspuns corect ! ");
            } else {
                wrongAnswers++;
                System.out.println("Ai raspuns gresit :(");
            }
            answer = "";
            if(correctAnswers+wrongAnswers<26){
                //Daca nu s-au pus 26 de intrebari mai punem una
            }
            else if(correctAnswers+wrongAnswers==26){
                //
            }
        }
    }
}