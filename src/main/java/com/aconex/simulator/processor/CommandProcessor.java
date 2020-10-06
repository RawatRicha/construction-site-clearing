package com.aconex.simulator.processor;

import com.aconex.simulator.commands.AdvanceCommand;
import com.aconex.simulator.commands.Command;
import com.aconex.simulator.commands.QuitCommand;
import com.aconex.simulator.entities.Bulldozer;
import com.aconex.simulator.entities.Position;
import com.aconex.simulator.entities.Square;
import com.aconex.simulator.entities.enums.Activity;
import com.aconex.simulator.entities.enums.Cost;
import com.aconex.simulator.entities.enums.Type;
import com.aconex.simulator.exception.SimulationException;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Applied commands on the Bulldozer, gets the list of next positions, validates and keeps count of cost-items.
 */
public class CommandProcessor implements BiFunction<Bulldozer, Command, Bulldozer> {

    private Square[][] siteMap;

    public CommandProcessor(Square[][] siteMap) {
        this.siteMap = siteMap;
    }

    @Override
    public Bulldozer apply(Bulldozer bulldozer, Command command){

        bulldozer.getCommandList().add(command);

        if (bulldozer.getPosition() != null && !(command instanceof QuitCommand)) {

            List<Position> path = command.apply(bulldozer.getPosition());//returns respective new coordinates
            System.out.println("Path is....    " +  path);
            
            if (!bulldozer.isPlaced()) { // when this is the first command
            	System.out.println("Inside if 1 ------- Path is....    " +  bulldozer.getPosition());
            	path.add(0, bulldozer.getPosition());//adding (0,0)
            	System.out.println("Inside if 1(i) ------- Path is....    " +  path);
                path.remove(path.size() - 1);//removing the last position
                System.out.println("Inside if 1(ii) ------- Path is....    " +  path);
            }
            if (!path.isEmpty()) { //to check paint-scratch scenario
            	System.out.println("Inside if 2 ------- Path is....    " +  bulldozer.getPosition());
                bulldozer.setPosition(path.get(path.size() - 1));
            }

            if (command instanceof AdvanceCommand && !path.isEmpty()) {//if we are advancing i.e., bulldozer has moved 
            	System.out.println("Inside if 3 ------- Path is....    " +  bulldozer.getPosition());
            		bulldozer.setPlaced(true);
                    try {
                        process(path, bulldozer);
                    } catch (SimulationException e) {
                        bulldozer.getCommandList().add(new QuitCommand(e.getMessage()));
                    }
            }
            updateCostMap(bulldozer, Cost.COMMUNICATION_COST, 1);
        }

        return bulldozer;
    }

    private Bulldozer process(List<Position> positions, Bulldozer bulldozer) {//cost calculated based on type of tree and type of square
        positions.forEach(p -> {
            try {

                Square square = siteMap[p.getX()][p.getY()];

                if (square.getType() == Type.PROTECTED_TREE) {

                    updateCostMap(bulldozer, Cost.DESTRUCTION_OF_PROTECTED_TREE_COST, 1);

                    throw new SimulationException("\nSession ended because there is an attempt to "
                            + "remove a tree that is protected");
                }

                if (square.isCleared()) {

                    updateCostMap(bulldozer, Cost.FUEL_COST, Activity.VISITING_CLEAN_SQUARE.getFuelUsage());

                } else {

                    updateCostMap(bulldozer, Cost.FUEL_COST, square.getType().getRelevantActivity().getFuelUsage());

                    if (square.getType() == Type.TREE && !p.equals(bulldozer.getPosition())) {

                        updateCostMap(bulldozer, Cost.PAINT_DAMAGE_COST, 1);

                    }

                    square.setCleared(true);
                }
            } catch (ArrayIndexOutOfBoundsException e){

                throw new SimulationException("\nSession ended because there is an attempt to navigate "
                        + "beyond the boundaries of the site; ");
            }
        });

        return bulldozer;
    }

    private void updateCostMap(Bulldozer bulldozer, Cost costType, int i) {
        Map<Cost, Integer> costMap = bulldozer.getItemisedCostMap();
        int value = costMap.getOrDefault(costType, 0) + i;
        costMap.put(costType, value);
    }

}
