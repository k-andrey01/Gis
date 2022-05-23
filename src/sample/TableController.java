package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class TableController
{
    public static Stage tableStage;
    public static MyShape myShape;
    public static ObservableList<MyShape> myShapes = FXCollections.observableArrayList();;

    public static DatabaseHandler databaseHandler;

    static {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private MyShape selectedMyShape;

    @FXML
    private TableView<MyShape> shapesTableView;

    public void initialize() throws SQLException, ClassNotFoundException
    {
        initMyShape();
        initTableColumns();
        shapesTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MyShape>()
        {
            @Override
            public void changed(ObservableValue<? extends MyShape> observableValue, MyShape oldShape, MyShape newShape)
            {
                selectedMyShape = newShape;
            }
        });
        databaseHandler.selectQuery();
        shapesTableView.setItems(myShapes);
    }

    @FXML
    private void onClickDeleteBtn() throws SQLException
    {
        if (selectedMyShape != null)
        {
            myShape = selectedMyShape;
            int index = myShapes.indexOf(myShape);
            databaseHandler.deleteQuery();
            if (myShape != null)
            {
                myShapes.remove(index);
            }
            else
            {
                //showError();
            }
        }
    }

    @FXML
    private void onClickEditBtn() throws SQLException
    {
        if (selectedMyShape != null)
        {
            myShape = selectedMyShape;
            int index = myShapes.indexOf(myShape);
            startEditingShapeStage();
            if (myShape != null)
            {
                databaseHandler.updateQuery();
                if (myShape != null)
                {
                    myShapes.set(index, myShape);
                }
                else
                {
                   // showError();
                }
            }
        }
    }

    @FXML
    private void onClickCloseBtn()
    {
        Stage stage = (Stage) shapesTableView.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void initTableColumns()
    {
        TableColumn<MyShape, Integer> idColumn = new TableColumn<>("АйДи");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        shapesTableView.getColumns().add(idColumn);

        TableColumn<MyShape, String> shapeColumn = new TableColumn<>("Фигура");
        shapeColumn.setCellValueFactory(new PropertyValueFactory<>("shape"));
        shapesTableView.getColumns().add(shapeColumn);

        TableColumn<MyShape, Double> lengthColumn = new TableColumn<>("Длина");
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        shapesTableView.getColumns().add(lengthColumn);

        TableColumn<MyShape, Double> squareColumn = new TableColumn<>("Площадь");
        squareColumn.setCellValueFactory(new PropertyValueFactory<>("square"));
        shapesTableView.getColumns().add(squareColumn);

        TableColumn<MyShape, Double> perimeterColumn = new TableColumn<>("Периметр");
        perimeterColumn.setCellValueFactory(new PropertyValueFactory<>("perimeter"));
        shapesTableView.getColumns().add(perimeterColumn);
    }

    private void startEditingShapeStage()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editing_shape_view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Редактировать фигуру");
        stage.setScene(scene);

       EditingShapeController.editingShapeStage = stage;

        stage.showAndWait();
    }

    private void startAddingShapeStage()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adding_shape_view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Добавить фигуру");
        stage.setScene(scene);

        AddingShapeController.addingShapeStage = stage;

        stage.showAndWait();
    }

    public static void showError()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Перепроверьте значения полей");
        alert.showAndWait();
    }

    private void initMyShape()
    {
        myShape = new MyShape(0, "Фигура", 0.0, 0.0, 0.0);
    }
}
