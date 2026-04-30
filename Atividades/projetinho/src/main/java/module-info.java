module com.example.projetinho {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projetinho to javafx.fxml;
    exports com.example.projetinho;
}