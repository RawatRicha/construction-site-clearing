package com.aconex.simulator.commands;


import com.aconex.simulator.entities.Position;

import java.util.ArrayList;
import java.util.List;

public class RightCommand implements Command {

    @Override
    public List<Position> apply(Position currentPosition) {

        List<Position> path = new ArrayList<>();

        if (currentPosition != null) {

            currentPosition.setDirection(currentPosition.getDirection().turnRight());
            path.add(currentPosition);

        }

        return path;
    }

    @Override
    public String toString() {
        return "turn right";
    }
}
