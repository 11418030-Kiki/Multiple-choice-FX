
import insidefx.undecorator.Undecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;


public class Main extends Application {




    @Override
    public void start(final Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));

        Undecorator undecorator = new Undecorator(primaryStage, (Region)root);
        undecorator.getStylesheets().add("skin/undecorator.css");

        //primaryStage.setTitle("Chestionare Auto categoria B");
        //Scene scene = new Scene(root,600,400);
        Scene scene = new Scene(undecorator);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {

        System.out.println("Starting the aplication...");
        DBConnect connect = new DBConnect();
        connect.getData();
        launch(args);
    }
}
