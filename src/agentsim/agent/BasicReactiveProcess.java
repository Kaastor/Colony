package agentsim.agent;


import dissim.simspace.core.SimControlException;
import dissim.simspace.process.BasicSimProcess;

public class  BasicReactiveProcess extends BasicSimProcess<BasicAgent, Object> {

    private double reactionTime = 1.0;

    BasicReactiveProcess(BasicAgent entity, int priority) throws SimControlException {
        super(entity, priority);
    }

    BasicReactiveProcess(BasicAgent entity) throws SimControlException {
        super(entity);
    }

    @Override
    public double controlStateTransitions() {
        try {
            getSimEntity().act();
        }
        catch (SimControlException e){
            e.printStackTrace();
        }
        return reactionTime;
    }

    public void setReactionTime(double reactionTime) {
        this.reactionTime = reactionTime;
    }
}
