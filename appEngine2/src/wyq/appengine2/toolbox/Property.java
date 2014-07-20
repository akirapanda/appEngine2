package wyq.appengine2.toolbox;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import wyq.appengine2.di.utils.GetResRealPath;

/**
 * This class reads the property of the configuration.
 * 
 * @author dewafer
 * 
 */
public class Property extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7807247115254016009L;
	private static final String SURFIX = ".properties";

	public Property(String p) {
		loadPropertyFile(null, p);
	}

	public Property(Class<?> c) {
		loadPropertyFile(c, c.getSimpleName() + SURFIX);
	}

	public Property() {
	}

	protected void loadPropertyFile(Class<?> c, String name) {

		try {
			URL propUrl = new GetResRealPath(c, name).getResRealPath();
			if (name != null && propUrl == null) {
				// name provided but not found with class
				propUrl = new File(name).toURI().toURL();
			}
			loadAction(propUrl.openStream());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void load(String file) {
		loadPropertyFile(null, file);
	}

	public void load(Class<?> c) {
		loadPropertyFile(c, c.getSimpleName() + SURFIX);
	}

	protected void loadAction(InputStream in) throws IOException {
		load(in);
	}

	public boolean isKeyDefined(String keyRegExp) {
		for (Object key : this.keySet()) {
			String strKey = key.toString();
			if (Pattern.matches(keyRegExp, strKey)) {
				return true;
			}
		}
		return false;
	}

	public String[] getProperties(String keyRegExp) {
		List<String> p = new ArrayList<String>();
		for (Object key : this.keySet()) {
			String strKey = key.toString();
			if (Pattern.matches(keyRegExp, strKey)) {
				p.add(getProperty(strKey));
			}
		}
		String[] values = new String[p.size()];
		return p.toArray(values);
	}
}
