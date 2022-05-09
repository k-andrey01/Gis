package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    public static Stage register;

    @FXML
    private TextField regX;
    @FXML
    private TextField regY;

    @FXML
    private void regCoords() throws IOException {
        Controller.regX = Double.parseDouble(regX.getText());
        Controller.regY = Double.parseDouble(regY.getText());
        Controller.isMapRegister = true;

        Stage stage = (Stage) regX.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void cancel() throws IOException {
        Stage stage = (Stage) regX.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
