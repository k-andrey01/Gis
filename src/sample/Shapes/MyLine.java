package sample.Shapes;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import sample.Controller;

import java.util.Optional;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static sample.Controller.*;

public class MyLine {
    private Line line;

    private double x1;
    private double y1;
    private double x2;
    private double y2;

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

    private double length;

    public MyLine(Line line)
    {
        this.line = line;
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("GIS");
                if (Controller.isMapRegister) {
                    alert.setHeaderText("Информация о линии");

                    Double width1 = x1 - startX;
                    Double height1 = y1 - startY;
                    Double gottenX1 = 63.9 * (regX + width1 * widthGrad / imgWidth);
                    Double gottenY1 = 63.9 * (regY - height1 * heightGrad / imgHeight);

                    Double width2 = x2 - startX;
                    Double height2 = y2 - startY;

                    Double gottenX2 = 63.9 * (regX + width2 * widthGrad / imgWidth);
                    Double gottenY2 = 63.9 * (regY - height2 * heightGrad / imgHeight);

                    length = sqrt(abs(gottenX2 - gottenX1) + abs(gottenY2 - gottenY1));

                    alert.setContentText("Длина: " + sqrt(abs(gottenX2 - gottenX1) + abs(gottenY2 - gottenY1)));
                } else {
                    alert.setHeaderText("Для получения данных зарегистрируйте карту!");
                }

                ButtonType ok = new ButtonType("ОК");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(ok);

            Optional<ButtonType> result = alert.showAndWait();
            }
        };
        this.line.setOnMouseClicked(eventHandler);;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
