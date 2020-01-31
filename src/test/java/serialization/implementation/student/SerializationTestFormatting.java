package serialization.implementation.student;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.logging.Logger;

@TestInstance(Lifecycle.PER_CLASS)
public interface SerializationTestFormatting {
	
	static final Logger log = Logger.getLogger(StudentTestCases.class.getName());
	
	@BeforeAll
	default void testIntroMessage() {
		log.info("Beginning serialization tests with Student class...");
	}
	
	@AfterAll
	default void testOutroMessage() {
		log.info("Finished with testing the Student class.");
	}
	
	@BeforeEach
	default void testNameDisplay(TestInfo info) {
		log.info(() -> String.format("Executing test: [%s]",
	            info.getDisplayName()));

	}
	
}
