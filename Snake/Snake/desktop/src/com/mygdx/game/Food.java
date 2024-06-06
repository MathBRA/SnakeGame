package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import java.util.List;

public class Food extends Square {

    int type;

    public Food(List<Square> snake1, List<Square> snake2) {
        super(0, 0, Color.RED);
        renew(snake1, snake2);
    }

    public void renew(List<Square> snake1, List<Square> snake2) {
        Point pos = findNewPos();
        if(checkPos(snake1, pos) && checkPos(snake2, pos)){
            x = pos.x; y = pos.y;
        }

        int prob = (int) (Math.random() * 100);

        if(prob <= 50) {
            type = 1;
            color = Color.RED;
        }
        else if(prob <= 65) {
            type = 2;
            color = Color.WHITE;
        }
        else if(prob <= 80) {
            type = 3;
            color = Color.CYAN;
        }
        else if(prob <= 95) {
            type = 4;
            color = Color.LIGHT_GRAY;
        }
        else if(prob <= 100) {
            type = 5;
            color = Color.YELLOW;
        }
    }

    public void eaten(Snake snake, Snake otherSnake){
        switch(type){
            case 1:
                snake.grow();
                break;
            case 2:
                snake.grow();
                otherSnake.mod = otherSnake.mod*2;
                otherSnake.duration += 100;
                break;
            case 3:
                snake.grow();
                snake.mod = snake.mod/2;
                snake.duration += 100;
                break;
            case 4:
                for(int i = 0; i < 3; i++) {
                    snake.grow();
                }
                break;
            case 5:
                for(int i = 0; i < 5; i++) {
                    snake.grow();
                }
                break;
        }
    }

    public Point findNewPos() {
        int x_grid = SnakeGame.WIDTH / SnakeGame.SQUARE_SIZE;
        int y_grid = SnakeGame.HEIGHT / SnakeGame.SQUARE_SIZE;
        int x = ((int) (Math.random() * x_grid)) * SnakeGame.SQUARE_SIZE;
        int y = ((int) (Math.random() * y_grid)) * SnakeGame.SQUARE_SIZE;

        return new Point(x, y);
    }

    public boolean checkPos(List<Square> snake, Point pos){
        for (Square s : snake) {
            if (s.x == pos.x && s.y == pos.y) {
                return false;
            }
        }

        return true;
    }

    public void updateWin() {
        color = Color.GREEN;
    }


}