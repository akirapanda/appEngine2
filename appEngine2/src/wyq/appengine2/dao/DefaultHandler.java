package wyq.appengine2.dao;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import wyq.appengine2.db.DBEngine;
import wyq.appengine2.db.DBEngine.DBResult;
import wyq.appengine2.db.DBEngineHandler;
import wyq.appengine2.db.Types;
import wyq.appengine2.di.utils.GetResRealPath;
import wyq.appengine2.exception.ExceptionHandler;
import wyq.appengine2.toolbox.TextFile;

/**
 * Default handler of the DaoEngine.
 * 
 * @author dewafer
 * @version 1
 */
public class DefaultHandler implements DaoEngineHandler, DBEngineHandler {

	private DBEngine db = new DBEngine();

	private DaoResult daoResult = null;
	private Object[] params = null;
	private Class<?>[] paramTypes = null;

	private ExceptionHandler exceptionHandler;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		Class<?> clazz = method.getDeclaringClass();
		String fileName = clazz.getSimpleName() + "_" + method.getName()
				+ ".sql";
		String sql = new TextFile(getRealPath(clazz, fileName)).readAll();

		daoResult = null;
		// params = getSqlParams(args);
		params = args;
		paramTypes = method.getParameterTypes();

		DBEngineHandler tmpHandler = db.getHandler();
		db.setHandler(this);
		db.connect();
		db.executeSQL(sql);
		db.close();
		db.setHandler(tmpHandler);

		return daoResult;
	}

	protected URI getRealPath(Class<?> c, String name)
			throws MalformedURLException, URISyntaxException {
		return new GetResRealPath(c, name).getResRealPath().toURI();
	}

	// protected Object[] getSqlParams(Object[] args) {
	// Object[] argTmp = new Object[0];
	// if (args != null && args.length == 1) {
	// Object arg = args[0];
	// if (arg instanceof Object[]) {
	// argTmp = (Object[]) arg;
	// } else if (arg instanceof Collection<?>) {
	// Collection<?> c = (Collection<?>) arg;
	// argTmp = c.toArray(argTmp);
	// } else {
	// argTmp = args;
	// }
	// } else {
	// argTmp = args;
	// }
	// return argTmp;
	// }

	@Override
	public void prepareParameter(PreparedStatement stmt) {
		if (params == null)
			return;
		try {
			for (int i = 0; i < params.length; i++) {
				Object value = params[i];
				if (value == null) {
					stmt.setNull(i + 1, getJDBCType(paramTypes[i]));
				} else {
					stmt.setObject(i + 1, params[i]);
				}
			}
		} catch (SQLException e) {
			exceptionHandler.handle(e);
		}
	}

	protected int getJDBCType(Class<?> c) {
		return Types.getJDBCType(c);
	}

	@Override
	public void processResult(DBResult result) {
		try {
			daoResult = new DaoResult(result);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
	}

	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

}
