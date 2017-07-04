package Test;

import agentsim.agent.BasicBelief;

public class Beliefs extends BasicBelief{

    private String message = "Wiadomość przed.";

    public Beliefs() {
        super();
    }

    public void setMessage(String message){
        this.message = message;
        beliefsChanged();
    }

    public String getMessage() {
        return message;
    }
}
