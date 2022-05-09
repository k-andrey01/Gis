package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Controller {
    public static Stage sample;
    private Image image;

    public static Double widthImg;
    public static Double heightImg;

    private Double startX;
    private Double startY;

    private Double chooseX;
    private Double chooseY;

    private Double gottenX;
    private Double gottenY;

    private Double widthGrad = 0.5;
    private Double heightGrad = 0.66;

    private Double x_coordVal;
    private Double y_coordVal;

    @FXML
    private Label x_coord;
    @FXML
    private Label y_coord;

    public static Double regX;
    public static Double regY;

    @FXML
    private ImageView myMap;
    @FXML
    private Label nowRegX;
    @FXML
    private Label nowRegY;

    @FXML
    private void openNewFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(sample);
        if (file != null)
        {
            image = new Image(file.toURI().toURL().toString());
            widthImg = image.getWidth();
            heightImg = image.getHeight();

            myMap.setImage(image);
        }
    }

    @FXML
    private void registerCoords() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Регистрация координат");
        stage.setScene(scene);

        stage.showAndWait();

        initRegCoords();
    }

    private void initRegCoords(){
        nowRegX.setText(regX.toString());
        nowRegY.setText(regY.toString());
    }

    @FXML
    private void getCoords(MouseEvent mouseEvent) throws IOException {
        startX = myMap.getX();
        startY = myMap.getImage().getHeight();

        chooseX = mouseEvent.getX();
        chooseY = mouseEvent.getY();

        Double width = chooseX - startX;
        Double height = chooseY - startY;

        System.out.println(width);
        gottenX = regX + width*widthGrad/myMap.getImage().getWidth();
        gottenY = regY - height*heightGrad/myMap.getImage().getHeight();
        System.out.println(gottenX+" "+gottenY);

        y_coord.setText(gottenX.toString());
        x_coord.setText(gottenY.toString());

        x_coordVal = gottenY;
        y_coordVal = gottenX;
    }

    @FXML
    private void onClickLineBtn()
    {

    }

    @FXML
    private void onClickEllipseBtn()
    {

    }

    @FXML
    private void onClickPolygonBtn()
    {

    }

    @FXML
    private void onClickZoneBtn()
    {

    }

    @FXML
    private void closeApp(){
        Stage stage = (Stage) myMap.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
