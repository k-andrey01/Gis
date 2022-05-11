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
import sample.Shapes.MyZone;

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
    private static MyZone myZone;
    public ArrayList<MyLine> myLines = new ArrayList<>();
    public ArrayList<MyPolygon> myPolygons = new ArrayList<>();
    public ArrayList<MyEllipse> myEllipses = new ArrayList<>();
    public ArrayList<MyZone> myZones = new ArrayList<>();

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
    private Boolean isDrawZone = false;

    public static Double xLineStart;
    public static Double yLineStart;
    public static Double xLineEnd;
    public static Double yLineEnd;

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

    @FXML
    private Label xPix;
    @FXML
    private Label yPix;

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
    private void openTable() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("table_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Данные");
        stage.setScene(scene);

        stage.showAndWait();
    }

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
        chooseX = mouseEvent.getX();
        chooseY = mouseEvent.getY();

        xPix.setText(chooseX.toString());
        yPix.setText(chooseY.toString());
        if (isMapRegister) {
            startX = myMap.getX();
            startY = myMap.getImage().getHeight();

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

                myLines.add(myLine);

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

                    myPolygons.add(myPolygon);

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

                myEllipses.add(myEllipse);

                imgAnchor.getChildren().add(myEllipse.getEllipse());
                countPoints = 0;
                isDrawEllipse = false;
            }
        }

        else if (isDrawZone){
            xLineStart = mouseEvent.getX();
            yLineStart = mouseEvent.getY()+30;
            try {
                startZonaStage(xLineStart, yLineStart);
            } catch (IOException e) {
                e.printStackTrace();
            }

            myZone = new MyZone(new Ellipse());
            double ygeoRadius = ZoneController.Sf / Math.PI / ZoneController.G;

            myZone.getEllipse().setCenterX(xLineStart);
            myZone.getEllipse().setCenterY(yLineStart);

            Double height1 = abs(yLineStart - startY);
            Double gottenY1 = 63.9 * (regY - height1 * heightGrad / imgHeight);

            Double width1 = abs(xLineStart - startX);
            Double gottenX1 = 63.9 * (regX + width1 * widthGrad / imgWidth);
            myZone.setXgeoCenter(gottenX1);
            myZone.setYgeoCenter(gottenY1);

            Double hypo = sqrt(height1*height1+width1*width1);
            myZone.getEllipse().setRadiusX(ygeoRadius*height1);
            myZone.getEllipse().setRadiusY(ZoneController.G/63.9*width1);

            myZone.getEllipse().setStrokeWidth(5);
            myZone.getEllipse().setStroke(Color.DARKGREEN);
            myZone.getEllipse().setFill(Color.GREEN);
            myZone.setGeoSquare(ZoneController.Sf);
            myZones.add(myZone);

            imgAnchor.getChildren().add(myZone.getEllipse());
            isDrawZone = false;
        }
    }

    private void startZonaStage(double x, double y) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("zone_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Создание зоны заражения");
        stage.setScene(scene);
        ZoneController.zonaStage = stage;
        ZoneController.xcenter = x;
        ZoneController.ycenter= y;

        stage.showAndWait();
    }

    @FXML
    private void onClickLineBtn()
    {
        isDrawLine = true;
        isDrawPoly = false;
        isDrawEllipse = false;
        isDrawZone = false;
    }

    @FXML
    private void onClickEllipseBtn()
    {
        isDrawEllipse = true;
        isDrawLine = false;
        isDrawPoly = false;
        isDrawZone = false;
    }

    @FXML
    private void onClickPolygonBtn()
    {
        isDrawPoly = true;
        isDrawLine = false;
        isDrawEllipse = false;
        isDrawZone = false;
    }

    @FXML
    private void onClickZoneBtn()
    {
        isDrawEllipse = false;
        isDrawLine = false;
        isDrawPoly = false;
        isDrawZone = true;
    }

    @FXML
    private void closeApp(){
        Stage stage = (Stage) myMap.getScene().getWindow();
        stage.close();
    }

}
