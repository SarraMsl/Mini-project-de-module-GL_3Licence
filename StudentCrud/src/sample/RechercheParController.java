package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller.*;

public class RechercheParController implements Initializable {
    @FXML
    private CheckBox checkNom;

    @FXML
    private CheckBox checkMatricule;

    @FXML
    private CheckBox checkDate;

    @FXML
    void VALIDER(ActionEvent event) {
        nomSelection = checkNom.isSelected();
        MatriculeSelection = checkMatricule.isSelected();
        dateSelection = checkDate.isSelected();

        ((Stage)checkNom.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         checkNom.setSelected(nomSelection);
         checkMatricule.setSelected(MatriculeSelection);
         checkDate.setSelected(dateSelection);
    }
}
