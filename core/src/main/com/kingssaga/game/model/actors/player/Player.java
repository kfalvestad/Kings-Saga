package com.kingssaga.game.model.actors.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kingssaga.game.*;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.controller.ai.Box2DLocation;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.model.items.CoinPouch;
import com.kingssaga.game.model.items.Consumable;
import com.kingssaga.game.model.items.Item;
import com.kingssaga.game.model.items.Key;
import com.kingssaga.game.model.actors.Stats;

import java.util.Timer;
import java.util.ArrayList;

/**
 * The Player class represents a player in the game.
 * It extends the Actor class and contains information about the player's
 * location, coin pouch, weapon, and inventory.
 */
public class Player extends Actor implements Stats {

  protected Box2DLocation location;
  protected final FactoryManager fm;
  private final CoinPouch coinPouch;
  private boolean isAffectedByPotion;
  private Timer potionTimer;
  private float originalSpeed;
  private int runningBoots;

  /**
   * Constructs a new Player with the specified GameManager instance.
   *
   */
  public Player(FactoryManager fm, SoundPlayer sp, CoinPouch coinPouch) {
    this.sp = sp;
    this.fm = fm;
    this.coinPouch = coinPouch;
    health = Constants.PLAYER_HEALTH;
    maxHealth = Constants.PLAYER_MAX_HEALTH;
    speed = Constants.PLAYER_SPEED;
    originalSpeed = speed;
    takeDamageDelay = 0;
    attackDelay = 0;
    runningBoots = 1;
    isFacingLeft = false;
    inventory = new ArrayList<>();
    hitSounds = new ArrayList<>();
    hitSounds.add("hit1.mp3");
    hitSounds.add("hit2.mp3");
    hitSounds.add("hit3.mp3");

    setUp();
  }

  protected void setUp() {
    createSprite(fm.getGraphicsFactory(), Constants.PLAYER_SPRITE_PATH);
    width = sprite.getWidth() / Constants.PPM;
    height = sprite.getHeight() / Constants.PPM;
    sprite.setBounds(0, 0, width, height);

    defineBody(fm.getBox2DFactory(), Constants.PLAYER_SPAWN);
    fm.getWeaponFactory().giveSpawnWeapon(this);
  }

  @Override
  public void update() {
    updateSpritePosition();
    updateAttackDelay();
    updateTakeDamageDelay();
  }


  public void attack() {
    if (attackDelay == 0) {
      wieldedWeapon.doPrimaryAttack(body.getPosition());
      attackDelay = wieldedWeapon.getDelay();
    }
  }

  @Override
  protected void updateAttackDelay() {
      if (attackDelay > 0) {
        attackDelay--;
      }
  }

  @Override
  protected void updateSpritePosition() {
    sprite.setPosition(
            body.getPosition().x - width / 2,
            body.getPosition().y - height / 2);
  }

  @Override
  protected void defineBody(Box2DFactory b2df, Vector2 position) {
    body = b2df.createBody(this, position);
    location = b2df.getNewBox2DLocation(body);

    FixtureDef fixtureDef = b2df.getNewFixtureDef();
    fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
    fixtureDef.filter.maskBits = Constants.OBJECT_BIT | Constants.EXIT_BIT | Constants.DOOR_BIT | Constants.NPC_BIT;

    PolygonShape shape = b2df.getNewPolygonShape();
    shape.setAsBox(width / 4, 1 / Constants.PPM, new Vector2(0, -height / 2), 0);
    fixtureDef.shape = shape;
    body.createFixture(fixtureDef).setUserData(this);

    shape.setAsBox(width / 2, height / 2);
    fixtureDef.isSensor = true;
    fixtureDef.filter.categoryBits = Constants.PLAYER_HITBOX_BIT;
    fixtureDef.filter.maskBits = Constants.ATTACK_BIT | Constants.ITEM_BIT | Constants.DOOR_BIT | Constants.NPC_BIT;
    body.createFixture((fixtureDef)).setUserData(this);

    shape.dispose();
  }

  /**
   * Moves the player based on the input from the keyboard.
   */
  public void move(boolean right, boolean left, boolean up, boolean down) {
    float deltaX = 0;
    float deltaY = 0;

    if (right) {
      deltaX += 1;
      if (isFacingLeft) {
        flip();
      }
    }

    if (left) {
      deltaX -= 1;
      if (!isFacingLeft) {
        flip();
      }
    }

    if (up) {
      deltaY += 1;
    }

    if (down) {
      deltaY -= 1;
    }

    Vector2 deltaVector = new Vector2(deltaX, deltaY).nor();

    body.setLinearVelocity((deltaVector.x * speed * runningBoots), (deltaVector.y * speed * runningBoots));
  }

  /**
   * Gets the player's location
   * 
   * @return The player's location
   */
  public Box2DLocation getLocation() {
    return location;
  }

  /**
   * Adds an item to the player's inventory.
   * If the inventory is full, the item will not be added.
   * If the item is a CoinPouch, the value of the CoinPouch will be added to the
   * player's coin pouch.
   * 
   * @return {@code true} if the item was added to the inventory, {@code false} if
   *         not.
   */
  public boolean addToInventory(Item item) {
    if (item instanceof CoinPouch) {
      coinPouch.addCoins(item.getValue());
    } else if (item instanceof Key) {
        inventory.add(item);
    } else if (inventory.size() < Constants.INVENTORY_SIZE) {
      inventory.add(item);
    } else {
      return false;
    }

    return true;
  }

  /**
   * Consumes the specified consumable item.
   * If the item is permanent, the effect will be applied to the player.
   * If the item is temporary, the effect will be applied to the player for the
   * specified duration. If the player is already affected by a potion, nothing
   * will happen.
   *
   */
  public void consumeItem(Consumable item) {
    if (item.getEffectType() == Consumable.EffectType.PERMANENT) {
      item.consumeEffect(this);
    } else if (item.getEffectType() == Consumable.EffectType.TEMPORARY) {
      if (!isAffectedByPotion) {
        item.consumeEffect(this);
      }
    }
  }

  /**
   * Temporarily boosts the player's speed by the specified multiplier
   * for the specified duration.
   * Created with the help of GitHub Copilot
   * 
   * @param duration        the duration of the speed boost in seconds
   * @param speedMultiplier the multiplier by which the speed is boosted
   */
  public void boostSpeed(float duration, float speedMultiplier) {
    if (isAffectedByPotion) {
      potionTimer.cancel();
    }
    isAffectedByPotion = true;
    speed *= speedMultiplier;
    potionTimer = new Timer();
    potionTimer.schedule(new java.util.TimerTask() {
      @Override
      public void run() {
        speed = originalSpeed;
        isAffectedByPotion = false;
      }
    }, (long) (duration * 1000));
  }

  public boolean isAffectedByPotion() {
    return isAffectedByPotion;
  }

  public boolean hasKey(int ID) {
    for (Item item : inventory) {
      if (item instanceof Key) {
        if (((Key) item).getID() == ID) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public int getCoins() {
    return this.coinPouch.getCoins();
  }

  @Override
  public void addCoins(int amount) {
    this.coinPouch.addCoins(amount);
  }

  @Override
  public void removeCoins(int amount) {
    coinPouch.removeCoins(amount);
  }

    @Override
  public void giveRunningBoots() {
    runningBoots = Constants.RUNNING_BOOTS_SPEED;
    }

    @Override
  public boolean hasRunningBoots() {
    return runningBoots > 1;
    }
}
