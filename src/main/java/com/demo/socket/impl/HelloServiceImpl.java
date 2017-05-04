package com.demo.socket.impl;

import com.demo.socket.HelloService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        System.out.println("hello " + name);
        return "hello " + name;
    }

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8008);
        while (true) {
            final Socket socket = server.accept();
            new Thread(() -> {
                try {
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                    String methodName = input.readUTF();
                    Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                    Object[] arguments = (Object[]) input.readObject();
                    HelloService service = new HelloServiceImpl();
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    Method method = service.getClass().getMethod(methodName, parameterTypes);
                    Object result = method.invoke(service, arguments);
                    output.writeObject(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
