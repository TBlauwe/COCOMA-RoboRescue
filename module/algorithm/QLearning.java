package COCOMA_RoboRescue.module.algorithm;

import java.util.LinkedList;
import java.util.Queue;

public class QLearning {

    //| =============================
    //| ========== MEMBERS ==========
    //| =============================
    private Matrix Q;
    private Matrix R;

    private float gamma;


    //| =======================================
    //| ==========  PUBLIC FUNCTIONS ==========
    //| =======================================
    public QLearning(){
        Q = new Matrix(0, 0);
        R = new Matrix(Q.getRows(), Q.getCols());
    }

    public void compute(int horizon){

        // Initialization
        // --------------
        gamma = 0;
        // TODO ~ Set environment rewards
        Q.fill(0);

        int state = randomState();
        int nextState;
        int action;

        // Computation
        // --------------
        for (int i=0; i < horizon; i++){
            do{
                action = randomActionFrom(state);
                nextState = getNextState(state, action);
                Q.setValueAt(R.getValuetAt(state, action) + gamma * Q.getMaxFromRow(nextState), state, action);
                state = nextState;
            }while(!goalReached());
        }

    }

    public Queue<Integer> getBestActionsFromTo(int initialState, int goalState){
        Queue<Integer> fifo = new LinkedList<Integer>();

        int state = initialState;
        int action;

        while (state != goalState){
            action = Q.getMaxIndexFromRow(state);
            fifo.add (action);
            state = getNextState(state, action);
        }

        return  fifo;
    }



    //| =======================================
    //| ========== GETTERS & SETTERS ==========
    //| =======================================
    public Matrix getQ(){ return Q; }
    public Matrix getR(){ return R; }


    //| ========================================
    //| ==========  PRIVATE FUNCTIONS ==========
    //| ========================================
    private boolean goalReached(){
        // TODO
        throw new UnsupportedOperationException("To implement");
    }

    private int getNextState(int state, int action){
        // TODO
        throw new UnsupportedOperationException("To implement");
    }

    private int randomState(){
        return Utility.randomIntegerInRange(Q.getRows());
    }

    private int randomActionFrom(int state){
        // TODO ~ Return possible action based on state-transition graph
        throw new UnsupportedOperationException("To implement");
    }
}
