package com.aconex.simulator.commands;


import com.aconex.simulator.entities.Position;

import java.util.List;

/**
 * Commands take the current position and returns the list of positions,
 * based on their respective implementation.
 */
public interface Command {

    List<Position> apply(Position currentPosition); //when we call this apply function in the user side, the class checks for the instance of the command automatically 
}
