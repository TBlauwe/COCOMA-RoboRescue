package qlpd.qLearning;


public class State {
    // ATTRIBUTES
    public final boolean onBlockade;
    public final int nbAgents;

    public State(boolean isOnBlockade, int nbAgents) {
        if (nbAgents < 0) {
            throw new AssertionError();
        }
        this.onBlockade = isOnBlockade;
        this.nbAgents = nbAgents;
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
            return s.isOnBlockade() == isOnBlockade()
                    && s.getNbOfAgents() == getNbOfAgents();
        }
        return false;
    }

    public String toString()
    {
        return Boolean.toString(this.onBlockade) + "-" + this.nbAgents;
    }

    @Override
    public int hashCode() {
        return (isOnBlockade() ? 1 : 0) << getNbOfAgents(); // Pas top
    }
}