import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import insidefx.undecorator.Undecorator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class EditQuestionController {

    @FXML private TableView<QuestionDetails> questionsTableView;
    @FXML private TableColumn<QuestionDetails,String> idQuestion;
    @FXML private TableColumn<QuestionDetails,String> intrebareText;
    @FXML private TableColumn<QuestionDetails,String> varianta1Text;
    @FXML private TableColumn<QuestionDetails,String> varianta2Text;
    @FXML private TableColumn<QuestionDetails,String> varianta3Text;
    @FXML private TableColumn<QuestionDetails,String> raspunsCorect;

    @FXML private JFXButton backButton;
    @FXML private JFXButton saveButton;
    @FXML private JFXButton resetButton;
    @FXML private JFXButton browseButton;
    @FXML private JFXTextField intrebareTextField;
    @FXML private JFXTextField answerATextField;
    @FXML private JFXTextField answerBTextField;
    @FXML private JFXTextField answerCTextField;
    @FXML private JFXTextField correctAnswerTextField;
    @FXML private ImageView imageView;

    private final FileChooser fileChooser = new FileChooser();
    private FileInputStream fin;
    private File file;


    private Stage stageQues;

    private ObservableList<QuestionDetails> data;
    private QuestionDetails clickedRow;



    void start(Stage stage)throws IOException{
        Parent home = FXMLLoader.load(getClass().getResource("/EditQuestion.fxml"));
        Undecorator undecorator = new Undecorator(stage,(Region)home);
        undecorator.getStylesheets().add("skin/undecorator.css");

        Scene homeScene = new Scene(undecorator,800,600);
        homeScene.setFill(Color.TRANSPARENT);
        stage.setScene(homeScene);
        stage.show();
        stageQues = stage;
    }

    @FXML private void initialize() {
        try {
            Connection conn = DBConnect.getConnection();
            data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM questions");
            while(rs.next()){
                data.add(new QuestionDetails(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
            }
        }catch (Exception ex){ex.printStackTrace();}

        idQuestion.setCellValueFactory(new PropertyValueFactory<>("idQuiz"));
        intrebareText.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        varianta1Text.setCellValueFactory(new PropertyValueFactory<>("answerA"));
        varianta2Text.setCellValueFactory(new PropertyValueFactory<>("answerB"));
        varianta3Text.setCellValueFactory(new PropertyValueFactory<>("answerC"));
        raspunsCorect.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));

        questionsTableView.setItems(null);
        questionsTableView.setItems(data);

        questionsTableView.setRowFactory(tv -> {
            TableRow<QuestionDetails> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(! row.isEmpty() && event.getButton() == MouseButton.PRIMARY &&
                        event.getClickCount() == 2){
                    clickedRow = row.getItem();
                    setInformation(clickedRow);
                }
            });
            return row;
        });

    }

    private void setInformation(QuestionDetails item){

        imageView.setImage(null);
        intrebareTextField.setText(item.getQuestionText());
        answerATextField.setText(item.getAnswerA());
        answerBTextField.setText(item.getAnswerB());
        answerCTextField.setText(item.getAnswerC());
        correctAnswerTextField.setText(item.getCorrectAnswer());
        try{
            DBConnect connect = new DBConnect();
            connect.getImageFromSQL(Integer.parseInt(item.getIdQuiz()),imageView);
        }catch (Exception ex) { ex.printStackTrace(); }
    }

    @FXML private void handleButtonAction(ActionEvent event) throws IOException ,InterruptedException{
        if(event.getSource() == backButton){
            Stage stage = (Stage) backButton.getScene().getWindow();
            AdministrationController home = new AdministrationController();
            home.start(stage);
        }
        if(event.getSource() == resetButton){
            imageView.setImage(null);
            intrebareTextField.setText("");
            answerATextField.setText("");
            answerBTextField.setText("");
            answerCTextField.setText("");
            correctAnswerTextField.setText("");
        }
        if(event.getSource() == browseButton) {
            try{
                file = fileChooser.showOpenDialog(stageQues);
                if(file.isFile() && (file.getName().contains(".jpg")) || file.getName().contains(".png") ||
                        file.getName().contains(".bmp") || file.getName().contains(".JPG")){

                    final Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);

                    fin = new FileInputStream(file);

                }
            }catch(NullPointerException ignored){}
        }
        if(event.getSource() == saveButton){
           /* if(!intrebareTextField.getText().trim().isEmpty() && !answerATextField.getText().trim().isEmpty() &&
                    !answerBTextField.getText().trim().isEmpty() && !answerCTextField.getText().trim().isEmpty() &&
                    !correctAnswerTextField.getText().trim().isEmpty())*/{
                DBConnect connect = new DBConnect();
                if(imageView.getImage() == null) {
                    connect.editAQuestion(intrebareTextField.getText(), answerATextField.getText(), answerBTextField.getText(), answerCTextField.getText(),
                            correctAnswerTextField.getText(),clickedRow.getIdQuiz(), null);
                }
                else{
                    connect.editAQuestion(intrebareTextField.getText(), answerATextField.getText(), answerBTextField.getText(), answerCTextField.getText(),
                            correctAnswerTextField.getText(),clickedRow.getIdQuiz(),fin,file);
                }
            }
        }
    }
}
