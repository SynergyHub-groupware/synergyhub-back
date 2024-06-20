package synergyhubback.post.domain.type;

import lombok.Getter;

@Getter
public enum PostCommSet {
    ALLOW_NORMAL(0),
    ALLOW_ANONYMOUS(1),
    ALLOW_BOTH(2),
    ALLOW_NONE(3);

    private final int value;

    PostCommSet(int value) {
        this.value = value;
    }

    public static PostCommSet fromValue(int value) {
        for (PostCommSet set : PostCommSet.values()) {
            if (set.value == value) {
                return set;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
