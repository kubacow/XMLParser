package org.example;

import com.thoughtworks.xstream.XStream;

import java.io.*;
import java.util.List;

public class XMLHandler {

    public static void saveListToXML(List<Person> personList, String filePath) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "utf-8"))) {
            XStream xStream = new XStream();
            xStream.alias("person", Person.class);
            xStream.toXML(personList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Person> loadListFromXML(String filePath) {


        XStream xstream = new XStream();
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new Class[]{Person.class});

        xstream.alias("person", Person.class);
        try {
            FileInputStream fis = new FileInputStream(filePath);

            List<Person> persons = (List<Person>) xstream.fromXML(fis);

            return persons;

            } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
