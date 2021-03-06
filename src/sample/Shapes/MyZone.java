package sample.Shapes;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import sample.Controller;
import sample.ZoneController;

import java.util.Optional;

import static java.lang.Math.abs;
import static sample.Controller.*;

public class MyZone {
    private Ellipse ellipse;

    private double xgeoCenter;
    private double ygeoCenter;
    private double xgeoRadius;
    private double ygeoRadius;

    private double geoSquare;

    private String name;
    private String about;
    private Integer id;
    private Double square;
    private Double power;

    public MyZone(Integer id, String name, Double power, Double square, String about) {
        this.name = name;
        this.about = about;
        this.id = id;
        this.square = square;
        this.power = power;
    }

    public Integer getId() {
        return id;
    }

    public Double getSquare() {
        return square;
    }

    public Double getPower() {
        return power;
    }

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

    public MyZone(Ellipse ellipse)
    {
        this.ellipse = ellipse;
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ГИС");
                alert.setHeaderText("Информация о зоне заражения в географической системе координат");

                Double width = xLineStart - startX;
                Double height = yLineStart - startY;

                Double gottenX = regX + width * widthGrad / imgWidth;
                Double gottenY = regY - height * heightGrad / imgHeight;

                alert.setContentText("Очаг x = " + gottenY + "; Очаг y = " + gottenX
                        + "\nГлубина зоны заражения = " + ZoneController.G + " км"
                        + "\nПлощадь = " + geoSquare + " км^2");
                ButtonType ok = new ButtonType("ОК");
                ButtonType delete = new ButtonType("Удалить");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ok, delete);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == delete)
                {
                    Controller.group.getChildren().remove(ellipse);
                    Controller.myLines.remove(MyZone.this);
                }
            }
        };
        this.ellipse.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public Ellipse getEllipse() {
        return ellipse;
    }

    public void setEllipse(Ellipse ellipse) {
        this.ellipse = ellipse;
    }

    public double getXgeoCenter() {
        return xgeoCenter;
    }

    public void setXgeoCenter(double xgeoCenter) {
        this.xgeoCenter = xgeoCenter;
    }

    public double getYgeoCenter() {
        return ygeoCenter;
    }

    public void setYgeoCenter(double ygeoCenter) {
        this.ygeoCenter = ygeoCenter;
    }

    public double getXgeoRadius() {
        return xgeoRadius;
    }

    public void setXgeoRadius(double xgeoRadius) {
        this.xgeoRadius = xgeoRadius;
    }

    public double getYgeoRadius() {
        return ygeoRadius;
    }

    public void setYgeoRadius(double ygeoRadius) {
        this.ygeoRadius = ygeoRadius;
    }

    public double getGeoSquare() {
        return geoSquare;
    }

    public void setGeoSquare(double geoSquare) {
        this.geoSquare = geoSquare;
    }
}
