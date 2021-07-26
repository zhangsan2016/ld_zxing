package com.ailiwean.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.ailiwean.core
 * @ClassName: RespectScalePool
 * @Description:
 * @Author: SWY
 * @CreateDate: 2020/8/22 3:51 PM
 */
class RespectScalePool extends ThreadPoolExecutor {

    public RespectScalePool(int corePoolSize,
                            int maximumPoolSize,
                            long keepAliveTime,
                            TimeUnit unit,
                            BlockingQueue<? extends Runnable> workQueue,
                            RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, (BlockingQueue<Runnable>) workQueue, handler);
    }

    /***
     *     线程池任务拒绝策略：
     *        目前会执行的两个线程
     *               GrayProcessThread ： {@link com.ailiwean.core.able.AbleManager#grayProcessHandler$delegate}
     *              CameraProcessThread ： {@link com.google.android.cameraview.BaseCameraView#cameraHandler}
     *
     */
    public static class RespectScalePolicy extends DiscardOldestPolicy {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (executor.isShutdown())
                return;
            if (!(r instanceof TypeRunnable) ||
                    !(executor instanceof RespectScalePool) ||
                    !(executor.getQueue() instanceof RespectScaleQueue)) {
                super.rejectedExecution(r, executor);
                return;
            }

            TypeRunnable typeRunnable = (TypeRunnable) r;
            RespectScaleQueue<?> respectScaleQueue = (RespectScaleQueue<?>) executor.getQueue();
            //舍弃同类型任务
            respectScaleQueue.poll(typeRunnable.type);
            executor.execute(r);
        }
    }
}
