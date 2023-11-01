package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString

public class Person implements Serializable {

    public static List<Person> extent = new ArrayList<>();
    enum Type {EXTERNAL, INTERNAL}
    enum Field {
        FIRST_NAME,
        LAST_NAME,
        MOBILE,
        EMAIL,
        PESEL,
        TYPE
    }

    private String personId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String pesel;
    private Type type;
    private static String externalPath = "src/main/resources/Employees/External/emps.xml";
    private static String internalPath = "src/main/resources/Employees/Internal/emps.xml";
    public Person(String personId, String firstName, String lastName, String mobile, String email, String pesel, String type) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.pesel = pesel;

        if(type.equalsIgnoreCase("external"))
            this.type = Type.EXTERNAL;
        else
            this.type = Type.INTERNAL;
    }

    public static Person find(Type type, String firstName, String lastName, String mobile) {
        for(Person p : extent) {
            if(p.getType() == type && p.getFirstName().equals(firstName) && p.getLastName().equals(lastName) && p.getMobile().equals(mobile))
                return p;
        }

        System.out.println("Employee does not exist.");
        return null;
    }

    public static void create (Person person) {
        extent.add(person);
    }

    public static boolean remove (String personId) {
        for(Person p : extent) {
            if(p.getPersonId().equals(personId)) {
                extent.remove(p);
                return true;
            }
        }
        return false;
    }

    public static void modify(String personId, Field field, String value) {
        for (Person p : extent) {
            if (p.getPersonId().equals(personId)) {
                switch (field) {
                    case FIRST_NAME -> p.setFirstName(value);
                    case LAST_NAME -> p.setLastName(value);
                    case MOBILE -> p.setMobile(value);
                    case EMAIL -> p.setEmail(value);
                    case PESEL -> p.setPesel(value);
                    case TYPE -> {
                        if (value.equalsIgnoreCase("external")) {
                            p.setType(Type.EXTERNAL);
                        } else {
                            p.setType(Type.INTERNAL);
                        }
                    }
                    default -> {
                        System.out.println("Invalid field. Please choose one of the valid fields.");
                        return;
                    }
                }
                return;
            }
        }
        System.out.println("Person with ID " + personId + " does not exist.");
    }

    public void setPesel(String pesel) {
        if(pesel.length() == 11)
            this.pesel = pesel;
        else
            System.out.println("Invalid pesel.");
    }

    public void setEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (email.matches(emailRegex)) {
            this.email = email;
        } else {
            System.out.println("Invalid email format. Please provide a valid email address.");
        }
    }
    public static void loadToExtent() {
        List<Person> externalEmployees = XMLHandler.loadListFromXML(externalPath);
        List<Person> internalEmployees = XMLHandler.loadListFromXML(internalPath);

        if(externalEmployees != null) {
            Person.extent.addAll(externalEmployees);
            System.out.println("External employees loaded successfully.");
        }
        else
            System.out.println("Error loading external employees!");

        if(internalEmployees != null) {
            Person.extent.addAll(internalEmployees);
            System.out.println("Internal employees loaded successfully.");
        }
        else
            System.out.println("Error loading internal employees!");
    }

    public static void saveExtent() {
        List<Person> tmpExternal = new ArrayList<>();
        List<Person> tmpInternal = new ArrayList<>();

        for(Person p : extent) {
            if(p.getType() == Type.EXTERNAL) {
                tmpExternal.add(p);
            }
            else
                tmpInternal.add(p);
        }
        XMLHandler.saveListToXML(tmpExternal, externalPath);
        XMLHandler.saveListToXML(tmpInternal, internalPath);
    }
}
