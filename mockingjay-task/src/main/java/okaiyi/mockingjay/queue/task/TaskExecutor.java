package okaiyi.mockingjay.queue.task;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okaiyi.mockingjay.commons.logger.Logger;
/**
 * 任务执行器
 *
 */
public class TaskExecutor {
	private Logger logger=Logger.getDefaultLogger(TaskExecutor.class);
	private ConcurrentLinkedQueue<Tasker> queue;
	private Lock lock;
	private Condition empty;
	private TaskMonitor monitor;
	private boolean stopThread;
	private ExecutorService exec;
	//固定两个线程池大小,一个用来取任务,一个用来执行任务
	private static final int THREAD_SIZE=2;
	public TaskExecutor(TaskMonitor monitor) {
		queue = new ConcurrentLinkedQueue<Tasker>();
		lock = new ReentrantLock();
		stopThread = false;
		empty = lock.newCondition();
		if (monitor != null) {
			this.monitor = monitor;
		} else {
			this.monitor = new TaskMonitor() {

				@Override
				public void taskCreate(Tasker tasker) {
					
				}

				@Override
				public void beforeExceture(Tasker tasker) {

				}

				@Override
				public void afterExceture(Tasker tasker, TaskResult result) {

				}

				@Override
				public void destory(Tasker[] tasker) {

				}
			};
		}
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				stopThread = true;
				Tasker[] taskes = new Tasker[queue.size()];
				int index = 0;
				for (Iterator<Tasker> it = queue.iterator(); it.hasNext();) {
					taskes[index++] = it.next();
				}
				TaskExecutor.this.monitor.destory(taskes);
			}
		}));
		exec=Executors.newFixedThreadPool(THREAD_SIZE);
		exec.execute(new Runnable() {
			@Override
			public void run() {
				TaskExecutor.this.beginExecutor();
			}
		});
	}
	/**
	 * 执行任务
	 * @param task
	 */
	public void executorTask(final Tasker task){
		exec.execute(new Runnable() {
			@Override
			public void run() {
				TaskExecutor.this.putTask(task);
			}
		});
	}
	
	
	/**
	 * 往执行器中添加任务
	 * 
	 * @param task
	 */
	private void putTask(Tasker task) {
		lock.lock();
		try {
			queue.offer(task);
			this.monitor.taskCreate(task);
			if (!queue.isEmpty()) {
				empty.signal();
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 执行任务
	 */
	private void beginExecutor() {
		lock.lock();
		try {
			while (!stopThread) {
				if (queue.isEmpty()) {
					empty.await();
				}
				Tasker tasker = queue.poll();
				this.monitor.beforeExceture(tasker);
				TaskResult result = tasker.doTask();
				this.monitor.afterExceture(tasker, result);
			}
		} catch (InterruptedException e) {
			logger.exception(e);
		} finally {
			lock.unlock();
		}
	}
}
