package com.nrgserver.ergovision.shared.infrastructure.keepalive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Self-ping service to keep the application alive on free hosting platforms
 * that spin down inactive instances (like Render free tier).
 *
 * This service pings the health endpoint every 25 seconds to prevent the
 * instance from spinning down due to inactivity.
 */
@Service
@ConditionalOnProperty(name = "keepalive.enabled", havingValue = "true", matchIfMissing = false)
public class KeepAliveService {

    private static final Logger logger = LoggerFactory.getLogger(KeepAliveService.class);

    private final RestTemplate restTemplate;
    private final String healthUrl;
    private volatile boolean applicationReady = false;

    public KeepAliveService(
            @Value("${keepalive.url:http://localhost:8080/actuator/health}") String healthUrl) {
        this.restTemplate = new RestTemplate();
        this.healthUrl = healthUrl;
        logger.info("KeepAlive service initialized. Will ping: {} (after application is ready)", healthUrl);
    }

    /**
     * Listen for ApplicationReadyEvent to ensure the application is fully started
     * before beginning self-ping operations.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        this.applicationReady = true;
        logger.info("KeepAlive service activated. Application is ready, will start pinging in 60 seconds.");
    }

    /**
     * Pings the health endpoint every 25 seconds (within the 30-second window)
     * to keep the service active on free hosting platforms.
     *
     * Only starts pinging 60 seconds after application is ready to ensure
     * all endpoints (including CORS configuration) are fully initialized.
     */
    @Scheduled(fixedDelay = 25000, initialDelay = 60000)
    public void ping() {
        if (!applicationReady) {
            logger.debug("Application not ready yet, skipping KeepAlive ping");
            return;
        }

        try {
            String response = restTemplate.getForObject(healthUrl, String.class);
            logger.debug("KeepAlive ping successful: {}", response);
        } catch (Exception e) {
            logger.warn("KeepAlive ping failed: {}", e.getMessage());
        }
    }
}
