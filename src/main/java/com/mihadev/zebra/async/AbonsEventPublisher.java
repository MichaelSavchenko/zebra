package com.mihadev.zebra.async;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AbonsEventPublisher {
    private final ApplicationEventPublisher publisher;

    public AbonsEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishRefreshCacheEvent() {
        publisher.publishEvent(new RefreshAbonsCacheEvent());
    }
}
