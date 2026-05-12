package org.example.farmersupport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Health & Latency endpoint for Prometheus monitoring
 * Simple endpoint to check service availability and measure response time
 */
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Autowired(required = false)
    private DataSource dataSource;

    private static final long START_TIME = System.currentTimeMillis();

    /**
     * Simple health check endpoint
     * Returns 200 OK if service is up
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getHealthStatus() {
        long startTime = System.currentTimeMillis();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "farmer-support");
        response.put("timestamp", System.currentTimeMillis());
        response.put("uptime_ms", System.currentTimeMillis() - START_TIME);

        // Check database connection if available
        if (dataSource != null) {
            try (Connection conn = dataSource.getConnection()) {
                response.put("database", "UP");
            } catch (Exception e) {
                response.put("database", "DOWN");
                response.put("database_error", e.getMessage());
            }
        }

        // Calculate latency
        long latency = System.currentTimeMillis() - startTime;
        response.put("latency_ms", latency);

        return ResponseEntity.ok(response);
    }

    /**
     * Prometheus-compatible metrics endpoint
     * Returns metrics in Prometheus format
     */
    @GetMapping("/metrics")
    public ResponseEntity<String> getMetrics() {
        long startTime = System.currentTimeMillis();
        long uptime = System.currentTimeMillis() - START_TIME;
        long latency = System.currentTimeMillis() - startTime;

        StringBuilder metrics = new StringBuilder();
        metrics.append("# HELP farmer_support_up Service is up or down\n");
        metrics.append("# TYPE farmer_support_up gauge\n");
        metrics.append("farmer_support_up 1\n\n");

        metrics.append("# HELP farmer_support_uptime_ms Service uptime in milliseconds\n");
        metrics.append("# TYPE farmer_support_uptime_ms gauge\n");
        metrics.append("farmer_support_uptime_ms ").append(uptime).append("\n\n");

        metrics.append("# HELP farmer_support_latency_ms Response latency in milliseconds\n");
        metrics.append("# TYPE farmer_support_latency_ms gauge\n");
        metrics.append("farmer_support_latency_ms ").append(latency).append("\n");

        return ResponseEntity.ok(metrics.toString());
    }

    /**
     * Simple ping endpoint
     * Minimal response for lightweight checks
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}

