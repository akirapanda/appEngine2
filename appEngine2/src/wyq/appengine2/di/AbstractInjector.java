package wyq.appengine2.di;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * Base class for all the injectors.
 * 
 * @since 2
 * @version 1.0
 * @author dewafer
 *
 */
public abstract class AbstractInjector {

	public AbstractInjector() {
		super();
	}

	protected abstract Object[] getParameterObjects(PropertyDescriptor property);

	public final <T> T doInject(T o) {

		PropertyDescriptor[] allProperties = getBeanProperties(o);

		for (PropertyDescriptor property : allProperties) {
			if (property.getWriteMethod() != null) {
				set(o, property);
			}
		}

		return o;
	}

	/**
	 * Use java.beans.Introspector to get all the setters of the bean.
	 * 
	 * @param o
	 * @return
	 */
	protected final PropertyDescriptor[] getBeanProperties(Object o) {

		Class<?> clazz = o.getClass();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			// ignore
			e.printStackTrace();
		}

		return beanInfo == null ? new PropertyDescriptor[0] : beanInfo
				.getPropertyDescriptors();
	}

	/**
	 * invoke the setter of the bean
	 * 
	 * @param bean
	 * @param setter
	 * @return
	 */
	protected final <T> T set(T bean, PropertyDescriptor property) {

		Object[] params = getParameterObjects(property);

		try {
			property.getWriteMethod().invoke(bean, params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean;
	}

}