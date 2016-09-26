package okaiyi.mockingjay.queue;

import okaiyi.mockingjay.queue.task.TaskExecutor;
import okaiyi.mockingjay.queue.task.TaskMonitor;
import okaiyi.mockingjay.queue.task.TaskResult;
import okaiyi.mockingjay.queue.task.Tasker;

public class TaskExcutorTest {
	public static void main(String[] args) {
		final TaskExecutor te = new TaskExecutor(new TaskMonitor() {

			@Override
			public void taskCreate(Tasker tasker) {

			}

			@Override
			public void destory(Tasker[] tasker) {
				System.out.println("destory:" + tasker.length);
				for(Tasker task:tasker){
					task.doTask();
				}
			}

			@Override
			public void beforeExceture(Tasker tasker) {

			}

			@Override
			public void afterExceture(Tasker tasker, TaskResult result) {

			}
		});
		new Thread(new Runnable() {

			@Override
			public void run() {
				Tasker tasker = new Tasker() {
					private int count;

					@Override
					public TaskResult doTask() {
						System.out.println("Thread 1:" + count);
						count++;
						return TaskResult.getTaskSuccess();
					}

					@Override
					public String getTaskId() {
						return "111";
					}
				};
				for (int i = 0; i < 10; i++) {
					te.executorTask(tasker);
				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				Tasker tasker = new Tasker() {
					private int count;

					@Override
					public TaskResult doTask() {
						System.out.println("Thread 2:" + count);
						count++;
						return TaskResult.getTaskSuccess();
					}
					@Override
					public String getTaskId() {
						return "222";
					}
				};
				for (int i = 0; i < 100; i++) {
					te.executorTask(tasker);
				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				Tasker tasker = new Tasker() {
					private int count;

					@Override
					public TaskResult doTask() {
						System.out.println("Thread 3:" + count);
						count++;
						return TaskResult.getTaskSuccess();
					}
					@Override
					public String getTaskId() {
						return "333";
					}
				};
				for (int i = 0; i < 100; i++) {
					if(i==20){
						System.exit(0);
					}
					te.executorTask(tasker);
				}
			}
		}).start();
	}
}
