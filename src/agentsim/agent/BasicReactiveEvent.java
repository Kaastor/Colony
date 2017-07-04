package agentsim.agent;


import dissim.simspace.core.BasicSimStateChange;
import dissim.simspace.core.SimControlException;

public class BasicReactiveEvent extends BasicSimStateChange<BasicAgent, Object> {

    BasicReactiveEvent(BasicAgent entity, double delayToRun, int priority ) throws SimControlException {
        super(entity, delayToRun, priority);
    }

    BasicReactiveEvent(BasicAgent entity, int priority) throws SimControlException {
        super(entity, priority);
    }

    BasicReactiveEvent(BasicAgent entity) throws SimControlException {
        super(entity);
    }

    @Override
    protected void transition() throws SimControlException {
        try {
            getSimEntity().act();
        } catch (SimControlException e) {
            e.printStackTrace();
        }
    }
}
