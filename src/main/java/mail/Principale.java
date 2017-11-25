package mail;

import loggers.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Principale {


    @Autowired
    private static MailService mailClient;

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext("mail");

        //mailClient = (MailService) context.getBean("MailClient");
        mailClient = (MailService) context.getBean("MailClient");

        mailClient.send("Test subject", "Awesome client");
        mailClient.receive();
    }

    public static void log(String message) {
        try {
            Class loggerF = Class.forName("loggers.FileLogger");
            Class loggerC = Class.forName("loggers.ConsoleLogger");
            Logger loggerCC = (Logger) loggerC.newInstance();
            Logger loggerFF = (Logger) loggerF.newInstance();
            loggerCC.log(message);
            loggerFF.log(message);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


}
