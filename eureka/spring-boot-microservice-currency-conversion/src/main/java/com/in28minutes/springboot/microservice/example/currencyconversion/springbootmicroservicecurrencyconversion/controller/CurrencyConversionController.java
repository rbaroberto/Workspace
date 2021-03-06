package com.in28minutes.springboot.microservice.example.currencyconversion.springbootmicroservicecurrencyconversion.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.springboot.microservice.example.currencyconversion.springbootmicroservicecurrencyconversion.bean.CurrencyConversionBean;
import com.in28minutes.springboot.microservice.example.currencyconversion.springbootmicroservicecurrencyconversion.proxy.ForexServiceProxy;

@RestController
public class CurrencyConversionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ForexServiceProxy proxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		/*
		 * without the feign client proxy Map<String, String> uriVariables = new
		 * HashMap<>(); uriVariables.put("from", from); uriVariables.put("to", to);
		 * ResponseEntity<CurrencyConversionBean> responseEntity = new
		 * RestTemplate().getForEntity(
		 * "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
		 * CurrencyConversionBean.class, uriVariables); CurrencyConversionBean response
		 * = responseEntity.getBody();
		 */

		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);
		
		response.setTotalCalculatedAmount(quantity.multiply(response.getConversionMultiple()));
		return response;

	}
}