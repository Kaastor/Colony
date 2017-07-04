package Test;


import dissim.simspace.core.SimControlException;
import dissim.simspace.core.SimModel;

public class AppTest {
    public static void main(String[] args) {
        try {
            SimModel.getInstance().ASTRONOMICALSimulation();
            SimModel.getInstance().startSimulation();

        } catch (SimControlException var5) {
            var5.printStackTrace();
        }
    }
}
