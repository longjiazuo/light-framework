package org.light4j.framework.mvc.sample.controller;

import java.util.List;
import java.util.Map;

import org.light4j.framework.mvc.annotatiom.Action;
import org.light4j.framework.mvc.annotatiom.Controller;
import org.light4j.framework.mvc.annotatiom.Inject;
import org.light4j.framework.mvc.bean.Data;
import org.light4j.framework.mvc.bean.Param;
import org.light4j.framework.mvc.bean.View;
import org.light4j.framework.mvc.sample.model.Customer;
import org.light4j.framework.mvc.sample.service.CustomerService;

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