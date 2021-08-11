-- QUEUE
BEGIN
    DBMS_AQADM.CREATE_QUEUE_TABLE(
            queue_table => 'HR.EVENT_EMPLOYEE_TAB',
            multiple_consumers => TRUE,
            queue_payload_type => 'SYS.AQ$_JMS_TEXT_MESSAGE'
    );

    DBMS_AQADM.CREATE_QUEUE(
            queue_name => 'HR.EVENT_EMPLOYEE_QUEUE',
            queue_table => 'HR.EVENT_EMPLOYEE_TAB',
            max_retries => 3,
            retry_delay => 1
    );

    DBMS_AQADM.START_QUEUE(
            queue_name => 'HR.EVENT_EMPLOYEE_QUEUE'
    );
END;

CREATE OR REPLACE PROCEDURE HR.PROC_ENQUEUE_MESSAGE ( v_message IN VARCHAR2)
AS
    enqueue_options    dbms_aq.enqueue_options_t;
    message_properties dbms_aq.message_properties_t;
    message_handle     RAW(16);
    message            SYS.AQ$_JMS_TEXT_MESSAGE;
BEGIN
    message := sys.aq$_jms_text_message.construct;
    message.set_text(v_message);
    DBMS_AQ.ENQUEUE(
            queue_name => 'HR.EVENT_EMPLOYEE_QUEUE',
            enqueue_options    => enqueue_options,
            message_properties => message_properties,
);
END;
