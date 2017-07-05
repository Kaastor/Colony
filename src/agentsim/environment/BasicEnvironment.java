package agentsim.environment;


import agentsim.agent.BasicAgent;
import agentsim.util.Int2D;

import java.util.HashMap;
import java.util.Map;

public class BasicEnvironment {

    private static final int INITIAL_ENV_WIDTH = 15;
    private static final int INITIAL_ENV_HEIGHT = 15;
    private final int width;
    private final int height;
    private final BasicAgent[][] field;
    private final Map<BasicAgent, Int2D> agentsWithPosition;

    public BasicEnvironment() {
        this(INITIAL_ENV_WIDTH, INITIAL_ENV_HEIGHT);
    }

    public BasicEnvironment(int width, int height) {
        this.field = new BasicAgent[width][height];
        this.width = width;
        this.height = height;
        agentsWithPosition = new HashMap<>();

        initializeFields();
    }

    private void initializeFields(){
        for(int x = 0 ; x < width ; x++){
            for(int y = 0 ; y < height ; y++){
                field[x][y] = null;
            }
        }
    }

    public void addAgent(int x, int y, BasicAgent agent){
        field[x][y] = agent;
        agentsWithPosition.put(agent, new Int2D(x, y));
    }

    public void removeAgent(BasicAgent agent) {
        Int2D agentPosition = agentsWithPosition.get(agent);
        agentsWithPosition.remove(agent);
        field[agentPosition.getX()][agentPosition.getY()] = null;
    }

    public void clearAgents(){
        for(int x = 0 ; x < width ; x++){
            for(int y = 0 ; y < height ; y++){
                if(field[x][y]!=null)
                    field[x][y] = null;
            }
        }
        agentsWithPosition.clear();
    }

    public BasicAgent getAgent(int x, int y){
        return field[x][y];
    }

    public Int2D getAgentPosition(BasicAgent agent){
        return agentsWithPosition.get(agent);
    }

    public boolean setAgentPosition(Int2D newPosition, BasicAgent agent){
        if(agent == null) return false;
        if(newPosition == null) return false;
        if(!positionInBounds(newPosition)) return false;

        Int2D position = agentsWithPosition.get(agent);
        if(position != null) {
            if (position.equals(newPosition)) return true;
            else {
                field[position.getX()][position.getX()] = null;
                agentsWithPosition.remove(agent);
                agentsWithPosition.put(agent, newPosition);
                field[newPosition.getX()][newPosition.getY()] = agent;
            }
        }
        return true;
    }

    public int distanceBetween(Int2D position1, Int2D position2){
        return (int)Math.hypot(position1.getX()-position2.getX(), position1.getY()-position2.getY());
    }

    private boolean positionInBounds(Int2D position){
        return position.getX() >=0 && position.getX() < width &&
                position.getY() >=0 && position.getY() < height;
    }

    private boolean positionInBounds(int x, int y){
        return x >=0 && x < width &&
                y >=0 && y < height;
    }
}
