package com.example.lbycpeiproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ToDoListController {

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField taskNameField;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private TextArea notesArea;

    @FXML
    private TableView<Task> taskTableView;

    @FXML
    private TableColumn<Task, String> courseNameColumn;

    @FXML
    private TableColumn<Task, String> taskNameColumn;

    @FXML
    private TableColumn<Task, String> statusColumn;

    @FXML
    private TableColumn<Task, String> dueDateColumn;

    @FXML
    private TableColumn<Task, String> notesColumn;

    private ObservableList<Task> taskList;

    @FXML
    public void initialize() {
        statusComboBox.setItems(FXCollections.observableArrayList("Not Started", "In Progress", "Completed"));

        taskList = FXCollections.observableArrayList();
        taskTableView.setItems(taskList);

        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    @FXML
    protected void handleOk() {
        String courseName = courseNameField.getText();
        String taskName = taskNameField.getText();
        String status = statusComboBox.getValue();
        String dueDate = dueDatePicker.getValue() != null ? dueDatePicker.getValue().toString() : "";
        String notes = notesArea.getText();

        Task newTask = new Task(courseName, taskName, status, dueDate, notes);
        taskList.add(newTask);

        handleCancel();
    }

    @FXML
    protected void handleCancel() {
        courseNameField.clear();
        taskNameField.clear();
        statusComboBox.setValue(null);
        dueDatePicker.setValue(null);
        notesArea.clear();
    }

    @FXML
    void returnWindow(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-screen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleChangeStatus() {
        Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            String newStatus = statusComboBox.getValue();
            if (newStatus != null && !newStatus.isEmpty()) {
                selectedTask.setStatus(newStatus);
                taskTableView.refresh();
            }
        }
    }

    public static class Task {
        private String courseName;
        private String taskName;
        private String status;
        private String dueDate;
        private String notes;

        public Task(String courseName, String taskName, String status, String dueDate, String notes) {
            this.courseName = courseName;
            this.taskName = taskName;
            this.status = status;
            this.dueDate = dueDate;
            this.notes = notes;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getTaskName() {
            return taskName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDueDate() {
            return dueDate;
        }

        public String getNotes() {
            return notes;
        }
    }
}
