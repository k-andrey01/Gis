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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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

    public static Boolean isMapRegister;
    private Boolean isDrawLine = false;
    private Boolean isDrawPoly = false;
    private Boolean isDrawEllipse = false;

    private Double xLineStart;
    private Double yLineStart;
    private Double xLineEnd;
    private Double yLineEnd;

    private Integer countPoints = 0;

    @FXML
    private Label x_coord;
    @FXML
    private Label y_coord;

    public static Double regX;
    public static Double regY;

    @FXML
    private AnchorPane imgAnchor;
    @FXML
    private ImageView myMap;
    @FXML
    private Label nowRegX;
    @FXML
    private Label nowRegY;

    @FXML
    private void openNewFile() throws IOException {
        isMapRegister = false;
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
        if (isMapRegister) {
            startX = myMap.getX();
            startY = myMap.getImage().getHeight();

            chooseX = mouseEvent.getX();
            chooseY = mouseEvent.getY();

            Double width = chooseX - startX;
            Double height = chooseY - startY;

            gottenX = regX + width * widthGrad / myMap.getImage().getWidth();
            gottenY = regY - height * heightGrad / myMap.getImage().getHeight();

            y_coord.setText(gottenX.toString());
            x_coord.setText(gottenY.toString());

            x_coordVal = gottenY;
            y_coordVal = gottenX;
        }else{

        }
    }

    @FXML
    private void onClickLineBtn()
    {
        isDrawLine = true;
    }
    @FXML
    private void draw(MouseEvent mouseEvent){
        if (isDrawLine){
            if (countPoints==0) {
                xLineStart = mouseEvent.getX();
                yLineStart = mouseEvent.getY();
                countPoints++;
            }else{
                xLineEnd = mouseEvent.getX();
                yLineEnd = mouseEvent.getY();
                Line line = new Line(xLineStart,yLineStart,xLineEnd,yLineEnd);
                line.setStrokeWidth(3);
                line.setStroke(Color.BLUE);
                imgAnchor.getChildren().add(line);
                countPoints=0;
                isDrawLine=false;
            }
        }else{

        }
    }

    @FXML
    private void onClickEllipseBtn()
    {
        isDrawEllipse = true;
    }

    @FXML
    private void onClickPolygonBtn()
    {
        isDrawPoly = true;
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
