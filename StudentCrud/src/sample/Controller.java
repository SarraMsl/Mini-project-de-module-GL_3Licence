package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {


/*diclaration pour roucherch */
    public static boolean nomSelection;
    public static boolean MatriculeSelection;
    public static boolean dateSelection;
/* diclaration pour utiluse dans prerstat */
    Connection con;

    @FXML
    private TextField fieldId;
    @FXML
    private TextField fieldNom;
    @FXML
    private TextField fieldPrenom;
    @FXML
    private TextField fieldRecherche;



    @FXML
    private TextField fieldImma;

    @FXML
    private DatePicker dpDOB;

    @FXML
    private DatePicker dpDOBAC;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldAdr;

    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, Integer> columnId;

    @FXML
    private TableColumn<Student, String> columnNom;
    @FXML
    private TableColumn<Student, String>  columnPrenom;
    @FXML
    private TableColumn<Student, String> columnImma;

    @FXML
    private TableColumn<Student, Date> columnDOB;

    @FXML
    private TableColumn<Student, Date> columnDback;

    @FXML
    private TableColumn<Student, String> columnEmail;

    @FXML
    private TableColumn<Student, String> columnAdr;

    @FXML
    private TableColumn<Student, String> columnCycle;

    @FXML
    private ComboBox<String> comboCycle;

    @FXML
    private TextField fieldNom1;

    @FXML
    void AJOUTER(ActionEvent event) throws SQLException {

        /* idkhal al bayanat iyla 9a3idat albayanat 3an tarik insert */
        PreparedStatement preparedStatement = con.prepareStatement("insert into student(nom,prenom,immatrulation,date_obtention_bac,cycle,email,adresse,date_naissance)" +
                "values(?,?,?,?,?,?,?,?)");

        preparedStatement.setString(1,fieldNom.getText().trim());
        preparedStatement.setString(2,fieldPrenom.getText().trim());
        preparedStatement.setString(3,fieldImma.getText().trim());
        preparedStatement.setDate(4,Date.valueOf(dpDOBAC.getValue()));
        preparedStatement.setString(5,comboCycle.getValue());
        preparedStatement.setString(6,fieldEmail.getText().trim());
        preparedStatement.setString(7,fieldAdr.getText().trim());
        preparedStatement.setDate(8, Date.valueOf(dpDOB.getValue()));
        preparedStatement.executeUpdate();




        table.getItems().clear();
        fetchAll(); //ifragh les champe
        table.getSelectionModel().selectLast();
        fieldId.clear();
        fieldNom.clear();
        fieldPrenom.clear();
        fieldImma.clear();
        dpDOBAC.setValue(null);

        fieldEmail.clear();
        fieldAdr.clear();
        dpDOB.setValue(null);
        fieldNom.requestFocus();
    }

    @FXML
    void MODIFIER(ActionEvent event) throws SQLException {
        /* hna butoon modifier pour fais  un changement */
        PreparedStatement pr =
                con.prepareStatement("update   student set nom= ?,prenom=?,immatrulation=?,date_obtention_bac=?,cycle=?,email=?,adresse=?,date_naissance=? where id=?");
        pr.setString(1,fieldNom.getText());
        pr.setString(2,fieldPrenom.getText());
        pr.setString(3,fieldImma.getText());
        pr.setDate(4,Date.valueOf(dpDOBAC.getValue()));
        pr.setString(5,comboCycle.getValue());
        pr.setString(6,fieldEmail.getText());
        pr.setString(7,fieldAdr.getText());
        pr.setDate(8,Date.valueOf(dpDOB.getValue()));
        pr.setInt(9, Integer.parseInt(fieldId.getText()));

        pr.executeUpdate();




        table.getItems().clear();

        //refrech ba3d modification
         fetchAll();


        /* ifragh les champ*/
        fieldId.clear();
        fieldNom.clear();
        fieldPrenom.clear();
        fieldImma.clear();
        dpDOBAC.setValue(null);
        fieldEmail.clear();
        fieldAdr.clear();
        dpDOB.setValue(null);
        fieldNom.requestFocus();
    }

    @FXML
    void SUPPRIMER(ActionEvent event) throws SQLException {
        /* pour supprimer*/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        /* message */
        alert.setTitle("Supprimer");
        alert.setHeaderText("Confirmation");
        alert.setContentText("êtes-vous sûr de vouloir supprimer cet etudiant?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            PreparedStatement preparedStatement = con.prepareStatement("delete from student where id = "+table.getSelectionModel().getSelectedItem().getId());
            preparedStatement.executeUpdate();
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        } else {

        }
    }

    @FXML
    void RECHERCHE_PAR(ActionEvent event) throws IOException {
        /*pour choix de recherch imm ou nom ou date*/
        Parent root = FXMLLoader.load(getClass().getResource("recherche_par.fxml"));
        Stage window = new Stage();
        window.setTitle("Hello World");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(new Scene(root));
        window.showAndWait();

    }

    @FXML
    void RECHERCHE(ActionEvent event) throws IOException, SQLException {
        table.getItems().clear();
        String sql = "";
        /* concat al rabt bayna  nom et immat dans la recherch dans la base et la acsse pour entre deux element    */
               if(nomSelection && MatriculeSelection){
            sql = "select * from student where CONCAT(nom,' ',immatrulation) like '%"+fieldRecherche.getText()+"%'";
        } else if(nomSelection && dateSelection){
            sql = "select * from student where CONCAT(nom,' ',date_obtention_bac) like '%"+fieldRecherche.getText()+"%'";
        } else if(MatriculeSelection && dateSelection){
            sql = "select * from student where CONCAT(immatrulation,' ',date_obtention_bac) like '%"+fieldRecherche.getText()+"%'";
        } else if(nomSelection){
            sql = "select * from student where nom like '%"+fieldRecherche.getText()+"%'";
        } else if(MatriculeSelection){
            sql = "select * from student where immatrulation like '%"+fieldRecherche.getText()+"%'";
        } else if(dateSelection){
            sql = "select * from student where date_obtention_bac like '%"+fieldRecherche.getText()+"%'";
        }



        PreparedStatement pr = con.prepareStatement(sql);
        ResultSet resultSet = pr.executeQuery();
        while(resultSet.next()){

            Student student = new Student();
            student.setId(resultSet.getInt(1));
            student.setNom(resultSet.getString(2));
            student.setPrenom(resultSet.getString(3));
            student.setMatricule(resultSet.getString(4));
            student.setDate_obtention_bac(resultSet.getDate(5));
            student.setCycle(resultSet.getString(6));
            student.setEmail(resultSet.getString(7));
            student.setAdresse(resultSet.getString(8));
            student.setDate_naissance(resultSet.getDate(9));
            table.getItems().add(student);
        }

    }

    @Override//التهيئة
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // apel l focntion connect li twajadla connection l base de donnes
        connect();


        // n3amro combo box w tout les cycle
        comboCycle.getItems().addAll("license","Master","Doctora");


        // ******* adapter
        columnId.setCellValueFactory(data -> new SimpleObjectProperty(((Student)data.getValue()).getId()));

        columnNom.setCellValueFactory(data -> new SimpleObjectProperty(((Student)data.getValue()).getNom()));

        columnPrenom.setCellValueFactory(data -> new SimpleObjectProperty(((Student)data.getValue()).getPrenom()));
        columnImma.setCellValueFactory(data -> new SimpleObjectProperty(((Student)data.getValue()).getMatricule()));
        columnDOB.setCellValueFactory(data -> new SimpleObjectProperty(((Student)data.getValue()).getDate_naissance()));

        columnDback.setCellValueFactory(data -> new SimpleObjectProperty(((Student)data.getValue()).getDate_obtention_bac()));

        columnEmail.setCellValueFactory(data -> new SimpleObjectProperty(((Student)data.getValue()).getEmail()));

        columnAdr.setCellValueFactory(data -> new SimpleObjectProperty(((Student)data.getValue()).getAdresse()));
        columnCycle.setCellValueFactory(data -> new SimpleObjectProperty(((Student)data.getValue()).getCycle()));
        //**************************************

        // njibo biha kol les students
        try {
            fetchAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        fieldRecherche.setOnKeyReleased(keyEvent -> {

            try {
                RECHERCHE(null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });


        table.getSelectionModel().selectedItemProperty().addListener((observableValue, student, t1) -> {

            Student studentSelectionnee = t1;
            fieldId.setText(studentSelectionnee.getId()+"");
            fieldNom.setText(studentSelectionnee.getNom());
            fieldPrenom.setText(studentSelectionnee.getPrenom());
            fieldImma.setText(studentSelectionnee.getMatricule());
            fieldAdr.setText(studentSelectionnee.getAdresse());
            fieldEmail.setText(studentSelectionnee.getEmail());
            dpDOB.setValue(studentSelectionnee.getDate_naissance().toLocalDate());
            dpDOBAC.setValue(studentSelectionnee.getDate_obtention_bac().toLocalDate());
            comboCycle.getSelectionModel().select(studentSelectionnee.getCycle());

            //DOB = day of birth
        });




    }




    /* pour connecté la base de données  (driver ) */
    void connect(){ // al rabt bi 9a3idat al bayanat

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+"localhost"+":3306/"+"university"+"?autoReconnect=true&useUnicode=true&characterEncoding=utf-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true&connectTimeout=1000&socketTimeout=10000", "root", "");


            System.out.println("connected...");
        } catch (ClassNotFoundException | SQLException e) {

        }
    }

   /* bah njibo ga3 les etudion  men la base */
    void fetchAll() throws SQLException {


        PreparedStatement pr = con.prepareStatement("select * from student");
        ResultSet resultSet = pr.executeQuery();
        while(resultSet.next()){

            Student student = new Student();
            student.setId(resultSet.getInt(1));
            student.setNom(resultSet.getString(2));
            student.setPrenom(resultSet.getString(3));
            student.setMatricule(resultSet.getString(4));
            student.setDate_obtention_bac(resultSet.getDate(5));
            student.setCycle(resultSet.getString(6));
            student.setEmail(resultSet.getString(7));
            student.setAdresse(resultSet.getString(8));
            student.setDate_naissance(resultSet.getDate(9));
            table.getItems().add(student);
        }

    }

    public TableColumn<Student, String> getColumnPrenom() {
        return columnPrenom;
    }
}
