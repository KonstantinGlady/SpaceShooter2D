package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class Star extends Sprite {

private Vector2 v;
private Rect worldBounds;
private float height;

private float starAnimateInterval;
private float starAnimateTimer;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        float vx = Rnd.nextFloat(-0.005f, 0.005f);
        float vy = Rnd.nextFloat(-0.2f, -0.03f);
        v = new Vector2(vx, vy);
        starAnimateInterval = 1f;
        starAnimateTimer = Rnd.nextFloat(0, 1f);
        height = 0.01f;
    }

    @Override

    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
        setHeightProportion(height);

    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        checkBound();
        height += 0.00005f;
        setHeightProportion(height);
        starAnimateTimer += delta;
        if (starAnimateTimer > starAnimateInterval) {
        starAnimateTimer = 0f;
        height = 0.01f;
        }

    }

    private void checkBound() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}
