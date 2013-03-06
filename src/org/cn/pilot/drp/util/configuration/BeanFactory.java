package org.cn.pilot.drp.util.configuration;

import java.util.HashMap;
import java.util.Map;

import org.cn.pilot.drp.util.PLog;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ����DAOObject����ServiceObject��̬�����ࣨ���󹤳�+��̬����
 * 
 * @author Pilot
 * 
 */
public class BeanFactory {

	private static BeanFactory instance = new BeanFactory();

	public static BeanFactory getInstance() {
		return instance;
	}

	private final String beansConfigName = "beans_config.xml";

	// key= class name; value = corresponding instance
	private Map<String, Object> serviceMap = new HashMap<String, Object>();
	private Map<String, Object> daoMap = new HashMap<String, Object>();

	// ��һ�ξ��У��Ժ��ٵ������document��������ҪѰ��
	private Document doc = null;

	private BeanFactory() {
		try {
			doc = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + beansConfigName));

			System.out.println("��ȡbeans_config.xml����ȡ ----> "
					+ Thread.currentThread().getContextClassLoader().getResource("/" + beansConfigName).getPath() + PLog.atLocation(this));
		} catch (DocumentException e) {
			e.printStackTrace();
			// ������������
			throw new RuntimeException();
		}
	}

	/**
	 * @param c
	 *            ����Service��Abstract Class����
	 * @return ��Ӧ��Service�������Ķ�̬������
	 */
	public synchronized Object getServiceObjectWithDynamicProxy(Class c) {
		String key = c.getName();
		if (serviceMap.containsKey(key)) {
			return serviceMap.get(key);
		}
		// object�����ڴ��� not store in the memory
		Element elt = (Element) doc.selectSingleNode("//service[@id=\"" + key + "\"]");
		String className = elt.attributeValue("class");
		Object serviceObject = null;
		try {
			serviceObject = Class.forName(className).newInstance();
			System.out.println("��ȡService Object>>>>" + key + "@" + serviceObject.getClass().getName() + PLog.atLocation(this));
			// ��̬�����װҵ���߼���
			TransactionHandler handler = new TransactionHandler();
			serviceObject = handler.bind(serviceObject);

			serviceMap.put(key, serviceObject);

		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		return serviceObject;
	}

	public synchronized Object getDAOObject(Class c) {
		String key = c.getName();
		if (daoMap.containsKey(key)) {
			return daoMap.get(key);
		}

		// not store in the memory

		Element elt = (Element) doc.selectSingleNode("//dao[@id=\"" + key + "\"]");
		String className = elt.attributeValue("class");
		Object daoObject = null;
		try {
			daoObject = Class.forName(className).newInstance();
			daoMap.put(key, daoObject);
			System.out.println("��ȡDAO Object>>>>" + key + "@" + daoObject.getClass().getName() + PLog.atLocation(this));
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		return daoObject;
	}

	public static void main(String[] args) {
		// mainʹ�ã���tomcatʹ�õģ��ǲ�ͬ��ClassLoader�����Բ�̫�ò��ԡ�--·��
	}

}
