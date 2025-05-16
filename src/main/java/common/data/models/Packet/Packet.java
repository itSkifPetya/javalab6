package common.data.models.Packet;

public class Packet<F, S> {
    private F first;
    private S second;

    public Packet(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void clear() {
        first = null;
        second = null;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public void update(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
