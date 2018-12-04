package COCOMA_RoboRescue.module.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Matrix {

    //| =============================
    //| ========== MEMBERS ==========
    //| =============================
    private int rows;
    private int cols;
    private float[][] values;


    //| =======================================
    //| ==========  PUBLIC FUNCTIONS ==========
    //| =======================================
    Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        values = new float[rows][cols];
    }

    public void fill(float value){
        for(float[] row : values){
            Arrays.fill(row, value);
        }
    }

    public void fillRow(int row, float value){
        checkCoordinates(row, 0);
        Arrays.fill(values[row], value);
    }

    public void fillColumn(int col, float value){
        checkCoordinates(0, col);
        for(float[] row: values){
            row[col] = value;
        }
    }

    public float getMaxFromRow(int row){
        checkCoordinates(row, 0);
        float max = values[row][0];
        for(float value : values[row]){
            if (value > max)
                max = value;
        }
        return max;
    }

    public int getMaxIndexFromRow(int row){
        checkCoordinates(row, 0);

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
    public int getRows(){ return rows; }
    public int getCols(){ return cols; }

    public float getValuetAt(int row, int col){
        checkCoordinates(row, col);
        return values[row][col];
    }

    public void setValueAt(float value, int row, int col){
        checkCoordinates(row, col);
        values[row][col] = value;
    }


    //| ========================================
    //| ==========  PRIVATE FUNCTIONS ==========
    //| ========================================
    private void checkCoordinates(int x, int y){
        if(x >= 0 && x < rows && y >= 0 && y < cols){
            throw new IllegalArgumentException("Coordinate out of bounds");
        }
    }
}
