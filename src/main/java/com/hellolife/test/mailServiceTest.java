package com.hellolife.test;

import com.hellolife.email.service.impl.MailServiceBaseImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class mailServiceTest {
    @Autowired
    private MailServiceBaseImpl MailService;

    @Test
    public void testSimpleMail() throws Exception {
        MailService.sendSimpleMail("z15958337902@163.com","test simple mail"," hello this is simple mail");

        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        MailService.sendHtmlMail("z15958337902@163.com","test simple mail",content);

    }
}
