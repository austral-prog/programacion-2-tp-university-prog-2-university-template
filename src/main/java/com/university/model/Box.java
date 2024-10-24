package TpUniversity.model;

public class Box<First, Second> {

    private final First first;
    private final Second second;

    public Box(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    public First getFirst() {
        return first;
    }

    public Second getSecond() {
        return second;
    }

}