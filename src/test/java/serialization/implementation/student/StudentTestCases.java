package serialization.implementation.student;

//import static org.junit.jupiter.api.Assertions.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

class StudentTestCases implements SerializationTestFormatting {

	Student studNoah = new Student("Noah Drake", "College of Charleston", 2021, 3.5);
	@Test
	void testSerializeCSV() {
		try {
			Student newStudNoah;

			//Use system-specific file name separators
			Student.serializeToCSV(studNoah, Paths.get("testCSV.csv"));
			newStudNoah = Student.deserializeStudent(Paths.get("testCSV.csv"));
			System.out.println("Serialized student is equal to deserialized student: " + studNoah.equals(newStudNoah));
			
		} catch (IOException | InvalidPathException | NotSerializableException | WrongFileExtensionException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testSerializeXML() {
		try {
			Student newStudNoah;
			StringBuilder xml = new StringBuilder();
			//Use system-specific file name separators
			Student.serializeToXML(studNoah, Paths.get("test1.xml"));

			//Deserialization
			XStream xstream = new XStream(new StaxDriver());
			xstream.registerConverter(new StudentConverter());

			File xmlFile = new File("test1.xml");
			newStudNoah = (Student) xstream.fromXML(new FileInputStream(xmlFile));

			System.out.println("Serialized student is equal to deserialized student: " + studNoah.equals(newStudNoah));

		} catch (IOException | InvalidPathException | WrongFileExtensionException e) {
			e.printStackTrace();
		}
	}

}
