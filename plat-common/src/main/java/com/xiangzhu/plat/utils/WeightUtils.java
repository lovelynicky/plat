package com.xiangzhu.plat.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by liluoqi on 15/5/27.
 */
public class WeightUtils {

    public static double formatGramToKiloGramDouble(double gram) {
        return new BigDecimal(formatGramToKiloGramString(gram)).doubleValue();
    }

    public static String formatGramToKiloGramString(double gram) {
        BigDecimal originGram = new BigDecimal(gram);
        DecimalFormat decimalFormat = new DecimalFormat("####.####");
        return decimalFormat.format(originGram.multiply(new BigDecimal(0.001)));
    }

    public static void main(String[] args) {
        System.out.println(MathUtils.add(formatGramToKiloGramDouble(600), 0.3));
    }
}
