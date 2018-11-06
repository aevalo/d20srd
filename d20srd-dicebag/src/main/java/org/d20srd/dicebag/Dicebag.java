package org.d20srd.dicebag;

import org.d20srd.core.Dice;

class Dicebag {
  public static void main(String[] args) {
    Dice percentile = Dice.generate("d%");
    Dice d6 = Dice.generate("d6");
    System.out.println(String.format("Rolling d%%: %d", percentile.roll()));
    System.out.println(String.format("Rolling d6: %d", d6.roll()));
	}
}
