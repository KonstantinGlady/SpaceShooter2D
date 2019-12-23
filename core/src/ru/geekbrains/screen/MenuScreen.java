package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;



public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture bg;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 touch;

    private Background background;




    @Override

    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        img = new Texture("badlogic.jpg");
      
        pos = new Vector2();
        v = new Vector2();
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.2f, 	0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);

        batch.draw(img, pos.x, pos.y, 0.5f, 0.5f);
        batch.end();


        if (pos.dst(touch) > 0.005) {
            pos.add(v);
        }
    }

    @Override
    public void dispose() {
        img.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
            this.touch = touch;
             v.set(touch.sub(pos)).nor().scl(0.002f);

       return super.touchDown(touch, pointer, button);

    }



}


