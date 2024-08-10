package com.example.lbycpeiproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.control.Label;

public class GWACalculatorController {

    @FXML
    private TextField courseCode1, units1, grade1;
    @FXML
    private TextField courseCode2, units2, grade2;
    @FXML
    private TextField courseCode3, units3, grade3;
    @FXML
    private TextField courseCode4, units4, grade4;
    @FXML
    private TextField courseCode5, units5, grade5;
    @FXML
    private TextField courseCode6, units6, grade6;
    @FXML
    private TextField courseCode7, units7, grade7;

    @FXML
    private Label deansLister, totalUnits, gpa;

    @FXML
    protected void calculateGPA() {
        try {
            // Initialize total units and weighted sum of grades
            int totalUnitsValue = 0;
            double weightedSum = 0.0;
            boolean isDeansLister = true;

            // Arrays of TextFields
            TextField[] unitsFields = {units1, units2, units3, units4, units5, units6, units7};
            TextField[] gradeFields = {grade1, grade2, grade3, grade4, grade5, grade6, grade7};

            // Loop through all courses
            for (int i = 0; i < unitsFields.length; i++) {
                String unitsText = unitsFields[i].getText();
                String gradeText = gradeFields[i].getText();

                // Only process fields that are not empty
                if (!unitsText.isEmpty() && !gradeText.isEmpty()) {
                    int unitsValue = Integer.parseInt(unitsText);
                    double gradeValue = Double.parseDouble(gradeText);

                    totalUnitsValue += unitsValue;
                    weightedSum += unitsValue * gradeValue;

                    // Check if the student can be a Dean's Lister
                    if (gradeValue < 2.0) {
                        isDeansLister = false;
                    }
                }
            }

            // If no courses were entered, display an error
            if (totalUnitsValue == 0) {
                gpa.setText("No courses entered!");
                deansLister.setText("N/A");
                return;
            }

            // Calculate the GPA
            double finalGPA = weightedSum / totalUnitsValue;

            // Dean's Lister qualification (3.0 or higher GPA)
            isDeansLister = isDeansLister && finalGPA >= 3.0;

            // Display the results
            totalUnits.setText(String.valueOf(totalUnitsValue));
            gpa.setText(String.format("%.3f", finalGPA));
            deansLister.setText(isDeansLister ? "YES" : "NO");

        } catch (NumberFormatException e) {
            // Handle invalid input
            gpa.setText("Invalid input!");
            deansLister.setText("N/A");
        }
    }

    @FXML
    protected void returnWindow(ActionEvent event) {
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
}
