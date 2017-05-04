package com.test;

import java.util.ArrayList;
import java.util.List;

public class StreamTest {
    public static void main(String[] args) {
        List<Person> datas = new ArrayList<>();
        datas.add(new Person("test", "123"));
        datas.add(new Person("test2", "456"));
        datas.add(new Person("test3", "456"));
//        String name = datas.stream().reduce((p1, p2) -> p1.getId().equals("456") ? p1 : p2).get().getName();
        String name = datas.stream().filter(e -> "456".equals(e.getId()))
                .map(Person::getName).findFirst().orElse("");
        System.out.println(name);
    }

    static class Person{
        private String name;
        private String id;

        public Person(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
