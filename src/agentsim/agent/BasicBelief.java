package agentsim.agent;


import dissim.broker.observer.Observable;
import dissim.broker.observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BasicBelief implements Observable {

    private Class simEntityClass;
    private HashMap<String, ArrayList<Observer>> agentAndObserversMap;

    public BasicBelief() {
        this.simEntityClass = BasicAgent.class;
        this.agentAndObserversMap = new HashMap<>();
    }

    @Override
    public void register(Class agentClass, Observer observer) {
        if(agentClass != null) {
            if(BasicAgent.class.isAssignableFrom(agentClass)) {
                ArrayList<Observer> observersList = this.agentAndObserversMap.computeIfAbsent(agentClass.getSimpleName(), k -> new ArrayList<>());
                if(!observersList.contains(observer)) {
                    observersList.add(observer);
                }
            }
        }
    }

    @Override
    public void unregister(Class agentClass, Observer observer) {
        System.out.println(agentAndObserversMap);
        if(agentClass != null) {
            List<Observer> observersList = this.agentAndObserversMap.get(agentClass.getSimpleName());
            if(observersList != null) {
                observersList.remove(observer);
            }
        }
        System.out.println(agentAndObserversMap);
    }

    @Override
    public void notifyObservers(Class agentClass) {
        List<Observer> observersList = this.agentAndObserversMap.get(agentClass.getSimpleName());
        for (Observer observer : observersList) {
            if (observer != null) {
                observer.notification(agentClass, this);
            }
        }
    }

    public void beliefsChanged(Class agentClass){
        notifyObservers(agentClass);
    }

    public void beliefsChanged(){
        notifyObservers(simEntityClass);
    }

    @Override
    public String toString() {
        return "BasicBelief{" +
                "simEntityClass=" + simEntityClass.getSimpleName() +
                ", agentAndObserversMap=" + agentAndObserversMap +
                '}';
    }
}
