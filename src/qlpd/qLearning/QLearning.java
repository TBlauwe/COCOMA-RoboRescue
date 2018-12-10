package qlpd.qLearning;


import java.util.*;

public class QLearning {

    //| =============================
    //| ========== MEMBERS ==========
    //| =============================
    private QValues Q;

    private float alpha;
    private float beta;
    private float gamma;

    private State precState;


    //| =======================================
    //| ==========  PUBLIC FUNCTIONS ==========
    //| =======================================
    public QLearning(){
        Q = new QValues();
        alpha = 0.4f;
        beta = 8;
        gamma = 0.9f;
    }

    public boolean isInitialised()
    {
        return this.precState != null;
    }

    // Must be cll
    public void initialize(State state){
        precState = state;
        this.Q.addEntry(precState);
    }

    public void update(State currentState, Action actionDone, float rew){
        if(precState==null)
            throw new UnknownError("initialize() must be called, in order to set precState");
        Q.addEntry(currentState);
        Q.putValue(precState, actionDone, alpha * (rew + gamma * Q.getMaxRewardFrom(currentState) - Q.getValuetAt(precState, actionDone)));
        this.precState = currentState;
    }

    public Map<Action, Float> getActionsProbabilities(State currentState)
    {
        HashMap<Action, Float> result = new HashMap<>(Action.values().length);
        float sum = 0;
        for(Map.Entry<Action, Float> entry : Q.getRow(currentState).entrySet())
        {
            float probability = (float) Math.exp(beta * entry.getValue());
            sum += probability;
            result.put(entry.getKey(), probability);
        }
        for(Map.Entry<Action, Float> entry : result.entrySet())
        {
            entry.setValue(entry.getValue()/sum);
        }
        return result;
    }

    public Action nextAction(State currentState){
        Map<Action, Float> actionsProbability = this.getActionsProbabilities(currentState);

        // Get action using proportional probability
        float proba = new Random().nextFloat();
        // Compute probability
        float cumulativeProbability = 0;
        for (Map.Entry<Action, Float> entry : actionsProbability.entrySet())
        {
            cumulativeProbability += entry.getValue();
            if (proba <= cumulativeProbability)
            {
                return entry.getKey();
            }
        }
        assert false; // Should never reach this point;
        return null;
    }

    //| =======================================
    //| ========== GETTERS & SETTERS ==========
    //| =======================================
    public QValues getQ(){ return Q; }


    //| ========================================
    //| ==========  PRIVATE FUNCTIONS ==========
    //| ========================================
    private boolean goalReached(){
        // TODO
        throw new UnsupportedOperationException("To implement");
    }
}
