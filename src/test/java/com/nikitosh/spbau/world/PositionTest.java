package com.nikitosh.spbau.world;

import com.nikitosh.spbau.model.world.*;
import org.junit.*;

import static junitx.framework.Assert.*;
import static org.mockito.Mockito.*;

//CheckStyle:OFF: MagicNumber

public class PositionTest {

    @Test
    public void testMove() {
        WorldMap map = mock(WorldMap.class);
        when(map.isCellEmpty(-1, 0)).thenReturn(false);
        when(map.isCellEmpty(0, -1)).thenReturn(false);
        when(map.isCellEmpty(1, 0)).thenReturn(true);
        when(map.isCellEmpty(1, 1)).thenReturn(true);
        Position position = new Position(0, 0);

        position.move(map, Movement.UP);
        assertEquals(new Position(0, 0), position);

        position.move(map, Movement.LEFT);
        assertEquals(new Position(0, 0), position);

        position.move(map, Movement.RIGHT);
        assertEquals(new Position(1, 0), position);

        position.move(map, Movement.DOWN);
        assertEquals(new Position(1, 1), position);
    }
}

//CheckStyle:ONF: MagicNumber
