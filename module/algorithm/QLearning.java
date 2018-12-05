package COCOMA_RoboRescue.module.algorithm;

import COCOMA_RoboRescue.Utility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class QLearning {

    //| =============================
    //| ========== MEMBERS ==========
    //| =============================
    private QValues Q;

    private float alpha;
    private float beta;
    private float gamma;


    //| =======================================
    //| ==========  PUBLIC FUNCTIONS ==========
    //| =======================================
    public QLearning(){
        Q = new QValues();
        alpha = 0.4f;
        beta = 8;
        gamma = 0.9f;
    }

    public void update(State precState, State currentState, Action actionDone, float rew){
        Q.putValue(precState, actionDone, alpha * (rew + gamma * Q.getMaxRewardFrom(currentState) - Q.getValuetAt(precState, actionDone)));
    }

    public Action nextAction(State currentState){
        HashMap<Action, Float> actionsProbability = new HashMap<Action, Float>(Action.values().length);

        // Compute sum
        float sum = 0;
        for (Map.Entry<Action, Float> entry : Q.getRow(currentState).entrySet()) {
            sum += Math.exp(beta * entry.getValue());
        }


        // Compute probability
        float lastProba = 0;
        for (Map.Entry<Action, Float> entry : Q.getRow(currentState).entrySet()) {
            float proba = ((float) Math.exp(beta * entry.getValue()) + lastProba) / sum;
            lastProba = proba;
            actionsProbability.put(entry.getKey(), proba);
        }

        // Get action using proportional probability
        float proba = Utility.generateRandomFloat();
        for (Map.Entry<Action, Float> entry : actionsProbability.entrySet())
        {
            if (proba - entry.getValue() <= 0)
                return entry.getKey();
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
