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
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ok);

                Optional<ButtonType> result = alert.showAndWait();
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
