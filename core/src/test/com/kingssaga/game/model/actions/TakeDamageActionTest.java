package com.kingssaga.game.model.actions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.kingssaga.game.model.actors.Actor;
import com.kingssaga.game.model.TestActor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TakeDamageActionTest {

    private Actor target;

    @BeforeEach
    void initializeActorWithFullHealth() {
        target = new TestActor();
        target.setHealth(100);
        assertEquals(100, target.getHealth());
        assert(target.isAlive());
    }

    @Test
    void shouldReduceHealthByDamageAmount() {
        final int damage = 10;
        TakeDamageAction action = new TakeDamageAction(damage, target);
        action.execute();
        assertEquals(90, target.getHealth());
    }

    @Test
    void shouldSetHealthToZeroIfDamageExceedsCurrentHealth() {
        final int damage = 110;
        TakeDamageAction action = new TakeDamageAction(damage, target);
        assertEquals(action.execute(), ActionResult.SUCCESS);
        assertEquals(0, target.getHealth());
        assert(!target.isAlive());
    }

    @Test
    void shouldThrowExceptionIfDamageIsZeroOrNegative() {
        final int damage = 0;
        assertThrows(IllegalArgumentException.class, () -> new TakeDamageAction(damage, target));
        final int negativeDamage = -10;
        assertThrows(IllegalArgumentException.class, () -> new TakeDamageAction(negativeDamage, target));
    }

    @Test
    void shouldNotChangeHealthIfActorIsAlreadyDead() {
        target.setHealth(0);
        assert(!target.isAlive());
        final int damage = 10;
        TakeDamageAction action = new TakeDamageAction(damage, target);
        assertEquals(action.execute(), ActionResult.SUCCESS);
        assertEquals(0, target.getHealth());
        assert(!target.isAlive());
    }

}