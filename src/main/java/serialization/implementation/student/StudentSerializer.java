package serialization.implementation.student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class StudentSerializer {

    public static void serialize(Student stud, Path path) {

        try {

            OutputStream outStream = Files.newOutputStream(path, StandardOpenOption.CREATE,
                            StandardOpenOption.WRITE, StandardOpenOption.APPEND);

            ObjectOutputStream objectStream = new ObjectOutputStream(outStream);
            objectStream.writeObject(stud);
            outStream.close();
            objectStream.close();

        } catch(IOException e) {
            System.out.println(e.getStackTrace());
        }

    }

    public static void serialize(Student stud, String path) {

        try {

            OutputStream outStream = Files.newOutputStream(Paths.get(path), StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE, StandardOpenOption.APPEND);

            ObjectOutputStream objectStream = new ObjectOutputStream(outStream);
            objectStream.writeObject(stud);
            outStream.close();
            objectStream.close();

        } catch(IOException e) {
            System.out.println(e.getStackTrace());
        }

    }

    public static Student deserialize(Path path) {

        Student stud = null;
        try {

            InputStream inStream = Files.newInputStream(path, StandardOpenOption.READ);

            ObjectInputStream objectStream = new ObjectInputStream(inStream);
            stud = (Student) objectStream.readObject();
            inStream.close();
            objectStream.close();

            } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return stud;
    }

    public static Student deserialize(String path) {

        Student stud = null;
        try {
            InputStream inStream = Files.newInputStream(Paths.get(path), StandardOpenOption.READ);

            ObjectInputStream objectStream = new ObjectInputStream(inStream);
            stud = (Student) objectStream.readObject();
            inStream.close();
            objectStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return stud;

    }

}
