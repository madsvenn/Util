package com.leetcode;
import java.util.HashMap;
import java.util.Map;

/*
 * 1805. 字符串中不同整数的数目
 */

class NumDifferentIntegers{


    public static void main(String[] args){
    
        String str = "9";

        System.out.println(numDifferentIntegers(str));
        
    }

    public static int numDifferentIntegers(String word) {

        char[] s = word.toCharArray();
        Map<String,Object> map = new HashMap<>();
        int l=0;
        int r=0;
        int n=s.length;
        while(true){
            
            while(l<n && (s[l]<'0' || s[l]>'9')){
                l++;
            }       
            if(l==n)
               break;  
            r=l;       
            while(r<n && s[r]>='0' && s[r]<='9'){
                r++;
            }
            while(l<r){
                if(s[l]=='0'){
                    l++;
                }else
                    break;     
            }
            map.put(word.substring(l, r), map);
            l=r;

        }
        return map.size();
    }
    
}