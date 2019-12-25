package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;


public class Ship extends Sprite {

    private Vector2 v;
    private Rect worldBounds;


    public Ship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        v = new Vector2(0, 0);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        checkBound();

        pos.add(v);

    }

    private void checkBound() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        v.set(touch.x - pos.x, 0).scl(0.01f);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        pos.set(0, worldBounds.getBottom() + 0.1f);
        setHeightProportion(0.09f);
    }


    public void keyDown(int keycode) {
        switch (keycode) {
            case LEFT:
                v.set(-0.002f, 0);
                break;
            case RIGHT:
                v.set(0.002f, 0);
                break;
        }
    }
}
