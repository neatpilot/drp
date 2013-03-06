package org.cn.pilot.drp.util.configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import org.cn.pilot.drp.util.PLog;
import org.cn.pilot.drp.util.exception.ApplicationException;

/**
 * 动态代理封装Service业务逻辑层
 * 
 * @author Pilot
 * @version ---1.1 fix exception logic error after calling method.invoke() ---[ Mar 6, 2013 3:55:09 PM ] --> 
 */
public class TransactionHandler implements InvocationHandler {
	private Object targetObject;

	/**
	 * 
	 * @param targetoObject
	 *            Service业务逻辑层的具体实现类
	 * @return
	 */
	public Object bind(Object targetoObject) {
		this.targetObject = targetoObject;

		return Proxy.newProxyInstance(targetoObject.getClass().getClassLoader(), targetoObject.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object ret = null;
		Connection conn = null;
		try {
			// 添加事务管理
			conn = ConnectionManager.getConnection();
			if (isManualTransaction(method.getName())) {
				// 手动控制事务提交
				ConnectionManager.beginTransaction(conn);
			}
			System.out.println("执行------>  " + method.getName() + PLog.atLocation(this));
			// 调用目标对象的业务逻辑方法
			ret = method.invoke(targetObject, args);

			// 提交事务
			if (!conn.getAutoCommit()) {
				ConnectionManager.commitTransaction(conn); // commitTransaction()中含有对connection的autoCommit判断
			}
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				InvocationTargetException ete = (InvocationTargetException) e;
				if (ete.getTargetException() instanceof ApplicationException) {
					if (!conn.getAutoCommit()) {
						ConnectionManager.rollbackTransaction(conn);
					}
					throw ete.getTargetException();
				}
			}
			e.printStackTrace();
			throw new ApplicationException("操作失败！");
		} finally {
			ConnectionManager.closeConnection();
		}
		return ret;
	}

	/**
	 * 检查此方法是否需要手动控制数据库事务处理
	 * 
	 * @param methodName
	 * @return
	 */
	private boolean isManualTransaction(String methodName) {
		ManualTransactionReader instance = ManualTransactionReader.getInstance();
		return instance.isManualTransaction(targetObject.getClass().getName(), methodName);
	}
}
