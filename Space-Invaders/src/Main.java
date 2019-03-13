import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
//	int lives = 3;
//	VBox live = new VBox();
//	
//	Text numLive = new Text("Lives Remaining: " + lives); 
	
	// Text scoreboard = new Text("Score");

	
	private Pane root = new Pane();

    private double time = 0;
    
    private Sprite player = new Sprite(300, 750, 40, 40, "player", Color.BLUE);

    private Parent content() {
        root.setPrefSize(800, 800);
        
//        ImageView galaxy = new ImageView(new Image(getClass().getResourceAsStream("images/galaxy.jpg"), 800, 800, true, true));
//        root.getChildren().add(galaxy);

        root.getChildren().add(player);
    	
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        nextLvl();

        return root;
    }

    private void nextLvl() {
        for (int i = 0; i < 11; i++) {
            Sprite s = new Sprite(90 + i*60, 50, 30, 30, "enemy", Color.RED);

            root.getChildren().add(s);
        }
    }

    private List<Sprite> sprites() {
        return root.getChildren().stream().map(n -> (Sprite)n).collect(Collectors.toList());
    }

    private void update() {
        time += 0.016;
        
        sprites().forEach(s -> {
            switch (s.type) {

                case "enemybullet":
                    s.moveDown();

                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead = true;
                        s.dead = true;
                    }
                    break;

                case "playerbullet":
                    s.moveUp();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                        }
                    });

                    break;

                case "enemy":
                    if (time > 2) {
                        if (Math.random() < 0.2) {
                            shoot(s);
                        }
                    }
                    break;
            }
        });

        root.getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;
            return s.dead;
        });

        if (time > 2) {
            time = 0;
        }
        
        else if(player.dead) {
        	// lives = lives - 1;
        	
//        	System.out.println("**********GAME OVER!***********");
//        	System.out.println("**********GAME OVER!***********");
//        	System.out.println("**********GAME OVER!***********");
//			System.exit(0);	
		}
    }

    private void shoot(Sprite shooter) {
        Sprite bullet = new Sprite((int) shooter.getTranslateX() + 20, (int) shooter.getTranslateY(), 5, 20, shooter.type + "bullet", Color.BLACK);

        root.getChildren().add(bullet);
    }
    
    Timer timer = new Timer();
    boolean pause = false;
    
	@Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(content());
        
        scene.setOnKeyPressed(e -> {
        	
    	 if(e.getCode() == KeyCode.ESCAPE) {timer.cancel(); System.exit(0);}
    	 else if(e.getCode() == KeyCode.SPACE) {shoot(player);}
    	 else if(e.getCode() == KeyCode.LEFT) {player.moveLeft();}
    	 else if(e.getCode() == KeyCode.RIGHT) {player.moveRight();}
    	 else if(e.getCode() ==  KeyCode.P) {pause = !pause;} // This button does not work yet - do not know why
    	 
        });
        
//        scene.setOnKeyPressed(e -> {
//            switch (e.getCode()) {
//                case LEFT:
//                    player.moveLeft();
//                    break;
//                case RIGHT:
//                    player.moveRight();
//                    break;
//                case SPACE:
//                    shoot(player);
//                    break;
//                case ESCAPE:
//                	System.exit(0);
//                	break;
//               case P:
//            	   
//                default:
//                	break;
//            }
//        });
        
        stage.setScene(scene);
        stage.show();
    }
	

	public static void main(String[] args) {launch(args);}
	
}