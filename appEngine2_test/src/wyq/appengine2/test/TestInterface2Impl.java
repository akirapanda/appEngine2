package wyq.appengine2.test;

import wyq.appengine2.test.ObjectCreatorTest.TestInterface2;

public class TestInterface2Impl implements TestInterface2 {

	@Override
	public String getValue() {
		return getClass().getName();
	}

}
