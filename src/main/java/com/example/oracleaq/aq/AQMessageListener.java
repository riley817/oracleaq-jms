package com.example.oracleaq.aq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Slf4j
@RequiredArgsConstructor
public class AQMessageListener implements SessionAwareMessageListener {

    @JmsListener(destination = "${oracle.aq.name}")
    public void onMessage(Message message, Session session) throws JMSException {

        TextMessage textMessage = (TextMessage) message;
        if(!StringUtils.hasText(textMessage.getText())) {
            throw new JMSException("Message is empty...");
        }

        log.debug("[Message] {}", textMessage.getText());

    }
}
