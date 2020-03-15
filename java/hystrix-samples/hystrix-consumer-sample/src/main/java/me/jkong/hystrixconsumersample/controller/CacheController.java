package me.jkong.hystrixconsumersample.controller;

import me.jkong.hystrixconsumersample.utils.HttpClientUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 缓存服务的接口
 * @author Administrator
 *
 */
@RestController
public class CacheController {

	@GetMapping("/change/product")
	public String changeProduct(Long productId) {
		// 拿到一个商品id
		// 调用商品服务的接口，获取商品id对应的商品的最新数据
		// 用HttpClient去调用商品服务的http接口
		String url = "http://127.0.0.1:8080/products/" + productId;
		String response = HttpClientUtils.sendGetRequest(url);
		System.out.println(response);  
		
		return response;
	}
	
}
