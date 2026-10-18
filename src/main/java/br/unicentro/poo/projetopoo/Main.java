package br.unicentro.poo.projetopoo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/br/unicentro/poo/projetopoo/View/Telas/TelaPrincipal.fxml")
        );
        Scene scene = new Scene(loader.load(),900,600);
        stage.setTitle("Pet-Shop");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
