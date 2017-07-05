package Test.Messages;


import agentsim.util.Int2D;

public class Message {

    private final int senderAgent;
    private final int receiverAgent;
    private final Int2D goalPosition;

    public Message(int senderAgent, int receiverAgent, Int2D goalPosition) {
        this.senderAgent = senderAgent;
        this.receiverAgent = receiverAgent;
        this.goalPosition = goalPosition;
    }

    public Message(int senderAgent, int receiverAgent) {
        this.senderAgent = senderAgent;
        this.receiverAgent = receiverAgent;
        this.goalPosition = null;
    }

    public int getSenderAgent() {
        return senderAgent;
    }

    public int getReceiverAgent() {
        return receiverAgent;
    }

    public Int2D getGoalPosition() {
        return goalPosition;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderAgent=" + senderAgent +
                ", receiverAgent=" + receiverAgent +
                ", goalPosition=" + goalPosition +
                '}';
    }
}
