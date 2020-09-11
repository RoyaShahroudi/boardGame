package boardGame.otherClasses;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Star extends Parent {
    private int y;
    private int x;


    public Star(int row, int column, int side, int x, int y) {
        this.y = y;
        this.x = x;

        int posX = (1079 - (column * side))/2 + x * side;
        int posY = (506 - (row * side))/2 + y * side;

        Circle circle = new Circle(side * 0.4);

        circle.setCenterX(posX + side/2);
        circle.setCenterY(posY + side/2);

        circle.setFill(Color.YELLOW);
        getChildren().add(circle);

    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }


}
