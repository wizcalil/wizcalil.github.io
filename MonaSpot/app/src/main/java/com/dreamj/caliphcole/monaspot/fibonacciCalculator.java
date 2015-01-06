package com.dreamj.caliphcole.monaspot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CaliphCole on 01/03/2015.
 */
public class fibonacciCalculator {

    public static String[] getfibonacci(){
        long[] fibonacciValues = new long[70];
        String[] results = new String[70];
        fibonacciValues[0] = 1;
        fibonacciValues[1] = 1;
        fibonacciValues[2] = 2;
        results[0] = "1";
        results[1] = "1";
        results[2] = "2";

       /* for(int i = 3; i < 70; i ++){
            fibonacciValues[i] = fibonacciValues[i-3] + fibonacciValues[i-2] + fibonacciValues[i-1];
            results[i] = String.valueOf(fibonacciValues[i]);
        }*/
        List<String> where = new ArrayList<String>();
       for (int i = 0 ; i<70; i ++){


           where.add("NewsFeeds Will be Coming Soon");
       }
        where.toArray(results);
        return results;
    }
}
