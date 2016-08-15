package okaiyi.mockingjay.queue.task;
/**
 * 线程任务,泛型T用来指定无法被持久化或者保存的内容,例如Spring所管理的service或者bean
 */
public interface Tasker{
	/**
	 * 执行任务,并返回任务执行结果
	 */
	TaskResult doTask();
	/**
	 * 获取该任务执行的唯一ID，同一操作的任务,ID必须一致
	 * @return
	 */
	String getTaskId();
}
