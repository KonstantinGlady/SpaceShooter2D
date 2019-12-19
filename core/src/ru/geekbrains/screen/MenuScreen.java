package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    //TODO Реализовать движение логотипа badlogic (можно свою картинку вставить)
    // при нажатии клавиши мыши (touchDown) в точку нажатия на экране и остановку в данной точке.

    private Texture img;
    private Texture background;
    private Vector2 pos;
    private Vector2 v;

    private float speedMax;
    private float destinyY;
    private float destinyX;

    @Override
    public void show() {
        super.show();
        background = new Texture("textures/bg.png");
        img = new Texture("dramaQueen.jpg");
        pos = new Vector2(150, 150);
        v = new Vector2();

        speedMax = 2;
        destinyX = 0;
        destinyY = 0;
    }

    @Override
    public void render(float delta) {

        super.render(delta);
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(img, pos.x, pos.y);
        batch.end();

        if (pos.dst(destinyX, destinyY) > 1) {
            pos.add(v);
        }

    }

    @Override
    public void dispose() {
        img.dispose();
        background.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        destinyX = screenX;
        destinyY = Gdx.graphics.getHeight() - screenY;

        v.set(destinyX - pos.x, destinyY - pos.y).nor().scl(Math.min(pos.dst(destinyX, destinyY), speedMax));

        return false;
    }

    //TODO Реализовать управление логотипом с помощью клавиш
    // со стрелками на клавиатуре*** (дополнительное задание, не обязательное к выполнению)

    @Override
    public boolean keyDown(int keycode) {

       v.set(0,0);

        switch (keycode) {
            case Keys.UP:

                pos.add(0, 2);
               break;

            case Keys.DOWN:

                pos.add(0, -2);
                break;
            case Keys.LEFT:

                pos.add(-2, 0);
                break;
            case Keys.RIGHT:

                pos.add(2, 0);
                break;

            default:

        }
        return super.keyDown(keycode);
    }


}
