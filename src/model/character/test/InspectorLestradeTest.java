package model.character.test;

import model.character.implementation.InspectorLestrade;
import model.game.Board;
import model.tile.Tile;

import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sarah on 2018-11-01.
 */
public class InspectorLestradeTest {

    String[] design = {""};

    Board board = new Board(design);

    InspectorLestrade lestrade = new InspectorLestrade();

    @Test
    public void requiredValuesForAbility() throws Exception {

    }

    @Test
    public void hasToDoAbility() throws Exception {
        boolean expected = lestrade.hasToDoAbility();
        assertEquals(expected, true);

    }

    @Test
    public void canDoAbilityBefore() throws Exception {
        boolean expected = lestrade.canDoAbilityBefore();
        assertEquals(expected, true);
    }

    @Test
    public void canDoAbilityDuring() throws Exception {
        boolean expected = lestrade.canDoAbilityDuring();
        assertEquals(expected, false);
    }

    @Test
    public void canDoAbilityAfter() throws Exception {
        boolean expected = lestrade.canDoAbilityAfter();
        assertEquals(expected, true);
    }

    @Test
    public void ability() throws Exception {
        Tile[] arr = null;
        boolean expected = lestrade.ability(arr);
        assertEquals(expected, true);

    }

    @Test
    public void canMove() throws Exception {

    }

    @Test
    public void canInteract() throws Exception {

    }

    @Test
    public void setLit() throws Exception {

    }

    @Test
    public void getLit() throws Exception {

    }

    @Test
    public void setSuspect() throws Exception {

    }

    @Test
    public void getSuspect() throws Exception {

    }

    @Test
    public void getLocation() throws Exception {

    }

    @Test
    public void setLocation() throws Exception {

    }

}