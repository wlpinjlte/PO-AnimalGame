package agh.ics.oop.gui;

import agh.ics.oop.mapElements.Animal;
import agh.ics.oop.mapElements.Grass;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

public class GuiFieldBox {
    private Group group=new Group();
    private final LinkedList<Animal> animals;
    private final Boolean isGrass;
    private final int cellWidth;
    private final int cellHeight;
    private Animal findMaxEnergyAnimal(){
        Animal animalToReturn=animals.get(0);
        for(Animal animal:animals){
            if(animalToReturn.getEnergy()<animal.getEnergy()){
                animalToReturn=animal;
            }
        }
        return animalToReturn;
    }
    public GuiFieldBox(LinkedList<Animal> animals,boolean isGrass,int cellWidth,int cellHeight) throws FileNotFoundException {
        this.animals=animals;
        this.isGrass=isGrass;
        this.cellHeight=cellHeight;
        this.cellWidth=cellWidth;
        Image backgroundImage = new Image(new FileInputStream("src/main/resources/background.png"));
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitHeight(cellHeight);
        backgroundView.setFitWidth(cellWidth);
        group.getChildren().add(backgroundView);
        if(animals.size()>0&& isGrass){
            Image grassImage = new Image(new FileInputStream(Grass.getImageResource()));
            ImageView grassImageView = new ImageView(grassImage);
            grassImageView.setFitHeight(cellHeight);
            grassImageView.setFitWidth(cellWidth);
            Image animalImage = new Image(new FileInputStream(findMaxEnergyAnimal().getImageResource()));
            ImageView animalImageView = new ImageView(animalImage);
            animalImageView.setFitHeight(cellHeight);
            animalImageView.setFitWidth(cellWidth);
            VBox labelHolder=new VBox();
            labelHolder.setMinSize(cellWidth,cellHeight);
            Label label=new Label(String.valueOf(animals.size()));
            label.setFont(Font.font(cellHeight/2));
            label.setTextFill(Color.web("#FFFFFF"));
            labelHolder.getChildren().add(label);
            labelHolder.setAlignment(Pos.CENTER);
            group.getChildren().addAll(grassImageView,animalImageView,labelHolder);
        }else if(isGrass){
            Image grassImage = new Image(new FileInputStream(Grass.getImageResource()));
            ImageView grassImageView = new ImageView(grassImage);
            grassImageView.setFitHeight(cellHeight);
            grassImageView.setFitWidth(cellWidth);
            group.getChildren().addAll(grassImageView);
        }else if(animals.size()>0){
            Image animalImage = new Image(new FileInputStream(findMaxEnergyAnimal().getImageResource()));
            ImageView animalImageView = new ImageView(animalImage);
            animalImageView.setFitHeight(cellHeight);
            animalImageView.setFitWidth(cellWidth);
            VBox labelHolder=new VBox();
            labelHolder.setMinSize(cellWidth,cellHeight);
            Label label=new Label(String.valueOf(animals.size()));
            label.setFont(Font.font(cellHeight/2));
            label.setTextFill(Color.web("#FFFFFF"));
            labelHolder.getChildren().add(label);
            labelHolder.setAlignment(Pos.CENTER);
            group.getChildren().addAll(animalImageView,labelHolder);
        }
    }
    public Group getField(){
        return group;
    }
}
