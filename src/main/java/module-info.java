module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens valuutavahetus to javafx.fxml;
    exports valuutavahetus;
}