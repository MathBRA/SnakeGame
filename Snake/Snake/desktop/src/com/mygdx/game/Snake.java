package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    public final ArrayList<Square> body = new ArrayList<>();
    public Square head;
    private float dx, dy;

    int uptade = 0;
    int mod = 2;
    int duration = 0;

    int up, down, left, right;

    Color color;
    Color headColor;

    public Snake(int x, int y, int up, int down, int left, int right, Color color, Color headColor, float direction) {
        // Initialize the head of the snake
        head = new Square(x, y, Color.GREEN);
        body.add(head);
        dx = direction; // moves to the right

        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;

        this.color = color;
        this.headColor = headColor;
    }

    public void updatePlayer(){
        dirCalc();
        body.remove(body.size() - 1);
        moveSnake();
    }

    private void moveSnake() {
        head.setColor(color);
        float new_x = head.x + dx;
        float new_y = head.y + dy;
        head = new Square(new_x, new_y, headColor);

        body.add(0, head);
    }

    public void grow(){
        Square tail = body.get(body.size() - 1);
        body.add(body.size(), tail);
    }

    public int checkCollideWithFood(Food food1, Food food2) {

        if(head.overlaps(food1)){
            return 1;
        }
        if(head.overlaps(food2)){
            return 2;
        }
        return 0;
    }
    public int checkGameEnd(List<Square> otherSnake) {

        if (head.x == SnakeGame.WIDTH || head.x < 0 || head.y == SnakeGame.HEIGHT || head.y < 0) {
            return 1;
        }
        /*for (Square s : body.subList(1, body.size())) {
            if (head.overlaps(s)) {
                return 1;
            }
        }*/
        for(Square s : otherSnake) {
            if (head.overlaps(s)) {
                return 1;
            }
        }
        return 0;
    }

    private void dirCalc() {
        if ((Gdx.input.isKeyPressed(left) && dx < 1)) {
            dy = 0;
            dx = -SnakeGame.SQUARE_SIZE;
        }
        else if ((Gdx.input.isKeyPressed(right) && dx > -1)) {
            dy = 0;
            dx = SnakeGame.SQUARE_SIZE;
        }
        else if ((Gdx.input.isKeyPressed(up)) && dy > -1) {
            dx = 0;
            dy = SnakeGame.SQUARE_SIZE;
        }
        else if ((Gdx.input.isKeyPressed(down)) && dy < 1) {
            dx = 0;
            dy = -SnakeGame.SQUARE_SIZE;
        }
    }
    public void draw(ShapeRenderer shapeRenderer) {
        for (Square square: body) {
            square.draw(shapeRenderer);
        }
    }

}