package com.xiangzhu.plat.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by liluoqi on 15/6/2.
 */
public class MathUtils {
    private static DecimalFormat decimalFormat = new DecimalFormat("####.####");
    private static final int SCALE = 4;

    public static double divide(BigDecimal before, BigDecimal after) {
        return Double.valueOf(decimalFormat.format(before.divide(after, SCALE, BigDecimal.ROUND_HALF_UP)));
    }

    public static double divide(String before, String after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).divide(new BigDecimal(after), SCALE, BigDecimal.ROUND_HALF_UP)));
    }

    public static double divide(String before, long after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).divide(new BigDecimal(after), SCALE, BigDecimal.ROUND_HALF_UP)));
    }

    public static double divide(long before, long after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).divide(new BigDecimal(after), SCALE, BigDecimal.ROUND_HALF_UP)));
    }

    public static double divide(double before, String after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).divide(new BigDecimal(after), SCALE, BigDecimal.ROUND_HALF_UP)));
    }


    public static double divide(String before, double after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).divide(new BigDecimal(after), SCALE, BigDecimal.ROUND_HALF_UP)));
    }

    public static double divide(double before, double after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).divide(new BigDecimal(after), SCALE, BigDecimal.ROUND_HALF_UP)));
    }

    public static double multiple(int before, int after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).multiply(new BigDecimal(after))));
    }

    public static double multiple(long before, long after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).multiply(new BigDecimal(after))));
    }

    public static double multiple(double before, long after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).multiply(new BigDecimal(after))));
    }

    public static double multiple(double before, double after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).multiply(new BigDecimal(after))));
    }

    public static double multiple(long before, double after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).multiply(new BigDecimal(after))));
    }

    public static boolean greater(double before, double after) {
        return new BigDecimal(before).compareTo(new BigDecimal(after)) > 0;
    }

    public static boolean greaterOrEqual(double before, double after) {
        return new BigDecimal(before).compareTo(new BigDecimal(after)) > 0;
    }

    public static boolean lower(double before, double after) {
        return new BigDecimal(before).compareTo(new BigDecimal(after)) < 0;
    }

    public static boolean lowerOrEqual(double before, double after) {
        return new BigDecimal(before).compareTo(new BigDecimal(after)) < 0;
    }

    public static boolean equals(double before, double after) {
        return new BigDecimal(before).compareTo(new BigDecimal(after)) == 0;
    }

    public static double minus(String before, String after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).subtract(new BigDecimal(after))));
    }

    public static double minus(double before, double after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).subtract(new BigDecimal(after))));
    }

    public static double add(double before, double after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).add(new BigDecimal(after))));
    }

    public static double add(double before, int after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).add(new BigDecimal(after))));
    }

    public static double add(double before, float after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).add(new BigDecimal(after))));
    }

    public static double add(String before, String after) {
        return Double.valueOf(decimalFormat.format(new BigDecimal(before).add(new BigDecimal(after))));
    }

    public static void main(String[] args) {
        System.out.println(divide(405.0000, 3.1));
    }
}

