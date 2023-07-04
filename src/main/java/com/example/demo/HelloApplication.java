package com.example.demo;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class HelloApplication extends Application {
    Dijkstra dijkstra = new Dijkstra();
    File file = null;
    int w = 1200;
    int h = 800;


    public void InsertTheCountry(Pane map , ChoiceBox<String> src_combo , ChoiceBox<String> dest_combo , TextArea path_field , Group gr) {


        for (Node node : dijkstra.getNodes().values()) {


            double  y = h -(node.x + 90) /180 * h;
            double x= ( (node.y + 180) / 360 * w);
            Button button = new Button(node.name);
            button.setOnMouseEntered(event -> {
                button.setStyle("-fx-background-color: green; ");
            });

            button.setOnMouseExited(event -> {
                button.setStyle(
                        "-fx-background-radius: 10em; " +
                                "-fx-min-width: 7px; " +
                                "-fx-min-height: 7px; " +
                                "-fx-max-width: 7px; " +
                                "-fx-max-height: 7px;" +
                                "-fx-background-color: red"
                );
            });
            button.setOnMouseClicked(e -> {
                if (src_combo.getValue() == null) {
                    src_combo.setValue(node.name);
                } else if (dest_combo.getValue() == null) {
                    dest_combo.setValue(node.name);
                } else {
                    path_field.setText("Source and Destination are full");
                    Alert alert = new Alert(Alert.AlertType.NONE, "Source and Destination are Full ", ButtonType.OK);
                    if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                    }
                }
            });
            button.setStyle(
                    "-fx-background-radius: 10em; " +
                            "-fx-min-width: 7px; " +
                            "-fx-min-height: 7px; " +
                            "-fx-max-width: 7px; " +
                            "-fx-max-height: 7px;" +
                            "-fx-background-color: red"
            );

            //label.setStyle("-fx-font-size: 15;-fx-text-fill: white ; -fx-stroke: black; -fx-stroke-width: 50;");

            button.setLayoutX(node.x);
            button.setLayoutY(node.y);

            map.getChildren().addAll( button);


        }



    }

    @Override
    public void start(Stage stage) throws IOException {


        Pane st8 = new Pane();
        Image mh8 = new Image("Map.jpg");
        ImageView ziad = new ImageView(mh8);
        ziad.setFitWidth(1200);
        ziad.setFitHeight(800);
        
        Group gr = new Group();
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        Label label=new Label("Start");
        choiceBox.setPrefHeight(25);
        choiceBox.setPrefWidth(150);
        choiceBox.setLayoutX(1260);
        choiceBox.setLayoutY(27);
        choiceBox.setAccessibleText("Source");
        label.setLayoutX(1220);
        label.setLayoutY(27);


        ChoiceBox<String> choiceBox2 = new ChoiceBox<>();
        Label label1=new Label("End");
        choiceBox2.setPrefHeight(25);
        choiceBox2.setPrefWidth(150);
        choiceBox2.setLayoutX(1260);
        choiceBox2.setLayoutY(92);
        choiceBox2.setAccessibleText("Destination");
        label1.setLayoutY(92);
        label1.setLayoutX(1220);

        TextArea textArea = new TextArea();
        Label label2=new Label("The Road");
        textArea.setPrefWidth(150);
        textArea.setPrefHeight(157);
        textArea.setLayoutX(1260);
        textArea.setLayoutY(140);
        textArea.setEditable(false);
        label2.setLayoutX(1200);
        label2.setLayoutY(140);

        TextField textField = new TextField();
        Label label3=new Label("Destans");
        textField.setPrefWidth(150);
        textField.setPrefHeight(25);
        textField.setLayoutX(1260);
        textField.setLayoutY(310);
        textField.setEditable(false);
        label3.setLayoutY(320);
        label3.setLayoutX(1200);

        Button rest = new Button("Clear");
        rest.setPrefWidth(50);
        rest.setPrefHeight(25);
        rest.setLayoutX(1301);
        rest.setLayoutY(415);
       // rest.setDisable(true);
        rest.setOnAction(e->{
            choiceBox.setValue(null);
            choiceBox2.setValue(null);
            textArea.setText("");
            textField.setText("");
            gr.getChildren().clear();

        });


        Button button = new Button("Run");
        button.setPrefWidth(50);
        button.setPrefHeight(25);
        button.setLayoutX(1260);
        button.setLayoutY(358);
       // button.setDisable(true);
        button.setOnAction(event -> {

            if(choiceBox.getValue() == null||choiceBox2.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.NONE, "You must Chose a Country ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            }


            try {
                textField.setText("");
                textArea.setText("");
                dijkstra.ClacDijkstra(choiceBox.getValue(), choiceBox2.getValue());
                textArea.setText(dijkstra.getShortPath());
                textField.setText(String.valueOf(dijkstra.getShortPathWeight())+"  Meters");

                for (int i = 0; i < dijkstra.shortestPathNodes.length - 1; i++) {
                    Node temp = dijkstra.getNodes().get(dijkstra.shortestPathNodes[i]);
                    Node parent = dijkstra.getNodes().get(dijkstra.shortestPathNodes[i + 1]);

                    double x = temp.x;
                    double y =temp.y ;
                    double x1 =parent.x ;
                    double y1 = parent.y ;

                    Line line = new Line(x,y, x1, y1);
                    Polyline polyline = new Polyline(x,y, x1, y1);
                    if(i==0){
                        Label start=new Label("Start");
                        start.setStyle("-fx-background-color: green ;"+"-fx-text-fill: black;");
                        start.setLayoutX(x-15);
                        start.setLayoutY(y-15);
                        gr.getChildren().add(start);
                    }
                    else if(i==dijkstra.shortestPathNodes.length - 2){
                        Label end=new Label("End");
                        end.setStyle("-fx-background-color: green ;"+"-fx-text-fill: black;");
                        end.setLayoutX(x1);
                        end.setLayoutY(y1-15);
                        gr.getChildren().add(end);

                    }
                    if (i == 0 || i == dijkstra.shortestPathNodes.length - 2) {
                        line.setStroke(Color.BLUE);
                        line.setStrokeWidth(3);
                        polyline.setStroke(Color.BLUE);
                        polyline.setStrokeWidth(3);
                    } else {
                        line.setStroke(Color.BLUE);
                        line.setStrokeWidth(3);
                        polyline.setStroke(Color.BLUE);
                        polyline.setStrokeWidth(3);
                    }
                    gr.getChildren().add(polyline);
                }


                dijkstra.setShortPath("");
                dijkstra.shortestPathNodes = null;
                dijkstra.setShortPathWeight(0);


            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Can not Go from  " + choiceBox.getValue() + " to  " + choiceBox2.getValue(), ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                dijkstra.setShortPath("");
                dijkstra.shortestPathNodes = null;
                dijkstra.setShortPathWeight(0);


            }

        });

        Button Load = new Button("Load");
        Load.setPrefWidth(50);
        Load.setPrefHeight(25);
        Load.setLayoutX(1334);
        Load.setLayoutY(358);
        Load.setOnAction(e->{
            choiceBox.setValue(null);
            choiceBox2.setValue(null);
            file = new File("bnbn.txt");
            if (file != null) {
                try {
                    dijkstra.read(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.NONE, "You must Chose a File ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                return;
            }

            for (Node node : dijkstra.getNodes().values()) {
                choiceBox.getItems().add(node.name);
            }


            for (Node node : dijkstra.getNodes().values()) {
                choiceBox2.getItems().add(node.name);
            }



            ObservableList<String> items = choiceBox.getItems();
            Collections.sort(items);
            choiceBox.setItems(items);

            ObservableList<String> items2 = choiceBox2.getItems();
            Collections.sort(items2);
            choiceBox2.setItems(items2);

            ObservableList<String> items1 = choiceBox.getItems();
            items.sort(Comparator.naturalOrder());
            choiceBox.setItems(items1);

            ObservableList<String> items3 = choiceBox2.getItems();
            items2.sort(Comparator.naturalOrder());
            choiceBox2.setItems(items3);

            InsertTheCountry(st8,choiceBox,choiceBox2,textArea,gr);

            Load.setDisable(true);
            rest.setDisable(false);
            button.setDisable(false);


        });


        st8.getChildren().addAll(ziad, gr, label,choiceBox, label1,choiceBox2, rest, button, Load,label2,textArea,label3, textField);
        Scene scene = new Scene(st8, 1450, 800);
       // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setTitle("Dijkstra’s Algorithm!");
        stage.setScene(scene);
        // stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}