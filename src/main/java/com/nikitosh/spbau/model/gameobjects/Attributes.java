package com.nikitosh.spbau.model.gameobjects;

public final class Attributes {
    private int health;
    private int attack;
    private int defense;

    public static class Builder {
        /**
         * Optional parameters
         */
        private int health = 0;
        private int attack = 0;
        private int defense = 0;

        public Builder health(int health) {
            this.health = health;
            return this;
        }

        public Builder attack(int attack) {
            this.attack = attack;
            return this;
        }

        public Builder defense(int defense) {
            this.defense = defense;
            return this;
        }

        public Attributes build() {
            return new Attributes(this);
        }
    }

    private Attributes(Builder builder) {
        health = builder.health;
        attack = builder.attack;
        defense = builder.defense;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public void increaseHealth(int delta) {
        health = Math.max(health + delta, 0);
    }

    public void increaseAttack(int delta) {
        attack = Math.max(attack + delta, 0);
    }

    public void increaseDefense(int delta) {
        defense = Math.max(defense + delta, 0);
    }
}
