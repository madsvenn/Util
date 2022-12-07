package com.leetcode;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MinOperations {


    public static void main(String[] args) {

        int []nums1={6,6};
        int []nums2={1};

        System.out.println(minOperations(nums1, nums2));
    }

    public static int minOperations(int[] nums1, int[] nums2) {

        int[] a= new int[7];
        int[] b= new int[7];

        int def = 0;
        for(int x: nums1){
            a[x]++;
            def+=x;
        }
        for(int x: nums2){
            b[x]++;
            def-=x;
        }
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        if(nums1.length>nums2.length?nums1.length>nums2.length*6:nums2.length>nums1.length*6){
            return -1;
        }

        if(def>0){
            return OperationsHelp(b,a,def);
        }
        return OperationsHelp(a,b,-def);
    }

    private static int OperationsHelp(int[] a,int[] b,int def){
        int[] h = new int[7];
        for(int i=1;i<=6;i++){
            h[6-i] += a[i];
            h[i-1] += b[i];
        }
        System.out.println(Arrays.toString(h));
        int tmp=0;
        int i=5;
        while(def>0&&i>0){
            int op = Math.min((def+i-1)/i, h[i]);
            tmp += op;
            def -= op*i;
            i--;
        }
        return tmp;
    }
    
}
