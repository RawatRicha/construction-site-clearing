package com.aconex.simulator.commands;


import com.aconex.simulator.entities.Position;

import java.util.ArrayList;
import java.util.List;

public class LeftCommand implements Command{

    @Override
    public List<Position> apply(Position currentPosition) {

        List<Position> path = new ArrayList<>();

        if (currentPosition != null) {

            currentPosition.setDirection(currentPosition.getDirection().turnLeft());
            path.add(currentPosition);

        }

        return path;
    }

    @Override
    public String toString() {
        return "turn left";
    }
}
