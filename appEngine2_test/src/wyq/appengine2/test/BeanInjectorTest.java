package wyq.appengine2.test;

import org.junit.Assert;
import org.junit.Test;

import wyq.appengine2.di.BeanInjector;

public class BeanInjectorTest {

	@Test
	public final void testInject001() {
		BeanInjector.inject(this);
		Assert.assertEquals(DefaultHandler.class.getName(), handler.getName());
		Assert.assertNull(this.getMember2());
	}
	
	private TestHandler handler;

	public TestHandler getHandler() {
		return handler;
	}

	public void setHandler(TestHandler handler) {
		this.handler = handler;
	}

	private NotExistInterface member2;

	public interface NotExistInterface {

	}

	public NotExistInterface getMember2() {
		return member2;
	}

	public void setMember2(NotExistInterface member2) {
		this.member2 = member2;
	}

}
