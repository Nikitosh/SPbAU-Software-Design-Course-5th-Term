package com.nikitosh.spbau.ui.visitors;

import com.nikitosh.spbau.model.gameobjects.creatures.*;

public interface Visitor {
    void visit(Hero hero);
    void visit(Orc orc);
    void visit(Troll troll);
}
