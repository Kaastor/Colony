package Test.Messages;


import agentsim.agent.BasicAgent;
import dissim.simspace.core.BasicSimStateChange;
import dissim.simspace.core.SimControlException;

public class ResponsePositionMessage extends BasicSimStateChange<BasicAgent, Message> {

    public ResponsePositionMessage(BasicAgent entity, Message message) throws SimControlException {
        super(entity, 0, message);
    }

    @Override
    protected void transition() throws SimControlException {
        System.out.println(simTime() + " " + getSimEntity().getId() + " Wysyłam ResponsePositionMessage. " + getTransitionParams().toString());
    }
}
