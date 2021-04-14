package com.mihadev.zebra.async;

import com.mihadev.zebra.service.AbonService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AbonsEventListener {

    private final AbonService abonService;

    public AbonsEventListener(AbonService abonService) {
        this.abonService = abonService;
    }

    @EventListener
    @Async
    public void refreshCacheAsync(RefreshAbonsCacheEvent event) {
        System.out.println("Thread.currentThread(): " + Thread.currentThread());
        abonService.refreshAbonCache();
    }
}
