package wyq.appengine2.factory;

/**
 * All the factories should have this interface, but do not implement this
 * interface directly, extend AbstractFactory instead.
 * 
 * @author wyq
 * 
 * @param <T>
 */
public interface Factory<T> {

	/**
	 * @deprecated use Factory.manufacture instead
	 */
	public abstract T factory(FactoryParameter parameterObject);

	/**
	 * @deprecated use Factory.manufacture instead
	 */
	public abstract FactoryParameter prepare(Object... values);

	public abstract T manufacture(Object... values);

	public abstract String getName();

	public interface FactoryParameter {
	};

}