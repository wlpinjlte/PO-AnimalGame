package agh.ics.oop.gui;

import agh.ics.oop.Simulation;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.maps.GlobeMap;
import agh.ics.oop.maps.HellPortal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.io.FileNotFoundException;
import java.util.List;

public class App extends Application implements IMapRefreshObserver{
    private HellPortal map;
    private GridPane gridPane = new GridPane();
    private Simulation simulation;
    private final int cellWidth = 30;
    private final int cellHeight = 30;
    @Override
    public void init() throws Exception {
        super.init();
        map=new HellPortal(10,10);
        simulation=new Simulation(map);
        simulation.addObserver(this);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        Thread simulationThread=new Thread(simulation);
        simulationThread.start();
        renderMap();
        Scene scene = new Scene(gridPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void renderMap(){
        for(int i=0;i<=map.getEndOfMap().y();i++){
            gridPane.getRowConstraints().add(new RowConstraints(cellHeight));
        }
        for(int i=0;i<=map.getEndOfMap().x();i++){
            gridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }
        for (int x = 0; x <= map.getEndOfMap().x(); x++) {
            for (int y = 0; y <= map.getEndOfMap().y(); y++) {
                Vector2d position = new Vector2d(x, y);
                if(map.animalsAt(position).size()>0&& map.grassStatus(position)){
                    Label label = new Label("AG");
                    GridPane.setHalignment(label, HPos.CENTER);
                    gridPane.add(label, position.x(), position.y(), 1, 1);
                }else if(map.grassStatus(position)){
                    Label label = new Label("G");
                    GridPane.setHalignment(label, HPos.CENTER);
                    gridPane.add(label, position.x(), position.y(), 1, 1);
                }else if(map.animalsAt(position).size()>0){
                    Label label = new Label("A");
                    GridPane.setHalignment(label, HPos.CENTER);
                    gridPane.add(label, position.x(), position.y(), 1, 1);
                }
            }
        }
    }
    @Override
    public void refresh() {
        Platform.runLater(() -> {
            gridPane.setGridLinesVisible(false);
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
            gridPane.getChildren().clear();
            gridPane.setGridLinesVisible(true);
            renderMap();
        });
    }
}
