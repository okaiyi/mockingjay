package okaiyi.mockingjay.queue.task;
/**
 *  任务执行结果
 */
public class TaskResult {
	/**
	 * 阀值时间类型
	 *
	 */
	public  enum ThresholdType{
		MILLIS,SECODE,MINUTES,HOUR,DAY,MONTH,YEAR
	}
	
	/**
	 * 重新执行任务
	 */
	public static final int OPERATION_RETRY=1;
	/**
	 * 取消执行任务
	 */
	public static final int OPERATION__CANCEL=2;
	/**
	 * 无效值
	 */
	public static final int INVALID_VALUE=-1;
	
	private TaskResult(){
		
	}
	/**
	 * 任务执行结果,true表示执行成功,false表示执行失败
	 */
	private boolean result;
	/**
	 * 需要执行的操作
	 */
	private int operation;
	/**
	 * 阀值
	 */
	private int threshold;
	/**
	 * 阀值时间类型
	 */
	private ThresholdType thresholdType;
	/**
	 * 任务执行成功
	 * @return
	 */
	public static final TaskResult getTaskSuccess(){
		TaskResult tr=new TaskResult();
		tr.result=true;
		tr.threshold=INVALID_VALUE;
		tr.operation=INVALID_VALUE;
		return tr;
	}
	/**
	 * 任务执行失败,取消任务
	 * @return
	 */
	public static final TaskResult getTaskCancel(){
		TaskResult tr=new TaskResult();
		tr.result=false;
		tr.operation=OPERATION__CANCEL;
		tr.operation=INVALID_VALUE;
		return tr;
	}
	
	
	/**
	 * 任务是否执行成功
	 * @return
	 */
	public boolean isResult() {
		return result;
	}
	/**
	 * 获取操作
	 * @return OPERATION_RETRY 重新执行任务
	 * OPERATION__CANCEL 取消任务执行
	 */
	public int getOperation() {
		return operation;
	}
	/**
	 * 获取阀值
	 * @return
	 */
	public int getThreshold() {
		return threshold;
	}
	/**
	 * 获取时间类型
	 * @return
	 */
	public ThresholdType getThresholdType() {
		return thresholdType;
	}
	
}

