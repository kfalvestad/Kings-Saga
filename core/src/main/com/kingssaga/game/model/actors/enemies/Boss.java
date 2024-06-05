package com.kingssaga.game.model.actors.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kingssaga.game.model.items.CoinPouch;
import com.kingssaga.game.Constants;
import com.kingssaga.game.model.factories.FactoryManager;
import com.kingssaga.game.SoundPlayer;
import com.kingssaga.game.model.factories.Box2DFactory;
import com.kingssaga.game.model.items.ItemType;

import java.util.List;
import java.util.ArrayList;

public class Boss extends Enemy {
    private final int ROAR_DELAY = 100;
    private final float FLIP_SPRITE_MARGIN = 0.1f;
    protected final int ATTACK_WHEN_OUTSIDE_RANGE = 50;
    protected final int ATTACK_WHEN_INSIDE_RANGE = 100;
    private final String STOMPING_SOUND = "roar2.wav";
    private final String CHARGING_SOUND = "roar1.wav";

    private float width;
    private float height;

    public enum BossState {
        HUNTING, CHARGING, STOMPING
    }

    private BossState currentState;

    protected int enemyWithinRangeCounter;
    protected int enemyOutsideRangeCounter;

    private boolean isRoaring;
    private int roaringCounter;

    private final List<ItemType> availableLootItems = new ArrayList<>();

    public Boss(FactoryManager fm, Vector2 position, SoundPlayer sp) {
        super(Constants.BOSS_HEALTH, Constants.BOSS_SPEED, Constants.BOSS_ACCELERATION, sp);
        this.fm = fm;
        currentState = BossState.HUNTING;
        maxHealth = Constants.BOSS_MAX_HEALTH;
        enemyWithinRangeCounter = 0;
        enemyOutsideRangeCounter = 0;
        isFacingLeft = true;
        attackDelay = 0;
        takeDamageDelay = 0;
        initialPosition = position;
        setUp();
    }

    protected void setUp() {
        createSprite(fm.getGraphicsFactory(), Constants.BOSS_SPRITE_PATH);
        defineBody(fm.getBox2DFactory(), initialPosition);
        steering = fm.getBox2DFactory().getNewSteeringEntity(this.body, width);
        steering.setMaxLinearSpeed(this.speed);
        steering.setMaxLinearAcceleration(this.acceleration);
        this.coinPouch = (CoinPouch) fm.getItemFactory().getNewItem(ItemType.COIN_POUCH);
        this.availableLootItems.add(ItemType.MELEE_WEAPON);
        addToLoot(fm.getLootFactory().createLoot(availableLootItems, 0, 2));
        addToLoot(coinPouch);
    }

    @Override
    protected void updateSteering() {
        if (!isRoaring && currentState == BossState.HUNTING) {
            steering.update(behavior);
        }
    }

    private void updateRoaring() {
        if (isRoaring) {
            roaringCounter++;
        }
    }

    @Override
    protected void updateSpritePosition() {
        if (isFacingLeft) {
            sprite.setPosition(body.getPosition().x - width / 3, body.getPosition().y - height / 2);
        } else {
            sprite.setPosition(body.getPosition().x - width / 1.5f, body.getPosition().y - height / 2);
        }
    }

    private void updateAttackCounters() {
        if (isWithinRange(playerLocation.getPosition(), wieldedWeapon.getRange()) && !isRoaring) {
            enemyWithinRangeCounter++;
            enemyOutsideRangeCounter = 0;
        } else if (!isRoaring) {
            enemyWithinRangeCounter = 0;
            enemyOutsideRangeCounter++;
        }
    }

    @Override
    public void update() {
        updateSteering();
        updateFacing(FLIP_SPRITE_MARGIN);
        updateAttackDelay();
        updateRoaring();
        updateSpritePosition();
        updateTakeDamageDelay();
        updateAttackingState();
    }

    protected void updateAttackingState() {
        if (currentState != BossState.HUNTING && attackDelay == 0 && roaringCounter >= ROAR_DELAY) {
            roaringCounter = 0;
            isRoaring = false;
            doAttack();
        } else {
            updateAttackCounters();
            if (enemyWithinRangeCounter >= ATTACK_WHEN_INSIDE_RANGE) {
                enemyWithinRangeCounter = 0;
                readyAttack(STOMPING_SOUND, BossState.STOMPING);
            } else if (enemyOutsideRangeCounter >= ATTACK_WHEN_OUTSIDE_RANGE) {
                enemyOutsideRangeCounter = 0;
                readyAttack(CHARGING_SOUND, BossState.CHARGING);
            }
        }
    }


        private void readyAttack(String file, BossState state) {
            sp.playSound(file);
            isRoaring = true;
            body.setLinearVelocity(new Vector2(0, 0));
            currentState = state;
        }

    protected void doAttack() {
        attackDelay = wieldedWeapon.getDelay();
        if (currentState == BossState.STOMPING) {
            doStompAttack();
        } else if (currentState == BossState.CHARGING) {
            doChargeAttack();
        }
    }

    private void doStompAttack() {
        wieldedWeapon.doSecondaryAttack(body.getPosition());
        currentState = BossState.HUNTING;
    }

    private void doChargeAttack() {
        wieldedWeapon.getPrimaryAttack().setTarget(playerLocation.getPosition());
        wieldedWeapon.doPrimaryAttack(body.getPosition());
    }

    @Override
    protected void defineBody(Box2DFactory b2df, Vector2 position) {
        width = sprite.getWidth() / 2 / Constants.PPM;
        height = sprite.getHeight() / 2 / Constants.PPM;
        sprite.setBounds(0, 0, width, height);

        body = b2df.createBody(this, position);

        FixtureDef fixtureDef = b2df.getNewFixtureDef();
        fixtureDef.filter.categoryBits = Constants.ENEMY_BIT;
        fixtureDef.filter.maskBits = Constants.OBJECT_BIT;

        PolygonShape shape = b2df.getNewPolygonShape();
        shape.setAsBox(width / 4, 1 / Constants.PPM, new Vector2(0, -height / 2), 0);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);

        shape.setAsBox(width / 4, height / 4, new Vector2(0, -height / 4), 0);
        fixtureDef.filter.categoryBits = Constants.ENEMY_HITBOX_BIT;
        fixtureDef.filter.maskBits = Constants.ATTACK_BIT | Constants.ENEMY_HITBOX_BIT;
        body.createFixture((fixtureDef)).setUserData(this);

        shape.dispose();
    }

    public BossState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(BossState currentState) {
        this.currentState = currentState;
    }

    @Override
    protected void updateAttackDelay() {
        if (attackDelay > 0) {
        attackDelay--;
    } else {
        wieldedWeapon.getSecondaryAttack().setIsActive(false);
        wieldedWeapon.getPrimaryAttack().setIsActive(false);
    }
}
}
