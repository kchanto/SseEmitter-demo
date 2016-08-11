package com.example.miner;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.example.controller.interfaces.CoinMinerTxn;

import rx.Observable;


@Component
public class CoinMiner implements CoinMinerTxn {

	
//    public CompletableFuture<BigDecimal> mineAsync(ExecutorService executorService) {
//    	System.out.println("Executor");
//    	System.out.println(executorService.toString());
//        return CompletableFuture.supplyAsync(this::mine, executorService);
//    }
	
//	@SuppressWarnings("rawtypes")
//	public 	Observable<BigDecimal> mineMany(int count, ExecutorService executorService) {
//	    final ReplaySubject<BigDecimal> subject = ReplaySubject.create();
//	    final List<CompletableFuture<BigDecimal>> futures = IntStream
//	            .range(0, count)
//	            .mapToObj(x -> mineAsync(executorService))
//	            .collect(Collectors.toList());
//	    futures
//	            .forEach(future ->
//	                    future.thenRun(() -> subject.onNext(BigDecimal.ONE)));
//	 
//	    final CompletableFuture[] futuresArr = futures.toArray(new CompletableFuture[futures.size()]);
//	    CompletableFuture
//	            .allOf(futuresArr)
//	            .thenRun(subject::onCompleted);
//	 
//	    return subject;
//	}
	
	public Observable<BigDecimal> mineMany(int count, ExecutorService executorService) {
	    final List<Observable<BigDecimal>> observables = IntStream
	            .range(0, count)
	            .mapToObj(x -> mineAsync(executorService))
	            .collect(Collectors.toList());
	    return Observable.merge(observables);
	}
	 
	private Observable<BigDecimal> mineAsync(ExecutorService executorService) {
	    final CompletableFuture<BigDecimal> future = 
	         CompletableFuture.supplyAsync(this::mine, executorService);
	    return Futures.toObservable(future);
	}
	
	
	
}