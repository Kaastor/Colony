package agentsim.agent;

import agentsim.environment.BasicEnvironment;
import dissim.broker.IEventBroker;
import dissim.broker.IEventSubscriber;
import dissim.broker.observer.Observable;
import dissim.broker.observer.Observer;
import dissim.config.SimParameters;
import dissim.simspace.core.BasicSimContext;
import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimControlException;


public abstract class BasicAgent extends BasicSimEntity implements Observer {

    private static int idCount;
    private final int id;
    public final IEventBroker communicationBroker;
    private BasicBelief belief;
    private BasicReactiveProcess reactiveProcess;
    private BasicReactiveEvent reactiveEvent;
    private BasicEnvironment environment;
    private boolean oneShot;
    private int priority;

    public BasicAgent(BasicSimContext context, double delayToRun, boolean oneShot, BasicEnvironment environment, BasicBelief belief, int priority) throws SimControlException{
        super(context);
        this.communicationBroker = context.getContextEventBroker();
        this.id = idCount++;
        this.oneShot = oneShot;
        this.priority = priority;
        this.environment = environment;
        this.belief = belief;
        if(belief != null)
            belief.register(BasicAgent.class, this);
        setReactiveProcess(delayToRun);
    }

    public BasicAgent(BasicSimContext context, BasicEnvironment environment, BasicBelief belief, int priority) throws SimControlException {
        this(context, 0, false, environment, belief, priority);
    }
    public BasicAgent(BasicSimContext context, double delayToRun, BasicEnvironment environment, BasicBelief belief, int priority) throws SimControlException {
        this(context, delayToRun, false, environment, belief, priority);
    }
    public BasicAgent(BasicSimContext context, BasicEnvironment environment, BasicBelief belief) throws SimControlException {
        this(context, 0, false, environment, belief, SimParameters.DefaultSimPriority);
    }
    public BasicAgent(BasicSimContext context, double delayToRun, BasicEnvironment environment, BasicBelief belief) throws SimControlException {
        this(context, delayToRun, false, environment, belief, SimParameters.DefaultSimPriority);
    }
    public BasicAgent(BasicSimContext context, BasicEnvironment environment, int priority) throws SimControlException {
        this(context, 0, false, environment, null, priority);
    }
    public BasicAgent(BasicSimContext context, double delayToRun, BasicEnvironment environment, int priority) throws SimControlException {
        this(context, delayToRun, false, environment, null, priority);
    }
    public BasicAgent(BasicSimContext context, BasicEnvironment environment) throws SimControlException {
        this(context, 0, false, environment, null, SimParameters.DefaultSimPriority);
    }
    public BasicAgent(BasicSimContext context, double delayToRun, BasicEnvironment environment) throws SimControlException {
        this(context, delayToRun, false, environment, null, SimParameters.DefaultSimPriority);
    }

    public BasicAgent(BasicSimContext context, double delayToRun, boolean oneShot,  BasicEnvironment environment) throws SimControlException {
        this(context, delayToRun, oneShot, environment, null, SimParameters.DefaultSimPriority);
    }

    private void setReactiveProcess(double delayToRun){
        try {
            if (oneShot)
                reactiveEvent = new BasicReactiveEvent(this, delayToRun, priority);
            else{
                reactiveProcess = new BasicReactiveProcess(this, priority);
                reactiveProcess.start(delayToRun);
            }
        }
        catch (SimControlException e){
            e.printStackTrace();
        }
    }

    public abstract void act() throws SimControlException;

    public abstract void decisionMaker() throws SimControlException;

    @Override
    public void notification(Class aClass, Observable observable){
        try {
            decisionMaker();
        }
        catch (SimControlException e){
            e.printStackTrace();
        }
    }

    public BasicReactiveProcess getReactiveProcess() {
        return reactiveProcess;
    }

    public BasicReactiveEvent getReactiveEvent() {
        return reactiveEvent;
    }

    public IEventBroker getCommunicationBroker() {
        return communicationBroker;
    }

    public void subscribeToMessage(Class messageClass, IEventSubscriber subscriber){
        getCommunicationBroker().subscribe(messageClass, subscriber);
    }

    public int getId() {
        return id;
    }

    public BasicEnvironment getEnvironment() {
        return environment;
    }

    public void setEnvironment(BasicEnvironment environment) {
        this.environment = environment;
    }

    public final boolean interrupt() throws SimControlException {
        if (oneShot)
            return reactiveEvent.interrupt();
        else {
            System.out.println(" interrupt - process " + reactiveProcess.getSimStatus());
            return reactiveProcess.interrupt();//nie zmienia na INTERRUPTED
        }
    }

    protected void onInterruption() throws SimControlException {
    }

    public final boolean terminate() throws SimControlException {
        if (oneShot)
            return reactiveEvent.terminate();
        else
            return reactiveProcess.terminate();
    }

    protected void onTermination() throws SimControlException { }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                '}';
    }
}
