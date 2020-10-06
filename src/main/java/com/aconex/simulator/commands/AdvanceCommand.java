package com.aconex.simulator.commands;


import com.aconex.simulator.entities.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Calculated the list of positions for an Advance command. The starting position for the calculation is always the
 * next square from the current one.
 */
public class AdvanceCommand implements Command{

    private int step; //e.g. step in a 4 is 1, 2, 3, 4

    public AdvanceCommand(int step) {
        this.step = step;
    }

    public List<Position> apply(Position currentPosition) {

        List<Position> path = new ArrayList<>();

        if (currentPosition != null) {

            int row = currentPosition.getX();
            int col = currentPosition.getY();

            switch (currentPosition.getDirection()){
                case WEST:
                    return IntStream.iterate(col - 1, i -> i - 1)
                            .limit(step)
                            .mapToObj(i -> new Position(row, i, currentPosition.getDirection()))
                            .collect(Collectors.toList());
                case EAST:
                    return IntStream.rangeClosed(col + 1, col + step)
                            .map(i -> i++)
                            .mapToObj(i -> new Position(row, i, currentPosition.getDirection()))
                            .collect(Collectors.toList());
                case NORTH:
                    return IntStream.iterate(row - 1, i -> i - 1)
                            .limit(step)
                            .mapToObj(i -> new Position(i, col, currentPosition.getDirection()))
                            .collect(Collectors.toList());
                case SOUTH:
                    return IntStream.rangeClosed(row + 1, row + step)
                            .map(i -> i++)
                            .mapToObj(i -> new Position(i, col, currentPosition.getDirection()))
                            .collect(Collectors.toList());
                default:
                    return path;
            }
        }

        return path;
    }

    public int getStep() {
        return step;
    }

    @Override
    public String toString() {
        return "advance "+ step;
    }
}
