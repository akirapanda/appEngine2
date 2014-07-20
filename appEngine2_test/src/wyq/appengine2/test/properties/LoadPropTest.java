package wyq.appengine2.test.properties;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.junit.Test;

import wyq.appengine2.toolbox.Property;
import wyq.appengine2.toolbox.XMLProperty;

public class LoadPropTest {

	@Test
	public void loadTest001() {
		Property p = new Property(this.getClass());
		assertResult(p);
	}

	@Test
	public void loadTest002() {
		Property p = new XMLProperty(this.getClass());
		assertResult(p);

	}

	@Test
	public void loadTest003() {
		Property p = new Property("LoadPropTest_003.properties");
		assertResult(p);
	}

	@Test
	public void loadTest004() {
		Property p = new XMLProperty("LoadPropTest.xml");
		assertResult(p);

	}

	@Test(expected = FileNotFoundException.class)
	public void loadTest005() throws Throwable {
		try {
			new Property(NoPropClass.class);
		} catch (Exception e) {
			throw e.getCause();
		}
	}

	private void assertResult(Properties p) {
		assertEquals(2, p.size());
		assertEquals("this is value1", p.getProperty("value1"));
		assertEquals("this is value2", p.getProperty("value2"));
	}

	class NoPropClass {

	}
}
