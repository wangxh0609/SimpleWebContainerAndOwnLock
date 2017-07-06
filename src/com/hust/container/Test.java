package com.hust.container;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test {

	public static void main(String[] args) throws Exception {
		SimpleHttpServer shs=new SimpleHttpServer();
		
		shs.setBasePath("F:\\java_workspace\\MyOwnWebContainer\\bin");
		shs.start();
		//AbstractQueuedSynchronizer 
		//ReentrantLock
		//ReentrantReadWriteLock
		//ConcurrentHashMap<K, V>
		//RunnableFuture<V>
	}

}
