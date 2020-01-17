package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {
    Game game;
    GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, Game game, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.04f);
        setLeft(worldBounds.getLeft() + 0.2f);
        setBottom(worldBounds.getBottom() + 0.3f);
    }

    @Override
    public void action() {
      //  game.setScreen(new GameScreen(game));// самый просто способ. GameScreen в конструкторе не передаем, только Game
       gameScreen.newGame(); // второй способ как просили, Game не нужен передаем GameScreen в конструкторе
    }
}
