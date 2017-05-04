package com.demo.thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道输入/输出
 */
public class Piped {
    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        //管道连接
        out.connect(in);
        new Thread(new Print(in), "PrintThread").start();
        int input = 0;
        try {
            System.out.println("please input:");
            while ((input = System.in.read()) != -1) {
                out.write(input);
            }
        } finally {
            out.close();
            in.close();
        }
    }

    static class Print implements Runnable {
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.println(Thread.currentThread().getName() + ":" + (char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
