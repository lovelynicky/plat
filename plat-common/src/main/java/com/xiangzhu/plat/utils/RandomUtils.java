package com.xiangzhu.plat.utils;

/**
 * Created by liluoqi on 15/4/29.
 */


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 随机数生成器
 */
public class RandomUtils {

    /**
     * 生成三位的随机数
     *
     * @return
     */
    public static String getThreeRandomNumbers() {
        Random random = new Random();
        DecimalFormat decimalFormat = new DecimalFormat("000");
        return decimalFormat.format(random.nextInt(1000));
    }

    /**
     * 生成七位的随机数
     *
     * @return
     */
    public static String getSevenRandomNumbers() {
        Random random = new Random();
        DecimalFormat decimalFormat = new DecimalFormat("0000000");
        return decimalFormat.format(random.nextInt(10000000));
    }

    /**
     * 生成六位的随机数
     *
     * @return
     */
    public static String getSixRandomNumbers() {
        Random random = new Random();
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        return decimalFormat.format(random.nextInt(1000000));
    }

    /**
     * 生成十位的随机数
     *
     * @return
     */
    public static String getTenRandomNumbers() {
        Random random = new Random();
        return String.valueOf(random.nextLong());
    }

    /**
     * 排列组合顺序
     *
     * @param range
     */
    public static List<ArrayList<Integer>> getPermutationAndCombination(int range, int choose) {
        List<ArrayList<Integer>> permutationAndCombinations = new ArrayList<ArrayList<Integer>>();
        List<String> onlyPermutationAndCombinationStrings = new ArrayList<String>();
        while (onlyPermutationAndCombinationStrings.size() < getPermutationAndCombinationLimit(range, choose)) {
            ArrayList<Integer> permutationAndCombination = new ArrayList<Integer>();
            String onlyPermutationAndCombinationString = StringUtils.EMPTY;
            for (int i = 0; i < range; i++) {
                getRandomSequenceNoRepeat(range, permutationAndCombination);
                onlyPermutationAndCombinationString = onlyPermutationAndCombinationString.concat(String.valueOf(permutationAndCombination.get(i)));
            }
            if (onlyPermutationAndCombinationStrings.size() >= 0 && !onlyPermutationAndCombinationStrings.contains(onlyPermutationAndCombinationString)) {
                onlyPermutationAndCombinationStrings.add(onlyPermutationAndCombinationString);
                permutationAndCombinations.add(permutationAndCombination);
            }
        }
        return permutationAndCombinations;
    }

    public static void getRandomSequenceNoRepeat(int range, List<Integer> randomSequenceNoRepeat) {
        if (randomSequenceNoRepeat == null || randomSequenceNoRepeat.size() >= range) return;
        Random random = new Random();
        String rangeString = String.valueOf(range);
        StringBuilder formatStringBuilder = new StringBuilder();
        for (int i = 0; i < rangeString.length(); i++) {
            formatStringBuilder.append(String.format("%s", "0"));
        }
        DecimalFormat decimalFormat = new DecimalFormat(formatStringBuilder.toString());
        String nextString = decimalFormat.format(random.nextInt(range));
        Integer nextSequence = Integer.valueOf(nextString);
        if (randomSequenceNoRepeat.contains(nextSequence)) {
            getRandomSequenceNoRepeat(range, randomSequenceNoRepeat);
        } else {
            randomSequenceNoRepeat.add(nextSequence);
        }
    }

    public static String acquireNewToken(int length) {
        return String.format("%s%s", DateUtils.formatDateToSecondsWithoutDecoration(new Date()), RandomStringUtils.randomAlphanumeric(length));
    }

    public static long getPermutationAndCombinationLimit(int range, int choose) {
        if (range <= 0) {
            return 0;
        }
        if (choose == 0 || choose == range) {
            return 1;
        }
        long total = 1;
        for (int i = 1; i <= range; i++) {
            total = total * i;
        }
        for (int i = 1; i <= choose; i++) {
            total = total / i;
        }
        return total;
    }

    public static void main(String[] args) {
//        List<ArrayList<Integer>> listList = getPermutationAndCombination(4);
//        for (ArrayList<Integer> list : listList) {
//            for (Integer integer : list) {
//                System.out.print(String.format("%s,", integer));
//            }
//            System.out.println("");
//        }
        System.out.println(getPermutationAndCombinationLimit(4, 2));
        System.out.println(acquireNewToken(15));
    }
}
