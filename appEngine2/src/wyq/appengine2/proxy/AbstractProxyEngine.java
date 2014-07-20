package wyq.appengine2.proxy;

import java.lang.reflect.InvocationHandler;

import wyq.appengine2.di.ProxyCreator;

public abstract class AbstractProxyEngine {

	public AbstractProxyEngine() {
		super();
	}

	public abstract InvocationHandler getHandler();

	@SuppressWarnings("unchecked")
	protected <T> T getProxyInstance(Class<? extends T> iface) {
		return (T) ProxyCreator
				.newProxy(new Class<?>[] { iface }, getHandler());

	}
}