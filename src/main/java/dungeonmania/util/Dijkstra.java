package dungeonmania.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import dungeonmania.DungeonMap.DungeonMap;

public class Dijkstra {
    public Position DijkstraPath(DungeonMap dungeonMap, Position position){

        Map<Position, Double> dist = new HashMap<Position, Double>();
        Map<Position, Position> prev  = new HashMap<Position,Position>();

        List<Position> allPositions = new ArrayList<Position>();
        dungeonMap.getMap().forEach((key, value) -> {
            allPositions.add(key);
        }); 

        for (Position p: allPositions){
            dist.put(p, Double.POSITIVE_INFINITY);
            prev.put(p, null);
        }
        dist.put(position, 0.0);
        Queue<Node> queue = new PriorityQueue<>();
        //Queue<Position> queue = new PriorityQueue<>(allPositions);
        queue.add(new Node(position, 0.0));

        while (queue.size() > 0){
            Position u = queue.poll().getPos();

            for (Position v: getCardinallyAdjacentSquares(u)){
                if (!dist.containsKey(v)){
                    continue;
                }
                 //right now, i ignore swamp tiles so 1 movement is 1 cost
                if (dist.get(u) + costCalculation(dungeonMap, u, v) < dist.get(v)){
                    dist.put(v, dist.get(u)+ costCalculation(dungeonMap, u, v));
                    prev.put(v, u);
                }
                
            }
            
        }

        return previous;
    

    }

    public List<Position> getCardinallyAdjacentSquares(Position p) {
        List<Position> caSquares = new ArrayList<Position>();

        caSquares.add(p.translateBy(Direction.UP));
        caSquares.add(p.translateBy(Direction.RIGHT));
        caSquares.add(p.translateBy(Direction.DOWN));
        caSquares.add(p.translateBy(Direction.LEFT));

        return caSquares;
    }

    public double costCalculation(DungeonMap dungeonMap, Position from, Position to){
        //will have to consider swamp tiles later
        return 1;
    }

    
}
