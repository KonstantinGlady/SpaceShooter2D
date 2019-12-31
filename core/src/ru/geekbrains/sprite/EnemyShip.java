package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.utils.Regions;

public class EnemyShip extends Sprite {

    private Vector2 v;
    private Rect worldBounds;


   
    public EnemyShip() {

              v = new Vector2();

    }
    public void set(TextureRegion region,Rect worldBounds) {

        float fx = Rnd.nextFloat(worldBounds.getLeft()+0.05f,worldBounds.getRight()-0.05f);

        this.regions = Regions.split(region,1,2,2);
        this.worldBounds = worldBounds;
        this.pos.set(fx,0);
        this.v.set(0,-0.09f);
        setHeightProportion(0.11f);
        setTop(worldBounds.getTop() - 0.05f);

        }

    @Override
    public void resize(Rect worldBounds) {

        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setTop(worldBounds.getTop() - 0.05f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds) ) {
            destroy();
        }
    }


}
