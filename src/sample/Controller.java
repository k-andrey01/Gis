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
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Math.*;

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

    private Double ellipseRadiusX;
    private Double ellipseRadiusY;

    ArrayList<Double> polyLines = new ArrayList<Double>();
    private Boolean stopGetPoints = false;
    private int counterPoints = 2;

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
        }
    }

    @FXML
    private void onClickLineBtn()
    {
        isDrawLine = true;
        isDrawPoly = false;
        isDrawEllipse = false;
    }

    @FXML
    private void draw(MouseEvent mouseEvent){
        if (isDrawLine){
            if (countPoints==0) {
                xLineStart = mouseEvent.getX();
                yLineStart = mouseEvent.getY()+30;
                countPoints++;
            }else{
                xLineEnd = mouseEvent.getX();
                yLineEnd = mouseEvent.getY()+30;
                Line line = new Line(xLineStart,yLineStart,xLineEnd,yLineEnd);
                line.setStrokeWidth(5);
                line.setStroke(Color.BLUE);

                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent mouseEvent)
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("GIS");

                        if (isMapRegister)
                        {
                            alert.setHeaderText("Информация о линии");
                            Double width1 = xLineStart - startX;
                            Double height1 = yLineStart - startY;

                            Double gottenX1 = 63.9 * (regX + width1 * widthGrad / myMap.getImage().getWidth());
                            Double gottenY1 = 63.9 * (regY - height1 * heightGrad / myMap.getImage().getHeight());

                            Double width2 = xLineEnd - startX;
                            Double height2 = yLineEnd - startY;

                            Double gottenX2 = 63.9*(regX + width2 * widthGrad / myMap.getImage().getWidth());
                            Double gottenY2 = 63.9*(regY - height2 * heightGrad / myMap.getImage().getHeight());

                            alert.setContentText("Длина: " + sqrt(abs(gottenX2-gottenX1)+abs(gottenY2-gottenY1)));
                        }
                        else
                        {
                            alert.setHeaderText("Для получения данных зарегистрируйте карту!");
                        }

                        ButtonType ok = new ButtonType("ОК");
                        ButtonType delete = new ButtonType("Удалить");
                        alert.getButtonTypes().clear();
                        alert.getButtonTypes().addAll(ok, delete);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == delete)
                        {
                            imgAnchor.getChildren().remove(line);
                        }
                    }
                };
                line.setOnMouseClicked(eventHandler);

                imgAnchor.getChildren().add(line);
                countPoints=0;
                isDrawLine=false;
            }
        }

        else if (isDrawPoly){
            if (countPoints==0){
                counterPoints = 2;
                polyLines.clear();
                xLineStart = mouseEvent.getX();
                yLineStart = mouseEvent.getY()+30;
                Line line1 = new Line(xLineStart,yLineStart,xLineStart,yLineStart);
                line1.setStrokeWidth(5);
                line1.setStroke(Color.ORANGE);
                imgAnchor.getChildren().add(line1);
                polyLines.add(xLineStart);
                polyLines.add(yLineStart);
                countPoints++;
            }else{
                if (!stopGetPoints && counterPoints<=50){
                    xLineEnd = mouseEvent.getX();
                    yLineEnd = mouseEvent.getY()+30;
                    Line line1 = new Line(xLineEnd,yLineEnd,xLineEnd,yLineEnd);
                    line1.setStrokeWidth(5);
                    line1.setStroke(Color.ORANGE);
                    imgAnchor.getChildren().add(line1);
                    polyLines.add(xLineEnd);
                    polyLines.add(yLineEnd);
                    if (counterPoints>0)
                        stopGetPoints = (polyLines.get(counterPoints) - polyLines.get(0)<20) && (polyLines.get(counterPoints+1) - polyLines.get(1)<20);
                    counterPoints+=2;
                }

                else {
                    double[] doublePolyLines = new double[polyLines.size()];
                    int i = 0;
                    for (Double point : polyLines) {
                        doublePolyLines[i] = point;
                        i++;
                    }
                    Polygon polygon = new Polygon(doublePolyLines);
                    polygon.setStroke(Color.ORANGE);
                    polygon.setStrokeWidth(3);
                    polygon.setFill(Color.YELLOW);
                    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent mouseEvent)
                        {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("GIS");

                            if (isMapRegister)
                            {
                                alert.setHeaderText("Информация о полигоне");
                                Double perimeter = 0.0;
                                for (int i=0; i<polyLines.size()-2; i+=2) {
                                        Double width1 = doublePolyLines[i] - startX;
                                        Double height1 = doublePolyLines[i+1] - startY;

                                        Double gottenX1 = 63.9 * (regX + width1 * widthGrad / myMap.getImage().getWidth());
                                        Double gottenY1 = 63.9 * (regY - height1 * heightGrad / myMap.getImage().getHeight());

                                        Double width2 = doublePolyLines[i+2] - startX;
                                        Double height2 = doublePolyLines[i+3] - startY;

                                        Double gottenX2 = 63.9 * (regX + width2 * widthGrad / myMap.getImage().getWidth());
                                        Double gottenY2 = 63.9 * (regY - height2 * heightGrad / myMap.getImage().getHeight());

                                    perimeter += sqrt(abs(gottenX2-gottenX1)+abs(gottenY2-gottenY1));
                                }

                                alert.setContentText("Периметр: "+perimeter);
                            }
                            else
                            {
                                alert.setHeaderText("Для получения данных зарегистрируйте карту!");
                            }

                            ButtonType ok = new ButtonType("ОК");
                            ButtonType delete = new ButtonType("Удалить");
                            alert.getButtonTypes().clear();
                            alert.getButtonTypes().addAll(ok, delete);

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == delete)
                            {
                                imgAnchor.getChildren().remove(polygon);
                            }
                        }
                    };
                    polygon.setOnMouseClicked(eventHandler);
                    imgAnchor.getChildren().add(polygon);
                    countPoints = 0;
                    isDrawPoly = false;
                    stopGetPoints = false;
                }
            }
        }

        else if (isDrawEllipse){
            if (countPoints==0){
                xLineStart = mouseEvent.getX();
                yLineStart = mouseEvent.getY()+30;
                countPoints++;
            }
            else if (countPoints==1){
                ellipseRadiusX = abs(mouseEvent.getX()-xLineStart);
                countPoints++;
            }
            else if (countPoints==2){
                ellipseRadiusY = abs(mouseEvent.getY()+30-yLineStart);
                countPoints++;

                Ellipse ellipse = new Ellipse(xLineStart,yLineStart,ellipseRadiusX,ellipseRadiusY);
                ellipse.setStroke(Color.GREEN);
                ellipse.setStrokeWidth(3);
                ellipse.setFill(Color.GREENYELLOW);
                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent mouseEvent)
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("GIS");


                        if (isMapRegister)
                        {
                            Double height1 = yLineStart - startY;
                            Double gottenY1 = 63.9 * (regY - height1 * heightGrad / myMap.getImage().getHeight());
                            Double height2 = yLineStart+ellipseRadiusY - startY;
                            Double gottenY2 = 63.9*(regY - height2 * heightGrad / myMap.getImage().getHeight());
                            Double radiusY = abs(gottenY2-gottenY1);

                            Double width1 = xLineStart - startX;
                            Double gottenX1 = 63.9 * (regX + width1 * widthGrad / myMap.getImage().getWidth());
                            Double width2 = xLineStart+ellipseRadiusX - startX;
                            Double gottenX2 = 63.9*(regX + width2 * widthGrad / myMap.getImage().getWidth());
                            Double radiusX = abs(gottenX2-gottenX1);

                            Double square = PI*2*radiusX*2*radiusY/4;

                            Double perimeter = 2*PI*sqrt((4*radiusX*radiusX+4*radiusY*radiusY)/8);

                            alert.setHeaderText("Информация об эллипсе");
                            alert.setContentText("Площадь: "+square+"\nПериметр: "+perimeter+"\nРадиус вертикальный: "+radiusY+"\nРадиус горизонтальный: "+radiusX);
                        }
                        else
                        {
                            alert.setHeaderText("Для получения данных зарегистрируйте карту!");
                        }

                        ButtonType ok = new ButtonType("ОК");
                        ButtonType delete = new ButtonType("Удалить");
                        alert.getButtonTypes().clear();
                        alert.getButtonTypes().addAll(ok, delete);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == delete)
                        {
                            imgAnchor.getChildren().remove(ellipse);
                        }
                    }
                };
                ellipse.setOnMouseClicked(eventHandler);
                imgAnchor.getChildren().add(ellipse);
                countPoints = 0;
                isDrawEllipse = false;
            }
        }
    }

    @FXML
    private void onClickEllipseBtn()
    {
        isDrawEllipse = true;
        isDrawLine = false;
        isDrawPoly = false;
    }

    @FXML
    private void onClickPolygonBtn()
    {
        isDrawPoly = true;
        isDrawLine = false;
        isDrawEllipse = false;
    }

    @FXML
    private void onClickZoneBtn()
    {

    }

    @FXML
    private void closeApp(){
        Stage stage = (Stage) myMap.getScene().getWindow();
        stage.close();
    }

}
