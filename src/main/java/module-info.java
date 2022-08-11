module com.example.mastermindjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.mastermindjavafx to javafx.fxml;
    exports com.example.mastermindjavafx;
}