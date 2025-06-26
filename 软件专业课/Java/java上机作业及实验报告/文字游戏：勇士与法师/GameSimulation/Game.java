package GameSimulation;

import java.util.Random;

public abstract class
Game {
    static Random RandState = new Random();
    abstract void play();
}
