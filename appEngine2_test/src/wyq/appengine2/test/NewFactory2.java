package wyq.appengine2.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import wyq.appengine2.exception.ExceptionHandler;
import wyq.appengine2.factory.AbstractFactory;
import wyq.appengine2.test.NewFactory2.NParam2;

public class NewFactory2 extends AbstractFactory<Object, NParam2> {

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
		assertEquals(100, this.manufacture(1, 99));
	}

	@Override
	protected Object build(NParam2 param) {
		return param.getV3() + param.getV2();
	}

	class NParam2 extends NewFactoryParam {

		private int v3;

		private NParam2(int v3, int v2) {
			super(null, v2);
			this.v3 = v3;
		}

		public int getV3() {
			return v3;
		}

		public void setV3(int v3) {
			this.v3 = v3;
		}

	}

}
