package sample;

import javafx.animation.ParallelTransition;
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
import sample.Shapes.MyEllipse;
import sample.Shapes.MyLine;
import sample.Shapes.MyPolygon;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Math.*;

public class Controller {
    private static MyLine myLine;
    private static MyPolygon myPolygon;
    private static MyEllipse myEllipse;

    public static Double imgHeight;
    public static Double imgWidth;

    public static Stage sample;
    private Image image;

    public static Double widthImg;
    public static Double heightImg;

    public static Double startX;
    public static Double startY;

    private Double chooseX;
    private Double chooseY;

    private Double gottenX;
    private Double gottenY;

    public static Double widthGrad = 0.5;
    public static Double heightGrad = 0.66;

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

            imgWidth = myMap.getImage().getWidth();
            imgHeight = myMap.getImage().getHeight();
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
                myLine = new MyLine(new Line());
                myLine.getLine().setStartX(xLineStart);
                myLine.getLine().setStartY(yLineStart);
                myLine.setX1(xLineStart);
                myLine.setY1(yLineStart);
                countPoints++;
            }else{
                xLineEnd = mouseEvent.getX();
                yLineEnd = mouseEvent.getY()+30;

                myLine.setX2(xLineEnd);
                myLine.setY2(yLineEnd);
                myLine.getLine().setEndX(xLineEnd);
                myLine.getLine().setEndY(yLineEnd);

                myLine.getLine().setStrokeWidth(5);
                myLine.getLine().setStroke(Color.BLUE);

                imgAnchor.getChildren().add(myLine.getLine());
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

                    myPolygon = new MyPolygon(new Polygon(doublePolyLines));
                    myPolygon.setPoints(doublePolyLines);
                    myPolygon.getPolygon().setStrokeWidth(5);
                    myPolygon.getPolygon().setStroke(Color.ORANGE);
                    myPolygon.getPolygon().setFill(Color.YELLOW);

                    imgAnchor.getChildren().add(myPolygon.getPolygon());
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
                myEllipse = new MyEllipse(new Ellipse());
                countPoints++;
            }
            else if (countPoints==1){
                ellipseRadiusX = abs(mouseEvent.getX()-xLineStart);
                countPoints++;
            }
            else if (countPoints==2){
                ellipseRadiusY = abs(mouseEvent.getY()+30-yLineStart);
                countPoints++;

                myEllipse.getEllipse().setCenterX(xLineStart);
                myEllipse.getEllipse().setCenterY(yLineStart);
                myEllipse.getEllipse().setRadiusX(ellipseRadiusX);
                myEllipse.getEllipse().setRadiusY(ellipseRadiusY);
                myEllipse.setX1(xLineStart);
                myEllipse.setY1(yLineStart);
                myEllipse.setxRad(ellipseRadiusX);
                myEllipse.setyRad(ellipseRadiusY);

                myEllipse.getEllipse().setStroke(Color.GREEN);
                myEllipse.getEllipse().setStrokeWidth(3);
                myEllipse.getEllipse().setFill(Color.GREENYELLOW);

                imgAnchor.getChildren().add(myEllipse.getEllipse());
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
