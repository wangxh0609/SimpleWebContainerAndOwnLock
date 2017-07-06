package com.hust.ForkAndJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

	public static void main(String[] args) {
		ForkJoinPool forkjoinpool=new ForkJoinPool();
		CountTask task=new CountTask(1,100000);
		Future<Integer> future=forkjoinpool.submit(task);
		try{
			System.out.println(future.get());
		}catch(Exception e){
			
		}
	}

}
class CountTask extends RecursiveTask<Integer>{

	private static final int THRESHOLD=10000;//ãÐÖµ
	private int start;
	private int end;
	
	public CountTask(int start,int end){
		this.start=start;
		this.end=end;
	}
	
	@Override
	protected Integer compute() {
		int sum=0;
		boolean canCompute=(end-start)<=THRESHOLD;
		if(canCompute){
			for(int i=start;i<=end;i++){
				sum+=i;
			}
		}else{
			int middle=(start+end)/2;
			CountTask leftTask=new CountTask(start,middle);
			CountTask rightTask=new CountTask(middle+1,end);
			leftTask.fork();
			rightTask.fork();
			int leftres=leftTask.join();
			int rightres=rightTask.join();			
			sum=leftres+rightres;
		}
		return sum;
	}
	
}
