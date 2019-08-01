/**
 * 
 */
package com.jun.springbootstorm.base.constant;

/**
* @Title: Constants
* @Description:
* 常量类 
* @Version:1.0.0  
* @author JZxiaoxiao
* @date 2019年5月6日
*/
public class Constants {

	public Constants(){}
	/**
	 * 拓扑spot
	 */
	public static final class Spout {
		private Spout() {
		}

		public final static String INSERT_SPOUT="INSERT_SPOUT";

	}
	/**
	 * 拓扑bolt
	 */
	public static final class Bolt {
		private Bolt() {
		}

		public  final static String INSERT_MYSQL_BOLT="INSERT_MYSQL_BOLT";

		public  final static String INSERT_HBASE_BOLT="INSERT_HBASE_BOLT";

		public  final static String INSERT_REDIS_BOLT="INSERT_REDIS_BOLT";

	}
	
}
