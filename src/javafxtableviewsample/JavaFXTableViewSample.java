package javafxtableviewsample;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafxtableviewsample.Person;
 
public class JavaFXTableViewSample extends Application {
 
    // Creamos una tabla de objetos de tipo persona
    private final TableView<Person> table = new TableView<>();
    
    // Creamos una lista observable para almacenar a los contactos
    // Observable para poder actualizar la tabla conforme a los cambios observados en esta lista (esta lista puede observar cambios)
    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com"),
            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
            new Person("Ethan", "Williams", "ethan.williams@example.com"),
            new Person("Emma", "Jones", "emma.jones@example.com"),
            new Person("Michael", "Brown", "michael.brown@example.com"));
    
    // HBox para contener el apartado de insercion de contactos
    final HBox hb = new HBox();
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
        
        // Seteamos la escena con funciones ya vistas
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(550);
 
        // Label para el titulo de la agenda
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
 
        // Hacemos la tabla editable, se pueden modificar la disposicion de las columnas
        table.setEditable(true);
 
        // La primera columna sera para mostrar dentro del tipo persona un atributo tipo String, en concreto el nombre
        TableColumn<Person, String> firstNameCol = 
            new TableColumn<>("First Name");
        // Le ajustamos el ancho a la columna
        firstNameCol.setMinWidth(100);
        // Enlazamos la columna de la tabla con la propiedad nombre del objeto persona
        firstNameCol.setCellValueFactory(
            new PropertyValueFactory<>("firstName"));
        
        // Hacemos editable las celdas de la columna nombre. No solo las hacemos editables sino que ademas la lista se actualiza
        // conforme a los cambios efectuados
        firstNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        firstNameCol.setOnEditCommit(
            (CellEditEvent<Person, String> t) -> {
                ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
        });
 
 
        /* Lo mismo para apellido */
        TableColumn<Person, String> lastNameCol = 
            new TableColumn<>("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
            new PropertyValueFactory<>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        lastNameCol.setOnEditCommit(
             (CellEditEvent<Person, String> t) -> {
                 ((Person) t.getTableView().getItems().get(
                         t.getTablePosition().getRow())
                         ).setLastName(t.getNewValue());
         });
 
        /* Lo mismo para email */
        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
            new PropertyValueFactory<>("email"));
        emailCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());       
        emailCol.setOnEditCommit(
            (CellEditEvent<Person, String> t) -> {
                ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setEmail(t.getNewValue());
        });
 
        // Insertamos los datos de la lista en la tabla
        table.setItems(data);
        // Añadimos las columnas de antes a la tabla
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
 
        // Campo de texto para introducir nombre de un nuevo contacto
        final TextField addFirstName = new TextField();
        // Le ponemos prompt text, el texto en gris que indica que introducir en el campo
        addFirstName.setPromptText("First Name");
        // Establecemos un ancho
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        /* Lo mismo para el apellido */
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Last Name");
        /* Lo mismo para email */
        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(emailCol.getPrefWidth());
        addEmail.setPromptText("Email");
 
        // Añadimos un boton para añadir
        final Button addButton = new Button("Add");
        // Le establecemos un listener en la accion por defecto del boton que es clickarlo
        addButton.setOnAction((ActionEvent e) -> {
            // Al clickar, añadimos a la lista una nueva persona
            data.add(new Person(
                    addFirstName.getText(),
                    addLastName.getText(),
                    addEmail.getText()));
            // Limpiamos los campos para una nueva insercion
            addFirstName.clear();
            addLastName.clear();
            addEmail.clear();
        });
 
        // Añadimos los campos y el boton al habox que se bicara a pie de tabla
        hb.getChildren().addAll(addFirstName, addLastName, addEmail, addButton);
        hb.setSpacing(3);
 
        // Declaramos el VBox que tendra la ventana en general, damos espacio, paddin, y añadimos los componentes
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
 
        // Añadimos el VBox al grupo que es el nodo raiz de la escena
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        // Establecemos y mostramos la escena
        stage.setScene(scene);
        stage.show();
    }
}