package com.green.webclient.order;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.green.webclient.services.CustomerService;
import com.green.webclient.shopcart.CartInfo;
import com.green.webclient.shopcart.CartLineInfo;
import com.green.webmodels.entities.Order;
import com.green.webmodels.entities.OrderDetail;
import com.green.webmodels.enumerate.OrderStatus;

import net.bytebuddy.utility.RandomString;

@Service
public class OrderServices {

	@Autowired
	CustomerService customerService;

	@Autowired
	OrderRepository orderRepository;

	public String saveOrder(CartInfo cartInfo) {
		Order order = new Order();
		Set<OrderDetail> listOrderDetail = new HashSet<OrderDetail>();

		SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

		order.setTotalPrice(cartInfo.totalCartInfo());
		order.setCustomer(customerService.getByEmail(authentication.getName()));

		for(CartLineInfo cartLineInfo : cartInfo.getCartLines()) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setPrice(cartLineInfo.getTotal());
			System.out.println(cartLineInfo.getQuantity());
			orderDetail.setQuantity(cartLineInfo.getQuantity());
			orderDetail.setProductName(cartLineInfo.getProduct().getName());
			listOrderDetail.add(orderDetail);
		}
		order.setOrderDetails(listOrderDetail);
		order.setStatus(OrderStatus.NEW);
		order.setOrderCode(RandomString.make(45));

		orderRepository.save(order);

		return order.getOrderCode();
	}
	
	public Order getNewestOrderByCustomerID(Integer id)
	{
		return orderRepository.getNewestOrderByCustomerID(id);
	}
}