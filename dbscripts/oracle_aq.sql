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

