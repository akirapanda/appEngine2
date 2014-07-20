package wyq.appengine2.di;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

public class BeanInjector extends AbstractInjector {

	private static BeanInjector injector = new BeanInjector();

	public static <T> T inject(T o) {
		return injector.doInject(o);
	}

	@Override
	protected Object[] getParameterObjects(PropertyDescriptor property) {

		Class<?>[] parameterTypes = property.getWriteMethod()
				.getParameterTypes();
		Object[] params = new Object[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> paramType = parameterTypes[i];
			params[i] = getComponentByClass(paramType);
		}

		return params;
	}

	protected Map<Class<?>, Object> cache = new HashMap<Class<?>, Object>();

	@SuppressWarnings("unchecked")
	protected <T> T getComponentByClass(Class<T> clazz) {
		T component = null;

		// get object from cache
		if (cache.containsKey(clazz)) {
			Object tmp = cache.get(clazz);
			if (clazz.isInstance(tmp)) {
				return (T) tmp;
			}
		}

		try {
			component = ObjectCreator.create(clazz);
			cache.put(clazz, component);
		} catch (Exception e) {
			// ignore
			e.printStackTrace();
		}

		return component;
	}

}
