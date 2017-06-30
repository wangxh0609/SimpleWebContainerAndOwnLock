package com.hust.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;



/**
 * @功能 自定义线程池
 * @author dell
 *
 * @param <Job>
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

	private static final int MAX_WORKER_NUMBERS=10;//线程池最大数量
	private static final int DEFALUT_WORKER_NUMBERS=5;//线程池默认数量
	private static final int MIN_WORKER_NUMBERS=1;//线程池最小数量
	
	//工作列表
	private final LinkedList<Job> jobs=new LinkedList<Job>();
	
	//工作者列表
	private final List<Worker> workers=Collections.synchronizedList(new ArrayList<Worker>());
	
	private int workerNum=DEFALUT_WORKER_NUMBERS;
	
	//线程编号
	private AtomicLong threadNum=new AtomicLong();
	
	public DefaultThreadPool(){
		initializeWorkers(DEFALUT_WORKER_NUMBERS);
	}
	
	public DefaultThreadPool(int num){
		workerNum=num>MAX_WORKER_NUMBERS?MAX_WORKER_NUMBERS:num<MIN_WORKER_NUMBERS?MIN_WORKER_NUMBERS:num;
		initializeWorkers(workerNum);
	}
	
	//初始化工作者线程
	private void initializeWorkers(int num){
		for(int i=0;i<num;i++){
			Worker worker=new Worker();
			workers.add(worker);
			Thread thread=new Thread(worker,"ThreadPool-Worker-"+threadNum.incrementAndGet());
			thread.start();
		}
	}
	
	@Override
	public void execute(Job job) {
		if(job!=null){
			synchronized(jobs){
				jobs.add(job);
				jobs.notify();
			}
		}
	}

	@Override
	public void shutdown() {
		for(Worker worker:workers){
			worker.shutdown();
		}

	}

	@Override
	public void addWorkers(int num) {
		synchronized(jobs){
			if(num+this.workerNum>MAX_WORKER_NUMBERS){
				num=MAX_WORKER_NUMBERS-this.workerNum;
			}
			initializeWorkers(num);
			this.workerNum+=num;
		}

	}

	@Override
	public void removeWorker(int num) {
		synchronized(jobs){
			if(num>=this.workerNum){
				throw new IllegalArgumentException("beyond wordNum");
			}
			int count=0;
			while(count<num){
				Worker worker=workers.get(count);
				if(workers.remove(worker)){
					worker.shutdown();
					count++;
				}
			}
			this.workerNum-=count;
		}
	}

	@Override
	public int getJobSize() {		
		return jobs.size();
	}
	
	
	//工作者，消费任务
	class Worker implements Runnable{
		private volatile boolean running=true;

		@Override
		public void run() {
			while(running){
				Job job=null;
				synchronized(jobs){
					while(jobs.isEmpty()){
						try {
							jobs.wait();
						} catch (InterruptedException e) {
							//接受到外部中断
							Thread.currentThread().interrupt();
							return;
						}
					}
					job=jobs.removeFirst();
				}
				if(job!=null){
					try{
						job.run();
					}catch(Exception e){
						
					}
				}
			}					
		}
		public void shutdown(){
			running=false;
		}
		
	}
	
}
