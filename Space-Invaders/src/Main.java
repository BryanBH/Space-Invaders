import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Main extends Application{
	final Pane root = new Pane();
	final Scene scene = new Scene(root);

	private Sprite player = new Sprite(300,750,40,40,"player",Color.BLUE);

	//Creating a shoot method 
	
	private void shoot(Sprite p) {
		Sprite b = new Sprite((int)p.getTranslateX()+20,(int)p.getTranslateY(),5,20,
				p.type +"bullet",Color.BLACK);
		root.getChildren().add(b);
	}
	
	@Override
	public void start(Stage Stage) throws Exception {
	
	
	root.setPrefSize(600,800);
	root.getChildren().add(player);
	
	//Defining actions for Player
	scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		@SuppressWarnings("incomplete-switch")
		@Override
		public void handle(KeyEvent event) {
			
			
			switch(event.getCode()) {
				case LEFT: 
					player.moveLeft();
					break;
				case RIGHT:
					player.moveRight();
					break;
				case SPACE:
					shoot(player);
					break;
				case UP:
					player.moveUp();
					break;
				case DOWN:
					player.moveDown();
					break;

			}
		}

	});

	for(int j =0; j<5;j++) {
		Sprite s = new Sprite(100+j*100,50,30,30,"Sprite",Color.RED);
		root.getChildren().add(s);

	}


	Stage.setTitle("Space Invaders");
	Stage.setScene(scene);
	Stage.show();
	
	
	player.requestFocus();
	}
	public static void main(String[] args) {
		launch(args);
		
	}
}