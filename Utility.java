package COCOMA_RoboRescue

import java.util.concurrent.ThreadLocalRandom;
import java.util.LinkedList;
import java.util.Queue;


final public class Utility {

    private Utility(){}

    //| =====================================
    //| ==========  RANDOM RELATED ==========
    //| =====================================

    //| ~~~~~~~~~~ INTEGER ~~~~~~~~~~
    public static int randomIntegerInRange(int min, int max, boolean inclusive){
        return ThreadLocalRandom.current().nextInt(min,(inclusive) ? max + 1 : max);
    }

    public static int randomIntegerInRange(int min, int max){
        return randomIntegerInRange(min, max, false);
    }

    public static int randomIntegerInRange(int max){
        return randomIntegerInRange(0, max, false);
    }

    public static int randomIntegerInRange(){
        return randomIntegerInRange(0, 100, false);
    }
}
