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
			// ����������
			conn = ConnectionManager.getConnection();

			if (method.getName().startsWith("add") || method.getName().startsWith("del")
					|| method.getName().startsWith("modify")) { // ���������XML�ļ�������
				// �ֶ����������ύ
				ConnectionManager.beginTransaction(conn);
			}
			// ����Ŀ������ҵ���߼�����
			ret = method.invoke(targetObject, args);
			System.out.println("ִ��------>  "+method.getName());
			// �ύ����
			ConnectionManager.commitTransaction(conn); // commitTransaction()�к��ж�connection��autoCommit�ж�
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof InvocationTargetException) {
				InvocationTargetException ete = (InvocationTargetException) e;
				throw ete.getTargetException();
			}
			ConnectionManager.rollbackTransaction(conn);
			throw new ApplicationException("����ʧ�ܣ�");
		} finally {
			ConnectionManager.closeConnection();
		}

		return ret;
	}

}
