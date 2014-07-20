package wyq.appengine2.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Use this interface to implement the Proxy-invoke handler for the DaoEngine.
 * 
 * @author dewafer
 * @version 1
 * 
 */
public interface DaoEngineHandler extends InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable;
}
