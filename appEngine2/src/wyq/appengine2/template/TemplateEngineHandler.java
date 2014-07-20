package wyq.appengine2.template;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public interface TemplateEngineHandler extends InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable;
}
