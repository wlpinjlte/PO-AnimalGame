package agh.ics.oop.gui;

import agh.ics.oop.CONSTANT;
import agh.ics.oop.Simulation;
import agh.ics.oop.auxiliary.*;
import agh.ics.oop.mapElements.Animal;
import agh.ics.oop.mapElements.MapStats;
import agh.ics.oop.maps.GlobeMap;
import agh.ics.oop.maps.HellPortal;
import agh.ics.oop.maps.IWorldMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileNotFoundException;


public class App extends Application implements IMapRefreshObserver{
    private MapStats mapStats;
    private IWorldMap map;
    private boolean isStopped=false;
    private GridPane gridPane = new GridPane();
    private GridPane configScreen = new GridPane();
    private Animal chosenAnimal=null;
    private Simulation simulation;
    private final int cellWidth = 40;
    private final int cellHeight = 40;
    private MapVariants localMapVariant=MapVariants.GlobeMap;
    private MutationVariants localMutationVariant=MutationVariants.FullRandomization;
    private GrassFieldVariants localGrassFieldVariant=GrassFieldVariants.EquatorBiased;
    private GenomeVariants localGenomeVariant=GenomeVariants.GodsPlan;
    public int localPlusEnergy=10;
    public int localCostToConcieveChildren=25;
    public int localGenomeLength=16;
    //ms
    public int localMoveDelay=1000;
    public int localSimulationLength=100;
    public int localAmountOfAnimals=10;
    private Thread simulationThread;
    @Override
    public void start(Stage primaryStage) {
        Text buttonTittle1 = new Text("Choose the map variant");
        Button button1 = new Button("GlobeMap");
        button1.setStyle("-fx-background-color:green;");
        Button button2 = new Button("HellPortal");
        button1.setOnAction(event -> {
            localMapVariant=MapVariants.GlobeMap;
            button1.setStyle("-fx-background-color:green;");
            button2.setStyle("-fx-background-color:white;");
        });
        button2.setOnAction(event -> {
            localMapVariant=MapVariants.HellPortal;
            button2.setStyle("-fx-background-color:green;");
            button1.setStyle("-fx-background-color:white;");
        });

        Text buttonTittle2 = new Text("Choose the mutation variant");
        Button button3 = new Button("FullRandomization");
        Button button4 = new Button("PartialRandomization");
        button3.setStyle("-fx-background-color:green;");
        button3.setOnAction(event -> {
            localMutationVariant=MutationVariants.FullRandomization;
            button3.setStyle("-fx-background-color:green;");
            button4.setStyle("-fx-background-color:white;");
        });
        button4.setOnAction(event -> {
            localMutationVariant=MutationVariants.PartialRandomization;
            button4.setStyle("-fx-background-color:green;");
            button3.setStyle("-fx-background-color:white;");
        });

        Text buttonTittle3 = new Text("Choose the grass variant");
        Button button5 = new Button("EquatorBiased");
        Button button6 = new Button("DeathBiased");
        button5.setStyle("-fx-background-color:green;");
        button5.setOnAction(event -> {
            localGrassFieldVariant=GrassFieldVariants.EquatorBiased;
            button5.setStyle("-fx-background-color:green;");
            button6.setStyle("-fx-background-color:white;");
        });
        button6.setOnAction(event -> {
            localGrassFieldVariant=GrassFieldVariants.DeathBiased;
            button6.setStyle("-fx-background-color:green;");
            button5.setStyle("-fx-background-color:white;");
        });

        Text buttonTittle4 = new Text("Choose the genome variant");
        Button button7 = new Button("godsPlan");
        Button button8 = new Button("mayhem");
        button7.setStyle("-fx-background-color:green;");
        button7.setOnAction(event -> {
            localGenomeVariant=GenomeVariants.GodsPlan;
            button7.setStyle("-fx-background-color:green;");
            button8.setStyle("-fx-background-color:white;");
        });
        button8.setOnAction(event -> {
            localGenomeVariant=GenomeVariants.Mayhem;
            button8.setStyle("-fx-background-color:green;");
            button7.setStyle("-fx-background-color:white;;");
        });

        configScreen.setAlignment(Pos.CENTER);
        configScreen.setHgap(10);
        configScreen.setVgap(10);
        configScreen.setPadding(new Insets(10,10,10,10));
        configScreen.add(buttonTittle1,0,0,2,1);
        configScreen.add(button1,0,1,1,1);
        configScreen.add(button2,1,1,1,1);
        configScreen.add(buttonTittle2,0,2,2,1);
        configScreen.add(button3,0,3,1,1);
        configScreen.add(button4,1,3,1,1);
        configScreen.add(buttonTittle3,0,4,2,1);
        configScreen.add(button5,0,5,1,1);
        configScreen.add(button6,1,5,1,1);
        configScreen.add(buttonTittle4,0,6,2,1);
        configScreen.add(button7,0,7,1,1);
        configScreen.add(button8,1,7,1,1);

        Text text1 = new Text("Energy yield from eating grass:");
        Text text2 = new Text("Energy cost to procreate");
        Text text3 = new Text("Genome length");
        Text text4 = new Text("Move delay");
        Text text5 = new Text("Number of moves");
        Text text6 = new Text("Number of animals");

        TextField pEnergyTF = new TextField("10");
        configScreen.add(pEnergyTF,1,8,1,1);
        configScreen.add(text1,0,8,1,1);
        TextField procreateCostTF = new TextField("25");
        configScreen.add(procreateCostTF,1,9,1,1);
        configScreen.add(text2,0,9,1,1);
        TextField genLenTF = new TextField("16");
        configScreen.add(genLenTF,1,10,1,1);
        configScreen.add(text3,0,10,1,1);
        TextField moveDelTF = new TextField("1000");
        configScreen.add(moveDelTF,1,11,1,1);
        configScreen.add(text4,0,11,1,1);
        TextField simLenTF = new TextField("100");
        configScreen.add(simLenTF,1,12,1,1);
        configScreen.add(text5,0,12,1,1);
        TextField amAnimTF = new TextField("10");
        configScreen.add(amAnimTF,1,13,1,1);
        configScreen.add(text6,0,13,1,1);

        Button button = new Button("Start the simulation");
        button.setOnAction(event -> {
            localPlusEnergy=Integer.parseInt(pEnergyTF.getText());
            localCostToConcieveChildren=Integer.parseInt(procreateCostTF.getText());
            localGenomeLength=Integer.parseInt(genLenTF.getText());
            localAmountOfAnimals=Integer.parseInt(amAnimTF.getText());
            localSimulationLength=Integer.parseInt(simLenTF.getText());
            localMoveDelay=Integer.parseInt(moveDelTF.getText());
            initializeCONSTANTandStart();
        });

        configScreen.add(button,3,14);
        Scene scene1 = new Scene(configScreen,640,640);
        primaryStage.setScene(scene1);
        primaryStage.show();


    }

    private void initializeCONSTANTandStart(){

        CONSTANT constant = new CONSTANT(localPlusEnergy,localCostToConcieveChildren,localGenomeLength,localMoveDelay,localSimulationLength,localAmountOfAnimals,localMapVariant,localGenomeVariant,localMutationVariant,localGrassFieldVariant);
        mapStats = new MapStats(constant,15*15);
        if(constant.mapVariant==MapVariants.GlobeMap){
            map=new GlobeMap(15,15,constant,mapStats);
        }
        else if(constant.mapVariant==MapVariants.HellPortal){
            map=new HellPortal(15,15,constant,mapStats);
        }

        try {
            simulation = new Simulation(map,constant);
        }
        catch (IllegalAccessException e){
        }
        simulation.addObserver(this);
        Stage primaryStage = new Stage();
        gridPane.setGridLinesVisible(false);
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        simulationThread=new Thread(simulation);
        simulationThread.start();
        try{
            renderMap();
        }
        catch(FileNotFoundException e){
        }
        Scene scene2 = new Scene(gridPane, (map.getEndOfMap().x()+1)*cellWidth, (map.getEndOfMap().y()+6)*cellHeight);
        primaryStage.setScene(scene2);
        primaryStage.show();
    }

    public void renderMap() throws FileNotFoundException {
        for(int i=0;i<=map.getEndOfMap().y();i++){
            gridPane.getRowConstraints().add(new RowConstraints(cellHeight));
        }
        for(int i=0;i<=map.getEndOfMap().x();i++){
            gridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }
        for (int x = 0; x <= map.getEndOfMap().x(); x++) {
            for (int y = 0; y <= map.getEndOfMap().y(); y++) {
                Vector2d position = new Vector2d(x, y);
                GuiFieldBox guiFieldBox=new GuiFieldBox(map.animalsAt(position),map.grassStatus(position),cellWidth,cellHeight);
                gridPane.add(guiFieldBox.getField(),x,y);
                if (!map.animalsAt(position).isEmpty()){
                VBox vbox = new VBox();
                gridPane.add(vbox,x,y);
                vbox.setOnMouseClicked((EventHandler<Event>) event -> {
                    if (isStopped) {
                        chosenAnimal = map.animalsAt(position).get(0);
                        refresh();
                    }
                });
                }
            }
        }
        Text text1=new Text("Animal Count: ");
        Text text11=new Text(Integer.toString(mapStats.getAmountOfAnimals()));
        gridPane.add(text1,0,16);
        gridPane.add(text11,4,16);
        Text text2=new Text("Grass Count: ");
        Text text21=new Text(Integer.toString(mapStats.getAmountOfGrass()));
        gridPane.add(text2,0,17);
        gridPane.add(text21,4,17);
        Text text3=new Text("Dead animals count: ");
        Text text31=new Text(Integer.toString(mapStats.getAmountOfDeadAnimals()));
        gridPane.add(text3,0,18);
        gridPane.add(text31,4,18);
        Text text4=new Text("free Spaces: ");
        Text text41=new Text(Integer.toString(mapStats.getFreeSpaces()));
        gridPane.add(text4,0,19);
        gridPane.add(text41,4,19);
        Text text5=new Text("average energy: ");
        Text text51=new Text(Double.toString(mapStats.getAverageEnergy()));
        gridPane.add(text5,0,20);
        gridPane.add(text51,4,20);
        Text text6=new Text("average lifespan: ");
        Text text61=new Text(Double.toString(mapStats.getAverageLifespan()));
        gridPane.add(text6,0,21);
        gridPane.add(text61,4,21);
        Text text7=new Text("average numberOfChildren: ");
        Text text71=new Text(Double.toString(mapStats.getAverageNumberOfChildren()));
        gridPane.add(text7,0,22);
        gridPane.add(text71,4,22);
        if(chosenAnimal!=null) {
            Text text8 = new Text("genome: ");
            Text text81 = new Text(chosenAnimal.getGenome().toString());
            gridPane.add(text8, 6, 16);
            gridPane.add(text81, 8, 16);
            Text text9 = new Text("active gene:");
            Text text91 = new Text(Integer.toString(chosenAnimal.getGenome().getGene(chosenAnimal.getDaysAlive() % chosenAnimal.getGenome().getGenomeLength())));
            gridPane.add(text9, 6, 17);
            gridPane.add(text91, 8, 17);
            Text text10 = new Text("energy: ");
            Text text101 = new Text(Integer.toString(chosenAnimal.getEnergy()));
            gridPane.add(text10, 6, 18);
            gridPane.add(text101, 8, 18);
            Text text111 = new Text("grass eaten: ");
            Text text1111 = new Text(Integer.toString(chosenAnimal.getAmountOfGrassEaten()));
            gridPane.add(text111, 6, 19);
            gridPane.add(text1111, 8, 19);
            Text text12 = new Text("children: ");
            Text text121 = new Text(Integer.toString(chosenAnimal.getNumberOfChildren()));
            gridPane.add(text12, 6, 20);
            gridPane.add(text121, 8, 20);
            if (!chosenAnimal.ifDied()) {
                Text text13 = new Text("days alive: ");
                Text text131 = new Text(Integer.toString(chosenAnimal.getDaysAlive()));
                gridPane.add(text13, 6, 21);
                gridPane.add(text131, 8, 21);
            } else {
                Text text14 = new Text("date of death: ");
                Text text141 = new Text(Integer.toString(chosenAnimal.getDateOfDeath()));
                gridPane.add(text14, 6, 21);
                gridPane.add(text141, 8, 21);
            }
        }


        Button stop = new Button("stop");
        Button start = new Button("start");
        if(!isStopped){
            gridPane.add(stop,12,23);
        }
        else{
            gridPane.add(start,12,23);
        }

        stop.setOnAction(event->{
            simulationThread.interrupt();
            isStopped=true;
            gridPane.getChildren().remove(stop);
            gridPane.add(start,12,23);
        });
        start.setOnAction(event->{
            this.simulation.setContinuationTrue();
            simulationThread=new Thread(this.simulation);
            isStopped=false;
            simulationThread.start();
            gridPane.getChildren().remove(start);
            gridPane.add(stop,12,23);
        });
    }
    @Override
    public void refresh() {
        Platform.runLater(() -> {
            gridPane.setGridLinesVisible(false);
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
            gridPane.getChildren().clear();
            gridPane.setGridLinesVisible(false);
            try {
                renderMap();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
