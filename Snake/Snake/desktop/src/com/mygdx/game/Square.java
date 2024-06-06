package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Square extends Rectangle {
    Color color;

    public Square(float x, float y, Color color) {
        super(x, y, SnakeGame.SQUARE_SIZE - 1, SnakeGame.SQUARE_SIZE - 1);
        this.color = color;
    }
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height); // it's a square
    }
    public void setColor(Color color) { this.color = color; }
}