package com.example.controller.interfaces;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import rx.Observable;
import rx.Single;

public interface CoinMinerTxn {
	 default BigDecimal mine() {
		return new BigDecimal(1);
    }

	 public class Futures {
		 
		    public static <T> Observable<T> toObservable(CompletableFuture<T> future) {
		        return Observable.create(subscriber ->
		                future.whenComplete((result, error) -> {
		                    if (error != null) {
		                        subscriber.onError(error);
		                    } else {
		                        subscriber.onNext(result);
		                        subscriber.onCompleted();
		                    }
		                }));
		    }
		 
		    public static <T> Single<T> toSingle(CompletableFuture<T> future) {
		        return Single.create(subscriber ->
		                future.whenComplete((result, error) -> {
		                    if (error != null) {
		                        subscriber.onError(error);
		                    } else {
		                        subscriber.onSuccess(result);
		                    }
		                }));
		    }
		 
		}

}