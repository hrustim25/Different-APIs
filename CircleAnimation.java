package main;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import javafx.animation.Interpolator;

/**
 * @author Rustem
 * @version 1.0
 */
public class CircleAnimation {

	/**
	 * Position of all animation
	 */
	private double x, y;

	/**
	 * Count of circle groups
	 */
	private int countOfStages = 1;

	/**
	 * Rotating speed. Means time of one spin
	 */
	private double speed = 1;

	private double k = 0.11;

	/**
	 * Radius of biggest(first) group of circles
	 */
	private double R = 160;
	private double r = R * k;

	/**
	 * Count of circles in one group
	 */
	private int countOfCircles = 20;

	/**
	 * Array of circles rings
	 */
	private ArrayList<Circle>[] circles;
	private LinkedList<Animation>[] animations;

	/**
	 * Common color
	 */
	private static Color stroke = Color.BLACK;
	private static Color fill;

	/**
	 * Length between two circles
	 */
	private static final double length = 2 * Math.PI / 20;

	/**
	 * Constructor initializes circles and sets parameters to them
	 * 
	 * @param countOfStages
	 *            - value of spinning circles
	 * @param parent
	 *            - container for circles
	 */
	@SuppressWarnings("unchecked")
	public CircleAnimation(int countOfStages, Group parent) {
		this.countOfStages = countOfStages;

		circles = new ArrayList[countOfStages];
		for (int i = 0; i < circles.length; i++) {
			circles[i] = new ArrayList<>();
		}

		animations = new LinkedList[countOfStages];
		for (int a = 0; a < countOfStages; a++) {
			animations[a] = new LinkedList<>();
			
			for (int b = 0; b < countOfCircles; b++) {
				animations[a].add(null);
			}
		}
		for (int i = 1; i <= countOfStages; i++) {
			setStage(i, parent);
		}
		
		setColors();
		setStrokeWidth(4);
	}

	/**
	 * @return Count of circle stages
	 */
	public int getCountOfStages() {
		return this.countOfStages;
	}

	/**
	 * @param x
	 *            - All animation position
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return Animation X value
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * @param y
	 *            - All animation position
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return Animation Y value
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Sets big radius value and calculates little radius
	 * 
	 * @param radius
	 *            - value of big radius
	 */
	public void setRadius(double radius) {
		this.R = radius;
		this.r = radius * k;
	}

	/**
	 * Method creates and sets new stage of circles
	 * 
	 * @param stage
	 *            - number of new stage
	 */
	private void setStage(int stage, Group parent) {
		for (int i = 0; i < this.countOfCircles; i++) {
			Circle circle = new Circle(this.r / Math.pow(1.3, stage - 1));
			parent.getChildren().add(circle);
			circles[stage - 1].add(circle);
		}
	}

	/**
	 * Method sets count of stages
	 * 
	 * @param count
	 *            - count of circle groups
	 */
	public void setCountOfStages(int count) {
		this.countOfStages = count;
	}

	/**
	 * Animations launch
	 */
	public void start() {
		double degree;
		int currentStage = 0, index = 0, symbol = -1;

		for (int a = 0; a < countOfStages; a++) {
			animations[a].clear();
			for (int b = 0; b < countOfCircles; b++) {
				animations[a].add(null);
			}
		}

		for (ArrayList<Circle> stage : circles) {
			degree = 0;
			symbol *= -1;
			index = 0;
			for (Circle circle : stage) {
				degree += length;
				spinAnimation(circle, currentStage, index, degree, symbol);
				index++;
			}
			currentStage++;
		}
	}

	/**
	 * @param speed
	 *            - value of rotating speed
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * @return Animation speed value - {@link CircleAnimation#speed}
	 */
	public double getSpeed() {
		return this.speed;
	}

	/**
	 * Method changes variable value and sets color to the Circles
	 * 
	 * @param newColor
	 *            - value of stroke color
	 */
	public void setStroke(Color newColor) {
		stroke = newColor;
		for (ArrayList<Circle> stage : circles) {
			for (Circle c : stage) {
				c.setStroke(newColor);
			}
		}
	}

	/**
	 * Method changes variable value and sets color to the Circles
	 * 
	 * @param newColor
	 *            - value of fill color
	 */
	public void setFill(Color newColor) {
		fill = newColor;
		for (ArrayList<Circle> stage : circles) {
			for (Circle c : stage) {
				c.setFill(newColor);
			}
		}
	}

	/**
	 * Method sets stroke and fill colors from common class variables(fill -
	 * transparent, stroke - black)
	 */
	public void setColors() {
		for (ArrayList<Circle> stage : circles) {
			for (Circle c : stage) {
				c.setFill(fill);
				c.setStroke(stroke);
			}
		}
	}

	/**
	 * Method sets stroke and fill colors from method's arguments
	 * 
	 * @param stroke
	 *            - value of stroke color
	 * @param fill
	 *            - value of fill color
	 */
	public void setColors(Color stroke, Color fill) {
		for (ArrayList<Circle> stage : circles) {
			for (Circle c : stage) {
				c.setFill(fill);
				c.setStroke(stroke);
			}
		}
	}

	/**
	 * @param width
	 *            - stroke width
	 */
	public void setStrokeWidth(double width) {
		int counter = 0;

		for (ArrayList<Circle> stage : circles) {
			counter++;
			for (Circle c : stage) {
				c.setStrokeWidth(width * Math.pow(0.8, counter));
			}
		}
	}

	/**
	 * @param circle
	 *            - current working circle
	 * @param stage
	 *            - number of stage for current circle
	 * @param index
	 *            - position at array of current circle
	 * @param degree
	 *            - current degree of this circle
	 * @param symbol
	 *            - positive or negative(write 1 or -1)
	 */
	private void spinAnimation(Circle circle, int stage, int index, double degree, int symbol) {
		animations[stage].set(index, new Transition() {
			{
				setCycleDuration(Duration.seconds(1 / speed));
				setCycleCount(INDEFINITE);
				setInterpolator(Interpolator.LINEAR);
			}

			@Override
			protected void interpolate(double frac) {
				double angle = degree + (double) (symbol * length * frac);
				if (angle < 0) {
					angle = 2 * Math.PI + degree + symbol * length * frac;
				}
				if (angle > 2 * Math.PI) {
					angle = 2 * Math.PI - degree + symbol * length * frac;
				}

				circle.setLayoutX(x + symbol * CircleAnimation.this.R / Math.pow(1.4, stage) * Math.cos(angle));
				circle.setLayoutY(y + symbol * CircleAnimation.this.R / Math.pow(1.4, stage) * Math.sin(angle));
			}
		});
		animations[stage].get(index).play();
	}
}
