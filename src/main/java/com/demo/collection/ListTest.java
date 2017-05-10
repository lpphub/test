package com.demo.collection;

import java.util.LinkedList;

public class ListTest {
    public static void main(String[] args) {
        LinkedList<Integer> b = new LinkedList<>();
        b.add(21);
        b.add(3);
        System.out.println(b.poll());
        System.out.println(b.size());
    }
}
