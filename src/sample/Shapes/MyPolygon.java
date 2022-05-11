package sample.Shapes;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import sample.Controller;

import java.util.Optional;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static sample.Controller.*;
import static sample.Controller.imgHeight;

public class MyPolygon {
    private Polygon polygon;

    private double[] points;

    private double perimeter = 0;

    public MyPolygon(Polygon polygon)
    {
        this.polygon = polygon;
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("GIS");
                if (Controller.isMapRegister) {
                    alert.setHeaderText("Информация о полигоне");
                    for (int i=0; i<points.length-2; i+=2) {
                        Double width1 = points[i] - startX;
                        Double height1 = points[i+1] - startY;

                        Double gottenX1 = 63.9 * (regX + width1 * widthGrad / imgWidth);
                        Double gottenY1 = 63.9 * (regY - height1 * heightGrad / imgHeight);

                        Double width2 = points[i+2] - startX;
                        Double height2 = points[i+3] - startY;

                        Double gottenX2 = 63.9 * (regX + width2 * widthGrad / imgWidth);
                        Double gottenY2 = 63.9 * (regY - height2 * heightGrad / imgHeight);

                        perimeter += sqrt(abs(gottenX2-gottenX1)+abs(gottenY2-gottenY1));
                    }

                    alert.setContentText("Периметр: "+perimeter);
                } else {
                    alert.setHeaderText("Для получения данных зарегистрируйте карту!");
                }

                ButtonType ok = new ButtonType("ОК");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ok);

                Optional<ButtonType> result = alert.showAndWait();
            }
        };
        this.polygon.setOnMouseClicked(eventHandler);;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public double[] getPoints() {
        return points;
    }

    public void setPoints(double[] points) {
        this.points = points;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }
}
