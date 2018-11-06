package org.d20srd.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DiceTests {
  @Test
  void canCreatePercentileDice() {
    Dice d = Dice.generate("d%");
    assertNotNull(d, "Dice object should be valid.");
    assertTrue(d.isPercentile(), "d% should be percentile dice");
    assertEquals(d.nrOfDice(), 2, "d% should have two dice");
    assertEquals(d.nrOfSides(), 10, "d% should have ten sides");
    assertEquals(d.modifier(), 0, "d% should not have modifier");
  }

  @Test
  void canCreateDice() {
    Dice d = Dice.generate("d6");
    assertNotNull(d, "Dice object should be valid.");
    assertFalse(d.isPercentile(), "d6 should not be percentile dice");
    assertEquals(d.nrOfDice(), 1, "d6 should have one dice");
    assertEquals(d.nrOfSides(), 6, "d6 should have six sides");
    assertEquals(d.modifier(), 0, "d6 should not have modifier");
  }

  @Test
  void canCreateMultipleDice() {
    Dice d = Dice.generate("3d10");
    assertNotNull(d, "Dice object should be valid.");
    assertFalse(d.isPercentile(), "3d10 should not be percentile dice");
    assertEquals(d.nrOfDice(), 3, "3d10 should have three dice");
    assertEquals(d.nrOfSides(), 10, "3d10 should have ten sides");
    assertEquals(d.modifier(), 0, "3d10 should not have modifier");
  }

  @Test
  void canCreateDiceWithModifier() {
    Dice d = Dice.generate("d4+2");
    assertNotNull(d, "Dice object should be valid.");
    assertFalse(d.isPercentile(), "d4+2 should not be percentile dice");
    assertEquals(d.nrOfDice(), 1, "d4+2 should have one dice");
    assertEquals(d.nrOfSides(), 4, "d4+2 should have four sides");
    assertEquals(d.modifier(), 2, "d4+2 should have modifier");
  }

  @Test
  void canCreateMultipleDiceWithModifier() {
    Dice d = Dice.generate("2d20-5");
    assertNotNull(d, "Dice object should be valid.");
    assertFalse(d.isPercentile(), "2d20-5 should not be percentile dice");
    assertEquals(d.nrOfDice(), 2, "2d20-5 should have two dice");
    assertEquals(d.nrOfSides(), 20, "2d20-5 should have twenty sides");
    assertEquals(d.modifier(), -5, "2d20-5 should have modifier");
  }

  @Test
  void canNotCreateDiceWithNegativeCount() {
    Dice d = Dice.generate("-2d6");
    assertNull(d, "Dice object should be invalid.");
  }

  @Test
  void canNotCreateDiceWithIllegalCount() {
    Dice d = Dice.generate("ad6");
    assertNull(d, "Dice object should be invalid.");
  }

  @Test
  void canNotCreateDiceWithoutSides() {
    Dice d = Dice.generate("ad+4");
    assertNull(d, "Dice object should be invalid.");
  }

  @Test
  void canNotCreateDiceWithBadModifier() {
    Dice d = Dice.generate("d4*20");
    assertNull(d, "Dice object should be invalid.");
  }
}
