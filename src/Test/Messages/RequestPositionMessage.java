package Test.Messages;


import agentsim.agent.BasicAgent;
import dissim.simspace.core.BasicSimStateChange;
import dissim.simspace.core.SimControlException;

public class RequestPositionMessage extends BasicSimStateChange<BasicAgent, Message> {

    public RequestPositionMessage(BasicAgent entity, Message message) throws SimControlException {
        super(entity, 0, message);
    }

    @Override
    protected void transition() throws SimControlException {
        System.out.println(simTime() + " " + getSimEntity().getId() + " Wysyłam RequestPositionMessage. " + getTransitionParams().toString());
    }

}
