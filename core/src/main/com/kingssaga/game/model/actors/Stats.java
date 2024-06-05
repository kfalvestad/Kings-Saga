package com.kingssaga.game.model.actors;

public interface Stats {

    public int getHealth();

    public void setHealth(int health);

    public float getSpeed();

    public void setSpeed(int speed);

    public void giveRunningBoots();

    public boolean hasRunningBoots();

    public int getCoins();

    public void addCoins(int amount);

    public void removeCoins(int amount);
}
