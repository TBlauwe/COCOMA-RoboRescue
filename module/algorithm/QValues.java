package COCOMA_RoboRescue.module.algorithm;

import java.util.*;

import COCOMA_RoboRescue.module.algorithm.State;
import COCOMA_RoboRescue.module.algorithm.Action;


public class QValues {

    //| =============================
    //| ========== MEMBERS ==========
    //| =============================
    private HashMap<State, HashMap<Action, Float>> values;


    //| =======================================
    //| ==========  PUBLIC FUNCTIONS ==========
    //| =======================================
    QValues(){
        values = new HashMap<State, HashMap<Action, Float>>();
    }

    public Map.Entry<Action, Float> getMaxEntryFrom(State state){
        if (!values.containsKey(state)){
            throw new IllegalArgumentException("QValues does not have state " + state.toString());
        }
        Map.Entry<Action, Float> maxEntry = null;
        for(Map.Entry<Action, Float> entry : values.get(state).entrySet()){
            if (maxEntry == null || entry.getValue() > maxEntry.getValue())
                maxEntry = entry;
        }
        return maxEntry;
    }

    public float getMaxRewardFrom(State state){
        return getMaxEntryFrom(state).getValue();
    }

    public Action getMaxActionFrom(State state){
        return getMaxEntryFrom(state).getKey();
    }


    //| =======================================
    //| ========== GETTERS & SETTERS ==========
    //| =======================================

    // Add or set
    public void putValue(State state, Action action, float value){
        if (values.containsKey(state)){
            setValueAt(state, action, value);
        }else{
            HashMap<Action, Float> entry = new HashMap<Action, Float>(Action.values().length);
            entry.put(action, value);
            values.put(state, entry);
        }
    }

    public float getValuetAt(State state, Action action){
        if (!(values.containsKey(state) && values.get(state).containsKey(action)))
            throw new IllegalArgumentException("QValues does not have state or action");
        return values.get(state).get(action);
    }

    public void setValueAt(State state, Action action, float value){
        if (!(values.containsKey(state) && values.get(state).containsKey(action)))
            throw new IllegalArgumentException("QValues does not have state or action");
        HashMap<Action, Float> entry = values.get(state);
        entry.put(action, value);
        values.put(state, entry);
    }

    public HashMap<Action, Float> getRow(State state){
        if (!values.containsKey(state))
            throw new IllegalArgumentException("QValues does not have state");
        return values.get(state);
    }
}
