import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	private AnimationTimer timer;;
	private Pane root = new Pane();
	List<ImageView> aliens = new ArrayList<ImageView>();
	List<Circle> playerBullets = new ArrayList<Circle>();
	List<Circle> alienBullets = new ArrayList<Circle>();
	Circle dot = new Circle();
	boolean toRight = true;
	Text lives;
	Text points;
	int scoreMult = 1;
	double speedMult = 1;
	int numLives = 3;
	int numPoints = 0;
	double velX = 3;
	final int HEIGHT = 800;
	final int WIDTH = 600;
	MediaPlayer mediaplayer;
	MediaPlayer laserFx;
	MediaPlayer endGame;
	Timeline timeline;

	Player player = new Player(((WIDTH / 2) - 20), (HEIGHT - 50));

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage Stage) throws Exception {

		// Lives and points
		lives = new Text("Lives: 3");
		lives.setLayoutX(20);
		lives.setLayoutY(30);
		lives.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		lives.setFill(Color.WHITE);
		points = new Text("Points: 0");
		points.setLayoutX(480);
		points.setLayoutY(30);
		points.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		points.setFill(Color.WHITE);
		root.getChildren().addAll(lives, points);

		// Music
		Music();
		// Circle dot
		dot.setLayoutX(55);

		// adds player to pane
		root.getChildren().add(player.getGraphic());
		// creates aliens
		addAliens();

		// animationTimer
		timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				alienShootUp();
				playersShootUpdate();
				playerDestroyed();
				alienDestroyed();
				alienMovement();
				nextLevel();
				gameOver();
			}

		};
		timer.start();

		// Timer to make aliens shoot
		 timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			if (!aliens.isEmpty()) {
				alienShoot();
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		// stage set up
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		scene.setFill(Color.BLACK);
		// move player
		root.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case LEFT:
				player.moveLeft();
				if (player.getX() <= 0) {
					player.setX(0);
				}
				break;
			case RIGHT:
				player.moveRight();
				;
				if (player.getX() >= 550) {
					player.setX(550);
				}
				break;
			case SPACE:
				playerShoot(player.getX());
				break;
			case ESCAPE:
				System.exit(0);
				break;
			default:
				break;
			}
		});

		Stage.setScene(scene);
		Stage.setTitle("Space Invaders");
		Stage.show();
		root.requestFocus();

	}

	// Music
	private void Music() {
		mediaplayer = new MediaPlayer(new Media(getClass().getResource("Space Invaders.mp3").toString()));
		mediaplayer.setAutoPlay(true);
		mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
	}

	// Random
	public int random(int min, int max) {
		return (int) (Math.random() * max + min);
	}

	public ImageView Alien(double x, double y) {
		Image image = new Image("alien.png", 20, 20, false, false);
		ImageView node = new ImageView();
		node.setImage(image);
		node.setLayoutX(x);
		node.setLayoutY(y);
		node.setFitHeight(50);
		node.setFitWidth(50);
		return node;
	}

	// Choses random alien and makes it shoot
	public void alienShoot() {
		int getAlienBulletIndex = random(0, aliens.size() - 1);
		alienBullets.add(shoot(aliens.get(getAlienBulletIndex).getLayoutX() + 25,
				aliens.get(getAlienBulletIndex).getLayoutY() + 25));
		root.getChildren().add((Node) alienBullets.get(alienBullets.size() - 1));
	}

	public void addAliens() {
		for (int j = 0, w = 90; j < 6; j++, w += 70) {
			aliens.add(Alien(w, 50));
			root.getChildren().add((Node) aliens.get(j));
		}
		for (int j = 0, w = 90; j < 6; j++, w += 70) {
			aliens.add(Alien(w, 120));
			root.getChildren().add((Node) aliens.get(j + 6));
		}
		for (int j = 0, w = 90; j < 6; j++, w += 70) {
			aliens.add(Alien(w, 190));
			root.getChildren().add((Node) aliens.get(j + 12));
		}
	}

	public void alienMovement() {

		double velocity;

		if (toRight) {
			velocity = 0.5 * speedMult;
		} else {
			velocity = -0.5 * speedMult;
		}

		if (dot.getLayoutX() >= 110) {
			toRight = false;
			for (int j = 0; j < aliens.size(); j++) {
				aliens.get(j).setLayoutY(aliens.get(j).getLayoutY() + 8);
			}

		}
		if (dot.getLayoutX() <= 0) {
			toRight = true;
			for (int j = 0; j < aliens.size(); j++) {
				aliens.get(j).setLayoutY(aliens.get(j).getLayoutY() + 8);
			}
		}

		for (int j = 0; j < aliens.size(); j++) {
			aliens.get(j).setLayoutX(aliens.get(j).getLayoutX() + velocity);
		}
		dot.setLayoutX(dot.getLayoutX() + velocity);
	}

	public Circle shoot(double x, double y) {
		Circle c = new Circle();
		c.setFill(Color.RED.brighter());
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setRadius(6);
		return c;
	}

	public void playerShoot(double x) {
		playerBullets.add(shoot((x + 25), HEIGHT - 50));
		root.getChildren().add(playerBullets.get(playerBullets.size() - 1));
		laserFx = new MediaPlayer(new Media(getClass().getResource("laser fx.mp3").toString()));
		laserFx.setVolume(laserFx.getVolume()/2);
		laserFx.play();
		
	}

	private void playersShootUpdate() {
		if (!playerBullets.isEmpty()) {
			for (int i = 0; i < playerBullets.size(); i++) {
				playerBullets.get(i).setLayoutY(playerBullets.get(i).getLayoutY() - 3);
				if (playerBullets.get(i).getLayoutY() <= 0) {
					root.getChildren().remove(playerBullets.get(i));
					playerBullets.remove(i);
				}
			}
		}
	}

	private void alienShootUp() {
		if (!alienBullets.isEmpty()) {
			for (int i = 0; i < alienBullets.size(); i++) {
				alienBullets.get(i).setLayoutY(alienBullets.get(i).getLayoutY() + 3);
				if (alienBullets.get(i).getLayoutY() <= 0) {
					root.getChildren().remove(alienBullets.get(i));
					alienBullets.remove(i);
				}
			}
		}
	}

	public void playerDestroyed() {
		for (int i = 0; i < alienBullets.size(); i++) {

			if (((alienBullets.get(i).getLayoutX() > player.getX())
					&& ((alienBullets.get(i).getLayoutX() < player.getX() + 50))
					&& ((alienBullets.get(i).getLayoutY() > player.getY())
							&& ((alienBullets.get(i).getLayoutY() < player.getY() + 50))))) {
				player.setX((WIDTH / 2) - 20);
				numLives -= 1;
				lives.setText("Lives: " + String.valueOf(numLives));

			}
		}
	}

	private void alienDestroyed() {
		for (int i = 0; i < playerBullets.size(); i++) {
			for (int j = 0; j < aliens.size(); j++) {
				if (((playerBullets.get(i).getLayoutX() > aliens.get(j).getLayoutX())
						&& ((playerBullets.get(i).getLayoutX() < aliens.get(j).getLayoutX() + 50))
						&& ((playerBullets.get(i).getLayoutY() > aliens.get(j).getLayoutY())
								&& ((playerBullets.get(i).getLayoutY() < aliens.get(j).getLayoutY() + 50))))) {
					root.getChildren().remove(aliens.get(j));
					aliens.remove(j);
					root.getChildren().remove(playerBullets.get(i));
					playerBullets.remove(i);
					numPoints += 100 * scoreMult;
					points.setText("Points: " + String.valueOf(numPoints));
				}
			}
		}
	}

	// Creates the next level of aliens
	public void nextLevel() {
		if (aliens.isEmpty()) {
			addAliens();
			scoreMult += 1;
			speedMult += 0.5;
		}
	}

	// Prints game over texts
	public void gameOver() {
		if (numLives <= 0) {
			Text gameOver = new Text();
			gameOver.setFont(Font.font("Burford Rustic", FontWeight.BOLD, 50));
			gameOver.setX(WIDTH / 4);
			gameOver.setY(HEIGHT / 2);
			gameOver.setFill(Color.RED);
			gameOver.setStrokeWidth(3);
			gameOver.setStroke(Color.CRIMSON);
			gameOver.setText("GAME OVER");
			root.getChildren().add(gameOver);
			Text highScore = new Text();
			highScore.setFont(Font.font("Burford Rustic", FontWeight.BOLD, 50));
			highScore.setX(WIDTH / 5);
			highScore.setY(HEIGHT - 300);
			highScore.setFill(Color.GOLD);
			highScore.setStrokeWidth(3);
			highScore.setStroke(Color.GOLD);
			highScore.setText("High Score: " + String.valueOf(numPoints));
			root.getChildren().add(highScore);
			endGame = new MediaPlayer(new Media(getClass().getResource("Game over fx.mp3").toString()));
			endGame.play();
			timeline.stop();
			timer.stop();
			mediaplayer.stop();

		}
	}
}