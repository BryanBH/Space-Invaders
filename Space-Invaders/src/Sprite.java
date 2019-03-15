import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Sprite extends Rectangle {
	boolean dead = false;
	final String type;
	private double maxX;
	private double maxY;
	private double velX;
	private double velY;
	private double x;
	private double y;

	Image img;
	ImageView node = new ImageView();

	public Sprite(int x, int y, int w, int h, Image img, String type, Color color) {
		super(w, h, color);

		this.x = x;
		this.y = y;
		this.img = img;
		node.setImage(img);
		node.setX(x);
		node.setY(y);

		this.type = type;

	}

	void moveLeft() {
		setx(getx()-5);
	}

	void moveRight() {
		setx(getx()+5);
	}

	void moveUp() {
		sety(gety()-5);
	}

	void moveDown() {
		sety(gety()+5);
	}

	void update() {

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

	public double getVelX() {
		return velX;
	}

	public double getVelY() {
		return velY;
	}
	public ImageView getGraphic() {
		return node;
	}
	
	public void setBoundires(double x, double y) {
		this.maxX = x;
		this.maxY = y;
	}
}