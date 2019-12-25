package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;


import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Ship;
import ru.geekbrains.sprite.Star;



public class GameScreen extends BaseScreen {

    private Texture bg;
    private TextureAtlas atlas;
    private TextureAtlas mainAtlas;

    private Star[] stars;
    private Background background;
    private Ship ship;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));

        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));

        mainAtlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));


        stars = new Star[64];

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        ship = new Ship(mainAtlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {

        super.resize(worldBounds);

        background.resize(worldBounds);
        ship.resize(worldBounds);

        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        atlas.dispose();
        bg.dispose();
        mainAtlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
       ship.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    public void update(float delta) {

        ship.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }

    }

    public void draw() {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        ship.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }

        batch.end();

    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        return super.keyDown(keycode);
    }
}
