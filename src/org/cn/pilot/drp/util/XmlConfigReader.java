package org.cn.pilot.drp.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlConfigReader {

	private static XmlConfigReader instance = null;

	private JdbcConfig jdbcConfig = new JdbcConfig();
	
	// 保存DAO工厂初始配置;store DAO factory default settings
	// key = name(item DAO factory), value="org.cn.pilot.ItemDaoFactory4Oracle"
	private Map<String, String> daoFactoryMap= new HashMap<String, String>();

	private XmlConfigReader() {
		SAXReader reader = new SAXReader();
		// according to different loadclass environment, code line below get different paths
		//InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("sys_config.xml");

		//System.out.println("test~~"+DbaXmlReader.class.getProtectionDomain().getCodeSource().getLocation());
		//System.out.println("2~~~"+ DbaXmlReader.class.getClass().getResource("/").getPath());

		//File file = new File(DbaXmlReader.class.getClass().getResource("/").getPath() + "sys_config.xml");
		File file = new File("C:\\Pilot Workspace\\Eclipse\\sys_config.xml");
		try {
			Document doc = reader.read(file);

			// obtain info from xml
			Element driverNameElt = (Element) doc.selectObject("/config/db-info/driver-name");
			Element urlElt = (Element) doc.selectObject("/config/db-info/url");
			Element userElt = (Element) doc.selectObject("/config/db-info/user");
			Element passwordElt = (Element) doc.selectObject("/config/db-info/password");

			// load info into jdbc
			jdbcConfig.setDriverName(driverNameElt.getStringValue());
			jdbcConfig.setUrl(urlElt.getStringValue());
			jdbcConfig.setUser(userElt.getStringValue());
			jdbcConfig.setPassword(passwordElt.getStringValue());
			
//			//取得“/config/dao-factory/”节点下所有节点信息
//			List<Element> daoFactoryList = doc.selectNodes("/config/dao-factory/*");
//			for(Iterator<Element> iter = daoFactoryList.iterator();iter.hasNext();){
//				Element elt = iter.next();
//				String tagName = elt.getName();
//				String tagValue = elt.getText();
//				System.out.println(tagName+","+tagValue);
//				
//				daoFactoryMap.put(tagName, tagValue);
//			}
			
			//取得“/config/dao-factory/”节点下所有节点信息
			Element daoFactoryNode = (Element) doc.selectObject("/config/dao-factory");
			Iterator<Element> daoFactoryList = daoFactoryNode.elementIterator();
			while(daoFactoryList.hasNext()){
				Element elt = daoFactoryList.next();
				String tagName = elt.getName();
				String tagValue = elt.getText();
				
				daoFactoryMap.put(tagName, tagValue);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static synchronized XmlConfigReader getInstance() {
		if (instance == null) {
			instance = new XmlConfigReader();
		}
		return instance;
	}

	public JdbcConfig getJdbcConfig() {
		return jdbcConfig;
	}
	
	/**
	 * 根据默认配置名，获得DAOFactory的路径;
	 * @param name
	 * @return
	 */
	public String getDAOFactory(String name){
		return daoFactoryMap.get(name);
	}

	// test
	public static void main(String[] args) {
//		System.out.println(XmlConfigReader.getInstance().getJdbcConfig());
		XmlConfigReader.getInstance();
	}
}
