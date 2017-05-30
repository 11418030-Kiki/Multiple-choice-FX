import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import insidefx.undecorator.Undecorator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static java.lang.System.exit;


public class ChestionarController {

    @FXML private Text timpText;
    @FXML private JFXButton sendAnswer;
    @FXML private JFXButton exitButton;
    @FXML private JFXButton jumpButton;

    @FXML private Label quizText;
    @FXML private Label gresiteText;
    @FXML private Label corecteText;

    @FXML private JFXCheckBox answerA;
    @FXML private JFXCheckBox answerB;
    @FXML private JFXCheckBox answerC;

    @FXML private ImageView imagineQuiz;

    private boolean isStarted = false;
    private boolean questionsAreOver = false;

    private Integer correctAnswers = 0;
    private Integer wrongAnswers = 0;
    private int i = -1;

    private StringBuilder answer = new StringBuilder();


    private Timer timer = new Timer() ;

    private int countDown  = 1800;

    private void startCountDown(){
        //Pentru cronometru
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                Platform.runLater(() -> {

                    countDown--;
                    timpText.setText("Timp ramas: " + countDown / 60 + ": "+countDown % 60);

                    if(countDown < 0) {
                        timer.cancel();
                        exit(0);

                    }
                });
            }
        },1000,1000);
    }

    private static Integer idQuiz ;

    private final ArrayList<Integer> list = new ArrayList<>();
    //private ArrayList<String> questionArray = new ArrayList<String>(); Nu il mai folosim acum era ceva pentru schimbat ordine raspuns
    private Queue<Integer> sariQueue = new LinkedList<>();

    public void start(Stage stage)throws IOException{

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        Parent home = FXMLLoader.load(getClass().getResource("/Chestionar.fxml"));

        Undecorator undecorator = new Undecorator(stage,(Region)home);
        undecorator.getStylesheets().add("skin/undecorator.css");

        stage.setX((screenBounds.getWidth() - 1024) / 2);
        stage.setY((screenBounds.getHeight() - 768) / 2);

        Scene homeScene = new Scene(undecorator);
        homeScene.setFill(Color.TRANSPARENT);
        stage.setScene(homeScene);
        stage.show();
    }

    private void checkAnswer() { //O mica functie care verifica ce buton ai apasat
        if(answerA.isSelected()){
            answer.append("a");
        }
        if(answerB.isSelected()){
            answer.append("b");
        }
        if(answerC.isSelected()){
            answer.append("c");
        }
    }

    private void generateQuestions(){
        //Creaza o lista cu idQuiz-urile din baza de date, iar apoi face un
        //shuffle pentru a le amesteca astfel avem chestionare diferite mereu
        DBConnect connect = new DBConnect();
        if(list.isEmpty()) {
            for (int i = 1; i <= connect.getCountFromSQL("questions"); i++) {
                list.add(i);
            }
            Collections.shuffle(list);
        }
        if(i < 25 ) {
            idQuiz = list.get(++i);
        }
        if(i == 25 && !sariQueue.isEmpty() && questionsAreOver){
            //Daca i a ajuns la 25 , o sa modificam pe idQuiz din sariQueue
            idQuiz = sariQueue.element();
        }
    }

    @FXML private void initialize() throws InterruptedException {



        //Aici trebuie sa initializam o intrebare , apoi cand dai click pe buton;
        DBConnect connect = new DBConnect();
        generateQuestions();
        if(!isStarted) {
            isStarted = true;
            startCountDown();
        }



       /* Aici deselectam radio butoanele si stegem imaignea care a fost inainte in imageView. In cazul in care
                vine o intrebare fara o imagine si inainte a fost una cu imagine , imaginea trebuie sa dispara
                in plus radiobutoanele nu trebuie sa ramana selectate.*/
        answerA.setSelected(false);
        answerB.setSelected(false);
        answerC.setSelected(false);
        imagineQuiz.setImage(null);
        corecteText.setText("Intrebari corecte: "+correctAnswers);
        gresiteText.setText("Intrebari gresite: "+wrongAnswers);

        //Adaugam variantele de raspuns in arraylist-ul questionArray
        answerA.setText(connect.getInfoFromQuestions("varianta1Text",idQuiz));
        answerB.setText(connect.getInfoFromQuestions("varianta2Text",idQuiz));
        answerC.setText(connect.getInfoFromQuestions("varianta3Text",idQuiz));
        /*Le amestecam , in felul asta o sa avem variantele de raspuns in alta ordine, sa nu invatam mecanic
             Vezi examen CCNA.. :)) */
        //Collections.shuffle(questionArray);

        quizText.setText(connect.getInfoFromQuestions("intrebareText",idQuiz));
    /*  answerA.setText(questionArray.get(0));
        answerB.setText(questionArray.get(1));
        answerC.setText(questionArray.get(2));
        questionArray.removeAll(questionArray);*/
        try{
            connect.getImageFromSQL(idQuiz,imagineQuiz);
        }catch (Exception ex) { ex.printStackTrace(); }


    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException ,InterruptedException{
        if (event.getSource() == sendAnswer) {  //Apasam pe buton , verificam ce am bifat
            checkAnswer();  //Verificam ce am bifat si adaugam in string-ul answer , asta o sa il verfiicam cu raspunsu din baza de date
            DBConnect connect = new DBConnect();
            if (connect.verifyAnswer(idQuiz, answer.toString())) {  //Verificam daca ce am introdus este corect;
                correctAnswers++;
                if(questionsAreOver){
                    sariQueue.remove();
                }
                corecteText.setText("Intrebari corecte: "+correctAnswers);
            } else {
                wrongAnswers++;
                if(questionsAreOver){
                    sariQueue.remove();
                    sariQueue.add(idQuiz);
                }
                gresiteText.setText("Intrebari gresite: "+wrongAnswers);
            }
            answer.delete(0,answer.length());
            if(wrongAnswers > 4 || correctAnswers + wrongAnswers == 26){
                //Aici ori pici ori treci
                if(wrongAnswers > 4) {
                    CongratulationsController.resultText = "RESPINS";
                    connect.setContorStatus("chestionareRespins",
                            Integer.parseInt(connect.getInfoFromColumn("chestionareRespins",LoginController.idAccount_Current)) + 1,
                            LoginController.idAccount_Current);
                }
                else {
                    CongratulationsController.resultText = "ADMIS";

                    connect.setContorStatus("chestionareAdmis",
                            Integer.parseInt(connect.getInfoFromColumn("chestionareAdmis",LoginController.idAccount_Current)) + 1,
                            LoginController.idAccount_Current);

                }

                Parent result = FXMLLoader.load(getClass().getResource("/Congratulations.fxml"));
                Scene scene = new Scene(result,600,400);
                Stage stage=(Stage) sendAnswer.getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            }
            if(correctAnswers+wrongAnswers < 26 ){
                //Daca nu s-au pus 26 de intrebari mai punem una
                initialize();
            }
            if(wrongAnswers+correctAnswers+sariQueue.size() == 26 && !questionsAreOver){
                questionsAreOver = true;
                initialize();
            }


        }
        if(event.getSource() == exitButton){
            //Ceva cu are you shure ar fi frumy
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirma");
            alert.setHeaderText("Chestionare Auto categoria B");
            alert.setContentText("Esti sigur ca vrei sa iesi din test?");
            //This is fucking lambda expression

            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK){
                    try {
                        Stage stage = (Stage) exitButton.getScene().getWindow();
                        HomeController home = new HomeController();
                        home.start(stage);
                    }catch (IOException ex){ ex.printStackTrace(); }
                }
                else{
                    alert.close();
                }
            });
        }

        if(event.getSource() == jumpButton){    //Butonul sari peste
            if(sariQueue.contains(idQuiz)){
                sariQueue.remove(idQuiz);
                sariQueue.add(idQuiz);
                //Daca contine intrebarea , o stergem din coada iar apoi o punem iar, gen o punem la rand iar
                initialize();
            }
            else {
                //Daca nu exista in coada o adaugam
                sariQueue.add(idQuiz);
                initialize();
            }
            if(wrongAnswers+correctAnswers+sariQueue.size() == 26 && !questionsAreOver){
                //Daca in momentul in care am dat click pe sari este la ultima intrebare, si suma aceasta da 26
                questionsAreOver = true;
                //Setam acest bool care marcheaza ca s-au terminat intrebarile din chestionar, iar acum actualizarea lui idQuiz va avea loc din coada
                //Pana cand wrongAnswer + correctAnswer = 26 sau wrongAnswer > 4
            }
        }
    }
}