package com.yjy.test.game.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ArithmeticUtils {
    private ArithmeticUtils() {
    }

    public static int dividend(int dividend) {
        return dividend == 0 ? 1 : dividend;
    }

    public static long dividend(long dividend) {
        return dividend == 0 ? 1 : dividend;
    }

    public static BigDecimal to3Point(BigDecimal decimal) throws Exception {
        DecimalFormat df = new DecimalFormat("#.000");
        String format = df.format(decimal);
        return BigDecimal.valueOf(Double.valueOf(format));
    }

    public static void main(String[] args) throws Exception {
        BigDecimal to3Point = ArithmeticUtils.to3Point(BigDecimal.valueOf(3.00365));
        System.out.println(to3Point);
    }
}
