package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	public static final double width = 600, height = 600;

	private Group root = new Group();

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Test");
		stage.setResizable(false);
		Scene scene = new Scene(root, width, height);
		stage.setScene(scene);
		stage.show();

		scene.setOnMouseClicked(e -> {
			CircleAnimation a = new CircleAnimation(10, root);
			a.setRadius(150);
			a.setX(e.getX());
			a.setY(e.getY());
			a.setSpeed(1);
			a.start();
			
			scene.setOnMouseMoved(e2 -> {
				a.setX(e2.getX());
				a.setY(e2.getY());
			});
		});
	}

	public static void main(String[] args) {
		launch();
	}
}
