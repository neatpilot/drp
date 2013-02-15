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

	// ��һ�ξ��У��Ժ��ٵ������document��������ҪѰ��
	private Document doc = null;

	private BeanFactory() {
		try {
			doc = new SAXReader().read(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("/"+beansConfigName));
			
			// "."��ʾ��ǰĿ¼ "/"��ʾ��Ŀ¼
			//System.out.println("��ȡbeans_config.xml��Դ ----> "+BeanFactory.class.getResource(".").getPath());
			//System.out.println("��ȡbeans_config.xml����----> "+BeanFactory.class.getResource("/").getPath());
			System.out.println("��ȡbeans_config.xml����ȡ ----> "+Thread.currentThread().getContextClassLoader().getResource("/").getPath());
		} catch (DocumentException e) {
			e.printStackTrace();
			// ������������
			throw new RuntimeException();
		}
	}

	public synchronized Object getServiceObject(Class c) {
		String key = c.getName();
		if (serviceMap.containsKey(key)) {
			return serviceMap.get(key);
		}
		System.out.println(this.getClass().getName()+"��ȡService Object>>>>"+key);
		// not store in the memory
		Element elt = (Element) doc.selectSingleNode("//service[@id=\"" + key + "\"]");
		String className = elt.attributeValue("class");
		Object serviceObject = null;
		try {
			serviceObject = Class.forName(className).newInstance();
			
			//��̬�����װҵ���߼���
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
		System.out.println(this.getClass().getName()+"��ȡDAO Objectl>>>>"+key);
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
		//ʵ���÷�
		ItemDAO itemDAO = (ItemDAO) BeanFactory.getInstance().getDAOObject(ItemDAO.class);
		System.out.println(itemDAO);
	}

}
