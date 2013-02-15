package org.cn.pilot.drp.util;

import java.util.HashMap;
import java.util.Map;

import org.cn.pilot.drp.basedata.dao.dao.ItemDAO;
import org.cn.pilot.drp.util.database.TransactionHandler;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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
			doc = new SAXReader().read(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("/"+beansConfigName));
			
			// "."表示当前目录 "/"表示根目录
			//System.out.println("读取beans_config.xml，源 ----> "+BeanFactory.class.getResource(".").getPath());
			//System.out.println("读取beans_config.xml，根----> "+BeanFactory.class.getResource("/").getPath());
			System.out.println("读取beans_config.xml，读取 ----> "+Thread.currentThread().getContextClassLoader().getResource("/").getPath());
		} catch (DocumentException e) {
			e.printStackTrace();
			// 交给容器接收
			throw new RuntimeException();
		}
	}

	public synchronized Object getServiceObject(Class c) {
		String key = c.getName();
		if (serviceMap.containsKey(key)) {
			return serviceMap.get(key);
		}
		System.out.println(this.getClass().getName()+"读取Service Object>>>>"+key);
		// not store in the memory
		Element elt = (Element) doc.selectSingleNode("//service[@id=\"" + key + "\"]");
		String className = elt.attributeValue("class");
		Object serviceObject = null;
		try {
			serviceObject = Class.forName(className).newInstance();
			
			//动态代理包装业务逻辑层
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
		System.out.println(this.getClass().getName()+"读取DAO Objectl>>>>"+key);
		Element elt = (Element) doc.selectSingleNode("//dao[@id=\"" + key + "\"]");
		String className = elt.attributeValue("class");
		Object daoObject = null;
		try {
			daoObject = Class.forName(className).newInstance();
			daoMap.put(key, daoObject);
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
		//实际用法
		ItemDAO itemDAO = (ItemDAO) BeanFactory.getInstance().getDAOObject(ItemDAO.class);
		System.out.println(itemDAO);
	}

}
