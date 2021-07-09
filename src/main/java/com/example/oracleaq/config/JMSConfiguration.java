package com.example.oracleaq.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jms.AQjmsFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DestinationResolver;

import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.sql.DataSource;

@Slf4j
@EnableJms
@Configuration
@RequiredArgsConstructor
public class JMSConfiguration {

    @Qualifier("simpleDriverDataSource")
    private final DataSource dataSource;

    @Bean(name = "jmsQueueConnectionFactory")
    public QueueConnectionFactory jmsQueueConnectionFactory() throws JMSException {
        return AQjmsFactory.getQueueConnectionFactory(dataSource);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory (@Qualifier("jmsQueueConnectionFactory") QueueConnectionFactory jmsQueueConnectionFactory,
                                                                           DestinationResolver destinationResolver) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setSessionTransacted(true);
        factory.setConnectionFactory(jmsQueueConnectionFactory);
        factory.setDestinationResolver(destinationResolver);
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(@Qualifier("jmsQueueConnectionFactory") QueueConnectionFactory jmsQueueConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setConnectionFactory(jmsQueueConnectionFactory);
        return jmsTemplate;
    }
}
