package serialization.implementation.student;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class StudentConverter implements Converter {
    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context){
        Student stud = (Student) value;
        writer.startNode("name");
        writer.setValue(stud.getStudentName());
        writer.endNode();
        writer.startNode("school");
        writer.setValue(stud.getStudentSchool());
        writer.endNode();
        writer.startNode("classYear");
        writer.setValue(Integer.toString(stud.getStudentClassYear()));
        writer.endNode();
        writer.startNode("GPA");
        writer.setValue(Double.toString(stud.getStudentGPA()));
        writer.endNode();
    }

    public Student unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context){
        Student stud;
        String name;
        String school;
        int classYear;
        double gpa;

        reader.moveDown();
        name = reader.getValue();
        reader.moveUp();

        reader.moveDown();
        school = reader.getValue();
        reader.moveUp();

        reader.moveDown();
        classYear = Integer.valueOf(reader.getValue());
        reader.moveUp();

        reader.moveDown();
        gpa = Double.valueOf(reader.getValue());
        reader.moveUp();

        reader.close();

        stud = new Student(name, school, classYear, gpa);

        return stud;
    }

    @Override
    public boolean canConvert(Class object) {
        return object.equals(Student.class);
    }

}
