package okaiyi.mockingjay.queue.task;
/**
 * 任务存储
 *
 */
public interface TaskStorage {
	/**
	 * 存储任务
	 * @param task
	 */
	void saveTask(Tasker... task);
	/**
	 * 恢复任务
	 * @return
	 */
	Tasker[] recovery();
}
