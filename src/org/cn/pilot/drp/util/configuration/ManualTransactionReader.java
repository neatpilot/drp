package org.cn.pilot.drp.util.configuration;

import java.util.HashMap;
import java.util.Map;

import org.cn.pilot.drp.util.exception.ApplicationException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ManualTransactionReader {
	private static ManualTransactionReader instance = new ManualTransactionReader();

	public static ManualTransactionReader getInstance() {
		return instance;
	}

	private String configXMLFileName = "manual_transaction.xml";

	private Document document = null;
	private Map<String, Boolean> map = new HashMap<String, Boolean>();

	private ManualTransactionReader() {
		try {
			document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + configXMLFileName));
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ApplicationException(configXMLFileName + "读异常，请联系管理员");
		}
	}

	public boolean isManualTransaction(String serviceClassName, String methodName) {
		boolean result = false;
		if (map.containsKey(serviceClassName)) {
			return map.get(serviceClassName);
		} else {
			// (Element) doc.selectSingleNode("//service[@id=\"" + key + "\"]");
			Element serviceElt = (Element) document.selectSingleNode("//service[@id=\"" + serviceClassName + "\"]");
			Element methodElt = (Element) serviceElt.selectSingleNode("//method[@name=\"" + methodName + "\"]");
			String config = methodElt.attributeValue("value");
			if ("true".equals(config)) {
				result = true;
			} else if ("false".equals(config)) {
				result = false;
			} else {
				throw new ApplicationException("程序错误，请联系管理员");
			}
		}
		map.put(serviceClassName, result);
		return result;
	}

	@Override
	public String toString() {
		return "读取[" + Thread.currentThread().getContextClassLoader().getResource("/" + configXMLFileName).getPath() + "]";
	}

}
