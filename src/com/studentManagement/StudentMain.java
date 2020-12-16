package com.studentManagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentMain extends Application {
    Stage stage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root =
                    FXMLLoader.load(getClass().getResource("resources/Student.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("学生管理系统");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show() {
        start(stage);
    }
}
