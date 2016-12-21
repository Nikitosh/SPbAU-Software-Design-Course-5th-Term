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
        Attributes creatureAttributes = TestUtilities.createAttributes(10, 5, 5);
        Creature creature = TestUtilities.createCreature(creaturePosition, creatureAttributes);

        Position itemPosition = mock(Position.class);
        Attributes itemAttributes = TestUtilities.createAttributes(5, -1, 2);
        Item item = TestUtilities.createItem(itemPosition, itemAttributes);

        creature.applyItem(item, true);
        assertEquals(TestUtilities.createAttributes(15, 4, 7), creature.getAttributes());

        creature.applyItem(item, false);
        assertEquals(TestUtilities.createAttributes(10, 5, 5), creature.getAttributes());
    }

    @Test
    public void testAttack() {
        Position creaturePosition1 = mock(Position.class);
        Attributes creatureAttributes1 = TestUtilities.createAttributes(10, 5, 5);
        Creature creature1 = TestUtilities.createCreature(creaturePosition1, creatureAttributes1);

        Position creaturePosition2 = mock(Position.class);
        Attributes creatureAttributes2 = TestUtilities.createAttributes(10, 2, 3);
        Creature creature2 = TestUtilities.createCreature(creaturePosition2, creatureAttributes2);

        Position creaturePosition3 = mock(Position.class);
        Attributes creatureAttributes3 = TestUtilities.createAttributes(10, 0, 10);
        Creature creature3 = TestUtilities.createCreature(creaturePosition3, creatureAttributes3);

        creature1.attack(creature2);
        assertEquals(TestUtilities.createAttributes(8, 2, 3), creature2.getAttributes());

        creature1.attack(creature3);
        assertEquals(TestUtilities.createAttributes(10, 0, 10), creature3.getAttributes());
    }
}

//CheckStyle:ON: MagicNumber
