package org.blab.mde.core.test;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TestLab {
    @Test
    public void test1() {
        Integer[] arr = new Integer[]{3, 30, 34, 5, 9};
        assertEquals(generate(arr), "9534330");
    }

    public String generate(Integer[] numbers) {
        Arrays.sort(numbers, (a, b) -> {
            String ab = String.format("%d%d", a, b);
            String ba = String.format("%d%d", b, a);

            return ba.compareTo(ab);
        });

        StringBuilder largestNumber = new StringBuilder();
        for (Integer number : numbers) {
            largestNumber.append(number);
        }

        return largestNumber.toString();
    }
}
