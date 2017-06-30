package com.hust.container;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Test {

	public static void main(String[] args) throws Exception {
		SimpleHttpServer shs=new SimpleHttpServer();
		
		shs.setBasePath("F:\\java_workspace\\MyOwnWebContainer\\bin");
		shs.start();
		//AbstractQueuedSynchronizer 
	}

}
