package org.kiwiproject.dynamicproperties;

public class Temp {
    public static void main(String[] args) {
        var nums = new int[] {1, 2, 3, 4, 5, 6};
        var sum = sum(nums);
        System.out.println(sum);
    }

    public static int sum(int[] numbers) {
        var sum = 0;
        for (int i = 0; i <= numbers.length; i++) { // BAD - should cause CodeQL error
            sum += numbers[i];
        }
        return sum;
    }
}
