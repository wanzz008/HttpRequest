package com.wzz.http.http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发库中的BlockingQueue是一个比较好玩的类，顾名思义，就是阻塞队列。
 * 该类主要提供了两个方法put()和take()，前者将一个对象放到队列中，如果队列已经满了，就等待直到有空闲节点；
 * 后者从head取一个对象，如果没有对象，就等待直到有可取的对象。
 */
public class ThreadPoolManager {
    // 单例模式
    private static ThreadPoolManager manager = new ThreadPoolManager();

    public static ThreadPoolManager getManager(){
        return manager;
    }

    //请求队列   容量，大量的插入操作，阻塞
    //    LinkedBlockingQueue ，是一个阻塞的线程安全的队列，底层采用链表实现。
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue(); // 构造的时候若没有指定大小，则默认大小为Integer.MAX_VALUE，当然也可以在构造函数的参数中指定大小

    /** 1.把调用层取过来的任务放入到请求队列中 */
    public void execute(Runnable runnable){
        try {
            /** put方法：若向队尾添加元素的时候发现队列已经满了会发生阻塞一直等待空间，以加入元素。 */
            /** take: 若队列为空，发生阻塞，等待有元素。 */
            queue.put( runnable );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** 2.把队列中的任务放入到线程沲去进行执行 */
    private ThreadPoolExecutor executor;

    private ThreadPoolManager(){
        /**
         * 线程池初始化方法
         *
         * corePoolSize 核心线程池大小----10
         * maximumPoolSize 最大线程池大小----30
         * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
         * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
         * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列
         * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
         * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize + workQueue之和时,
         * 							即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
         * 						          任务会交给RejectedExecutionHandler来处理
         */
        executor = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10),mRejectedExecutionHandler);
        // 开始就把轮训队列的任务加进来
        executor.execute( new Task() );

    }
    // 拒绝策略：把失败的任务再重新加到请求队列里面
     private  RejectedExecutionHandler mRejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                Log.e("-----" , "error.............");
                queue.put( r );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    /** 轮训队列里的任务,有就放到线程池中执行 */
    private class Task implements Runnable{

        @Override
        public void run() {


            while (true){

                Runnable runnable = null ;
                try {
                    runnable = queue.take(); /** take: 若队列为空，发生阻塞，等待有元素。从而没任务时while(true)的循环也会卡在这 */

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 把任务再加到线程池中
                if (runnable != null ){
                    executor.execute( runnable );
                }
            }
        }
    }

}
