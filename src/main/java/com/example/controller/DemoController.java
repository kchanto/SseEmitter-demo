package com.example.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.component.NotificationsHystrixProxy;
import com.example.miner.CoinMiner;
import com.example.model.NotificationsResponse;

import io.jmnarloch.spring.boot.rxjava.async.ObservableSseEmitter;

@CrossOrigin()
@RestController
public class DemoController {
	
	@Autowired
	private NotificationsHystrixProxy proxy;

	@Autowired
	private CoinMiner coinMiner;

	@RequestMapping("/mine/{count}")
	public SseEmitter mine(@PathVariable int count) {

		ExecutorService executorService = Executors.newFixedThreadPool(count);

		final SseEmitter sseEmitter = new SseEmitter();
		coinMiner.mineMany(count, executorService)
			.scan(BigDecimal::add)
			.subscribe(
				value -> notifyProgress(sseEmitter, value), 
					sseEmitter::completeWithError, sseEmitter::complete
				);
		return sseEmitter;
	}

	private void notifyProgress(SseEmitter sseEmitter, BigDecimal value) {
		try {
			sseEmitter.send(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/obs/{count}")
	public ObservableSseEmitter<BigDecimal> messages(@PathVariable int count) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(count);
		
		return new ObservableSseEmitter<BigDecimal>(coinMiner.mineMany(count, executorService).scan(BigDecimal::add));
	}

	@RequestMapping("/notif/")
	public ObservableSseEmitter<NotificationsResponse> messages() {
		return new ObservableSseEmitter<NotificationsResponse>(proxy.getNotifications());
	}

}