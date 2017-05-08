import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class QuestionDetails {

    String getIdQuiz() {
        return idQuiz.get();
    }

    public StringProperty idQuizProperty() {
        return idQuiz;
    }

    public void setIdQuiz(String idQuiz) {
        this.idQuiz.set(idQuiz);
    }

    public String getQuestionText() {
        return questionText.get();
    }

    public StringProperty questionTextProperty() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText.set(questionText);
    }

    public String getAnswerA() {
        return answerA.get();
    }

    public StringProperty answerAProperty() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA.set(answerA);
    }

    public String getAnswerB() {
        return answerB.get();
    }

    public StringProperty answerBProperty() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB.set(answerB);
    }

    public String getAnswerC() {
        return answerC.get();
    }

    public StringProperty answerCProperty() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC.set(answerC);
    }

    public String getCorrectAnswer() {
        return correctAnswer.get();
    }

    public StringProperty correctAnswerProperty() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer.set(correctAnswer);
    }

    private final StringProperty idQuiz;
    private final StringProperty questionText;
    private final StringProperty answerA;
    private final StringProperty answerB;
    private final StringProperty answerC;
    private final StringProperty correctAnswer;

    public QuestionDetails(String idQuiz, String questionText, String answerA, String answerB
    , String answerC, String correctAnswer){
        this.idQuiz = new SimpleStringProperty(idQuiz);
        this.questionText = new SimpleStringProperty(questionText);
        this.answerA = new SimpleStringProperty(answerA);
        this.answerB = new SimpleStringProperty(answerB);
        this.answerC = new SimpleStringProperty(answerC);
        this.correctAnswer = new SimpleStringProperty(correctAnswer);
    }
}
