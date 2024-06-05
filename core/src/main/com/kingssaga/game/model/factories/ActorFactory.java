package com.kingssaga.game.model.factories;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.actors.NPC;
import com.kingssaga.game.model.actors.enemies.Boss;
import com.kingssaga.game.model.actors.enemies.Enemy;
import com.kingssaga.game.model.actors.enemies.SimpleEnemy;
import com.kingssaga.game.model.actors.player.Player;
import com.kingssaga.game.view.RenderOrder;

import java.util.ArrayList;
import java.util.List;

public class ActorFactory {

    protected SoundPlayer sp;
    private final List<Enemy> enemies;
    private Player player;
    private RenderOrder ro;
    private final FactoryManager fm;

    public ActorFactory(FactoryManager fm, SoundPlayer sp) {
        this.fm = fm;
        this.sp = sp;
        enemies = new ArrayList<>();
    }

    public SimpleEnemy createSimpleEnemy(Vector2 position) {
        WeaponFactory wf = fm.getWeaponFactory();
        SimpleEnemy enemy = new SimpleEnemy(fm, position, sp);
        wf.giveSpawnWeapon(enemy);
        addEnemy(enemy);
        return enemy;
    }

    public Boss createBoss(Vector2 position) {
        WeaponFactory wf = fm.getWeaponFactory();
        Boss boss = new Boss(fm, position, sp);
        wf.giveSpawnWeapon(boss, player.getLocation());
        addEnemy(boss);
        return boss;
    }

    public Player createPlayer(FactoryManager fm, SoundPlayer sp) {
        player = new Player(fm, sp, fm.getItemFactory().getNewCoinPouch());
        return player;
    }

    public void createNPC(MapObject object) {
        new NPC(fm.getBox2DFactory(), object);
    }

    /**
     * Adds an enemy to the game world.
     */
    private void addEnemy(Enemy enemy) {
        enemy.setPlayerLocation(player.getLocation());
        enemy.setBehavior();
        enemies.add(enemy);
        ro.add(enemy);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setRenderOrder(RenderOrder ro) {
        this.ro = ro;
    }
}
