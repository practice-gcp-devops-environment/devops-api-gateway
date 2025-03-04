package org.example.devopsapigateway.common.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class OutgoingRequestLoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).doOnSubscribe(subscription -> {
            ServerHttpRequest request = exchange.getRequest();
            URI outgoingUrl = request.getURI();
            String method = request.getMethod().toString();

            // 모든 헤더 가져오기
            HttpHeaders headers = request.getHeaders();
            StringBuilder headerLog = new StringBuilder();
            headers.forEach((key, value) -> headerLog.append(key).append(": ").append(value).append("\n"));

            System.out.println("[OUTGOING REQUEST] " + method + " " + outgoingUrl);
            System.out.println("[HEADERS] \n" + headerLog);
        });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}