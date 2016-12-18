package com.nikitosh.spbau.model.gameobjects;

public class Attributes {
    private int health;
    private int attack;
    private int defense;

    public static class Builder {
        /**
         * Required parameters
         */
        private int health;
        /**
         * Optional parameters
         */
        private int attack = 0;
        private int defense = 0;

        public Builder(int health) {
            this.health = health;
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
}
