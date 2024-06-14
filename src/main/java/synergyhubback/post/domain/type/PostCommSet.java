package synergyhubback.post.domain.type;

public enum PostCommSet {
    ALLOW_NORMAL(1),
    ALLOW_ANONYMOUS(2),
    ALLOW_BOTH(3),
    ALLOW_NONE(4);

    private final int value;

    PostCommSet(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
