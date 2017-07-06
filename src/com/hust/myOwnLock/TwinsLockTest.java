package com.hust.myOwnLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TwinsLockTest {

	public static void main(String[] args) {
		//ReentrantLock locktest=new ReentrantLock();
		
		
		final Lock lock=new TwinsLock();
		for(int i=0;i<10;i++){
			Thread thread=new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						lock.lock();
						try{
							Thread.sleep(1000);
							System.out.println(Thread.currentThread().getName());
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							lock.unlock();
						}
					}
					
				}
			});
			
			//thread.setDaemon(true);
			thread.start();
		}
		
	}

}
