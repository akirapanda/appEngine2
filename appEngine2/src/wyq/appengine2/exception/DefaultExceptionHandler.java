package wyq.appengine2.exception;

public class DefaultExceptionHandler implements ExceptionHandler {

	@Override
	public void handle(Exception e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}

}
