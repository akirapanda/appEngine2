package wyq.appengine2.di;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class provides the ability to create dynamic proxy objects. Proxies
 * created by this class will be cached. The InvocationHandler of the cached
 * proxy will be reused if the the delegating interfaces is expended.
 * 
 * @author dewafer
 * @since 2
 * @version 1.0
 *
 */
public class ProxyCreator {

	private static ProxyCreator proxyCreator = new ProxyCreator();

	public static Object newProxy(Class<?>[] ifaces, InvocationHandler handler) {
		return proxyCreator.delegate(ifaces, handler);
	}

	private Map<String, ProxyDelegate> cache = new HashMap<String, ProxyDelegate>();

	public Object delegate(Class<?>[] ifaces, InvocationHandler handler) {
		String proxyDelegateName = "ProxyDelegate:"
				+ handler.getClass().getName();
		if (!Proxy.isProxyClass(handler.getClass())) {
			proxyDelegateName += "#" + handler.hashCode();
		}
		ProxyDelegate proxyd = cache.get(proxyDelegateName);
		if (proxyd == null) {
			proxyd = new ProxyDelegate(ifaces, handler);
			cache.put(proxyDelegateName, proxyd);
		} else if (!proxyd.containsAll(ifaces)) {
			proxyd.renewInterfaces(ifaces);
		}
		return proxyd.getProxy();

	}

	class ProxyDelegate {

		private Object proxy;
		private Set<Class<?>> proxyFaces = new HashSet<Class<?>>();

		public ProxyDelegate(Class<?>[] ifaces, InvocationHandler handler) {
			addAll(ifaces);
			proxy = newProxy(handler);
		}

		public Object getProxy() {
			return proxy;
		}

		private boolean addAll(Class<?>... arg0) {
			return Collections.addAll(proxyFaces, arg0);
		}

		public boolean containsAll(Class<?>... arg0) {
			List<Class<?>> list = Arrays.asList(arg0);
			return proxyFaces.containsAll(list);
		}

		private Class<?>[] toArray() {
			Class<?>[] arr = new Class<?>[proxyFaces.size()];
			return proxyFaces.toArray(arr);
		}

		public void renewInterfaces(Class<?>... arg0) {
			InvocationHandler handler = Proxy.getInvocationHandler(proxy);
			addAll(arg0);
			proxy = newProxy(handler);
		}

		private Object newProxy(InvocationHandler handler) {
			return Proxy.newProxyInstance(handler.getClass().getClassLoader(),
					toArray(), handler);
		}

	}

}
