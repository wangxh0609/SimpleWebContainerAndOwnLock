package com.hust.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyBoundedQueue<T> {
	private Object[] items;
	private int addIndex,removeIndex,count;
	private Lock lock=new ReentrantLock();
	private Condition notEmpty=lock.newCondition();
	private Condition notFull=lock.newCondition();
	
	public MyBoundedQueue(int size){
		items=new Object[size];
	}

	public void add(T t) throws InterruptedException {
		lock.lock();
		try{
			while(count==items.length){
				notFull.await();
			}
			items[addIndex]=t;
			if(++addIndex==items.length){
				addIndex=0;
			}
			++count;
			notEmpty.signal();
		}finally{
			lock.unlock();
		}
	}
	
	public T remove() throws InterruptedException{
		lock.lock();
		try{
			while(count==0){
				notEmpty.await();
			}
			T obj=(T)items[removeIndex];
			if(++removeIndex==items.length){
				removeIndex=0;
			}
			--count;
			notFull.signal();
			return obj;
		}finally{
			lock.unlock();
		}
		
	}
}
