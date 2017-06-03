package org.light4j.framework.mvc.sample.service;
import java.util.List;
import java.util.Map;

import org.light4j.framework.mvc.annotatiom.Service;
import org.light4j.framework.mvc.helper.DataBaseHelper;
import org.light4j.framework.mvc.sample.model.Customer;
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
