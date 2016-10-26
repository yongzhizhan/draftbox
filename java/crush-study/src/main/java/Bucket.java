import java.nio.ByteBuffer;
import java.util.List;

/**
 * bucket,可以是device,也可以是机架,机房
 */
public abstract class Bucket {
    private final BucketType type;
    private final int id;

    Bucket(int id, BucketType type) {
        this.id = id;
        this.type = type;
    }

    public BucketType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public abstract void addItem(Bucket vBucket);
    public abstract void removeItem(Bucket vBucket);

    public abstract List<Bucket> getItems();
}