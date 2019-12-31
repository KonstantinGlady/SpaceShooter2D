package ru.geekbrains.pool;


import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.EnemyShip;

public class EnemyShipsPool extends SpritesPool<EnemyShip> {



    @Override
    public EnemyShip newObject() {
        return new EnemyShip();
    }

}
