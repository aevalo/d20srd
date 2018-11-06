package org.d20srd.core;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dice {
  private static final Logger logger = LogManager.getLogger(Dice.class);
  private Random rnd;
  private int nrOfDice;
  private int nrOfSides;
  private int modifier;
  private boolean isPercentile;

  private Dice(boolean isPercentile) {
    logger.traceEntry("%b", isPercentile);
    this.rnd = new Random();
    this.isPercentile = isPercentile;
    if (isPercentile) {
      logger.trace("Setting up d%");
      this.nrOfDice = 2;
      this.nrOfSides = 10;
      this.modifier = 0;
    } else {
      logger.trace("Setting up d6");
      this.nrOfDice = 1;
      this.nrOfSides = 6;
      this.modifier = 0;
    }
    logger.traceExit();
  }

  private Dice(int nrOfDice, int nrOfSides, int modifier) {
    logger.traceEntry("%i, %i, %i", nrOfDice, nrOfSides,modifier);
    this.rnd = new Random();
    this.nrOfDice = nrOfDice;
    this.nrOfSides = nrOfSides;
    this.modifier = modifier;
    this.isPercentile = false;
    logger.traceExit();
  }

  public static Dice generate(String expression) {
    logger.traceEntry(expression);
    try {
      if (Pattern.matches("^d%$", expression)) {
        return new Dice(true);
      } else {
        String regex = "^(?<count>\\d+)?d(?<sides>\\d+)(?<modifier>[+-]\\d+)?$";
        Pattern dicePattern = Pattern.compile(regex);
        Matcher matcher = dicePattern.matcher(expression);
        logger.trace(String.format("Group count: %d", matcher.groupCount()));
        logger.trace(String.format("Matches? %b", matcher.matches()));
        int count = Optional.ofNullable(matcher.group("count"))
          .map(str -> Integer.parseInt(str)).orElse(1);
        if (count < 1) {
          throw new IllegalArgumentException("Number of dice must be greater than 1");
        }
        int sides = Optional.ofNullable(matcher.group("sides"))
          .map(str -> Integer.parseInt(str)).orElse(1);
        if (sides < 1) {
          throw new IllegalArgumentException("Number of sides must be greater than 1");
        }
        int modifier = Optional.ofNullable(matcher.group("modifier"))
          .map(str -> Integer.parseInt(str)).orElse(0);
        if (modifier == Integer.MIN_VALUE) {
          throw new IllegalArgumentException("Modifier unparseable.");
        }
        return logger.traceExit(new Dice(count, sides, modifier));
      }
    }
    catch(PatternSyntaxException pse) {
      logger.error(String.format("Pattern syntax: %s", pse.getMessage()));
    }
    catch(IllegalStateException ise) {
      logger.error(String.format("Illegal state: %s", ise.getMessage()));
    }
    catch(NumberFormatException nfe) {
      logger.error(String.format("Number format: %s", nfe.getMessage()));
    }
    catch(IllegalArgumentException iae) {
      logger.error(String.format("Illegal argument: %s", iae.getMessage()));
    }
    catch(IndexOutOfBoundsException ioob) {
      logger.error(String.format("Index out of bounds: %s", ioob.getMessage()));
    }
    return logger.traceExit("Failed to create Dice", null);
  }

  public int nrOfDice() {
    logger.traceEntry();
    return logger.traceExit(nrOfDice);
  }
  public int nrOfSides() {
    logger.traceEntry();
    return logger.traceExit(nrOfSides);
  }
  public int modifier() {
    logger.traceEntry();
    return logger.traceExit(modifier);
  }
  public boolean isPercentile() {
    logger.traceEntry();
    return logger.traceExit(isPercentile);
  }

  public int roll() {
    try {
      if (isPercentile) {
        int tens = rnd.nextInt(nrOfSides);
        int ones = rnd.nextInt(nrOfSides);
        if (tens == 0 && ones == 0) {
          return 100;
        }
        return tens * 10 + ones;
      } else {
        return rnd.ints(nrOfDice, 1, nrOfSides + 1).sum() + modifier;
      }
    }
    catch(IllegalArgumentException iae) {
    }
    return -1;
  }
}
