package com.knx.gateway.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.misc.VM;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class DirectMemoryMonitorTask {


    @Scheduled(fixedRate = 3000)
    public void run(){
        try {
            Class c = Class.forName("java.nio.Bits");


            // A user-settable upper limit on the maximum amount of allocatable
            // direct buffer memory.  This value may be changed during VM
            // initialization if it is launched with "-XX:MaxDirectMemorySize=<size>".
//            private static volatile long maxMemory = VM.maxDirectMemory();
//            private static final AtomicLong reservedMemory = new AtomicLong();
//            private static final AtomicLong totalCapacity = new AtomicLong();
//            private static final AtomicLong count = new AtomicLong();

            Field field1 = c.getDeclaredField("maxMemory");
            field1.setAccessible(true);
            Field field2 = c.getDeclaredField("reservedMemory");
            field2.setAccessible(true);
            synchronized (c) {
                Long max =  (Long) field1.get(null);
                AtomicLong reserve = (AtomicLong) field2.get(null);
                log.info("DirectMemory, maxMemory:{}, reservedMemory:{}", max , reserve.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
