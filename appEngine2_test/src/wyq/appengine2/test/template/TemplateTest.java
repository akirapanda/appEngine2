package wyq.appengine2.test.template;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import wyq.appengine2.template.TemplateEngine;

public class TemplateTest {

	@Before
	public void setup() {
		TemplateEngine engine = TemplateEngine.get();
		template = engine.getTemplate(ITemplate.class);
	}

	ITemplate template;

	@Test
	public void test001() {
		println("template_method001:");
		println(template.method001());
	}

	@Test
	public void test002() {
		println("template_method002:");
		println(template.method002("Jack", new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss.ZZZ").format(new Date())));

	}

	@Test
	public void test003() {
		println("template_method003:");
		println(template.method003("\\*hello_key1*\\", "\\*world_key2*\\", "dear", "\\*ok_key3*\\"));
	}

	public static void println(Object o) {
		System.out.println(o);
	}

	public static void println() {
		System.out.println();
	}

}
