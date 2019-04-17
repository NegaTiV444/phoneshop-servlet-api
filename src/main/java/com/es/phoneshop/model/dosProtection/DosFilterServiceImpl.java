package com.es.phoneshop.model.dosProtection;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DosFilterServiceImpl implements DosFilterService {

    private DosFilterServiceImpl(){}

    private static class SingletonHandler{
        static final DosFilterService INSTANCE = new DosFilterServiceImpl();
    }

    public static DosFilterService getInstance(){
        return SingletonHandler.INSTANCE;
    }

    private Map<String, AtomicInteger> ipCallMap = new ConcurrentHashMap<>();

    private static final int REQUEST_LIMIT = 30;
    private static final int RESET_INTERVAL = 60 * 1000;
    private volatile Date lastResetTime = new Date();


    @Override
    public boolean isIpAllowed(String ip) {
        reset();
        if (ipCallMap.containsKey(ip)){
            AtomicInteger callCounter = ipCallMap.get(ip);
            return callCounter.incrementAndGet() < REQUEST_LIMIT;
        } else {
            ipCallMap.put(ip, new AtomicInteger(1));
            return true;
        }
    }


    private void reset(){
        if (System.currentTimeMillis() - lastResetTime.getTime() > RESET_INTERVAL) {
            ipCallMap.clear();
            lastResetTime = new Date();
        }
    }
}
