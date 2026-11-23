module com.busiedbog.exercise_35_1 {
    requires javafx.controls;
    requires java.sql;

    opens com.busiedbog.exercise_35_1 to javafx.fxml;
    exports com.busiedbog.exercise_35_1;
}