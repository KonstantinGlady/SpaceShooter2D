package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyShipsPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;

    private MainShip mainShip;
    private Music music;
    private BulletPool bulletPool;
    private EnemyShipsPool enemyShipsPool;
    private Rect worldBounds;

    private float setupNewEnemyShip;
    private float timerNewEnemyShip;


    @Override
    public void show() {
        super.show();
        timerNewEnemyShip = 3;
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        mainShip = new MainShip(atlas, bulletPool);

        enemyShipsPool = new EnemyShipsPool();

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.play();
        music.setLooping(true);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        freeAllDestroyed();
        draw();
        enemyShipSetup(delta);

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);

    }

    @Override
    public void dispose() {
        mainShip.dispose();
        music.dispose();
        atlas.dispose();
        bg.dispose();
        bulletPool.dispose();
        enemyShipsPool.dispose();
        super.dispose();

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        mainShip.touchDragged(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyShipsPool.updateActiveSprites(delta);

    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyShipsPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);

        enemyShipsPool.drawActiveSprites(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();

    }

    private void enemyShipSetup(float delta) {

        setupNewEnemyShip += delta;

        if (setupNewEnemyShip > timerNewEnemyShip) {
            setupNewEnemyShip = 0;
            EnemyShip enemyShip = enemyShipsPool.obtain();

            enemyShip.set(atlas.findRegion(getShipsName()), worldBounds);
        }

    }

    private String getShipsName() {

        return "enemy" + (int) Rnd.nextFloat(0, 3);

    }
}
