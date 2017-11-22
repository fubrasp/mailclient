import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Principale {

    private static MailService mailClient;

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext(String.valueOf(Principale.class
                .getClassLoader().getResource("beans.xml")));

        mailClient = (MailService) context.getBean("MailClient");

        mailClient.send("Test subject", "Awesome client");
        mailClient.receive();
    }

    public static void log(String message) {
        try {
            Class loggerF = Class.forName("FileLogger");
            Class loggerC = Class.forName("ConsoleLogger");
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
