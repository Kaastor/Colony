package Test;

import Test.Messages.BasicMessage;
import agentsim.agent.BasicAgent;
import agentsim.environment.BasicEnvironment;
import agentsim.util.Int2D;
import Test.Messages.Message;
import Test.Messages.RequestPositionMessage;
import Test.Messages.ResponsePositionMessage;
import agentsim.util.MutableInt2D;
import dissim.broker.IEvent;
import dissim.broker.IEventFilter;
import dissim.broker.IEventPublisher;
import dissim.broker.IEventSubscriber;
import dissim.random.SimGenerator;
import dissim.simspace.core.SimControlException;

import java.util.List;


public class Agent extends BasicAgent implements IEventFilter, IEventSubscriber {

    private Beliefs beliefs;

    public Agent(TestContext testContext, BasicEnvironment environment, Beliefs beliefs, int priority) throws SimControlException {
        super(testContext, environment, beliefs, priority);
        this.beliefs = beliefs;
        initialize();
    }

    public Agent(TestContext testContext,BasicEnvironment environment, Beliefs beliefs) throws SimControlException {
        super(testContext, environment, beliefs);
        this.beliefs = beliefs;
        initialize();
    }

    public Agent(TestContext testContext, double delayToRun, boolean oneShot, BasicEnvironment environment) throws SimControlException {
        super(testContext, delayToRun, oneShot, environment);
        initialize();
    }

    private void initialize(){
        subscribeToMessage(RequestPositionMessage.class, this);
        subscribeToMessage(ResponsePositionMessage.class, this);
    }
    @Override
    public void act() throws SimControlException {
        System.out.println(simTime() + " " + getId() + " agent Position: " + getEnvironment().getAgentPosition(this));

        if(getId() == 2)
            System.out.println(simTime() + " " + getId() + " Jestem trzeci, oneShot + delay");

        randomWalk();
        if(simTime() == 1 && getId() == 0) {
            System.out.println(simTime() + " " + getId() + " Beliefs się zmieni! " + beliefs.getMessage());
            beliefs.setMessage("Wiadomość po.");
        }
        if(simTime() == 1 && getId() == 1) {
            System.out.println(simTime() + " " + getId() + " Beliefs się zmieni! " + beliefs.getMessage());
            beliefs.setMessage("Wiadomość po2.");
        }

        if(simTime() == 3 && getId() == 0)
            new RequestPositionMessage(this, new Message(this.getId(), 1));

        if(simTime() == 5)
            this.interrupt();

        if(simTime() == 6)
            this.terminate();
    }

    @Override
    public void decisionMaker() throws SimControlException {
        System.out.println(simTime() + " " + getId() + " Beliefs się zmieniło! Powinienem sprawdzić czy moja decyzja jest aktualna... " + beliefs.getMessage());
    }

    @Override //roznice
    public void reflect(IEvent iEvent) {

    }

    @Override
    public void reflect(IEvent iEvent, IEventPublisher iEventPublisher) {
        int senderId, receiverId;
        if(iEvent instanceof RequestPositionMessage){
            receiverId = ((RequestPositionMessage)iEvent).getTransitionParams().getReceiverAgent();
            if(receiverId == getId()) {
                senderId = ((RequestPositionMessage) iEvent).getTransitionParams().getSenderAgent();
                Int2D goalPosition = new Int2D(0,0);
                System.out.println(simTime() + " " + getId() + " Go there AgentId: " + senderId +" -> " +goalPosition + " (wysyłam wiadomość zwrotną)");
                try{
                    new ResponsePositionMessage(this, new Message(getId(), senderId, goalPosition));
                }
                catch (SimControlException e){
                    e.printStackTrace();
                }
            }
        }
        if(iEvent instanceof ResponsePositionMessage){
            receiverId = ((ResponsePositionMessage)iEvent).getTransitionParams().getReceiverAgent();
            if(receiverId == getId()) {
                senderId = ((ResponsePositionMessage) iEvent).getTransitionParams().getSenderAgent();
                Int2D goalPosition = ((ResponsePositionMessage) iEvent).getTransitionParams().getGoalPosition();
                System.out.println(simTime() + " " + getId() + " Thanks for position agent: " + senderId + "!" + " I'm going there -> " + goalPosition);
                teleportTo(goalPosition);
            }
        }
    }

    @Override
    public List<IEventSubscriber> filter(IEvent iEvent) {
       //TODO filter wiadomości
        return null;
    }

    private void randomWalk(){
        Int2D positionInt2D = getEnvironment().getAgentPosition(this);
        SimGenerator generator = new SimGenerator();
        int x = generator.uniformInt(-1, 2);
        int y = generator.uniformInt(-1, 2);
        MutableInt2D newPosition = new MutableInt2D(positionInt2D);
        newPosition.moveByVector(x,y);
        getEnvironment().setAgentPosition(new Int2D(newPosition), this);
    }

    private void teleportTo(Int2D position){
        getEnvironment().setAgentPosition(position, this);
    }

    @Override
    protected void onInterruption() throws SimControlException {
        System.out.println(simTime() + " " + getId() + " onInterruption");
    }

    @Override
    protected void onTermination() throws SimControlException {
        System.out.println(simTime() + " " + getId() + " onTermination");
    }
}
