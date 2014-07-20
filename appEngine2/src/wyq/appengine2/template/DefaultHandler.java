package wyq.appengine2.template;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wyq.appengine2.di.utils.GetResRealPath;
import wyq.appengine2.toolbox.TextFile;

public class DefaultHandler implements TemplateEngineHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Class<?> clazz = method.getDeclaringClass();
		String fileName = clazz.getSimpleName() + "_" + method.getName()
				+ ".template";
		String template = new TextFile(getRealPath(clazz, fileName)).readAll();

		Pattern p = Pattern.compile("/\\*\\w+\\*/");
		Matcher m = p.matcher(template);

		List<String> keysInTemplate = new ArrayList<String>();
		while (m.find()) {
			String keyWord = m.group();
			keysInTemplate.add(keyWord.substring(2, keyWord.length() - 2));
		}

		Map<String, Object> keyWordValues = new LinkedHashMap<String, Object>();
		if (args != null && args.length != 0) {

			List<Integer> tokenArgsPositions = new ArrayList<Integer>();

			Annotation[][] parameterAnnotations = method
					.getParameterAnnotations();

			for (int i = 0; i < parameterAnnotations.length; i++) {
				Annotation[] annotations = parameterAnnotations[i];
				for (Annotation ano : annotations) {
					if (ano instanceof TemplateKeyWord) {
						TemplateKeyWord keyWord = (TemplateKeyWord) ano;
						String key = keyWord.value();
						Object value = args[i];
						keyWordValues.put(key, value);
						tokenArgsPositions.add(i);
					}
				}
			}

			for (String key : keysInTemplate) {
				if (!keyWordValues.containsKey(key)) {
					for (int i = 0; i < args.length; i++) {
						if (!tokenArgsPositions.contains(i)) {
							Object value = args[i];
							keyWordValues.put(key, value);
							tokenArgsPositions.add(i);
							break;
						}
					}
				}
			}
		}

		for (String key : keysInTemplate) {
			if (keyWordValues.containsKey(key)) {
				String repKey = "/\\*" + Matcher.quoteReplacement(key) + "\\*/";
				Object oValue = keyWordValues.get(key);
				String value = Matcher.quoteReplacement(String.valueOf(oValue));
				template = template.replaceAll(repKey, value);
			}
		}

		return template;
	}

	protected URI getRealPath(Class<?> c, String name)
			throws MalformedURLException, URISyntaxException {
		return new GetResRealPath(c, name).getResRealPath().toURI();
	}
}
