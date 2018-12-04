package COCOMA_RoboRescue.module.algorithm;

import rescuecore2.worldmodel.EntityID;

public class State {
    // ATTRIBUTES
    public final EntityID roadID;
    public final boolean onBlockade;
    public final int nbAgents;

    public State(EntityID roadID, boolean isOnBlockade, int nbAgents) {
        if (nbAgents < 0) {
            throw new AssertionError();
        }
        this.roadID = roadID;
        this.onBlockade = isOnBlockade;
        this.nbAgents = nbAgents;
    }

    public EntityID getRoadID() {
        return this.roadID;
    }

    public boolean isOnBlockade() {
        return this.onBlockade;
    }

    public int getNbOfAgents() {
        return this.nbAgents;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(this.getClass())) {
            State s = (State) obj;
            return s.getRoadID().equals(getRoadID())
                    && s.isOnBlockade() == isOnBlockade()
                    && s.getNbOfAgents() == getNbOfAgents();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getRoadID().hashCode() + (isOnBlockade() ? 1 : 0) << getNbOfAgents(); // Pas top
    }
}
