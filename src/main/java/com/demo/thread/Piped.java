package com.demo.thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 管道输入/输出
 */
public class Piped {

    public static void main(String[] args) throws Exception {
//        ioPipe();
        nioPipe();
    }

    public static void ioPipe() throws IOException {
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

    public static void nioPipe() {
        Pipe pipe = null;
        ExecutorService exec = Executors.newFixedThreadPool(2);
        try {
            pipe = Pipe.open();
            final Pipe pipeTemp = pipe;
            exec.submit(() -> {
                Pipe.SinkChannel sinkChannel = pipeTemp.sink();//向通道中写数据
                while (true) {
                    TimeUnit.SECONDS.sleep(1);
                    String newData = "Pipe Test At Time " + System.currentTimeMillis();
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    buf.clear();
                    buf.put(newData.getBytes());
                    buf.flip();
                    while (buf.hasRemaining()) {
                        System.out.println(Thread.currentThread().getName() + ": " + buf);
                        sinkChannel.write(buf);
                    }
                }
            });

            exec.submit(() -> {
                Pipe.SourceChannel sourceChannel = pipeTemp.source();//向通道中读数据
                while (true) {
                    TimeUnit.SECONDS.sleep(1);
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    buf.clear();
                    int bytesRead = sourceChannel.read(buf);
                    while (bytesRead > 0) {
                        buf.flip();
                        byte b[] = new byte[bytesRead];
                        int i = 0;
                        while (buf.hasRemaining()) {
                            b[i] = buf.get();
                            i++;
                        }
                        String s = new String(b);
                        System.out.println(Thread.currentThread().getName() + ": " + s);
                        bytesRead = sourceChannel.read(buf);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exec.shutdown();
        }
    }
}
