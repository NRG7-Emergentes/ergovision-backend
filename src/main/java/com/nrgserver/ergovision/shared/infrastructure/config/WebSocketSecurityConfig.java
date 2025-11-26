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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    System.out.println("🔥 [WS Security] CONNECT frame received");

                    // 🔥 ESTRATEGIA MEJORADA: Buscar token en múltiples ubicaciones
                    String token = extractTokenFromMultipleSources(accessor);

                    System.out.println("🔥 [WS Security] Extracted token: " + (token != null ? "PRESENT (length: " + token.length() + ")" : "NULL"));

                    if (token != null && !token.isEmpty()) {
                        try {
                            System.out.println("🔥 [WS Security] Validating token...");
                            String username = tokenService.getUserNameFromToken(token);
                            System.out.println("🔥 [WS Security] Extracted username from token: " + username);

                            if (username != null && !username.isBlank()) {
                                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                                if (tokenService.validateToken(token)) {
                                    UsernamePasswordAuthenticationToken authentication =
                                            new UsernamePasswordAuthenticationToken(
                                                    userDetails, null, userDetails.getAuthorities());

                                    accessor.setUser(authentication);
                                    SecurityContextHolder.getContext().setAuthentication(authentication);
                                    System.out.println("🔥 [WS Security] ✅ User authenticated: " + username);
                                } else {
                                    System.err.println("🔥 [WS Security] ❌ Token validation failed");
                                }
                            } else {
                                System.err.println("🔥 [WS Security] ❌ Username is null or blank");
                            }
                        } catch (Exception e) {
                            System.err.println("🔥 [WS Security] ❌ Authentication failed: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }

                return message;
            }

            /**
             * 🔥 EXTRACCIÓN MEJORADA: Buscar token en múltiples fuentes
             */
            private String extractTokenFromMultipleSources(StompHeaderAccessor accessor) {
                // 1. Buscar en query parameters de la URL
                List<String> urlHeaders = accessor.getNativeHeader("url");
                if (urlHeaders != null && !urlHeaders.isEmpty()) {
                    String url = urlHeaders.get(0);
                    System.out.println("🔥 [WS Security] URL from headers: " + url);
                    String tokenFromUrl = extractTokenFromUrl(url);
                    if (tokenFromUrl != null) {
                        System.out.println("🔥 [WS Security] ✅ Found token in URL");
                        return tokenFromUrl;
                    }
                }

                // 2. Buscar en headers STOMP nativos
                List<String> tokenHeaders = accessor.getNativeHeader("token");
                if (tokenHeaders != null && !tokenHeaders.isEmpty()) {
                    String token = tokenHeaders.get(0);
                    System.out.println("🔥 [WS Security] ✅ Found token in STOMP headers");
                    return token;
                }

                // 3. Buscar en Authorization header
                List<String> authHeaders = accessor.getNativeHeader("Authorization");
                if (authHeaders != null && !authHeaders.isEmpty()) {
                    String authHeader = authHeaders.get(0);
                    if (authHeader.startsWith("Bearer ")) {
                        String token = authHeader.substring(7);
                        System.out.println("🔥 [WS Security] ✅ Found token in Authorization header");
                        return token;
                    }
                }


                return null;
            }

            /**
             * 🔥 EXTRAER token de la URL (SockJS usa este método)
             */
            private String extractTokenFromUrl(String url) {
                if (url == null) return null;

                try {
                    System.out.println("🔥 [WS Security] Parsing URL for token: " + url);

                    // Patrón para buscar token=valor en la URL
                    Pattern pattern = Pattern.compile("[?&]token=([^&]*)");
                    Matcher matcher = pattern.matcher(url);

                    if (matcher.find()) {
                        String token = matcher.group(1);
                        System.out.println("🔥 [WS Security] ✅ Extracted token from URL");
                        return token;
                    } else {
                        System.out.println("🔥 [WS Security] ❌ No token found in URL");
                        return null;
                    }
                } catch (Exception e) {
                    System.err.println("🔥 [WS Security] Error extracting token from URL: " + e.getMessage());
                    return null;
                }
            }

        });
    }
}