package okaiyi.mockingjay.queue.task;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * 任务调度执行池,该类应当在应用中仅被实例化一次
 * TaskPool pool=new TaskPool();
 * pool.executorTask(task);//执行任务
 */
public class TaskPool implements TaskStorage,TaskMonitor{
	private TaskExecutor[] executors;
	private TaskMonitor monitor;
	private TaskStorage storage;
	/**
	 * 初始化一个线程池
	 * @param poolSize
	 */
	public TaskPool(int poolSize){
		this(poolSize,null,null);
	}
	public TaskPool(int poolSize,TaskMonitor monitor){
		this(poolSize,monitor,null);
	}
	/**
	 * 初始化一个线程池，并指定线程执行过程中的监控
	 * @param poolSize
	 * @param monitor
	 */
	public TaskPool(int poolSize,TaskMonitor monitor,TaskStorage storage){
		this.executors=new TaskExecutor[poolSize];
		this.monitor=monitor;
		this.storage=storage;
		for(int i=0;i!=poolSize;++i){
			executors[i]=new TaskExecutor(this);
		}
		Tasker[] taskes=this.recovery();
		for(int i=0;i!=taskes.length;++i){
			Tasker task=taskes[i];
			executors[getExecutorSelector(task.getTaskId())].executorTask(task);
		}
	}
	/**
	 * 执行一个任务
	 * @param executorId 任务编号,统一操作任务需要有相同的编号
	 * @param tasker
	 */
	public void executorTask(Tasker tasker){
		String taskId=tasker.getTaskId();
		if(taskId==null||taskId.equals(""))throw new IllegalArgumentException("Argument executorId is null.");
		executors[getExecutorSelector(taskId)].executorTask(tasker);
	}
	//获取传入任务编号,计算出从执行器数组中取出的编号
	private int getExecutorSelector(String executorId){
		byte[] bytes=DigestUtils.md5(executorId);
		int count=0;
		for(byte bit:bytes){
			count+=bit;
		}
		return count%executors.length;
	}
	/**
	 * 获取任务监听器
	 * @return
	 */
	public TaskMonitor getMonitor() {
		return this;
	}
	/**
	 * 获取任务存储器
	 * @return
	 */
	public TaskStorage getStorage() {
		return this;
	}
	private boolean monitorIsNotNull(){
		return this.monitor!=null;
	}
	private boolean storageIsNotNull(){
		return this.storage!=null;
	}
	@Override
	public void taskCreate(Tasker tasker) {
		if(monitorIsNotNull()){
			monitor.taskCreate(tasker);
		}
	}
	@Override
	public void beforeExceture(Tasker tasker) {
		if(monitorIsNotNull()){
			monitor.beforeExceture(tasker);
		}
	}
	@Override
	public void afterExceture(Tasker tasker, TaskResult result) {
		if(monitorIsNotNull()){
			monitor.afterExceture(tasker, result);
		}
	}
	@Override
	public void destory(Tasker[] tasker) {
		if(monitorIsNotNull()){
			monitor.destory(tasker);
			if(storageIsNotNull()){
				storage.saveTask(tasker);
			}
		}
	}
	@Override
	public void saveTask(Tasker... task) {
		if(storageIsNotNull()){
			this.storage.saveTask(task);			
		}
	}
	@Override
	public Tasker[] recovery() {
		if(storageIsNotNull()){
			return this.storage.recovery();
		}
		return new Tasker[0];
	}
}
