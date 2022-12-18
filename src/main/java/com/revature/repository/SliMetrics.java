package com.revature.repository;

import java.io.IOException;

public class SliMetrics {

    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("/Users/harolrojas/P0-Template/logs/sli.sh");
        try {
            Process p = pb.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
