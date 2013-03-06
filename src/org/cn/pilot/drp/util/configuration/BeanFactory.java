package org.cn.pilot.drp.util.configuration;

import java.util.HashMap;
import java.util.Map;

import org.cn.pilot.drp.util.PLog;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 返回DAOObject或者ServiceObject动态代理类（抽象工厂+动态代理）
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

	// 读一次就行，以后再调用这个document来根据需要寻找
	private Document doc = null;

	private BeanFactory() {
		try {
			doc = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + beansConfigName));

			System.out.println("读取beans_config.xml，读取 ----> "
					+ Thread.currentThread().getContextClassLoader().getResource("/" + beansConfigName).getPath() + PLog.atLocation(this));
		} catch (DocumentException e) {
			e.printStackTrace();
			// 交给容器接收
			throw new RuntimeException();
		}
	}

	/**
	 * @param c
	 *            传入Service的Abstract Class对象
	 * @return 对应的Service具体对象的动态代理类
	 */
	public synchronized Object getServiceObjectWithDynamicProxy(Class c) {
		String key = c.getName();
		if (serviceMap.containsKey(key)) {
			return serviceMap.get(key);
		}
		// object不在内存中 not store in the memory
		Element elt = (Element) doc.selectSingleNode("//service[@id=\"" + key + "\"]");
		String className = elt.attributeValue("class");
		Object serviceObject = null;
		try {
			serviceObject = Class.forName(className).newInstance();
			System.out.println("读取Service Object>>>>" + key + "@" + serviceObject.getClass().getName() + PLog.atLocation(this));
			// 动态代理包装业务逻辑层
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
			System.out.println("读取DAO Object>>>>" + key + "@" + daoObject.getClass().getName() + PLog.atLocation(this));
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
		// main使用，和tomcat使用的，是不同的ClassLoader，所以不太好测试。--路径
	}

}
