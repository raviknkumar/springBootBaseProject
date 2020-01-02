package com.ninjacart.sample.id;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.netflix.config.DynamicPropertyFactory;

@Service
public class TimeBasedIdGeneratorService {
    private static final AtomicInteger sequenceTill999 = new AtomicInteger(0);
    private static final int MAX_SEQUENCE = 999;

    public String getUniqueId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
        String datePrefix = dateFormat.format(new Date());
        String serverId = getServiceId(); //ensure this is fixed length as well, say 3 chars
        String uniqueNess = String.format("%04d", Math.abs(Thread.currentThread().getId() % 10000));
        String suffix = getNextSequence();
        return datePrefix + serverId + uniqueNess + suffix;
    }

    private String getNextSequence() {
        if (sequenceTill999.get() == MAX_SEQUENCE) {
                    sequenceTill999.set(0);
        }
        return String.format("%03d", sequenceTill999.incrementAndGet());
    }
    
    private String getServiceId() {
        return DynamicPropertyFactory.getInstance().getStringProperty("persistence.ThreeCharServiceId","001").getValue();
    }
}