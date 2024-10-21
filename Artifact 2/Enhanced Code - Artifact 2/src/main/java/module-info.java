module com.carmen.algorithmtesting {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.carmen.algorithmtesting to javafx.fxml;
    exports com.carmen.algorithmtesting;
    exports com.carmen.algorithmtesting.FXControllers;
    opens com.carmen.algorithmtesting.FXControllers to javafx.fxml;
}