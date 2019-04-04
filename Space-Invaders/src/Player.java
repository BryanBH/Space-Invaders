import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {

	
	private double velX = 5;
	private double x;
	private double y;
	ImageView node;
	
	public Player(int x, int y) {
		Image player = new Image("spaceship.jpg",20,20,false,false);
		this.x=x;
		this.y=y;
		node = new ImageView();
		node.setImage(player);
		node.setX(x);
		node.setY(y);
		node.setFitHeight(50);
		node.setFitWidth(50);
		
	}
	void moveLeft() {
		node.setX(node.getX()-(5+velX));
		setX(node.getX());
	}

	void moveRight() {
		node.setX(node.getX()+(5+velX));
		setX(node.getX());
	}

	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x=x;
		node.setX(x);
	}
	public void setY(double y) {
		this.y = y;
	}
	
	
	
	public ImageView getGraphic() {
		return node;
	}
}
