package wyq.appengine2.dao;

import java.lang.reflect.InvocationHandler;

import wyq.appengine2.di.BeanInjector;
import wyq.appengine2.di.ObjectCreator;
import wyq.appengine2.proxy.AbstractProxyEngine;

/**
 * This Engine provides an easy DAO object which can access the DB through the
 * JDBC Drivers. Simply use the DaoEngine.get() to get the DaoEngine with the
 * default configuration of the Repository.
 * 
 * @author dewafer
 * @version 1
 */
public class DaoEngine extends AbstractProxyEngine {

	protected DaoEngineHandler handler;

	public <T> T getDao(Class<? extends T> daoInterface) {
		return getProxyInstance(daoInterface);
	}

	@Override
	public InvocationHandler getHandler() {
		return handler;
	}

	public void setHandler(DaoEngineHandler handler) {
		this.handler = handler;
	}

	private static DaoEngine daoEngine = null;

	public static DaoEngine get() {
		if (daoEngine == null) {
			// init
			daoEngine = ObjectCreator.create(DaoEngine.class);
			// inject
			daoEngine = BeanInjector.inject(daoEngine);
		}
		return daoEngine;
	}
}
