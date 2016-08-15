package okaiyi.mockingjay.queue.task;
/**
 * 任务对象监控
 */
public interface TaskMonitor {
	/**
	 * 任务被创建
	 * @param tasker
	 */
	void taskCreate(Tasker tasker);
	/**
	 * 任务执行之前调用
	 * @param tasker
	 */
	void beforeExceture(Tasker tasker);
	/**
	 * 任务执行完成后调用
	 * @param tasker 
	 * @param result 任务执行结果
	 */
	void afterExceture(Tasker tasker,TaskResult result);
	/**
	 * 任务异常终止
	 */
	void destory(Tasker[] tasker);
}
