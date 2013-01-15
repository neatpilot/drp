package org.cn.pilot.util;

import java.io.File;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DbaXmlReader {

	private static DbaXmlReader instance = null;

	private JdbcConfig jdbcConfig = new JdbcConfig();

	private DbaXmlReader() {
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

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static synchronized DbaXmlReader getInstance() {
		if (instance == null) {
			instance = new DbaXmlReader();
		}
		return instance;
	}

	public JdbcConfig getJdbcConfig() {
		return jdbcConfig;
	}

	// test
	public static void main(String[] args) {
		System.out.println(DbaXmlReader.getInstance().getJdbcConfig());
	}
}
