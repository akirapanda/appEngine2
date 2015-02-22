package wyq.appengine2.di.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import wyq.appengine2.di.utils.RetryLookup.NotFound;
import wyq.appengine2.di.utils.RetryLookup.NotNullFilter;

/**
 * This class helps get the real path of the resource in the following orders:
 * <ol>
 * <li>Resource at the same position with the class. If class is null,
 * getClass() method is used.</li>
 * <li>Resource at application root.</li>
 * <li>Resource at relative path against application root.</li>
 * </ol>
 * 
 * @since 2
 * @version 1.0
 * @author dewafer
 *
 */
public class GetResRealPath {

	private Class<?> clazz;
	private String name;

	public GetResRealPath(Class<?> clazz, String name) {
		this.clazz = clazz;
		this.name = name;
	}

	public URL getResRealPath() throws MalformedURLException,
			URISyntaxException {

		URI nameUri = new URI(name);
		if (clazz == null) {
			if (nameUri.isAbsolute()) {
				return nameUri.toURL();
			} else {
				clazz = getClass();
			}
		}

		if (name == null) {
			name = clazz.getSimpleName();
		}
		URL[] lookups = { clazz.getResource(name),
				clazz.getResource("/" + name),
				clazz.getResource("/").toURI().resolve(nameUri).toURL() };

		URL realUrl = null;
		try {
			realUrl = new ResLookup(lookups).lookup();
		} catch (NotFound e) {
			// ignore
		}

		return realUrl;

	}

	private static final NotNullFilter<URL> filter = new RetryLookup.NotNullFilter<URL>();

	class ResLookup extends RetryLookup<URL, URL> {

		public ResLookup(URL[] lookups) {
			super(lookups, filter);
		}

		@Override
		public URL tryLookup(URL param) {

			if (param != null) {
				try {
					// try connect
					param.openConnection().connect();
					return param;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

	}

	// private String realPath = null;
	//
	// @Override
	// public String toString() {
	// if (realPath == null) {
	// try {
	// realPath = getResRealPath().toExternalForm();
	// } catch (MalformedURLException | URISyntaxException
	// | NullPointerException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// return realPath == null ? name : realPath;
	// }

}
