package com.hust.consumerAndProductor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;



public class Worker implements Runnable {
	private LinkedList jobs=null;
	private ReentrantLock lock=null;
	private Condition notfull=null;
	private Condition notEmpty=null;
	private volatile boolean running=false;
	
	public Worker(LinkedList jobs,ReentrantLock lock,Condition notEmpty,Condition notfull){		
		this.jobs=jobs;
		this.lock=lock;
		this.notEmpty=notEmpty;
		this.notfull=notfull;
		running=true;
	}
	public void shutDown(){
		this.running=false;
	}
	
	@Override
	public void run() {
		while(running){
			Runnable job=null;
			lock.lock();
			try{
				while(jobs.isEmpty()){
					try {
						notEmpty.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block				
					}
				}
				job=(Runnable)jobs.removeFirst();		
				notfull.signal();
			}finally{
				lock.unlock();
			}	
			if(job!=null){
				job.run();
			}
		}
	}

}


