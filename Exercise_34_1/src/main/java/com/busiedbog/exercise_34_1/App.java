package com.busiedbog.exercise_34_1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App extends Application {
    private static final String DB_URL = "jdbc:sqlite:staff.db";

    private final Label lblStatus = new Label();

    private final TextField tfId = new TextField();
    private final TextField tfLastName = new TextField();
    private final TextField tfFirstName = new TextField();
    private final TextField tfMi = new TextField();
    private final TextField tfAddress = new TextField();
    private final TextField tfCity = new TextField();
    private final TextField tfState = new TextField();
    private final TextField tfTelephone = new TextField();
    private final TextField tfEmail = new TextField();

    private Connection conn;

    @Override
    public void start(Stage primaryStage) {
        connectAndInitDb();

        tfId.setPrefColumnCount(12);
        tfLastName.setPrefColumnCount(10);
        tfFirstName.setPrefColumnCount(10);
        tfMi.setPrefColumnCount(2);
        tfAddress.setPrefColumnCount(16);
        tfCity.setPrefColumnCount(10);
        tfState.setPrefColumnCount(3);
        tfTelephone.setPrefColumnCount(12);
        tfEmail.setPrefColumnCount(20);

        HBox idRow = new HBox(6, new Label("ID"), tfId);
        idRow.setAlignment(Pos.CENTER_LEFT);

        HBox nameRow = new HBox(6,
                new Label("Last Name"), tfLastName,
                new Label("First Name"), tfFirstName,
                new Label("MI"), tfMi);
        nameRow.setAlignment(Pos.CENTER_LEFT);

        HBox addressRow = new HBox(6, new Label("Address"), tfAddress);
        addressRow.setAlignment(Pos.CENTER_LEFT);

        HBox cityStateRow = new HBox(6,
                new Label("City"), tfCity,
                new Label("State"), tfState);
        cityStateRow.setAlignment(Pos.CENTER_LEFT);

        HBox telRow = new HBox(6, new Label("Telephone"), tfTelephone);
        telRow.setAlignment(Pos.CENTER_LEFT);

        Button btView = new Button("View");
        Button btInsert = new Button("Insert");
        Button btUpdate = new Button("Update");
        Button btClear = new Button("Clear");

        btView.setOnAction(e -> viewRecord());
        btInsert.setOnAction(e -> insertRecord());
        btUpdate.setOnAction(e -> updateRecord());
        btClear.setOnAction(e -> clearFields());

        HBox buttonRow = new HBox(6, btView, btInsert, btUpdate, btClear);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setPadding(new Insets(6, 0, 0, 0));

        VBox root = new VBox(6,
                lblStatus,
                idRow,
                nameRow,
                addressRow,
                cityStateRow,
                telRow,
                buttonRow);
        root.setPadding(new Insets(8, 10, 10, 10));

        primaryStage.setTitle("ExtraExercise34_01");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void connectAndInitDb() {
        String sql = "CREATE TABLE IF NOT EXISTS Staff ("
                + "id CHAR(9) NOT NULL, "
                + "lastName VARCHAR(15), "
                + "firstName VARCHAR(15), "
                + "mi CHAR(1), "
                + "address VARCHAR(20), "
                + "city VARCHAR(20), "
                + "state CHAR(2), "
                + "telephone CHAR(10), "
                + "email VARCHAR(40), "
                + "PRIMARY KEY (id)"
                + ")";
        try {
            conn = DriverManager.getConnection(DB_URL);
            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (SQLException ex) {
            lblStatus.setText("DB error: " + ex.getMessage());
        }
    }

    private void viewRecord() {
        String id = tfId.getText().trim();
        if (id.isEmpty()) {
            lblStatus.setText("Enter an ID first");
            return;
        }

        String sql = "SELECT * FROM Staff WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tfLastName.setText(nvl(rs.getString("lastName")));
                    tfFirstName.setText(nvl(rs.getString("firstName")));
                    tfMi.setText(nvl(rs.getString("mi")));
                    tfAddress.setText(nvl(rs.getString("address")));
                    tfCity.setText(nvl(rs.getString("city")));
                    tfState.setText(nvl(rs.getString("state")));
                    tfTelephone.setText(nvl(rs.getString("telephone")));
                    tfEmail.setText(nvl(rs.getString("email")));
                    lblStatus.setText("Record found");
                } else {
                    clearDetailsOnly();
                    lblStatus.setText("Record not found");
                }
            }
        } catch (SQLException ex) {
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }

    private void insertRecord() {
        String id = tfId.getText().trim();
        if (id.isEmpty()) {
            lblStatus.setText("ID is required");
            return;
        }

        String sql = "INSERT INTO Staff "
                + "(id, lastName, firstName, mi, address, city, state, telephone, email) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, tfLastName.getText().trim());
            ps.setString(3, tfFirstName.getText().trim());
            ps.setString(4, tfMi.getText().trim());
            ps.setString(5, tfAddress.getText().trim());
            ps.setString(6, tfCity.getText().trim());
            ps.setString(7, tfState.getText().trim());
            ps.setString(8, tfTelephone.getText().trim());
            ps.setString(9, tfEmail.getText().trim());
            ps.executeUpdate();
            lblStatus.setText("Record inserted");
        } catch (SQLException ex) {
            lblStatus.setText("Insert failed: " + ex.getMessage());
        }
    }

    private void updateRecord() {
        String id = tfId.getText().trim();
        if (id.isEmpty()) {
            lblStatus.setText("Enter an ID to update");
            return;
        }

        String sql = "UPDATE Staff SET "
                + "lastName = ?, firstName = ?, mi = ?, address = ?, "
                + "city = ?, state = ?, telephone = ?, email = ? "
                + "WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tfLastName.getText().trim());
            ps.setString(2, tfFirstName.getText().trim());
            ps.setString(3, tfMi.getText().trim());
            ps.setString(4, tfAddress.getText().trim());
            ps.setString(5, tfCity.getText().trim());
            ps.setString(6, tfState.getText().trim());
            ps.setString(7, tfTelephone.getText().trim());
            ps.setString(8, tfEmail.getText().trim());
            ps.setString(9, id);

            int updated = ps.executeUpdate();
            lblStatus.setText(updated > 0 ? "Record updated" : "Record not found");
        } catch (SQLException ex) {
            lblStatus.setText("Update failed: " + ex.getMessage());
        }
    }

    private void clearFields() {
        tfId.clear();
        clearDetailsOnly();
        lblStatus.setText("");
    }

    private void clearDetailsOnly() {
        tfLastName.clear();
        tfFirstName.clear();
        tfMi.clear();
        tfAddress.clear();
        tfCity.clear();
        tfState.clear();
        tfTelephone.clear();
        tfEmail.clear();
    }

    private String nvl(String s) {
        return s == null ? "" : s;
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
