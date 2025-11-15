package com.nrgserver.ergovision.shared.infrastructure.config;

import com.nrgserver.ergovision.iam.infrastructure.tokens.jwt.BearerTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

@Configuration
public class WebSocketSecurityConfig implements org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer {

    private final BearerTokenService tokenService;
    private final UserDetailsService userDetailsService;

    public WebSocketSecurityConfig(
            BearerTokenService tokenService,
            @Qualifier("defaultUserDetailsService") UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> tokenParam = accessor.getNativeHeader("token");
                    String token = (tokenParam != null && !tokenParam.isEmpty()) ? tokenParam.get(0) : null;

                    if (token != null && !token.isEmpty()) {
                        try {
                            String username = tokenService.getUserNameFromToken(token);
                            if (username != null && !username.isBlank()) {
                                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                                if (tokenService.validateToken(token)) {
                                    UsernamePasswordAuthenticationToken authentication =
                                            new UsernamePasswordAuthenticationToken(
                                                    userDetails, null, userDetails.getAuthorities());

                                    accessor.setUser(authentication);
                                    SecurityContextHolder.getContext().setAuthentication(authentication);
                                }
                            }
                        } catch (Exception e) {
                            System.err.println("WebSocket authentication failed: " + e.getMessage());
                        }
                    } else {
                        // Optional debug: missing token on CONNECT frame
                        // System.out.println("WebSocket CONNECT without token");
                    }
                }

                return message;
            }
        });
    }
}
