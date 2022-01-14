package io.shodo.kata.dishes;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The objective of the exercise is to measure the signal quality for a channel of a given satellite on a scale of 10, depending on the
 * available dishes.
 * Each deviation of 0.1 with the right satellite weakens the signal by -1. With more than 1 deviation, the signal
 * is lost completely.
 *
 *
 * Used satellite in this exercise :
 * ASTRA (A) : 19.20E
 * HOTBIRD (H) : 13.00E
 * NILESAT (N) : 7.00W
 */
public class DishesTest {

  @Test
  public void RTMShouldBeWellDisplayed() {
    // FORMAT : "id,orientation"
    Dishes dishes = new Dishes("1,7W");
    // FORMAT : "name, SATELLITE"
    assertEquals("|**********|", dishes.signal("RTM, N"));
  }

  @Test
  public void ZDFShouldBeDisplayedBadlyAndEuronewsShouldNotBeDisplayed() {
    Dishes dishes = new Dishes("1,7W", "2,19E");
    assertEquals("No signal !", dishes.signal("Euronews, H"));
    assertEquals("|********..|", dishes.signal("ZDF, A"));
  }

  @Test
  public void shouldFindTheBestQuality() {
    Dishes dishes = new Dishes("1,19E", "2,19.3E");
    assertEquals("|*********.|", dishes.signal("ZDF, A"));
  }

  @Test
  public void EuronewsShouldBeDisplayedIfDishIsMoved() {
    Dishes dishes = new Dishes("1,7W", "2,19E");
    dishes.move("2", "6.7W");
    assertEquals("|***.......|", dishes.signal("Euronews, H"));
    dishes.move("2", "0.6E");
    assertEquals("|*********.|", dishes.signal("Euronews, H"));
  }

  @Test
  public void RTMShouldBeDisplayedIfDishIsMoved() {
    Dishes dishes = new Dishes("1,13E");
    dishes.move("1", "20.5W");
    assertEquals("|*****.....|", dishes.signal("RTM, N"));
  }

}
