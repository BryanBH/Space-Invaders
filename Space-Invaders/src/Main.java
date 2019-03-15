import java.util.Timer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
//	int lives = 3;
//	VBox live = new VBox();
//	
//	Text numLive = new Text("Lives Remaining: " + lives); 

	// Text scoreboard = new Text("Score");

	private Pane root = new Pane();

	Image spaceShip = new Image("spaceship.jpg", 40, 40, true, true);
	private Sprite player = new Sprite(300, 750, 40, 40, spaceShip, "player", Color.BLUE);

	private Parent content() {
		root.setPrefSize(800, 800);

		ImageView galaxy = new ImageView(
				new Image(getClass().getResourceAsStream("galaxy.png"), 800, 800, false, false));
		root.getChildren().add(galaxy);

		root.getChildren().add(player.getGraphic());

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {

			}
		};

		timer.start();

		nextLvl();

		return root;
	}

	private void nextLvl() {
		for (int i = 0; i < 11; i++) {
			Image alien = new Image("alien.png", 40, 40, true, true);
			Sprite s = new Sprite(90 + i * 60, 50, 30, 30, alien, "enemy", Color.RED);
			s.setBoundires(0, 800);

			root.getChildren().add(s.getGraphic());
		}
	}

	private void shoot(Sprite shooter) {
		Image laser = new Image("laser.png", 40, 40, true, true);
		Sprite bullet = new Sprite((int) shooter.getTranslateX() + 20, (int) shooter.getTranslateY(), 5, 20, laser,
				shooter.type + "bullet", Color.BLACK);

		root.getChildren().add(bullet.getGraphic());
	}

	Timer timer = new Timer();
	boolean pause = false;

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(content());

		root.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case LEFT:
				player.moveLeft();
			case RIGHT:
				player.moveRight();
				break;
			case SPACE:
				shoot(player);
				break;
			case ESCAPE:
				System.exit(0);
				break;
			case P:

			default:
				break;
			}
		});

		stage.setScene(scene);
		stage.show();
		root.requestFocus();
	}

	public static void main(String[] args) {
		launch(args);
	}

}