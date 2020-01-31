//Practice with serialization using a class which structures how information is stored about a college student.
package serialization.implementation.student;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import org.xml.sax.InputSource;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import java.io.FileInputStream;
import java.io.Serializable;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedWriter;

import java.nio.file.*;

import java.util.ArrayList;
import java.util.List;

//import com.thoughtworks.xstream.annotations.XStreamAlias;

//TODO:
// Get "find bugs"

public class Student implements Serializable {
	
	private String studentName;
	private String studentSchool;
	private int studentClassYear;
	private double studentGPA;

	public Student() {
		this("", "", 0, 0.0);
	}
	
	public Student(String name, String school, int classOf, double GPA) {
		studentName = name;
		studentSchool = school;
		studentClassYear = classOf;
		studentGPA = GPA;
	}
	
	
	/*
	 * Places the students name, school, class, and GPA into a newly created CSV file, serving as it's first element.
	 */

	public static void serializeToCSV(Student stud, Path pathName) throws IOException, InvalidPathException, NotDirectoryException, WrongFileExtensionException, NotSerializableException{
		
		if(!(pathName.toString().endsWith(".csv"))) {
			throw new WrongFileExtensionException("Need to use file extension '.csv' at end of path specified in parameter newPathName!");
		}
		
    	else {	
			List<String> linesToAdd = new ArrayList<String>();
			
			if (Files.exists(pathName, LinkOption.NOFOLLOW_LINKS )) {
				
				//Is this a file that contains students?
				if(Files.readAllLines(pathName).get(0).contains("studentName,studentSchool,studentClassYear,studentGPA")) {
					
					linesToAdd.add(stud.getStudentName() + "," + stud.getStudentSchool() + ","
							+ stud.getStudentClassYear() + "," + stud.getStudentGPA());
					Files.write(pathName, linesToAdd.get(0).getBytes(), StandardOpenOption.APPEND); //File already has students serialized in it, so append current student to file
					
				}
				
				else {
					
					throw new NotSerializableException("The path specified by serializeToCSV points to a file that is not formatted to serialize a student! Please use a different path");
			
				}
				
			}
			
			else {
				
				Files.createFile(pathName);
				linesToAdd.add("studentName,studentSchool,studentClassYear,studentGPA");
				linesToAdd.add(stud.getStudentName() + "," + stud.getStudentSchool() + ","
						+ stud.getStudentClassYear() + "," + stud.getStudentGPA());
				Files.write(pathName, linesToAdd);
				
			}
			
    	}
			
		
	}

    private static String formatXML(String xml){
		try {
			Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			Source xmlSource = new SAXSource(new InputSource(
					new ByteArrayInputStream(xml.getBytes())));
			StreamResult res =  new StreamResult(new ByteArrayOutputStream());
			serializer.transform(xmlSource, res);
			return new String(((ByteArrayOutputStream)res.getOutputStream()).toByteArray());
		} catch(Exception e) {
			return xml;
		}
	}

	public static void serializeToXML(Student stud, Path pathName) throws WrongFileExtensionException, IOException{
		if (!(pathName.toString().endsWith(".xml"))){
			throw new WrongFileExtensionException("The path specified by serializeToXML is not serializable to xml!");
		}

		else if (!Files.exists(pathName)){
			Files.createFile(pathName);
		}

		XStream xstream = new XStream(new StaxDriver());
        xstream.registerConverter(new StudentConverter());

		BufferedWriter xmlWriter = Files.newBufferedWriter(pathName, StandardOpenOption.WRITE);
        xstream.toXML(stud, xmlWriter);
        xmlWriter.close();


	}
		
	public static Student deserializeStudent (Path pathName) throws InvalidPathException, IOException, NotSerializableException {
		Student deserStudent = null;
		
		if (Files.exists(pathName, LinkOption.NOFOLLOW_LINKS)) {
			String pathString = pathName.toString();

			if (pathString.endsWith(".csv")){
			
				if(Files.readAllLines(pathName).get(0).contains("studentName,studentSchool,studentClassYear,studentGPA")){

					ArrayList<String> studentDataList;
					studentDataList = (ArrayList<String>) Files.readAllLines(pathName);

					if(studentDataList.size() > 1) {
						String attributes = studentDataList.get(1);
						String[] attributeList = attributes.split(",");

						//Student to be deserialized
						deserStudent = new Student(attributeList[0], attributeList[1], Integer.parseInt(attributeList[2]), Double.parseDouble(attributeList[3]));

						//Removing the deserialized student from the file
						studentDataList.remove(1);
						Files.write(pathName, studentDataList);
					}

					else {

						throw new NotSerializableException("The file does not contain any student attributes, but is in the correct format for student serialization.");

					}

				}

				else {

					throw new NotSerializableException("The path specified by deserializeStudent points to a file that is not formatted to serialize a student! Please use a different path.");
				}
				
			}

			else if(pathString.endsWith(".xml")){
				XStream xstream = new XStream(new StaxDriver());
				xstream.registerConverter(new StudentConverter());

				deserStudent = (Student) xstream.fromXML(new FileInputStream(pathName.toFile()));
			}
			
		}
		
		else {
			
			throw new NotSerializableException("The path specified by deserializeStudent does not exist...");
			
		}
		
		return deserStudent;
	}


	/*
	 * Adds the current Student's attributes to the csv with the specified name of the string "fileName." If the file doesn't exist, nothing will
	 * be done and addToFile will return false. Otherwise, it will add the attributes to the file and return true, even if the
	 * object's attributes are already in the file.
	 */
	
	public void setStudentName(String newStudentName) {
		
		studentName = newStudentName;
		
	}

	
	public void setStudentSchool(String newStudentSchool) {
		
 		studentSchool = newStudentSchool;
		
	}
	
	
	public void setStudentClassYear(int newStudentClassYear) {
		
		studentClassYear = newStudentClassYear;
		
	}
	
	public void setStudentGPA(double newStudentGPA) {
		
		studentGPA = newStudentGPA;
		
	}

	
	public String getStudentName() {
		
		return studentName;
		
	}

	
	public String getStudentSchool() {
		
		return studentSchool;
		
	}
	
	
	public int getStudentClassYear() {
		
		return studentClassYear;
		
	}
	
	public double getStudentGPA() {
		
		return studentGPA;
		
	}
	
		
    public boolean equals(Student stud) {
 
    	boolean doesEqual;
    	String otherStudentName;
    	String otherStudentSchool;
    	int otherStudentClassYear;
    	double otherStudentGPA;
    	
    	if (stud == null) {
    		doesEqual = false;
    	}
    	
    	else {
	    	otherStudentName = stud.getStudentName();
	    	otherStudentSchool = stud.getStudentSchool();
	    	otherStudentClassYear = stud.getStudentClassYear();
	    	otherStudentGPA = stud.getStudentGPA();
	    	doesEqual = (otherStudentName.equalsIgnoreCase(this.studentName) && otherStudentSchool.equalsIgnoreCase(this.studentSchool) 
	    			&& otherStudentClassYear == this.studentClassYear && otherStudentGPA == this.studentGPA);
    	}
    	
    	return doesEqual;
 
    }
	
}
