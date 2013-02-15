Learning Java Web

JAVAWEB + JSTL + Oracle

V1~V4

1. Project initilize
	JDBC
	Database config XMl file (dom4j)

2. User maintanience (JavaBean + JSP + AJAX)
3. PageModel for paging
4. BaseData maintainience (Servlet + JSP + AJAX)
5. BeanFactory for abstract factory pattern (service & DAO layer implementation)


V5

1. part of management for flowcard
	--domain (vo)
	--Servlet
		BaseServlet: read params that both needs
		FlowCardSerlvet extends BaseServlet
	--Manager (service) [throws ApplicationException]
	--DAO [throws DAOException]
2. ThreadLocal stores Connection
3. Dynamic Proxy for service layer
4. JSTL for web pages