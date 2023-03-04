package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage fenetre) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml")); //nwajdo interface biha hadi
        fenetre.setTitle("student"); //dirlanha titre
        fenetre.setScene(new Scene(root, 1120, 700)); //oui chgol 9olnalo dir view fe fenetre
        fenetre.show();// whadi na3rdoha






    }


    public static void main(String[] args) {
        launch(args);
    }
}
