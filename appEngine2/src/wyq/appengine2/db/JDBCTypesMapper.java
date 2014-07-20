/**
 * 
 */
package wyq.appengine2.db;


/**
 * @author wangyq
 * 
 */
public interface JDBCTypesMapper {

	public int getJDBCType(Class<?> c);

	public Class<?> getJavaType(int sqlType);
}
