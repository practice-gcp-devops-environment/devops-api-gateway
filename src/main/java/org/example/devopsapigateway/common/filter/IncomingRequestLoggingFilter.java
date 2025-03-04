package org.example.devopsapigateway.common.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class IncomingRequestLoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 전체 URI 가져오기
        String fullUrl = request.getURI().toString();
        String method = request.getMethod().toString();

        // 모든 헤더 가져오기
        HttpHeaders headers = request.getHeaders();
        StringBuilder headerLog = new StringBuilder();
        headers.forEach((key, value) -> headerLog.append(key).append(": ").append(value).append("\n"));

        System.out.println("[INCOMING REQUEST] " + method + " " + fullUrl);
        System.out.println("[HEADERS] \n" + headerLog);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
