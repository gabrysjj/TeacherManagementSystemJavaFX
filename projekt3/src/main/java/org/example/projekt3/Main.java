package org.example.projekt3;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;





public class Main extends Application {

    private ClassContainer classContainer;

    @Override
    public void start(Stage primaryStage) {
        // Inicjalizacja danych
        classContainer = new ClassContainer();
        initializeData();

        // Elementy GUI
        BorderPane root = new BorderPane();
        ListView<String> groupListView = new ListView<>();
        TableView<Teacher> teacherTableView = new TableView<>();
        HBox controls = new HBox(10);

        // Ustawienia listy grup
        groupListView.getItems().addAll(classContainer.classGroups.keySet());
        groupListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Konfiguracja tabeli nauczycieli
        TableColumn<Teacher, String> nameColumn = new TableColumn<>("Imię");
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        TableColumn<Teacher, String> lastNameColumn = new TableColumn<>("Nazwisko");
        lastNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastName()));

        TableColumn<Teacher, String> conditionColumn = new TableColumn<>("Stan");
        conditionColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCondition().toString()));

        TableColumn<Teacher, Integer> yearOfBirthColumn = new TableColumn<>("Rok urodzenia");
        yearOfBirthColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getYearOfBirth()));

        TableColumn<Teacher, Integer> salaryColumn = new TableColumn<>("Pensja");
        salaryColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getSalary()));

        teacherTableView.getColumns().addAll(nameColumn, lastNameColumn, conditionColumn, yearOfBirthColumn, salaryColumn);

        // Wyświetlanie nauczycieli na podstawie wybranej grupy
        groupListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                teacherTableView.getItems().clear();
                teacherTableView.getItems().addAll(classContainer.classGroups.get(newValue).getTeachers());
            }
        });

        // Dodawanie nowej grupy
        Button addGroupButton = new Button("Dodaj grupę");
        addGroupButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Dodaj grupę");
            dialog.setHeaderText("Wprowadź nazwę nowej grupy:");
            dialog.showAndWait().ifPresent(groupName -> {
                if (!classContainer.classGroups.containsKey(groupName)) {
                    classContainer.addClass(groupName, 5);
                    groupListView.getItems().add(groupName);
                }
            });
        });

        // Sortowanie według wynagrodzeń
        Button sortSalaryButton = new Button("Sortuj według wynagrodzeń");
        sortSalaryButton.setOnAction(e -> {
            teacherTableView.getItems().sort((t1, t2) -> Integer.compare(t2.getSalary(), t1.getSalary()));
        });

        // Wyszukiwanie nauczycieli
        TextField searchField = new TextField();
        searchField.setPromptText("Wpisz nazwisko nauczyciela i naciśnij Enter");
        searchField.setOnAction(e -> {
            String searchQuery = searchField.getText().trim().toLowerCase();
            if (!searchQuery.isEmpty()) {
                String selectedGroup = groupListView.getSelectionModel().getSelectedItem();
                if (selectedGroup != null) {
                    teacherTableView.getItems().clear();
                    teacherTableView.getItems().addAll(
                            classContainer.classGroups.get(selectedGroup).getTeachers().stream()
                                    .filter(teacher -> teacher.getLastName().toLowerCase().contains(searchQuery))
                                    .toList()
                    );
                }
            }
        });

        // Przyciski zarządzania nauczycielami
        Button addTeacherButton = new Button("Dodaj nauczyciela");
        addTeacherButton.setOnAction(e -> {
            Dialog<Teacher> dialog = TeacherDialog.createTeacherDialog();
            dialog.showAndWait().ifPresent(teacher -> {
                String selectedGroup = groupListView.getSelectionModel().getSelectedItem();
                if (selectedGroup != null) {
                    classContainer.classGroups.get(selectedGroup).addTeacher(teacher);
                    teacherTableView.getItems().add(teacher);
                }
            });
        });

        Button deleteTeacherButton = new Button("Usuń nauczyciela");
        deleteTeacherButton.setOnAction(e -> {
            Teacher selectedTeacher = teacherTableView.getSelectionModel().getSelectedItem();
            String selectedGroup = groupListView.getSelectionModel().getSelectedItem();
            if (selectedTeacher != null && selectedGroup != null) {
                classContainer.classGroups.get(selectedGroup).removeTeacher(selectedTeacher);
                teacherTableView.getItems().remove(selectedTeacher);
            }
        });

        Button editTeacherButton = new Button("Edytuj nauczyciela");
        editTeacherButton.setOnAction(e -> {
            Teacher selectedTeacher = teacherTableView.getSelectionModel().getSelectedItem();
            if (selectedTeacher != null) {
                Dialog<Teacher> dialog = TeacherDialog.createTeacherEditDialog(selectedTeacher);
                dialog.showAndWait().ifPresent(editedTeacher -> teacherTableView.refresh());
            }
        });

        // Układ GUI
        HBox searchAndSortControls = new HBox(10, searchField, sortSalaryButton, addGroupButton);
        controls.getChildren().addAll(addTeacherButton, deleteTeacherButton, editTeacherButton);
        root.setLeft(groupListView);
        root.setCenter(teacherTableView);
        root.setTop(searchAndSortControls);
        root.setBottom(controls);

        // Scena
        Scene scene = new Scene(root, 900, 700);
        primaryStage.setTitle("Teacher Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeData() {
        // Przykładowe dane
        classContainer.addClass("Matematycy", 7);
        classContainer.addClass("Fizycy", 3);
        classContainer.addClass("Informatycy", 6);

        Teacher t1 = new Teacher("Anna", "Kowalska", TeacherCondition.Obecny, 1980, 4000);
        Teacher t2 = new Teacher("Jan", "Nowak", TeacherCondition.Chory, 1975, 3500);
        Teacher t3 = new Teacher("Bogdan", "Dębski", TeacherCondition.Chory, 1954, 2400);
        Teacher t4 = new Teacher("Feliks", "Smęda", TeacherCondition.Obecny, 1986, 3700);
        Teacher t5 = new Teacher("Tomasz", "Kita", TeacherCondition.Nieobecny, 1973, 3200);
        Teacher t6 = new Teacher("Dorota", "Nawaleniec", TeacherCondition.Obecny, 1974, 4100);
        Teacher t7 = new Teacher("Urszula", "Janczyk", TeacherCondition.Delegacja, 1977, 5200);
        Teacher t8 = new Teacher("Piotr", "Twaróg", TeacherCondition.Delegacja, 1983, 3600);


        classContainer.classGroups.get("Matematycy").addTeacher(t1);
        classContainer.classGroups.get("Matematycy").addTeacher(t8);
        classContainer.classGroups.get("Matematycy").addTeacher(t7);
        classContainer.classGroups.get("Matematycy").addTeacher(t6);
        classContainer.classGroups.get("Matematycy").addTeacher(t5);
        classContainer.classGroups.get("Fizycy").addTeacher(t2);
        classContainer.classGroups.get("Informatycy").addTeacher(t3);
        classContainer.classGroups.get("Informatycy").addTeacher(t4);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
