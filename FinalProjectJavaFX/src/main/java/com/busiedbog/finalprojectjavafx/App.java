package com.busiedbog.finalprojectjavafx;

import com.busiedbog.finalprojectjavafx.Final_Project.Habit;
import com.busiedbog.finalprojectjavafx.Final_Project.RepeatingTask;
import com.busiedbog.finalprojectjavafx.Final_Project.SingleUseTask;
import com.busiedbog.finalprojectjavafx.Final_Project.Task;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class App extends Application {
    
    private ObservableList<Task> taskItems;
        private Habit mainHabit;
            private int earnedRewards;
            
    private ComboBox<String> taskTypeBox;
        private TextField taskNameField;
            private TextArea taskDescArea;
                private Spinner<Integer> rewardInput;
                    private ListView<Task> taskList;
                        private Label statusText;
                            private Label summaryText;
                            
    public App() {
                taskItems = FXCollections.observableArrayList(new ArrayList<Task>());
                        mainHabit = new Habit("Daily Habit", "Complete repeating tasks", null, 0, 0);
                                earnedRewards = 0;
    }
    
    @Override
        public void start(Stage primaryStage) {
                    loadStarterTasks();
                    
        BorderPane rootPane = new BorderPane();
                rootPane.setPadding(new Insets(16));
                
        Label titleLabel = new Label("Task & Habit Tracker");
                titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
                        rootPane.setTop(titleLabel);
                                BorderPane.setMargin(titleLabel, new Insets(0, 0, 14, 0));
                                
        VBox leftPanel = makeInputPanel();
                VBox rightPanel = makeTaskListPanel();
                
        HBox middleRow = new HBox(16, leftPanel, rightPanel);
                HBox.setHgrow(rightPanel, Priority.ALWAYS);
                        rootPane.setCenter(middleRow);
                        
        VBox bottomPanel = makeOutputPanel();
                rootPane.setBottom(bottomPanel);
                        BorderPane.setMargin(bottomPanel, new Insets(14, 0, 0, 0));
                        
        refreshSummary();
        
        Scene scene = new Scene(rootPane, 860, 520);
                primaryStage.setTitle("Task & Habit Tracker");
                        primaryStage.setScene(scene);
                                primaryStage.show();
        }
        
    VBox makeInputPanel() {
                VBox panel = new VBox(10);
                        panel.setPadding(new Insets(12));
                                panel.setPrefWidth(330);
                                        panel.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 6; -fx-background-radius: 6;");
                                        
        Label heading = new Label("New Task");
                heading.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
                
        ObservableList<String> taskTypes = FXCollections.observableArrayList("SingleUseTask", "RepeatingTask");
                taskTypeBox = new ComboBox<String>(taskTypes);
                        taskTypeBox.setValue("SingleUseTask");
                                taskTypeBox.setMaxWidth(Double.MAX_VALUE);
                                
        taskNameField = new TextField();
                taskNameField.setPromptText("Task name");
                
        taskDescArea = new TextArea();
                taskDescArea.setPromptText("Task description");
                        taskDescArea.setPrefRowCount(3);
                                taskDescArea.setWrapText(true);
                                
        rewardInput = new Spinner<Integer>(0, 1000, 10, 5);
                rewardInput.setEditable(true);
                        rewardInput.setMaxWidth(Double.MAX_VALUE);
                        
        Button addTaskBtn = new Button("Add Task");
                addTaskBtn.setMaxWidth(Double.MAX_VALUE);
                        addTaskBtn.setStyle("-fx-font-weight: bold;");
                                addTaskBtn.setOnAction(new EventHandler<ActionEvent>() {
                                                @Override
                                                            public void handle(ActionEvent event) {
                                                                                addTaskEvent();
                                                            }
                                });
                                
        GridPane fieldsGrid = new GridPane();
                fieldsGrid.setHgap(8);
                        fieldsGrid.setVgap(8);
                                fieldsGrid.add(new Label("Type:"), 0, 0);
                                        fieldsGrid.add(taskTypeBox, 1, 0);
                                                fieldsGrid.add(new Label("Name:"), 0, 1);
                                                        fieldsGrid.add(taskNameField, 1, 1);
                                                                fieldsGrid.add(new Label("Reward:"), 0, 2);
                                                                        fieldsGrid.add(rewardInput, 1, 2);
                                                                                GridPane.setHgrow(taskTypeBox, Priority.ALWAYS);
                                                                                        GridPane.setHgrow(taskNameField, Priority.ALWAYS);
                                                                                                GridPane.setHgrow(rewardInput, Priority.ALWAYS);
                                                                                                
        panel.getChildren().addAll(
                                heading,
                                fieldsGrid,
                                                new Label("Description:"),
                                                                taskDescArea,
                                                                                addTaskBtn
        );
                return panel;
    }
    
    VBox makeTaskListPanel() {
                VBox panel = new VBox(10);
                        panel.setPadding(new Insets(12));
                                panel.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 6; -fx-background-radius: 6;");
                                
        Label heading = new Label("Task List");
                heading.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
                
        taskList = new ListView<Task>(taskItems);
                VBox.setVgrow(taskList, Priority.ALWAYS);
                        taskList.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
                                        @Override
                                                    public ListCell<Task> call(ListView<Task> param) {
                                                                        return new ListCell<Task>() {
                                                                                                @Override
                                                                                                                    protected void updateItem(Task item, boolean empty) {
                                                                                                                                                super.updateItem(item, empty);
                                                                                                                                                                        if (empty || item == null) {
                                                                                                                                                                                                        setText(null);
                                                                                                                                                                        } else {
                                                                                                                                                                                                        String doneTag = item.getCompletion() >= 1.0f ? "[DONE]" : "[OPEN]";
                                                                                                                                                                                                                                    String typeTag;
                                                                                                                                                                                                                                                                if (item instanceof RepeatingTask) {
                                                                                                                                                                                                                                                                                                    RepeatingTask rep = (RepeatingTask) item;
                                                                                                                                                                                                                                                                                                                                    typeTag = "Repeating (x" + rep.getTimesCompleted() + ")";
                                                                                                                                                                                                                                                                } else {
                                                                                                                                                                                                                                                                                                    typeTag = "SingleUse";
                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                            setText(doneTag + " " + item.getName() + " (" + typeTag + ") - Reward: "
                                                                                                                                                                                                                                                                                                                                        + item.getReward() + " - " + item.getDescription());
                                                                                                                                                                        }
                                                                                                                    }
                                                                        };
                                                    }
                        });
                        
        Button completeBtn = new Button("Mark Selected Complete");
                completeBtn.setStyle("-fx-font-weight: bold;");
                        completeBtn.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                                    public void handle(ActionEvent event) {
                                                                        completeTaskEvent();
                                                    }
                        });
                        
        HBox buttonRow = new HBox(10, completeBtn);
                buttonRow.setAlignment(Pos.CENTER_RIGHT);
                
        panel.getChildren().addAll(heading, taskList, buttonRow);
                return panel;
    }
    
    VBox makeOutputPanel() {
                VBox panel = new VBox(6);
                        panel.setPadding(new Insets(10, 12, 10, 12));
                                panel.setStyle("-fx-background-color: #f4f6f8; -fx-border-color: #d0d7de; -fx-border-radius: 6; -fx-background-radius: 6;");
                                
        statusText = new Label("Ready.");
                statusText.setStyle("-fx-font-weight: bold;");
                
        summaryText = new Label();
                panel.getChildren().addAll(statusText, summaryText);
                        return panel;
    }
    
    void addTaskEvent() {
                String name = taskNameField.getText().trim();
                        if (name.isEmpty()) {
                                        statusText.setText("Enter a task name first.");
                                                    return;
                        }
                        
        String desc = taskDescArea.getText().trim();
                if (desc.isEmpty()) {
                                desc = "Complete a task";
                }
                
        int reward = rewardInput.getValue();
                String selectedType = taskTypeBox.getValue();
                
        Task createdTask;
                if ("RepeatingTask".equals(selectedType)) {
                                createdTask = new RepeatingTask(0, name, desc, reward, null, 86400000, new Date(), 0);
                } else {
                                SingleUseTask singleTask = new SingleUseTask(0, name, desc, reward);
                                            singleTask.setDueDate(new Date());
                                                        createdTask = singleTask;
                }
                
        taskItems.add(createdTask);
                taskList.getSelectionModel().select(createdTask);
                        taskNameField.clear();
                                taskDescArea.clear();
                                        statusText.setText("Added " + selectedType + ": " + name);
                                                refreshSummary();
    }
    
    void completeTaskEvent() {
                Task chosenTask = taskList.getSelectionModel().getSelectedItem();
                        if (chosenTask == null) {
                                        statusText.setText("Select a task first.");
                                                    return;
                        }
                        
        chosenTask.markComplete();
                if (chosenTask instanceof SingleUseTask) {
                                SingleUseTask singleTask = (SingleUseTask) chosenTask;
                                            singleTask.setCompletionDate(new Date());
                } else if (chosenTask instanceof RepeatingTask) {
                                RepeatingTask repTask = (RepeatingTask) chosenTask;
                                            repTask.resetTask();
                }
                
        earnedRewards += chosenTask.getReward();
                taskList.refresh();
                        statusText.setText("Completed: " + chosenTask.getName() + " (+" + chosenTask.getReward() + " reward)");
                                refreshSummary();
    }
    
    void refreshSummary() {
                int totalRepeatCount = 0;
                        for (Task currentTask : taskItems) {
                                        if (currentTask instanceof RepeatingTask) {
                                                            RepeatingTask repTask = (RepeatingTask) currentTask;
                                                                            totalRepeatCount += repTask.getTimesCompleted();
                                        }
                        }
                                mainHabit.setDescription("Repeating completions: " + totalRepeatCount);
                                        summaryText.setText("Tasks: " + taskItems.size()
                                                                + "  |  Total Rewards: " + earnedRewards
                                                                + "  |  " + mainHabit.getName() + " Completions: " + totalRepeatCount);
    }
    
    void loadStarterTasks() {
                SingleUseTask firstTask = new SingleUseTask(0, "Finish GUI Mockups", "Draft layouts for JavaFX screens", 20);
                        RepeatingTask secondTask = new RepeatingTask(0, "Practice Coding", "Work on Java project", 15, null, 86400000, new Date(), 0);
                                taskItems.addAll(firstTask, secondTask);
    }
    
    public static void main(String[] args) {
                launch(args);
    }
}
