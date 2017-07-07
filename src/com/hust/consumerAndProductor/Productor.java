package com.hust.consumerAndProductor;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Productor implements Runnable{
	private LinkedList jobs=null;
	private ReentrantLock lock=null;
	private Condition notfull=null;
	private Condition notEmpty=null;
	private volatile boolean running=false;
	
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
			System.out.println("������������һ����Ŀǰ��������"+jobs.size());
			notEmpty.signal();
		}finally{
			lock.unlock();
		}
		
	}
	@Override
	public void run() {
		while(running&&!Thread.currentThread().isInterrupted()){
			addJobs(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("����ִ������");
					
					try {
						Thread.sleep(500);
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
