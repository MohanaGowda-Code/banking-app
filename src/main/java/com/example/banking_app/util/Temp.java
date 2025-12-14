package com.example.banking_app.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Temp {


    public static void main(String[] args) {


        List<Integer> list = Arrays.asList(3, 2, 7, 8, 9, 0);

       long second = list.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .orElse(0);

        System.out.println(second);

    }
}

