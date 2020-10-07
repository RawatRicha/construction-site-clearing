package com.aconex.simulator;

import com.aconex.simulator.commands.QuitCommand;
import com.aconex.simulator.entities.Bulldozer;
import com.aconex.simulator.entities.Position;
import com.aconex.simulator.entities.Square;
import com.aconex.simulator.entities.enums.Direction;
import com.aconex.simulator.input.CommandParser;
import com.aconex.simulator.input.CommandReader;
import com.aconex.simulator.input.SiteMapReader;
import com.aconex.simulator.processor.CommandProcessor;
import com.aconex.simulator.output.CostCalculator;
import com.aconex.simulator.processor.SiteMapProcessor;

import java.util.List;

public class App {

    private static Square[][] siteMap;

    public static void main(String[] args) {

        System.out.println("Welcome to the Aconex site clearing simulator. This is a map of the site:\n");
        List<String> lines = new SiteMapReader().apply(args[0]);//to give input in the zeroth location 
        siteMap = new SiteMapProcessor().apply(lines);
        Bulldozer bulldozer = new Bulldozer(new Position(0 , 0, Direction.EAST)
        );
        new CommandReader().readCommand()
                .map(new CommandParser())
                .map(command -> new CommandProcessor(siteMap).apply(bulldozer, command))
                .filter(bulldozer1 -> bulldozer1.getLastCommand() instanceof QuitCommand)
                .findFirst().ifPresent(bulldozer1 -> new CostCalculator(siteMap).apply(bulldozer1));
    }
}
