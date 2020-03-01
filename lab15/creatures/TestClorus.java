package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Test the Clorus class.
 *  @author  Atlantic
 */

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus cTest = new Clorus(1);
        assertEquals(1, cTest.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), cTest.color());
        cTest.move();
        assertEquals(0.97, cTest.energy(), 0.01);
        cTest.stay();
        assertEquals(0.98, cTest.energy(), 0.01);
        cTest.move();
        assertEquals(0.95, cTest.energy(), 0.01);
        cTest.stay();
        assertEquals(0.96, cTest.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus cTest = new Clorus(2);
        Clorus cBaby = cTest.replicate();
        assertEquals(1, cTest.energy(), 0.01);
        assertEquals(1, cBaby.energy(), 0.01);
    }

    @Test
    public void testAttack() {
        Clorus cTest = new Clorus(1);
        Plip pTest = new Plip(0.5);
        cTest.attack(pTest);
        assertEquals(1.5, cTest.energy(), 0.01);
    }

    @Test
    public void testActionChoose() {

        // rule 1: no empty squares, STAY
        Clorus cTest = new Clorus(2.1);

        HashMap<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = cTest.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);

        // rule 2: Otherwise, if any Plips are seen,
        // the Clorus will ATTACK one of them randomly.
        Clorus cTest2 = new Clorus(2.1);

        HashMap<Direction, Occupant> surrounded2 = new HashMap<>();
        surrounded2.put(Direction.TOP, new Empty());
        surrounded2.put(Direction.BOTTOM, new Impassible());
        surrounded2.put(Direction.LEFT, new Impassible());
        surrounded2.put(Direction.RIGHT, new Plip(0.8));

        Action actual2 = cTest2.chooseAction(surrounded2);
        Action expected2 = new Action(Action.ActionType.ATTACK, Direction.RIGHT);
        assertEquals(expected2, actual2);

        // rule 3: Otherwise, if the Clorus has energy greater than or equal to one,
        // it will REPLICATE to a random empty square.
        Clorus cTest3 = new Clorus(1);

        HashMap<Direction, Occupant> surrounded3 = new HashMap<>();
        surrounded3.put(Direction.TOP, new Empty());
        surrounded3.put(Direction.BOTTOM, new Impassible());
        surrounded3.put(Direction.LEFT, new Impassible());
        surrounded3.put(Direction.RIGHT, new Impassible());

        Action actual3 = cTest3.chooseAction(surrounded3);
        Action expected3 = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        assertEquals(expected3, actual3);

        //rule 4: Otherwise, the Clorus will MOVE to a random empty square.
        Clorus cTest4 = new Clorus(0.01);

        HashMap<Direction, Occupant> surrounded4 = new HashMap<>();
        surrounded4.put(Direction.TOP, new Empty());
        surrounded4.put(Direction.BOTTOM, new Impassible());
        surrounded4.put(Direction.LEFT, new Impassible());
        surrounded4.put(Direction.RIGHT, new Impassible());

        Action actual4 = cTest4.chooseAction(surrounded4);
        Action expected4 = new Action(Action.ActionType.MOVE, Direction.TOP);
        assertEquals(expected4, actual4);


    }



}
