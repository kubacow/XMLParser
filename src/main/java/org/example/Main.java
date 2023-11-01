package org.example;

public class Main {
    public static void main(String[] args) {

        Person.loadToExtent();
        System.out.println(Person.extent);

        System.out.println(Person.find(Person.Type.EXTERNAL, "Jane", "Doe", "+45111333314"));

        Person.create(new Person("WR1", "Wojciech", "Rozalski", "+33123313", "email@gmail.com", "1234567891011", "external"));
        System.out.println(Person.extent);

        Person.create(new Person("HF1", "Henry", "Ford", "+666666666", "email@gmail.com", "1234567891011", "internal"));
        System.out.println(Person.extent);

        Person.remove("WR1");
        System.out.println(Person.extent);

        Person.modify("JD1", Person.Field.PESEL, "1321321");
        System.out.println(Person.extent);

        Person.modify("JD1", Person.Field.EMAIL, "dadwad");
        System.out.println(Person.extent);

        Person.modify("JD1", Person.Field.FIRST_NAME, "Kelly");
        System.out.println(Person.extent);

        Person.saveExtent();
    }
}
