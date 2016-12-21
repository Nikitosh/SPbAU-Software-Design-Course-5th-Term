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

public class HeroTest {

    @Test
    public void testApplyItemFromInventory() {
        Position position = mock(Position.class);
        Hero hero = new Hero(position);
        Attributes attributes = hero.getAttributes();
        int health = attributes.getHealth();
        int attack = attributes.getAttack();
        int defense = attributes.getDefense();
        Item item1 = TestUtilities.createItem(mock(Position.class), TestUtilities.createAttributes(1, 1, 1));
        Item item2 = TestUtilities.createItem(mock(Position.class), TestUtilities.createAttributes(2, 2, 2));
        hero.addItem(item1);
        hero.addItem(item2);

        hero.applyItemFromInventory(2);
        assertEquals(TestUtilities.createAttributes(health, attack, defense), hero.getAttributes());
        hero.applyItemFromInventory(1);
        assertEquals(TestUtilities.createAttributes(health + 2, attack + 2, defense + 2), hero.getAttributes());
        hero.applyItemFromInventory(0);
        assertEquals(TestUtilities.createAttributes(health + 3, attack + 3, defense + 3), hero.getAttributes());
        hero.applyItemFromInventory(1);
        assertEquals(TestUtilities.createAttributes(health + 1, attack + 1, defense + 1), hero.getAttributes());
    }
}

//CheckStyle:ON: MagicNumber
