package qlpd;

import qlpd.qLearning.Action;
import qlpd.qLearning.QLearning;
import qlpd.qLearning.State;
import rescuecore2.log.Logger;
import rescuecore2.messages.Command;
import rescuecore2.misc.geometry.GeometryTools2D;
import rescuecore2.misc.geometry.Line2D;
import rescuecore2.misc.geometry.Point2D;
import rescuecore2.misc.geometry.Vector2D;
import rescuecore2.standard.entities.*;
import rescuecore2.standard.messages.AKSpeak;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.EntityID;


import java.util.*;

/**
   A sample police force agent.
 */
public class SampleQPoliceForce extends AbstractSampleAgent<PoliceForce> {
    private static final String DISTANCE_KEY = "clear.repair.distance";
    private int distance;
    private QLearning qLearning;
    private Action action;
    private Blockade clearedBlockade;
    private float blockadeClearCost;
    private State previousState;

    @Override
    public String toString() {
        return "Sample police force";
    }

    @Override
    protected void postConnect() {
        super.postConnect();
        model.indexClass(StandardEntityURN.ROAD);
        distance = config.getIntValue(DISTANCE_KEY);
        qLearning = new QLearning();
    }

    private float computeReward(State previousState, Action action, State currentState)
    {
        float reward = 0;
        if(Action.DIG.equals(action))
        {
            if(clearedBlockade == null)
            {
                reward = -1;
            }
            else if(clearedBlockade.getRepairCost() < blockadeClearCost)
            {
                reward = 1f / (1+currentState.getNbOfAgents());
            }
        }
        return reward;
    }

    @Override
    protected void think(int time, ChangeSet changed, Collection<Command> heard) {
        if (time == config.getIntValue(kernel.KernelConstants.IGNORE_AGENT_COMMANDS_KEY)) {
            // Subscribe to channel 1
            sendSubscribe(time, 1);
        }
        model.merge(changed);
        State currentState = getState(heard);
        if(!qLearning.isInitialised())
        {
            qLearning.initialize(currentState);
        }
        if(this.action != null)
        {
            float rew = computeReward(previousState, this.action, currentState);
            System.out.println(rew);
            qLearning.update(currentState, this.action, rew);
        }
        this.action = qLearning.nextAction(currentState);
        if(this.action.equals(Action.DIG))
        {
            this.clear(time);
        }
        else
        {
            this.randomMove(time);
        }
        System.out.println("-----------------------------------------");
        System.out.println(currentState);
        for(Map.Entry<Action, Float> entry : qLearning.getActionsProbabilities(currentState).entrySet())
        {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
        System.out.println("-----------------------------------------");
        this.previousState = currentState;
    }


    private State getState(Collection<Command> heard)
    {
        Blockade blockade = this.getTargetBlockade();
        int nbAgts = 0;
        if (blockade != null) {
            for (Command cmd : heard) {
                AKSpeak clearCmd = (AKSpeak) cmd;
                if (blockade.getID().toString().equals(new String(clearCmd.getContent()))) {
                    nbAgts += 1;
                }
            }
        }
        return new State(blockade!=null, nbAgts);
    }


    private void randomMove(int time)
    {
        sendMove(time, randomWalk());
    }

    private void clear(int time)
    {
        Blockade target = getTargetBlockade();
        this.clearedBlockade = target;
        if (target != null) {
            Logger.info("Clearing blockade " + target);
            sendSpeak(time, 1, ("Clearing " + target).getBytes());
            //sendClear(time, target.getX(), target.getY());
            List<Line2D> lines = GeometryTools2D.pointsToLines(GeometryTools2D.vertexArrayToPoints(target.getApexes()), true);
            double best = Double.MAX_VALUE;
            Point2D bestPoint = null;
            Point2D origin = new Point2D(me().getX(), me().getY());
            for (Line2D next : lines) {
                Point2D closest = GeometryTools2D.getClosestPointOnSegment(next, origin);
                double d = GeometryTools2D.getDistance(origin, closest);
                if (d < best) {
                    best = d;
                    bestPoint = closest;
                }
            }
            Vector2D v = bestPoint.minus(new Point2D(me().getX(), me().getY()));
            v = v.normalised().scale(1000000);
            this.blockadeClearCost = target.getRepairCost();
            sendClear(time, (int)(me().getX() + v.getX()), (int)(me().getY() + v.getY()));
        }
    }

    @Override
    protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum() {
        return EnumSet.of(StandardEntityURN.POLICE_FORCE);
    }


    private Blockade getTargetBlockade() {
        Area location = (Area)location();
        Blockade result = getTargetBlockade(location, distance);
        if (result != null) {
            return result;
        }
        for (EntityID next : location.getNeighbours()) {
            location = (Area)model.getEntity(next);
            result = getTargetBlockade(location, distance);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private Blockade getTargetBlockade(Area area, int maxDistance)
    {
        if (area == null || !area.isBlockadesDefined()) {
            return null;
        }
        List<EntityID> ids = area.getBlockades();
        int x = me().getX();
        int y = me().getY();
        for (EntityID next : ids) {
            Blockade b = (Blockade) model.getEntity(next);
            double d = findDistanceTo(b, x, y);
            if (maxDistance < 0 || d < maxDistance) {
                return b;
            }
        }
        return null;
    }

    private double findDistanceTo(Blockade b, int x, int y) {
        List<Line2D> lines = GeometryTools2D.pointsToLines(GeometryTools2D.vertexArrayToPoints(b.getApexes()), true);
        double best = Double.MAX_VALUE;
        Point2D origin = new Point2D(x, y);
        for (Line2D next : lines) {
            Point2D closest = GeometryTools2D.getClosestPointOnSegment(next, origin);
            double d = GeometryTools2D.getDistance(origin, closest);
            if (d < best)
            {
                best = d;
            }

        }
        return best;
    }
}
