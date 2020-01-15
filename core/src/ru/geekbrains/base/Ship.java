package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Bullet;

public class Ship extends Sprite {

    protected Vector2 v;
    protected Vector2 v0;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected float bulletHeight;
    protected Vector2 bulletV;
    protected int damage;

    protected Sound shootSound;

    protected Rect worldBounds;

    protected float reloadInterval;
    protected float reloadTimer;

    protected int hp;

    public Ship() {
        super();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
        shootSound.play();
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int damage) {
        this.hp -=damage;
    }

    public int getDamage() {
        return damage;
    }
}
