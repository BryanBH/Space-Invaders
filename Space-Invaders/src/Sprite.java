import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Sprite extends Rectangle {
    boolean dead = false;
    final String type;
    private double maxX;
	private double maxY;
	private double velX;
	private double velY;

   public Sprite(int x, int y, int w, int h, String type, Color color) {
        super(w, h, color);

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    void moveLeft() {
        setTranslateX(getTranslateX() - 5);
    }

    void moveRight() {
        setTranslateX(getTranslateX() + 5);
    }

    void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }

    void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }
    
    void update() {
    	
    }

	public void setBoundires(double x , double y) {
		this.maxX = x;
		this.maxY = y;
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
}