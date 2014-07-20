package wyq.appengine2.test;

import static org.junit.Assert.*;

import org.junit.Test;

import wyq.appengine2.di.ObjectCreator;

public class ObjectCreatorTest {

	@Test
	public final void testCreate() {
		Object o = ObjectCreator.create(Object.class);
		assertNotNull(o);
	}

	@Test
	public final void testCreate2() {
		TestClass1 o = ObjectCreator.create(TestClass1.class, "field1",
				"field2");
		assertNotNull(o);
		assertEquals("field1", o.field1);
		assertEquals("field2", o.field2);
	}

	@Test(expected = java.lang.ClassNotFoundException.class)
	public final void testCreate3() throws Throwable {
		try {
			ObjectCreator.create(TestInterface1.class);
		} catch (RuntimeException e) {
			throw e.getCause();
		}
	}
	
	@Test
	public final void testCreate4() {
		TestInterface2 o = ObjectCreator.create(TestInterface2.class);
		assertNotNull(o);
		assertEquals(TestInterface2Impl.class.getName(), o.getValue());
	}
	
	@Test
	public final void testCreate5() {
		TestHandler o = ObjectCreator.create(TestHandler.class);
		assertNotNull(o);
		assertEquals(DefaultHandler.class.getName(), o.getName());
	}


	@Test
	public final void testNewInstance() {
		fail("Not yet implemented"); // TODO
	}

	public static class TestClass1 {
		String field1;
		String field2;

		public TestClass1(String field1, String field2) {
			this.field1 = field1;
			this.field2 = field2;
		}

	}

	public static interface TestInterface1 {

	}
	
	public static interface TestInterface2 {
		public String getValue();
	}
}
