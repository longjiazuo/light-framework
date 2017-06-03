# light framework
## 一.简介
### 1. 它是一款轻量级 Java Web 框架
* 内置 IOC、AOP(待实现)、ORM(待实现)、DAO(待实现)、MVC 等特性
* 基于 Servlet 3.0 规范
* 使用 Java 注解取代 XML 配置<br>

### 2. 它使应用充分做到“前后端分离”
* 客户端可使用 HTML 或 JSP 作为视图模板
* 服务端可发布 REST 服务(使用 REST 插件)(待实现)
* 客户端通过 AJAX 获取服务端数据并进行界面渲染<br>

### 3. 它可提高应用程序的开发效率
* 面向基于 Web 的中小规模的应用程序
* 新手能在较短时间内入门<br>

## 二. 入门
### 1. 创建一个 Maven Web 工程
---------
整个工程的目录结构如下:<br/>
```
light-framework-mvc-sample/
　　┗ src/
　　　　┗ main/
　　　　　　┗ java/
　　　　　　┗ resources/
　　　　　　┗ webapp/
　　┗ pom.xml>
```
在 java 目录下，创建以下包名目录结构：<br/>
```
org/
　　┗ light4j/
　　　　┗ sample/
　　　　　　┗ action/
　　　　　　┗ entity/
　　　　　　┗ service/
```
可见，基础包名为：org.light4j.sample，下面的配置中会用到它。
### 2. 配置 Maven 依赖
------------------
编辑pom.xml 文件，添加 light-framework 依赖：<br/>
```
<dependency>
    <groupId>org.light4j</groupId>
    <artifactId>light-framework</artifactId>
    <version>1.0.0</version>
</dependency>
```
### 3. 编写 Light 配置
----------------------
在 resources 目录下，创建一个名为 light.properties 的文件，内容如下：<br/>
```
light.framework.app.base_package=org.light4j.sample
light.framework.app.home_page=/users

light.framework.jdbc.driver=com.mysql.jdbc.Driver
light.framework.jdbc.url=jdbc:mysql://localhost:3306/light-sample
light.framework.jdbc.username=root
light.framework.jdbc.password=123456
```
提示：需根据实际情况修改以上配置。
### 4. 建库，数据库用mysql
```
-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `remark` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', 'customer1', 'Jack', '13512345678', 'Jack@gmail.com', null);
INSERT INTO `customer` VALUES ('2', 'customer2', 'Rose', '13623456789', 'rose@gmail.com', null);
```
### 5. 编写 Entity 类
```
package org.light4j.sample.model;

/**
 * 客户
 * 
 * @author longjiazuo
 * 
 */
public class Customer {

	/**
	 * ID
	 */
	private long id;

	/**
	 * 客户名称
	 */
	private String name;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 电话号码
	 */
	private String telephone;

	/**
	 * 邮箱地址
	 */
	private String email;

	/**
	 * 备注
	 */
	private String remark;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
```
### 6. 编写 Service 类
```
package org.light4j.sample.service;
import java.util.List;
import java.util.Map;

import org.light4j.framework.annotatiom.Service;
import org.light4j.framework.helper.DataBaseHelper;
import org.light4j.sample.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提供客户数据服务
 * 
 * @author longjiazuo
 * 
 */
@Service
public class CustomerService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerService.class);

	/**
	 * 获取客户列表
	 * 
	 * @return
	 */
	public List<Customer> getCustomerList() {
		String sql = "select * from customer";
		return DataBaseHelper.queryEntityList(Customer.class, sql);
	}

	/**
	 * 获取客户
	 * 
	 * @param id
	 * @return
	 */
	public Customer getCustomer(long id) {
		String sql = "select * from customer where id=" + id;
		return DataBaseHelper.queryEntity(Customer.class, sql);
	}

	/**
	 * 创建客户
	 * 
	 * @param fieldMap
	 * @return
	 */
	public boolean createCustomer(Map<String, Object> fieldMap) {
		return DataBaseHelper.insertEntity(Customer.class, fieldMap);
	}

	/**
	 * 更新客户
	 * 
	 * @param id
	 * @param fieldMap
	 * @return
	 */
	public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
		return DataBaseHelper.updateEntity(Customer.class, id, fieldMap);
	}

	/**
	 * 删除客户
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteCustomer(long id) {
		return DataBaseHelper.deleteEntity(Customer.class, id);
	}
}
```
### 7. 编写 Action 类
```
package org.light4j.sample.controller;

import java.util.List;
import java.util.Map;

import org.light4j.framework.annotatiom.Action;
import org.light4j.framework.annotatiom.Controller;
import org.light4j.framework.annotatiom.Inject;
import org.light4j.framework.bean.Data;
import org.light4j.framework.bean.Param;
import org.light4j.framework.bean.View;
import org.light4j.sample.model.Customer;
import org.light4j.sample.service.CustomerService;

/**
 * 处理客户相关请求
 * 
 * @author longjiazuo
 * 
 */
@Controller
public class CustomerController {

	@Inject
	private CustomerService customerService;

	/**
	 * 进入 客户列表 界面
	 * 
	 * @param param
	 * @return
	 */
	@Action("get:/customer")
	public View index(Param param) {
		List<Customer> customerList = customerService.getCustomerList();
		return new View("customer.jsp").addModel("customerList", customerList);
	}

	/**
	 * 显示客户基本信息
	 * 
	 * @param param
	 * @return
	 */
	@Action("get:/customer_show")
	public View show(Param param) {
		long id = param.getLong("id");
		Customer customer = customerService.getCustomer(id);
		return new View("customer_show.jsp").addModel("customer", customer);
	}

	/**
	 * 进入 创建客户 界面
	 * 
	 * @param param
	 * @return
	 */
	@Action("get:/customer_create")
	public View create(Param param) {
		return new View("customer_create.jsp");
	}

	/**
	 * 处理 创建客户 请求
	 * 
	 * @param param
	 * @return
	 */
	@Action("post:/customer_create")
	public Data createSubmit(Param param) {
		Map<String, Object> fieldMap = param.getMap();
		boolean result = customerService.createCustomer(fieldMap);
		return new Data(result);
	}

	/**
	 * 进入 编辑客户 界面
	 * 
	 * @param param
	 * @return
	 */
	@Action("get:/customer_edit")
	public View edit(Param param) {
		long id = param.getLong("id");
		Customer customer = customerService.getCustomer(id);
		return new View("customer_edit.jsp").addModel("customer", customer);
	}

	/**
	 * 处理 编辑客户 请求
	 * 
	 * @param param
	 * @return
	 */
	@Action("put:/customer_edit")
	public Data editSubmit(Param param) {
		long id = param.getLong("id");
		Map<String, Object> fieldMap = param.getMap();
		boolean result = customerService.updateCustomer(id, fieldMap);
		return new Data(result);
	}

	/**
	 * 处理 删除客户 请求
	 * 
	 * @param param
	 * @return
	 */
	@Action("delete:/customer_edit")
	public Data delete(Param param) {
		long id = param.getLong("id");
		boolean result = customerService.deleteCustomer(id);
		return new Data(result);
	}
}
```
### 8. 编写视图
在 Action 中使用了 JSP 作为视图展现技术，需要在编写以下 JSP 文件：
* customer.jsp
* customer_show.jsp
* customer_create.jsp
* customer_edit.jsp <br>

#### 9.  编写配置文件
在src/main/resources下面增加下面几个配置文件:
数据库配置文件config.properties:
```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/light_sample
jdbc.username=root
jdbc.password=123456
```
数据库包名等配置文件light.properties:
```
light.framework.jdbc.driver=com.mysql.jdbc.Driver
light.framework.jdbc.url=jdbc:mysql://localhost:3306/light_sample
light.framework.jdbc.username=root
light.framework.jdbc.password=123456
light.framework.app.base_package=org.light4j.sample
light.framework.app.jsp_path=/WEB-INF/view/
light.framework.app.asset_path=/asset/
```
日志配置文件log4j.properties:
```
log4j.rootLogger=ERROR,console,file
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%m%n

log4j.appender.file=org.apache.log4j.DailyRollingFileAppender
log4j.appender.file.File=${user.home}/logs/sample.log
log4j.appender.file.DatePattern='_'yyyyMMdd
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{HH:mm:ss,SSSS} %p %c (%L) -%m%n

log4j.logger.org.lightFramework.use=DEBUG
```
### 10. 编写web.xml引入light-framework,在web.xml中引入下面的servlet即可:
```
<servlet>
		<servlet-name>DispatcherServlet</servlet-name>
		<servlet-class>org.light4j.framework.DispatcherServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>DispatcherServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
```
如果上面没有问题，启动项目之后，在浏览器输入:http://127.0.0.1/light-framework-mvc-sample/customer<br>
就可以进行测试了。<br>