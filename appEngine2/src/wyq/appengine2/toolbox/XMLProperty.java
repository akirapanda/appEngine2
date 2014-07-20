package wyq.appengine2.toolbox;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class reads the XML files instead of properties file.
 * 
 * @author dewafer
 * 
 */
public class XMLProperty extends Property {

	private static final String SURFIX = ".xml";

	public XMLProperty(Class<?> c) {
		loadPropertyFile(c, c.getSimpleName() + SURFIX);
	}

	public XMLProperty(String file) {
		super(file);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3126579033871056194L;

	@Override
	protected void loadAction(InputStream in) throws IOException {
		loadFromXML(in);
	}

}
