import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
	boolean dead = false;
	final String type;

	/**
	* Sprite method that handles with the status of the object.
	* @param axis x and y, width, height, type of the object and color of the object.
	*/
	public Sprite(int x, int y, int w, int h,String type, Color color){
		super(w,h,color);

		this.type = type;

		// Movement of the object
		setTranslateX(x);
		setTranslateY(y);
	}

	// NOTE:position wise works like airplane controllers:  {positive digits - down} &  {negative digits - up}
	 void moveLeft() {
		setTranslateX(getTranslateX()-5);
	}

	void moveRight() {
		setTranslateX(getTranslateX()+5);
	}

	void moveUp() {
		setTranslateY(getTranslateY()-5);
	}

	void moveDown() {
		setTranslateY(getTranslateY()+5);
	}
}
