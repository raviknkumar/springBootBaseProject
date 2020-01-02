package com.ninjacart.sample;

import org.eclipse.jetty.server.AsyncNCSARequestLog;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;

@SpringBootApplication
@ComponentScan("com.ninjacart")
@EnableTransactionManagement
public class SampleApplication {

    @Bean
    public JettyServletWebServerFactory jettyEmbeddedServletContainerFactory(
            @Value("${server.port}") final int port,
            @Value("${server.maxThreads:128}") final int maxThreads,
            @Value("${server.minThreads:64}") final int minThreads,
            @Value("${server.idleTimeout:180000}") final int idleTimeout,
            @Value("${server.accesslog.dir:.}") final String logDir,
            @Value("${server.accesslog.enabled:true}") final boolean accessLogEnabled) {
        final JettyServletWebServerFactory factory = new JettyServletWebServerFactory(port);
        factory.addServerCustomizers((JettyServerCustomizer) server -> {
            HandlerCollection handlers = new HandlerCollection();
            for (Handler handler : server.getHandlers()) {
                handlers.addHandler(handler);
            }
            if (accessLogEnabled) {
                handlers.addHandler(createRequestLogHandler(logDir));
            }
            server.setHandler(handlers);
            final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
            threadPool.setMaxThreads(maxThreads);
            threadPool.setMinThreads(minThreads);
            threadPool.setIdleTimeout(idleTimeout);
        });
        return factory;
    }

    private RequestLogHandler createRequestLogHandler(String logDir) {
        AsyncNCSARequestLog log = new AsyncNCSARequestLog();
        log.setFilename(logDir + File.separator + "yyyy_mm_dd.request.log");
        log.setFilenameDateFormat("yyyy_MM_dd");
        log.setExtended(true);
        log.setLogServer(true);
        log.setLogLatency(true);
        log.setPreferProxiedForAddress(true);
        log.setRetainDays(30);
        log.setAppend(true);
        log.setLogCookies(false);
        log.setLogTimeZone("UTC");
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        requestLogHandler.setRequestLog(log);
        return requestLogHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
