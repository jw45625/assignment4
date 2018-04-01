package assignment4;
import javafx.scene.input.MouseEvent;

import javafx.application.*;

import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.awt.event.MouseEvent.*;

import javafx.scene.control.Button;

import javafx.scene.layout.FlowPane;

import javafx.scene.text.*;

public class Controller extends Application {
 
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Java FX Demo Program");
        //Group root = new Group();
        FlowPane fp = new FlowPane();
        
        Canvas canvas = new Canvas(300, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
               
        Text t = new Text("Hello World");
        Font f = new Font(50);
        t.setFont(f);
        t.setFill(Color.PURPLE);
        //t.setFont(Font.font(50));
        
        drawShapes(gc);
      
        
        fp.getChildren().add(t);
        fp.getChildren().add(canvas);
        
        Button button = new Button("Yeah");
        
        button.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		System.out.println("Hello World");
        		gc.strokeOval(0, 0, 45, 90);
        	}
        });
        
        fp.getChildren().add(button);
        
        //root.getChildren().add(canvas);
        
        Scene scene = new Scene(fp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private class MouseHandler implements EventHandler<MouseEvent> {
    	boolean drawing;
    	double newX;
    	double newY;
    	double oldX;
    	double oldY;
    	
    	public MouseHandler() {
    		drawing = false;
    	}
    	
    	@Override
    	public void handle(MouseEvent event) {
    		if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
    			drawing = !drawing;
    			newX = event.getX();
    			newY = event.getY();
    			System.out.println(newX + " " + newY);
    			
    		}
    		if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
    			if (drawing) {
    				oldX = newX;
    				oldY = newY;
    				newX = event.getX();
    				
    			}
    		}
    	}
    	
    	
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                       new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                         new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                          new double[]{210, 210, 240, 240}, 4);
    }
}