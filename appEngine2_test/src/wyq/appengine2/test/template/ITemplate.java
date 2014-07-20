package wyq.appengine2.test.template;

import wyq.appengine2.template.TemplateKeyWord;

public interface ITemplate {

	String method001();

	String method002(String name, String date);

	String method003(String key1, String key2,
			@TemplateKeyWord("username") String name, String key3);
}
