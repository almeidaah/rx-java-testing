import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class SourceConsumerTest {
    
    @Test
    public void testThreadExecution() throws Exception {

	ThreadPoolExecutor exec = new ThreadPoolExecutor(20, 64, 40, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
	exec.allowCoreThreadTimeOut(true);

	Scheduler s = Schedulers.from(exec);

	Observable.from(Arrays.asList("one", "two", "three"))
			.doOnNext(word -> System.out.printf("%s uses thread %s%n", word, Thread.currentThread().getName())).subscribeOn(s)
				.subscribe(word -> System.out.println(word));

    }

    @Test
    public void rangeLambdaTest() throws Exception {
	Observable.range(1, 15).subscribe(number -> System.out.println(number), error -> System.out.println("error"), () -> System.out.println("completed"));
    }
}
