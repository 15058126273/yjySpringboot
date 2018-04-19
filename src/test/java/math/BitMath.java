package math;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-19 17:01
 */
public class BitMath {

    public static void main(String[] args) {
        System.out.println(add(43,56));
        System.out.println(reduce(84,56));
    }

    public static int add(int a, int b) {
        if (b == 0) return a;
        return add(a ^ b, (a & b) << 1);
    }

    public static int reduce(int a, int b) {
        return add(a, -b);
    }
}
