package Test;


import agentsim.environment.BasicEnvironment;
import dissim.simspace.core.BasicSimContext;
import dissim.simspace.core.SimContextInterface;
import dissim.simspace.core.SimControlException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class TestContext extends BasicSimContext implements SimContextInterface {

    public TestContext() {
        super();
    }

    @Override
    public void initContext() {
        System.out.println("initContext ");
        try {

            BasicEnvironment environment= new BasicEnvironment();
            Beliefs beliefs1 = new Beliefs();
            Beliefs beliefs2 = new Beliefs();
            Agent agent1 = new Agent(this, environment, beliefs1);
            Agent agent2 = new Agent(this, environment, beliefs2,   2);
            Agent agent3 = new Agent(this, 2.0, true, environment);

            environment.addAgent(0, 0, agent1);
            environment.addAgent(14, 14, agent2);
            environment.addAgent(5, 5, agent3);
        }catch (SimControlException e){
            e.printStackTrace();
        }
    }

    @Override
    public void stopContext() {

    }
}
