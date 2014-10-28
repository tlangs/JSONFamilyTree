package com.langsford;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World");
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
