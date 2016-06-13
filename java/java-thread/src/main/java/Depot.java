import java.util.LinkedList;
import java.util.List;

/**
 * 带通知的仓库
 */

public class Depot<T> {
    List<ProductFuture<T>> productList = new LinkedList<ProductFuture<T>>();
    int capacity;

    public Depot(int capacity) {
        this.capacity = capacity;
    }

    public synchronized ProductFuture<T> produce(T product) {
        ProductFuture<T> productFuture = null;

        try {
            if (productList.size() > capacity)
                wait();

            //插入头
            productFuture = new ProductFuture<T>(product);
            productList.add(0, productFuture);

            //notify not empty
            notifyAll();
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }

        return productFuture;
    }

    public synchronized List<ProductFuture<T>> consume(int maxCount) {
        List<ProductFuture<T>> consumeList = new LinkedList<ProductFuture<T>>();

        try {
            if (productList.isEmpty())
                wait();

            int count = Math.min(maxCount, productList.size());
            consumeList.addAll(productList.size() - count, productList);

            //notify not full
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return consumeList;
    }
}
