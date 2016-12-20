package com.nikitosh.spbau.ui.visitors;

import com.nikitosh.spbau.model.gameobjects.creatures.*;
import com.nikitosh.spbau.model.gameobjects.items.*;

/**
 * Interface for implementing "Visitor" pattern.
 */

public interface Visitor {
    void visit(Hero hero);
    void visit(Orc orc);
    void visit(Troll troll);
    void visit(Kit kit);
    void visit(Shield shield);
}
