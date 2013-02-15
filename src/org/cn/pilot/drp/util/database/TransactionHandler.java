package org.cn.pilot.drp.util.database;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import org.cn.pilot.drp.util.exception.ApplicationException;

public class TransactionHandler implements InvocationHandler {
	private Object targetObject;

	public Object bind(Object targetoObject) {
		this.targetObject = targetoObject;

		return Proxy.newProxyInstance(targetoObject.getClass().getClassLoader(), targetoObject.getClass()
				.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object ret = null;

		Connection conn = null;
		try {
			// 添加事务管理
			conn = ConnectionManager.getConnection();

			if (method.getName().startsWith("add") || method.getName().startsWith("del")
					|| method.getName().startsWith("modify")) { // 这个可以在XML文件中配置
				// 手动控制事务提交
				ConnectionManager.beginTransaction(conn);
			}
			// 调用目标对象的业务逻辑方法
			ret = method.invoke(targetObject, args);
			System.out.println("执行------>  "+method.getName());
			// 提交事务
			ConnectionManager.commitTransaction(conn); // commitTransaction()中含有对connection的autoCommit判断
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof InvocationTargetException) {
				InvocationTargetException ete = (InvocationTargetException) e;
				throw ete.getTargetException();
			}
			ConnectionManager.rollbackTransaction(conn);
			throw new ApplicationException("操作失败！");
		} finally {
			ConnectionManager.closeConnection();
		}

		return ret;
	}

}
