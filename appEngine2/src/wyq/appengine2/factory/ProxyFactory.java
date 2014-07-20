package wyq.appengine2.factory;

import java.lang.reflect.InvocationHandler;

import wyq.appengine2.di.ProxyCreator;
import wyq.appengine2.factory.Factory.FactoryParameter;
import wyq.appengine2.factory.ProxyFactory.ProxyFactoryParam;

/**
 * This factory produces the Proxy.
 * 
 * @author dewafer
 * 
 */
public class ProxyFactory extends AbstractFactory<Object, ProxyFactoryParam> {


	@Override
	public FactoryParameter prepare(Object... values) {
		if (values == null || values.length != 2) {
			throw new IllegalArgumentException();
		}
		Class<?>[] interfaces = (Class<?>[]) values[0];
		InvocationHandler invocationHandler = (InvocationHandler) values[1];
		return new ProxyFactoryParam(interfaces, invocationHandler);
	}

	@Override
	protected Object build(ProxyFactoryParam param) {
		Class<?>[] ifaces = param.interfaces;
		InvocationHandler handler = param.invocationHandler;
		return ProxyCreator.newProxy(ifaces, handler);
	}

	class ProxyFactoryParam implements FactoryParameter {

		private Class<?>[] interfaces;
		private InvocationHandler invocationHandler;

		public ProxyFactoryParam(Class<?>[] interfaces,
				InvocationHandler invocationHandler) {
			this.interfaces = interfaces;
			this.invocationHandler = invocationHandler;
		}

	}

}
