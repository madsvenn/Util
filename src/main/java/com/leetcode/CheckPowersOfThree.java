
package com.leetcode;

class CheckPowersOfThree {

    /*
     * 给你一个整数 n ，如果你可以将 n 表示成若干个不同的三的幂之和，请你返回 true ，否则请返回 false 。
     * 
     * 对于一个整数 y ，如果存在整数 x 满足 y == 3x ，我们称这个整数 y 是三的幂。
     */
    public static void main(String[] args) {

        System.out.println(checkPowersOfThree(33));

    }

    public static boolean checkPowersOfThree(int n) {
        if (n % 3 == 1)
            n--;
        while (n % 3 == 0) {
            n = n / 3;
            if (n % 3 != 0)
                n--;
            if (n == 0)
                return true;
            if (n % 3 != 0)
                return false;
        }
        return false;
    }

}
