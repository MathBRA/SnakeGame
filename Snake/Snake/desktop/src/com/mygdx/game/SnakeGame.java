package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static java.lang.Thread.sleep;

public class SnakeGame extends ApplicationAdapter {

    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    Stage stage;

    public final static int WIDTH = 900;
    public final static int HEIGHT = 900;
    public final static int SQUARE_SIZE = 30;

    Snake snake1;
    Snake snake2;

    Food food1;
    Food food2;

    int gameEndS1 = 0;
    int gameEndS2 = 0;

    @Override
    public void create (){

        shapeRenderer = new ShapeRenderer();
        stage = new Stage(new ScreenViewport());
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        snake1 = new Snake(SQUARE_SIZE,  SQUARE_SIZE, Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D, Color.GREEN, Color.YELLOW, SQUARE_SIZE);
        snake2 = new Snake(WIDTH - 2*SQUARE_SIZE,  HEIGHT - 2*SQUARE_SIZE, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, Color.BLUE, Color.PURPLE, -SQUARE_SIZE);
        food1 = new Food(snake1.body, snake2.body);
        food2 = new Food(snake1.body, snake2.body);
    }

    public void update() {
        int delay;

        checkEaten(snake1, snake2);
        checkEaten(snake2, snake1);

        if(snake1.uptade % snake1.mod == 0){
            snake1.updatePlayer();
        }

        if(snake2.uptade % snake2.mod == 0){
            snake2.updatePlayer();
        }

        delay = 35;

        if(snake1.duration == 0){
            snake1.mod = 2;
        }
        if(snake2.duration == 0){
            snake2.mod = 2;
        }

        snake1.uptade = (snake1.uptade + 1) % 4;
        snake2.uptade = (snake1.uptade + 1) % 4;

        if(snake1.duration >= 0){
            snake1.duration--;
        }
        if(snake2.duration >= 0){
            snake2.duration--;
        }

        gameEndS1 = snake1.checkGameEnd(snake2.body);
        gameEndS2 = snake2.checkGameEnd(snake1.body);
        stagger(delay);
    }

    private void checkEaten(Snake snake, Snake otherSnake) {

        int comida;
        comida = snake.checkCollideWithFood(food1, food2);

        if(comida == 1){
            food1.eaten(snake, otherSnake);
            food1.renew(snake.body, snake2.body);
        }
        if(comida == 2){
            food2.eaten(snake, otherSnake);
            food2.renew(snake.body, snake2.body);
        }
    }

    private void drawEnd(Label.LabelStyle style) {
        Label label = new Label("Press SPACE to restart", style);
        label.setPosition(0, Gdx.graphics.getHeight() / 2f);
        label.setSize(Gdx.graphics.getWidth(), 20);
        label.setAlignment(Align.center);
        label.setFontScale(1.5f);
        stage.addActor(label);
    }
    private void drawWin(Label.LabelStyle style) {
        Label label = new Label("You've WON! Press SPACE to restart", style);
        label.setPosition(0, Gdx.graphics.getHeight() / 2f);
        label.setSize(Gdx.graphics.getWidth(), 20);
        label.setAlignment(Align.center);
        label.setFontScale(1.5f);
        stage.addActor(label);
    }
    private void stagger(int delay) {
        try {
            sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void render () {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.BLUE;

        if (gameEndS1 == 0 && gameEndS2 == 0) {
            update();
        }
        else if (gameEndS1 == 1 || gameEndS2 == 1) {
            drawEnd(labelStyle);
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                gameEndS1 = 0;
                gameEndS2 = 0;
                create();
            }
        }

        stage.act();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeType.Filled);
        snake1.draw(shapeRenderer);
        snake2.draw(shapeRenderer);
        food1.draw(shapeRenderer);
        food2.draw(shapeRenderer);
        shapeRenderer.end();
        stage.draw();
        stage.clear();
    }

    @Override
    public void dispose () {
        shapeRenderer.dispose();
        stage.dispose();
    }
}