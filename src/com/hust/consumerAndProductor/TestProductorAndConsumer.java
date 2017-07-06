package com.hust.consumerAndProductor;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductorAndConsumer {

	public static void main(String[] args) {
		ReentrantLock lock=new ReentrantLock();
		Condition notfull=lock.newCondition();
		Condition notEmpty=lock.newCondition();
		LinkedList  list=new LinkedList<>();
		
		boolean running=true;
		for(int i=0;i<10;i++){
			Thread t=new Thread(new Worker(list,lock,notEmpty,notfull));
			t.start();
		}
		for(int i=0;i<1;i++){
			Thread t=new Thread(new Productor(list, lock, notEmpty, notfull));
			t.start();
		}
	}

}
