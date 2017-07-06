package com.hust.consumerAndProductor;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Productor implements Runnable{
	private LinkedList jobs=null;
	private ReentrantLock lock=null;
	private Condition notfull=null;
	private Condition notEmpty=null;
	private boolean running=false;
	
	public Productor(LinkedList jobs,ReentrantLock lock,Condition notEmpty,Condition notfull){		
		this.jobs=jobs;
		this.lock=lock;
		this.notEmpty=notEmpty;
		this.notfull=notfull;
		running=true;
	}
	public void addJobs(Runnable runnable){
		lock.lock();
		try{
			while(jobs.size()>=10){
				try {
					notfull.await();
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			}
			jobs.add(runnable);	
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("生产者生产了一个！目前任务数是"+jobs.size());
			notEmpty.signal();
		}finally{
			lock.unlock();
		}
		
	}
	@Override
	public void run() {
		while(running){
			addJobs(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("正在执行任务！");
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}
				}
			});
		}
		
	}
	
	public void shutdown(){
		this.running=false;
	}
}
