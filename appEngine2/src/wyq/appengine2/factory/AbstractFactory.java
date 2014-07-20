package wyq.appengine2.factory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import wyq.appengine2.di.ObjectCreator;
import wyq.appengine2.exception.ExceptionHandler;
import wyq.appengine2.factory.Factory.FactoryParameter;

/**
 * This is the base abstract factory class of all the factories.
 * 
 * @author dewafer
 * 
 * @param <T>
 * @param <R>
 */
public abstract class AbstractFactory<T, R extends FactoryParameter> implements
		Factory<T> {

	protected ExceptionHandler exceptionHandler;
	private Class<R> actualFactoryParamType;

	@Override
	public T factory(FactoryParameter parameterObject) {
		if (getActualfactoryParamType().isInstance(parameterObject)) {
			return build(getActualfactoryParamType().cast(parameterObject));
		} else {
			throw new IllegalArgumentException();
		}
	}

	protected abstract T build(R param);

	@SuppressWarnings("unchecked")
	@Override
	public FactoryParameter prepare(Object... values) {

		FactoryParameter p = null;
		Object[] initValues = values;
		try {
			Class<R> fparamType = getActualfactoryParamType();
			// if is a member class, add parent as this
			if (fparamType.isMemberClass()) {
				List<Object> initValueList = new ArrayList<Object>();
				Collections.addAll(initValueList, values);
				initValueList.add(0, this);
				initValues = initValueList.toArray(initValues);
			}

			p = ObjectCreator.create((Class<FactoryParameter>) fparamType,
					initValues);

		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

		return p;
	}

	@SuppressWarnings("unchecked")
	protected Class<R> getActualfactoryParamType() {
		if (actualFactoryParamType == null) {
			// get sub class
			Class<?> subClass = getClass();
			// get generic this class
			Type type = subClass.getGenericSuperclass();
			// get parameterizedType
			if (type instanceof ParameterizedType) {
				ParameterizedType thisClassType = (ParameterizedType) type;
				Type[] types = thisClassType.getActualTypeArguments();

				// Retrieve the actual type of R
				if (types.length == 2) {
					Type actualClassType = types[1];
					if (FactoryParameter.class
							.isAssignableFrom((Class<?>) actualClassType)) {
						actualFactoryParamType = (Class<R>) actualClassType;
					}
				}
			}
		}
		return actualFactoryParamType;
	}

	@Override
	public T manufacture(Object... values) {
		return factory(prepare(values));
	}

	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}
}
