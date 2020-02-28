package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {


    /** red color. */
    private final int r = 34;
    /** green color. */
    private final int g = 0;
    /** blue color. */
    private final int b = 231;

    /** Energy loss when the Clorus moves.*/
    private final static double moveE = -0.03;
    /** Energy gain when the Clorus stays. */
    private final static double stayE = 0.01;
    /** Energy retained fraction when the Clorus replicates. */
    private final static double repERetained = 0.5;

    /**
     * creates Clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("Clorus");
        energy = e;
    }

    /**
     * creates a Clorus with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Clorus. If the Clorus has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        return color(r, g, b);
    }

    /**
     * If a Clorus attacks another creature, it will gain that creature’s energy.
     */
    public void attack(Creature c) {
         this.energy += c.energy();
    }

    /**
     * Cloruss should lose 0.03units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        this.energy += moveE;
        if (this.energy < 0) {
            this.energy = 0;
        }
    }


    /**
     * Cloruss gain 0.01 energy when staying due to photosynthesis.
     */
    public void stay() {
        this.energy += stayE;
    }

    /**
     * Clorus and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Clorus.
     */
    public Clorus replicate() {
            double babyEnergy = this.energy * repERetained;
            this.energy = this.energy * repERetained;
            return new Clorus(babyEnergy);
    }

    /**
     * Cloruses should obey exactly the following behavioral rules:
     * 1. If there are no empty squares, the Clorus will STAY
     * (even if there are Plips nearby they could attack since plip squares do not count as empty squares).
     * 2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to one,
     *    it will REPLICATE to a random empty square.
     * 4. Otherwise, the Clorus will MOVE to a random empty square.
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {

        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<> ();
        boolean anyPlips = false;
        for (Direction dirKey : neighbors.keySet()) {
            if (neighbors.get(dirKey).name().equals("empty")) {
                emptyNeighbors.add(dirKey);
            }
            if (neighbors.get(dirKey).name().equals("plip")) {
                anyPlips = true;
                plipNeighbors.add(dirKey);
            }
        }

        // Rule 1
        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (anyPlips) {
            Direction ranPlipDir = randomEntry(plipNeighbors);
            return new Action(Action.ActionType.ATTACK, ranPlipDir);
        }

        // Rule 3
        Direction ranDir = randomEntry(emptyNeighbors);
        if (this.energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, ranDir);
        }

        // Rule 4
        return new Action(Action.ActionType.MOVE, ranDir);
    }



}
