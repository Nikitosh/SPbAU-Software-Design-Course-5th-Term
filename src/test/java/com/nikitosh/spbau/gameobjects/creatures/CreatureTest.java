package com.nikitosh.spbau.gameobjects.creatures;

import com.nikitosh.spbau.*;
import com.nikitosh.spbau.model.gameobjects.*;
import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.gameobjects.items.*;
import com.nikitosh.spbau.model.world.*;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

//CheckStyle:OFF: MagicNumber

public class CreatureTest {

    @Test
    public void testApplyItem() {
        Position creaturePosition = mock(Position.class);
        Attributes creatureAttributes = TestUtils.createAttributes(10, 5, 5);
        Creature creature = TestUtils.createCreature(creaturePosition, creatureAttributes);

        Position itemPosition = mock(Position.class);
        Attributes itemAttributes = TestUtils.createAttributes(5, -1, 2);
        Item item = TestUtils.createItem(itemPosition, itemAttributes);

        creature.applyItem(item, true);
        assertEquals(TestUtils.createAttributes(15, 4, 7), creature.getAttributes());

        creature.applyItem(item, false);
        assertEquals(TestUtils.createAttributes(10, 5, 5), creature.getAttributes());
    }

    @Test
    public void testAttack() {
        Position creaturePosition1 = mock(Position.class);
        Attributes creatureAttributes1 = TestUtils.createAttributes(10, 5, 5);
        Creature creature1 = TestUtils.createCreature(creaturePosition1, creatureAttributes1);

        Position creaturePosition2 = mock(Position.class);
        Attributes creatureAttributes2 = TestUtils.createAttributes(10, 2, 3);
        Creature creature2 = TestUtils.createCreature(creaturePosition2, creatureAttributes2);

        Position creaturePosition3 = mock(Position.class);
        Attributes creatureAttributes3 = TestUtils.createAttributes(10, 0, 10);
        Creature creature3 = TestUtils.createCreature(creaturePosition3, creatureAttributes3);

        creature1.attack(creature2);
        assertEquals(TestUtils.createAttributes(8, 2, 3), creature2.getAttributes());

        creature1.attack(creature3);
        assertEquals(TestUtils.createAttributes(10, 0, 10), creature3.getAttributes());
    }
}

//CheckStyle:ON: MagicNumber
