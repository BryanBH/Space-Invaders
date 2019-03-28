import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
boolean dead = false;
	
	private double velX;
	private double velY;
	private double x;
	private double y;
	ImageView node;
	
	public Player(int x, int y) {
		Image player = new Image("spaceship.jpg",20,20,false,false);
		this.x=x;
		this.y=y;
		node = new ImageView();
		node.setImage(player);
		node.setLayoutX(x);
		node.setLayoutY(y);
		node.setFitHeight(50);
		node.setFitWidth(50);
		
	}
	void moveLeft() {
		node.setX(node.getX()-(5+velX));
		setx(node.getX());
	}

	void moveRight() {
		node.setX(node.getX()+(5+velX));
		setx(node.getX());
	}

	void moveUp() {
		node.setY(node.getY()-(5+velY));
		sety(node.getY());

	}

	void moveDown() {
		node.setY(node.getY()+(5+velY));
		sety(node.getY());
	}
	
	public double getx() {
		return x;
	}
	
	public double gety() {
		return y;
	}
	
	public void setx(double x) {
		this.x=x;
	}
	public void sety(double y) {
		this.y = y;
	}
	
	public void setVelx(double velX) {
		this.velX = velX;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	public ImageView getGraphic() {
		return node;
	}
}
