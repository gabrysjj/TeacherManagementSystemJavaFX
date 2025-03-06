package org.example.projekt3;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class TeacherDialog {

    public static Dialog<Teacher> createTeacherDialog() {
        Dialog<Teacher> dialog = new Dialog<>();
        dialog.setTitle("Dodaj nauczyciela");
        dialog.setHeaderText("Wprowadź dane nowego nauczyciela");


        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);


        TextField nameField = new TextField();
        nameField.setPromptText("Imię");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Nazwisko");
        ComboBox<TeacherCondition> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().setAll(TeacherCondition.values());
        conditionComboBox.setValue(TeacherCondition.Obecny); // Domyślny stan
        TextField yearOfBirthField = new TextField();
        yearOfBirthField.setPromptText("Rok urodzenia");
        TextField salaryField = new TextField();
        salaryField.setPromptText("Pensja");


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Imię:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Nazwisko:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Stan:"), 0, 2);
        grid.add(conditionComboBox, 1, 2);
        grid.add(new Label("Rok urodzenia:"), 0, 3);
        grid.add(yearOfBirthField, 1, 3);
        grid.add(new Label("Pensja:"), 0, 4);
        grid.add(salaryField, 1, 4);

        dialog.getDialogPane().setContent(grid);


        dialog.setResultConverter(new Callback<ButtonType, Teacher>() {
            @Override
            public Teacher call(ButtonType buttonType) {
                if (buttonType == okButtonType) {
                    try {
                        String name = nameField.getText();
                        String lastName = lastNameField.getText();
                        TeacherCondition condition = conditionComboBox.getValue();
                        int yearOfBirth = Integer.parseInt(yearOfBirthField.getText());
                        int salary = Integer.parseInt(salaryField.getText());

                        return new Teacher(name, lastName, condition, yearOfBirth, salary);
                    } catch (NumberFormatException e) {
                        showAlert("Wprowadź poprawne dane liczbowe dla roku urodzenia i pensji.");
                    }
                }
                return null;
            }
        });

        return dialog;
    }

    public static Dialog<Teacher> createTeacherEditDialog(Teacher teacher) {
        Dialog<Teacher> dialog = new Dialog<>();
        dialog.setTitle("Edytuj dane nauczyciela");
        dialog.setHeaderText("Modyfikuj dane nauczyciela");


        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);


        TextField nameField = new TextField(teacher.getName());
        TextField lastNameField = new TextField(teacher.getLastName());
        ComboBox<TeacherCondition> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().setAll(TeacherCondition.values());
        conditionComboBox.setValue(teacher.getCondition());
        TextField yearOfBirthField = new TextField(String.valueOf(teacher.getYearOfBirth()));
        TextField salaryField = new TextField(String.valueOf(teacher.getSalary()));


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Imię:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Nazwisko:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Stan:"), 0, 2);
        grid.add(conditionComboBox, 1, 2);
        grid.add(new Label("Rok urodzenia:"), 0, 3);
        grid.add(yearOfBirthField, 1, 3);
        grid.add(new Label("Pensja:"), 0, 4);
        grid.add(salaryField, 1, 4);

        dialog.getDialogPane().setContent(grid);


        dialog.setResultConverter(new Callback<ButtonType, Teacher>() {
            @Override
            public Teacher call(ButtonType buttonType) {
                if (buttonType == okButtonType) {
                    try {
                        teacher.setCondition(conditionComboBox.getValue());
                        teacher.addSalary(Integer.parseInt(salaryField.getText()) - teacher.getSalary());
                        return teacher;
                    } catch (NumberFormatException e) {
                        showAlert("Wprowadź poprawne dane liczbowe dla roku urodzenia i pensji.");
                    }
                }
                return null;
            }
        });

        return dialog;
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Błąd");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
