package com.kingssaga.game.model.factories;

public interface FactoryManager {

    GraphicsFactory getGraphicsFactory();
    Box2DFactory getBox2DFactory();
    WeaponFactory getWeaponFactory();
    LootFactory getLootFactory();
    ActorFactory getActorFactory();
    ItemFactory getItemFactory();
    GameObjectFactory getGameObjectFactory();
    AttackFactory getAttackFactory();
}
