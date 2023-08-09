package com.csci5308.medinteract.WebSockets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WebSocketsTestIT {
    @Autowired
    private WebSockets webSockets;

    @Test
    public void testConfigureMessageBroker() {

        // Arrange and Act
        MessageBrokerRegistry config = mock(MessageBrokerRegistry.class);
        webSockets.configureMessageBroker(config);

        // Assert
        verify(config, times(1)).enableSimpleBroker("/user");
        verify(config, times(1)).setApplicationDestinationPrefixes("/socket");
    }

    @Test
    void testConfigureMessageBrokerNull() {

        // Arrange and Act
        WebSockets webSockets = new WebSockets();
        webSockets.configureMessageBroker(
                new MessageBrokerRegistry(new ExecutorSubscribableChannel(), mock(MessageChannel.class)));

        // Assert
        assertTrue(webSockets.configureMessageConverters(null));
    }
}