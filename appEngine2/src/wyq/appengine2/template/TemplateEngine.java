package wyq.appengine2.template;

import wyq.appengine2.di.BeanInjector;
import wyq.appengine2.di.ObjectCreator;
import wyq.appengine2.proxy.AbstractProxyEngine;

/**
 * This class handles templates through interfaces.
 * 
 * @author dewafer
 * 
 */
public class TemplateEngine extends AbstractProxyEngine {

	private TemplateEngineHandler handler;

	public <T> T getTemplate(Class<? extends T> templateInterface) {
		return getProxyInstance(templateInterface);
	}

	private static TemplateEngine templateEngine = null;

	public static TemplateEngine get() {
		if (templateEngine == null) {
			// init
			templateEngine = ObjectCreator.create(TemplateEngine.class);
			// inject
			templateEngine = BeanInjector.inject(templateEngine);
		}
		return templateEngine;
	}

	@Override
	public TemplateEngineHandler getHandler() {
		return handler;
	}

	public void setHandler(TemplateEngineHandler handler) {
		this.handler = handler;
	}
}
