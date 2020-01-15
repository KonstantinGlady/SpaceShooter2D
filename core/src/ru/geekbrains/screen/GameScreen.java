package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyGenerator;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;

    private MainShip mainShip;

    private BulletPool bulletPool;
    private BulletPool bulletPoolEnemy;
    private EnemyPool enemyPool;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;

    private EnemyGenerator enemyGenerator;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        bulletPoolEnemy = new BulletPool();
        enemyPool = new EnemyPool(bulletPoolEnemy, bulletSound, worldBounds);
        mainShip = new MainShip(atlas, bulletPool, laserSound);
        enemyGenerator = new EnemyGenerator(atlas, enemyPool, worldBounds);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        checkDamage();
        freeAllDestroyed();
        draw();
    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        bg.dispose();
        bulletPool.dispose();
        bulletPoolEnemy.dispose();
        enemyPool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
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
        bulletPoolEnemy.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyGenerator.generate(delta);
    }

    private void checkCollisions() {
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (!enemyShip.isOutside(mainShip)) {
                enemyShip.destroy();
            }
        }
    }

    private void checkDamage() {
        List<EnemyShip> enemyShips = enemyPool.getActiveObjects();
        List<Bullet> bullets = bulletPool.getActiveObjects();
        for (Bullet bullet : bullets) {
            for (EnemyShip ship : enemyShips) {
                if (!bullet.isOutside(ship)) {
                    int enemyShipHp = ship.getHp();
                    int mainShimDamage = mainShip.getDamage();
                    if (mainShimDamage >= enemyShipHp) {
                        ship.destroy();
                    } else {
                        bullet.destroy();
                        ship.setHp(mainShimDamage);
                    }

                }
            }
        }
    }


    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        bulletPoolEnemy.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
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
        bulletPool.drawActiveSprites(batch);
        bulletPoolEnemy.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }
}
