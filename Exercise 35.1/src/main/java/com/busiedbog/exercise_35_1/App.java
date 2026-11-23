package com.busiedbog.exercise_35_1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class App extends Application {
    private static final int RECORD_COUNT = 1000;

    private final Label lblStatus = new Label("Not connected");
    private final TextArea taOutput = new TextArea();

    private String driverName = "org.sqlite.JDBC";
    private String dbUrl = "jdbc:sqlite:temp.db";
    private String username = "";
    private String password = "";

    private Connection conn;

    @Override
    public void start(Stage primaryStage) {
        Button btConnectDialog = new Button("Connect to Database");
        btConnectDialog.setOnAction(e -> openConnectDialog(primaryStage));

        BorderPane topBar = new BorderPane();
        topBar.setLeft(lblStatus);
        topBar.setRight(btConnectDialog);
        BorderPane.setAlignment(lblStatus, Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(6, 8, 6, 8));

        taOutput.setEditable(false);
        taOutput.setPrefRowCount(10);

        Button btBatch = new Button("Batch Update");
        Button btNonBatch = new Button("Non-Batch Update");

        btBatch.setOnAction(e -> runBatchUpdate());
        btNonBatch.setOnAction(e -> runNonBatchUpdate());

        HBox bottomBar = new HBox(8, btBatch, btNonBatch);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(6, 8, 8, 8));

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(taOutput);
        root.setBottom(bottomBar);
        BorderPane.setMargin(taOutput, new Insets(0, 8, 0, 8));

        primaryStage.setTitle("Exercise35_01");
        primaryStage.setScene(new Scene(root, 460, 260));
        primaryStage.show();
    }

    private void openConnectDialog(Stage owner) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Connect to DB");

        Label lblDialogStatus = new Label(conn != null ? "Connected to " + dbUrl : "Enter connection details");

        ComboBox<String> cbDriver = new ComboBox<>();
        cbDriver.setEditable(true);
        cbDriver.getItems().addAll(
                "org.sqlite.JDBC",
                "com.mysql.cj.jdbc.Driver",
                "com.mysql.jdbc.Driver"
        );
        cbDriver.setValue(driverName);
        cbDriver.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> cbUrl = new ComboBox<>();
        cbUrl.setEditable(true);
        cbUrl.getItems().addAll(
                "jdbc:sqlite:temp.db",
                "jdbc:mysql://localhost/javabook"
        );
        cbUrl.setValue(dbUrl);
        cbUrl.setMaxWidth(Double.MAX_VALUE);

        TextField tfUser = new TextField(username);
        PasswordField pfPass = new PasswordField();
        pfPass.setText(password);

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(8));

        grid.add(new Label("JDBC Driver"), 0, 0);
        grid.add(cbDriver, 1, 0);
        grid.add(new Label("Database URL"), 0, 1);
        grid.add(cbUrl, 1, 1);
        grid.add(new Label("Username"), 0, 2);
        grid.add(tfUser, 1, 2);
        grid.add(new Label("Password"), 0, 3);
        grid.add(pfPass, 1, 3);

        Button btConnectDb = new Button("Connect to DB");
        Button btClose = new Button("Close Dialog");

        btConnectDb.setOnAction(e -> {
            driverName = cbDriver.getValue() == null ? "" : cbDriver.getValue().trim();
            dbUrl = cbUrl.getValue() == null ? "" : cbUrl.getValue().trim();
            username = tfUser.getText().trim();
            password = pfPass.getText();

            if (connectToDatabase()) {
                lblDialogStatus.setText("Connected to " + dbUrl);
                lblStatus.setText("Connected");
            } else {
                lblDialogStatus.setText("Connection failed");
                lblStatus.setText("Not connected");
            }
        });

        btClose.setOnAction(e -> dialog.close());

        HBox connectRow = new HBox(btConnectDb);
        connectRow.setAlignment(Pos.CENTER_RIGHT);

        VBox centerBox = new VBox(6, grid, connectRow);

        HBox closeRow = new HBox(btClose);
        closeRow.setAlignment(Pos.CENTER);
        closeRow.setPadding(new Insets(0, 0, 8, 0));

        BorderPane dialogRoot = new BorderPane();
        dialogRoot.setTop(lblDialogStatus);
        dialogRoot.setCenter(centerBox);
        dialogRoot.setBottom(closeRow);
        BorderPane.setMargin(lblDialogStatus, new Insets(8, 8, 0, 8));
        BorderPane.setMargin(centerBox, new Insets(0, 8, 4, 8));

        dialog.setScene(new Scene(dialogRoot, 430, 240));
        dialog.showAndWait();
    }

    private boolean connectToDatabase() {
        try {
            if (!driverName.isEmpty()) {
                Class.forName(driverName);
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }

            if (username.isEmpty() && password.isEmpty()) {
                conn = DriverManager.getConnection(dbUrl);
            } else {
                conn = DriverManager.getConnection(dbUrl, username, password);
            }

            ensureTempTable();
            return true;
        } catch (Exception ex) {
            taOutput.appendText("Connection error: " + ex.getMessage() + "\n");
            return false;
        }
    }

    private void ensureTempTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Temp ("
                + "num1 DOUBLE, "
                + "num2 DOUBLE, "
                + "num3 DOUBLE"
                + ")";
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }

    private void runBatchUpdate() {
        if (!isReady()) {
            return;
        }

        String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";
        long startTime = System.currentTimeMillis();

        try {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < RECORD_COUNT; i++) {
                    ps.setDouble(1, Math.random());
                    ps.setDouble(2, Math.random());
                    ps.setDouble(3, Math.random());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            conn.commit();
            long elapsed = System.currentTimeMillis() - startTime;

            lblStatus.setText("Batch update succeeded");
            taOutput.appendText("Batch update completed\n");
            taOutput.appendText("The elapsed time is " + elapsed + "\n\n");
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
            lblStatus.setText("Batch update failed");
            taOutput.appendText("Batch error: " + ex.getMessage() + "\n");
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    private void runNonBatchUpdate() {
        if (!isReady()) {
            return;
        }

        String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";
        long startTime = System.currentTimeMillis();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < RECORD_COUNT; i++) {
                ps.setDouble(1, Math.random());
                ps.setDouble(2, Math.random());
                ps.setDouble(3, Math.random());
                ps.executeUpdate();
            }
            long elapsed = System.currentTimeMillis() - startTime;

            lblStatus.setText("Non-Batch update succeeded");
            taOutput.appendText("Non-Batch update completed\n");
            taOutput.appendText("The elapsed time is " + elapsed + "\n\n");
        } catch (SQLException ex) {
            lblStatus.setText("Non-Batch update failed");
            taOutput.appendText("Non-Batch error: " + ex.getMessage() + "\n");
        }
    }

    private boolean isReady() {
        try {
            if (conn == null || conn.isClosed()) {
                lblStatus.setText("Connect to a database first");
                return false;
            }
            return true;
        } catch (SQLException ex) {
            lblStatus.setText("Database connection invalid");
            return false;
        }
    }

    @Override
    public void stop() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ignored) {
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}