
/**
 * Hash algorithm by Bob Jenkins, 1996.
 *
 * You may use this code any way you wish, private, educational, or commercial.  It's free.
 * See: http://burtleburtle.net/bob/hash/doobs.html
 *
 * Use for hash table lookup, or anything where one collision in 2^^32
 * is acceptable.  Do NOT use for cryptographic purposes.
 *
 * Java port by Gray Watson http://256.com/gray/
 */
public class JenkinsHash {
    private static final long MAX_VALUE = 0xFFFFFFFFL;
    private static final long seed = 0x4E67C6A7;
    private static final long x = 231232;
    private static final long y = 1232;

    /**
     * Do subtraction and turn into 4 bytes.
     */
    private static long subtract(long val, long subtract) {
        return (val - subtract) & MAX_VALUE;
    }

    /**
     * Left shift val by shift bits and turn in 4 bytes.
     */
    private static long xor(long val, long xor) {
        return (val ^ xor) & MAX_VALUE;
    }

    /**
     * Left shift val by shift bits.  Cut down to 4 bytes.
     */
    private long leftShift(long val, int shift) {
        return (val << shift) & MAX_VALUE;
    }

    /**
     * Mix up the values in the hash function.
     */
    private long hashMix(long a, long b, long c) {
        a = subtract(a, b); a = subtract(a, c); a = xor(a, c >> 13);
        b = subtract(b, c); b = subtract(b, a); b = xor(b, leftShift(a, 8));
        c = subtract(c, a); c = subtract(c, b); c = xor(c, (b >> 13));
        a = subtract(a, b); a = subtract(a, c); a = xor(a, (c >> 12));
        b = subtract(b, c); b = subtract(b, a); b = xor(b, leftShift(a, 16));
        c = subtract(c, a); c = subtract(c, b); c = xor(c, (b >> 5));
        a = subtract(a, b); a = subtract(a, c); a = xor(a, (c >> 3));
        b = subtract(b, c); b = subtract(b, a); b = xor(b, leftShift(a, 10));
        c = subtract(c, a); c = subtract(c, b); c = xor(c, (b >> 15));

        return c;
    }

    public int hash(int a){
        long hash = (seed ^ a) & MAX_VALUE;

        hash = hashMix(a, x, hash);
        hash = hashMix(y, a, hash);

        return (int) hash;
    }

    public int hash(int a, int b){
        long hash = (seed ^ a ^ b) & MAX_VALUE;

        hash = hashMix(a, b, hash);
        hash = hashMix(x, a, hash);
        hash = hashMix(b, y, hash);

        return (int) hash;
    }

    public int hash(int a, int b, int c){
        long hash = (seed ^ a ^ b ^ c) & MAX_VALUE;

        hash = hashMix(a, b, hash);
        hash = hashMix(c, x, hash);
        hash = hashMix(y, a, hash);
        hash = hashMix(b, x, hash);
        hash = hashMix(y, c, hash);

        return (int) hash;
    }

    public int hash(int a, int b, int c, int d){
        long hash = (seed ^ a ^ b ^ c ^ d) & MAX_VALUE;

        hash = hashMix(a, b, hash);
        hash = hashMix(c, d, hash);
        hash = hashMix(a, x, hash);
        hash = hashMix(y, b, hash);
        hash = hashMix(c, x, hash);
        hash = hashMix(y, d, hash);

        return (int) hash;
    }

    public int hash(int a, int b, int c, int d, int e){
        long hash = (seed ^ a ^ b ^ c ^ d ^ e) & MAX_VALUE;

        hash = hashMix(a, b, hash);
        hash = hashMix(c, d, hash);
        hash = hashMix(e, x, hash);
        hash = hashMix(y, a, hash);
        hash = hashMix(b, x, hash);
        hash = hashMix(y, c, hash);
        hash = hashMix(d, x, hash);
        hash = hashMix(y, c, hash);

        return (int) hash;
    }
}
