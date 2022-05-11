package sample.Shapes;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import sample.Controller;

import java.util.Optional;

import static java.lang.Math.*;
import static java.lang.Math.PI;
import static sample.Controller.*;
import static sample.Controller.imgHeight;

public class MyEllipse {
    private Ellipse ellipse;
    private Double x1;
    private Double y1;
    private Double xRad;
    private Double yRad;

    private String name;
    private String about;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Ellipse getEllipse() {
        return ellipse;
    }

    public void setEllipse(Ellipse ellipse) {
        this.ellipse = ellipse;
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    public Double getxRad() {
        return xRad;
    }

    public void setxRad(Double xRad) {
        this.xRad = xRad;
    }

    public Double getyRad() {
        return yRad;
    }

    public void setyRad(Double yRad) {
        this.yRad = yRad;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }

    private double square;
    private double perimeter;
    private double radiusX;
    private double radiusY;

    public double getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(double radiusX) {
        this.radiusX = radiusX;
    }

    public double getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(double radiusY) {
        this.radiusY = radiusY;
    }

    public MyEllipse(Ellipse ellipse)
    {
        this.ellipse = ellipse;
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("GIS");
                if (Controller.isMapRegister) {
                    Double height1 = y1 - startY;
                    Double gottenY1 = 63.9 * (regY - height1 * heightGrad / imgHeight);
                    Double height2 = y1+yRad - startY;
                    Double gottenY2 = 63.9*(regY - height2 * heightGrad / imgHeight);
                    radiusY = abs(gottenY2-gottenY1);

                    Double width1 = x1 - startX;
                    Double gottenX1 = 63.9 * (regX + width1 * widthGrad / imgWidth);
                    Double width2 = x1+xRad - startX;
                    Double gottenX2 = 63.9*(regX + width2 * widthGrad / imgWidth);
                    radiusX = abs(gottenX2-gottenX1);

                    square = PI*2*radiusX*2*radiusY/4;

                    perimeter = 2*PI*sqrt((4*radiusX*radiusX+4*radiusY*radiusY)/8);

                    alert.setHeaderText("Информация об эллипсе");
                    alert.setContentText("Площадь: "+square+"\nПериметр: "+perimeter+"\nРадиус вертикальный: "+radiusY+"\nРадиус горизонтальный: "+radiusX);

                } else {
                    alert.setHeaderText("Для получения данных зарегистрируйте карту!");
                }

                ButtonType ok = new ButtonType("ОК");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ok);

                Optional<ButtonType> result = alert.showAndWait();
            }
        };
        this.ellipse.setOnMouseClicked(eventHandler);;
    }
}
