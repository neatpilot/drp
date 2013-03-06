package org.cn.pilot.drp.util.configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import org.cn.pilot.drp.util.PLog;
import org.cn.pilot.drp.util.exception.ApplicationException;

/**
 * ��̬�����װServiceҵ���߼���
 * 
 * @author Pilot
 * @version ---1.1 fix exception logic error after calling method.invoke() ---[ Mar 6, 2013 3:55:09 PM ] --> 
 */
public class TransactionHandler implements InvocationHandler {
	private Object targetObject;

	/**
	 * 
	 * @param targetoObject
	 *            Serviceҵ���߼���ľ���ʵ����
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
			// ����������
			conn = ConnectionManager.getConnection();
			if (isManualTransaction(method.getName())) {
				// �ֶ����������ύ
				ConnectionManager.beginTransaction(conn);
			}
			System.out.println("ִ��------>  " + method.getName() + PLog.atLocation(this));
			// ����Ŀ������ҵ���߼�����
			ret = method.invoke(targetObject, args);

			// �ύ����
			if (!conn.getAutoCommit()) {
				ConnectionManager.commitTransaction(conn); // commitTransaction()�к��ж�connection��autoCommit�ж�
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
			throw new ApplicationException("����ʧ�ܣ�");
		} finally {
			ConnectionManager.closeConnection();
		}
		return ret;
	}

	/**
	 * ���˷����Ƿ���Ҫ�ֶ��������ݿ�������
	 * 
	 * @param methodName
	 * @return
	 */
	private boolean isManualTransaction(String methodName) {
		ManualTransactionReader instance = ManualTransactionReader.getInstance();
		return instance.isManualTransaction(targetObject.getClass().getName(), methodName);
	}
}
