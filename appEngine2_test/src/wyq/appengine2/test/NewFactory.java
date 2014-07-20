package wyq.appengine2.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import wyq.appengine2.exception.ExceptionHandler;
import wyq.appengine2.factory.AbstractFactory;

public class NewFactory extends AbstractFactory<Object, NewFactoryParam> {

	@Before
	public final void setUp() {
		setExceptionHandler(new ExceptionHandler() {

			@Override
			public void handle(Exception e) {
				e.printStackTrace();
			}

		});
	}

	@Test
	public final void testFactory() {
		assertEquals("value199", this.manufacture("value1", 99));
	}

	@Override
	protected Object build(NewFactoryParam param) {
		return param.getV1() + param.getV2();
	}

}
