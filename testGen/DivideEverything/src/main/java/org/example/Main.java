package org.example;

import java.util.Random;

public class Main {

    public static Random RANDOM_GENERATOR = new Random();

    public static String PATH_TO_FILE = "C:\\HSE\\BI\\2024\\TestGen\\tests\\";

    public static String ANSWER_SUFFIX = ".a";

    public static String genTestName(int testNum, boolean isAnswer)
    {
        StringBuilder testName = new StringBuilder();

        if (testNum < 10)
            testName.append("0");

        testName.append(testNum);

        if (isAnswer)
            testName.append(ANSWER_SUFFIX);

        return testName.toString();
    }

    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static int genSolutionInt(int[] read)
    {
        int ans = read[1];
        for (int j = 2; j < read.length; ++j)
            ans = gcd(ans, read[j]);

        return ans;
    }

    public static void genSolution(int groupStart, int groupEnd)
    {
        for (int i = groupStart; i <= groupEnd; ++i)
        {
            int[] read = FileProcessor.readFile(PATH_TO_FILE + genTestName(i, false));
            int[] result = { genSolutionInt(read) };

            FileProcessor.writeToFile(PATH_TO_FILE + genTestName(i, true), -1, result, "");
        }
    }

    public static void genTests(int groupStart, int groupEnd, int maxNumber, int maxValue)
    {
        for (int i = groupStart; i < groupStart + 5; ++i)
        {
            int[] arr = new int[RANDOM_GENERATOR.nextInt(maxNumber)];
            assert (arr.length < maxNumber);
            if (arr.length == 0)
                arr = new int[1];

            int k = 0;
            while (k < arr.length)
            {
                arr[k] = 1 + Math.abs(RANDOM_GENERATOR.nextInt()) % maxValue;
                ++k;
            }

            FileProcessor.writeToFile(PATH_TO_FILE + genTestName(i, false), arr.length, arr, " ");
        }
        for (int i = groupStart + 5; i <= groupEnd - 1; ++i)
        {
            int[] arr = new int[RANDOM_GENERATOR.nextInt(maxNumber)];
            assert (arr.length < maxNumber);
            if (arr.length == 0)
                arr = new int[1];

            long dynamicValue = RANDOM_GENERATOR.nextInt(maxValue);
            while (dynamicValue == 0)
                dynamicValue = RANDOM_GENERATOR.nextInt(maxValue);

            assert (dynamicValue < maxValue);

            dynamicValue *= (3 + Math.abs(RANDOM_GENERATOR.nextInt()) % 6);
            int k = 0;
            while (k < arr.length && dynamicValue <= 1_000_000_000)
            {
                arr[k] = (int)dynamicValue;
                ++k;
                dynamicValue *= (3 + Math.abs(RANDOM_GENERATOR.nextInt()) % 6);
            }

            while (k < arr.length)
            {
                arr[k] = arr[k - 1];
                ++k;
            }

            FileProcessor.writeToFile(PATH_TO_FILE + genTestName(i, false), arr.length, arr, " ");
        }

        int[] arr = new int[maxNumber];
        arr[0] = maxValue;
        for (int i = 1; i < maxNumber; ++i)
        {
            arr[i] = (i + 1) * maxValue;
            assert (arr[i] <= 1000000000);
        }

        FileProcessor.writeToFile(PATH_TO_FILE + genTestName(groupEnd, false), maxNumber, arr, " ");
    }

    public static void main(String[] args) {
        genTests(1, 20,  1, 100);
        genTests(21, 40, 100, 10_000);
        genTests(41, 60, 10_000, 100_000);
        genTests(61, 80, 100_000, 1_000_000);
        genTests(81, 100, 1_000_000, 10_000_000);
        genSolution(1, 100);
    }
}