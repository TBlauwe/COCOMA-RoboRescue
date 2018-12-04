package COCOMA_RoboRescue.module.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import COCOMA_RoboRescue.module.algorithm.State;
import COCOMA_RoboRescue.module.algorithm.Action;


public class Q {

    //| =============================
    //| ========== MEMBERS ==========
    //| =============================
    private HashMap<State, HashMap<Action, Float>> values;


    //| =======================================
    //| ==========  PUBLIC FUNCTIONS ==========
    //| =======================================
    Q(){
        values = new HashMap<State, HashMap<Action, Float>>();
    }

    public void fill(float value){
        for(float[] row : values){
            Arrays.fill(row, value);
        }
    }

    public void fillRow(int row, float value){
        Arrays.fill(values[row], value);
    }

    public void fillColumn(int col, float value){
        for(float[] row: values){
            row[col] = value;
        }
    }

    public float getMaxFromRow(int row){
        float max = values[row][0];
        for(float value : values[row]){
            if (value > max)
                max = value;
        }
        return max;
    }

    public int getMaxIndexFromRow(int row){
        int     index   = 0;
        float   max     = values[row][index];
        float   value;

        for(int i=0; i < values[row].length; i++){
            value = values[row][i];
            if (value > max){
                max = value;
                index = i;
            }
        }
        return index;
    }

    //| =======================================
    //| ========== GETTERS & SETTERS ==========
    //| =======================================
    public float getValuetAt(int row, int col){
        return values[row][col];
    }

    public void setValueAt(float value, int row, int col){
        values[row][col] = value;
    }
}
