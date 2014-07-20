package wyq.appengine2.toolbox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Use {@linkplain wyq.appengine2.toolbox.ClassRunner#run(Class)
 * ClassRunner.run(Class)} method to run each method in the class.
 * 
 * @author dewafer
 * @version 2
 * 
 */
public class ClassRunner {

	public static void run(Class<?> runClass) {
		try {
			new ClassRunner().go(runClass);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	protected void go(Class<?> runClass) throws IllegalAccessException,
			InstantiationException, IllegalArgumentException,
			InvocationTargetException {

		if (runClass == null) {
			log("# runClass is null!");
			return;
		}

		log("# run class:[" + runClass + "]");
		long start = System.currentTimeMillis();

		Object o = runClass.newInstance();

		Method[] allMethods = runClass.getMethods();

		for (Method m : allMethods) {
			if (isCallableMethod(m, runClass)) {
				long mstart = System.currentTimeMillis();
				log("# Invoking method:[" + m + "]");
				Object result = null;
				try {
					result = m.invoke(o);
				} catch (Exception e) {
					e.printStackTrace();
					log("# method [" + m
							+ "] has thrown exception caused by: ["
							+ e.getCause() + "]");
				}
				log("# method [" + m + "] invoked. "
						+ (System.currentTimeMillis() - mstart) + " ms. used.");
				if (result != null) {
					log("# result:" + result);
				}
			}
		}

		log("# run class:[" + runClass + "] finished. "
				+ (System.currentTimeMillis() - start) + " ms. used.");
	}

	/**
	 * Override this method to determine what kind of method can be run.
	 * 
	 * @param m
	 *            Method in the class
	 * @param runClass
	 *            the running class
	 * @return Return true to run, false false to skip.
	 */
	protected boolean isCallableMethod(Method m, Class<?> runClass) {
		if (!runClass.equals(m.getDeclaringClass()))
			return false;
		if (!Modifier.isPublic(m.getModifiers()))
			return false;
		if (m.getParameterTypes().length != 0)
			return false;
		if (Modifier.isNative(m.getModifiers()))
			return false;
		if (Modifier.isStatic(m.getModifiers()))
			return false;
		if (Modifier.isFinal(m.getModifiers()))
			return false;
		return true;
	}

	protected static void log(Object o) {
		System.out.println(o);
	}
}
