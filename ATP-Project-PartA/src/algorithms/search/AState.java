package algorithms.search;

public abstract class  AState implements Comparable {
    protected double cost;
    protected AState parent;

  public abstract boolean equals(Object o);

    @Override
    public int compareTo(Object o) {
        if ((this.cost>((AState)o).cost)){
            return 1;
        }
        else if ((this.cost<((AState)o).cost)){
            return -1;
        }
        return 0;
    }
}
