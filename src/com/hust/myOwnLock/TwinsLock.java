package com.hust.myOwnLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {
	
	private final MySync sync=new MySync(4);
	
	private static final class MySync extends AbstractQueuedSynchronizer{
				
		
		MySync(int count){
			if(count<=0){
				throw new IllegalArgumentException("count must lager than zero");
			}
			setState(count);
		}
		
		public int tryAcquireShared(int reduceCount){
			for(;;){
				int current=getState();
				int newCount=current-reduceCount;
				if(newCount<0||compareAndSetState(current, newCount)){					
					return newCount;						
				}
			}
				
		}
		
		public boolean tryReleaseShared(int returnCount){
			for(;;){
				int current=getState();
				int newCount=current + returnCount;				
				if(compareAndSetState(current, newCount)){
					return true;
				}
			}
		}
	}

	@Override
	public void lock() {
		sync.acquireShared(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		sync.releaseShared(1);

	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
