package com.demo.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class ClientTest {
    public static void main(String[] args) {
        HelloService helloService = getProxy(HelloService.class, "127.0.0.1", 8008);
        System.out.println(helloService.sayHello("小王"));
    }

    public static <T> T getProxy(Class<T> interfaceClass, String host, int port) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                (proxy, method, arguments) -> {
                    Socket socket = new Socket(host, port);
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    output.writeUTF(method.getName());
                    output.writeObject(method.getParameterTypes());
                    output.writeObject(arguments);
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                    return input.readObject();
                });
    }

}
