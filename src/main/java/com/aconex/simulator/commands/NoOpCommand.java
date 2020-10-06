package com.aconex.simulator.commands;


import com.aconex.simulator.entities.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * NoOpCommand is used to handle unknown user inputs.
 */
public class NoOpCommand implements Command {

  private final String commandString;

  /**
   * Handles bad request
   * @param commandString
   */
  public NoOpCommand(String commandString) {
    this.commandString = commandString;
  }

  @Override
  public List<Position> apply(Position currentPosition) {
    System.out.println(commandString + " is not a known Command!");

    List<Position> path = new ArrayList<>();

    if (currentPosition != null) {

      path.add(currentPosition);

    }
    return path;
  }

  @Override
  public String toString() {
    return null;
  }
}
