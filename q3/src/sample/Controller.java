package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Controller{

	public Pane pane;
	public Circle cMain, p1, p2, p3;        //main circle, 3 dragable points
	public Label label1, label2, label3;    //labels that go with the points
	public Polygon triangle;                //Triangle drawn by points
	public double radius = 125.0;           //Radius of main circle


	//Returns angle between a horizontal line from center screen, and the mouse position
	public double getMouseAngle(double x_pos, double y_pos)
	{
		double mouseAngle;  //angle mouse is at relative to horizontal line from circle origin
		double a = Math.sqrt(Math.pow((x_pos - 375),2) + Math.pow((y_pos - 250),2));    //distance from mouse to origin
		double b = radius; //this distance is from (0,0) to (x,0) relative to main circle origin
		double c = Math.sqrt(Math.pow((x_pos - 250),2) + Math.pow((y_pos - 250),2)); //Distance from mouse (x,0) above
		mouseAngle = Math.acos((a * a - b * b - c * c) / (-2 * b * c)); //calculated using formula from manual
		if (y_pos < 250)    //Angles above origin
		{
			mouseAngle = -1 * mouseAngle;
		}
		return mouseAngle;
	}

	//returns angle between 3 points with point1 as the angle
	public int getAngle(Circle point1, Circle point2, Circle point3)
	{
		double angle;
		//side lengths
		double a = Math.sqrt(Math.pow((point2.getCenterX() - point3.getCenterX()),2) + Math.pow((point2.getCenterY() - point3.getCenterY()),2));
		double b = Math.sqrt(Math.pow((point1.getCenterX() - point3.getCenterX()),2) + Math.pow((point1.getCenterY() - point3.getCenterY()),2));
		double c =  Math.sqrt(Math.pow((point2.getCenterX() - point1.getCenterX()),2) + Math.pow((point2.getCenterY() - point1.getCenterY()),2));
		angle = Math.acos((a * a - b * b - c * c) / (-2 * b * c)); //calculated using formula from manual
		angle = angle * 180 / Math.PI;  //convert from RADS to DEG
		int deg_angle = (int) angle;
		return deg_angle;
	}


	public void displayAngles()
	{
		double label1x, label1y, label2x, label2y, label3x, label3y;
		//set label position with slight offset from point relative to center of circle
		label1x = p1.getCenterX() - ((2 / 3) * (p1.getCenterX() - 125));
		label1y = p1.getCenterY() - ((2 / 3) * (p1.getCenterY() - 125));
		label2x = p2.getCenterX() - ((2 / 3) * (p2.getCenterX() - 125));
		label2y = p2.getCenterY() - ((2 / 3) * (p2.getCenterY() - 125));
		label3x = p3.getCenterX() - ((2 / 3) * (p3.getCenterX() - 125));
		label3y = p3.getCenterY() - ((2 / 3) * (p3.getCenterY() - 125));
		//set the positions of the labels
		label1.setLayoutX(label1x);
		label1.setLayoutY(label1y);
		label2.setLayoutX(label2x);
		label2.setLayoutY(label2y);
		label3.setLayoutX(label3x);
		label3.setLayoutY(label3y);
		//set the text of the labels to the angle
		label1.setText(Integer.toString(getAngle(p1,p2,p3)));
		label2.setText(Integer.toString(getAngle(p2,p1,p3)));
		label3.setText(Integer.toString(getAngle(p3,p2,p1)));

	}
	//draws triangle and moves labels and updates angles
	public void draw()
	{
		triangle.getPoints().setAll(p1.getCenterX(),p1.getCenterY(),p2.getCenterX(),p2.getCenterY(),p3.getCenterX(),p3.getCenterY());
		displayAngles();
	}

	//redraws the points on the circle given an angle from mouse
	public void respawnPoint(Circle point, double theta)
	{
		double x, y;
		x = 250 + (radius * Math.cos(theta));
		y = 250 + (radius * Math.sin(theta));
		point.setCenterX(x);
		point.setCenterY(y);
		draw();
	}

	//randomly spawns points for triangle
	public void spawnPoint(Circle point)
	{
		double x, y, theta;
		theta = Math.random() * 2 * Math.PI;
		x = 250 + (radius * Math.cos(theta));
		y = 250 + (radius * Math.sin(theta));
		point.setCenterX(x);
		point.setCenterY(y);

	}

	//handles mouse drag releases for moving the points for the triangle
	public EventHandler<MouseEvent> circleOnMouseReleasedHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			Object source = event.getSource();

			double x_pos = event.getSceneX();
			double y_pos = event.getSceneY();
			if (source.equals(p1))
			{
				respawnPoint(p1, getMouseAngle(x_pos,y_pos));
			}
			else if (source.equals(p2))
			{
				respawnPoint(p2, getMouseAngle(x_pos,y_pos));
			}
			else if (source.equals(p3))
			{
				respawnPoint(p3, getMouseAngle(x_pos,y_pos));
			}
		}
	};

	//Runs on startup, sets the mouse event for the points, spawns 3 random points, draws a triangle with labels
	public void initialize()
	{
		p1.setOnMouseReleased(circleOnMouseReleasedHandler);
		p2.setOnMouseReleased(circleOnMouseReleasedHandler);
		p3.setOnMouseReleased(circleOnMouseReleasedHandler);
		spawnPoint(p1);
		spawnPoint(p2);
		spawnPoint(p3);
		draw();
	}
}
