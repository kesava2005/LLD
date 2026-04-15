import java.util.ArrayList;
import java.util.List;

interface NotificationType{

    boolean send(User user, String message);

}

class SMSNotification implements NotificationType{

    public boolean send(User user, String message){

        System.out.println("User : " + user.getName() + " " + message + " through sms");

        return true;

    }

}
class EmailNotification implements NotificationType{

    public boolean send(User user, String message){

        System.out.println("User : " + user.getName() + " " + message + " through email");

        return true;

    }

}
class PushNotification implements NotificationType{

    public boolean send(User user, String message){

        System.out.println("User : " + user.getName() + " " + message + " has been pushed");

        return true;

    }

}

class NotificationFactory {

    public static NotificationType getNotification(String type) {

        switch(type) {
            case "EMAIL":
                return new EmailNotification();
            case "SMS":
                return new SMSNotification();
            case "PUSH":
                return new PushNotification();
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }
}

interface Subject{

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void getNotified();

}

class ConcreteSubject implements Subject{

    private List<Observer> observers = new ArrayList<>();

    private String latestContent = "";

    private NotificationMessage notificationMessage;

    public void addObserver(Observer observer){

        observers.add(observer);

    }

    public void removeObserver(Observer observer){

        observers.remove(observer);

    }

    public String getLatestContent(){

        return latestContent;

    }

    public void setLatestContent(String latestContent){

        this.latestContent = latestContent;

    }

    public void setNotification(NotificationMessage notificationMessage){

        this.notificationMessage = notificationMessage;

    }

    public void getNotification(){

        getNotified();

    }

    public void getNotified(){

        for(Observer ob : observers){

            ob.update(notificationMessage.getMessage());

        }

    }

}

interface Observer{

    void addNotificationType(NotificationType notificationType);
    void removeNotificationType(NotificationType notificationType);
    void update(String content);

}

class User implements Observer{

    private String name;
    private int id;

    private List<NotificationType> preferredNotificationTypes = new ArrayList<>();


    public void addNotificationType(NotificationType notificationType){

        preferredNotificationTypes.add(notificationType);

    }

    public void removeNotificationType(NotificationType notificationType){

        preferredNotificationTypes.remove(notificationType);

    }


    User(String name, int id){

        this.name = name;
        this.id = id;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void update(String content){

        for(NotificationType channel : preferredNotificationTypes){

            boolean status = channel.send(this, content);

            if(!status){

                RetryService.sendWithRetry(this, channel, content, 1000, 3);

            }

        }

    }

}

class RetryService{

    public static void sendWithRetry(User user, NotificationType channel, String message, int delay, int retries){

        int attempts = 0;

        while(attempts < retries){

            boolean status = channel.send(user, message);

            if(status) break;

            attempts++;

            try{

                Thread.sleep(delay);

            }

            catch(Exception e){

                System.out.println(e.getStackTrace());

            }
            
            delay += 1000;

        }

    }

}

interface NotificationMessage{

    String getMessage();

}

class SimpleNotificationMessage implements NotificationMessage{

    private String content;

    SimpleNotificationMessage(String content){

        this.content = content;

    }

    public String getMessage(){

        return "got a notification about " + content + "with ";

    }

}

abstract class NotificationMessageDecorator implements NotificationMessage{

    protected NotificationMessage notificationMessage;

    NotificationMessageDecorator(NotificationMessage notificationMessage){

        this.notificationMessage = notificationMessage;

    }

    public String getMessage(){

        return notificationMessage.getMessage();

    }

}

class TimeStampNotificationMessage extends NotificationMessageDecorator{

    TimeStampNotificationMessage(NotificationMessage notificationMessage){

        super(notificationMessage);

    }

    public String getMessage(){

        return notificationMessage.getMessage() + "Time Stamp ,";

    }

}

class HeaderNotificationMessage extends NotificationMessageDecorator{

    HeaderNotificationMessage(NotificationMessage notificationMessage){

        super(notificationMessage);

    }

    public String getMessage(){

        return notificationMessage.getMessage() + "header content ,";

    }

}

class DescriptionNotificationMessage extends NotificationMessageDecorator{

    DescriptionNotificationMessage(NotificationMessage notificationMessage){

        super(notificationMessage);

    }

    public String getMessage(){

        return notificationMessage.getMessage() + "Description ,";

    }

}

public class NotificationSystem {

    public static void main(String[] args) {
        
        ConcreteSubject subject = new ConcreteSubject();

        Observer ob1 = new User("harry", 101);

        ob1.addNotificationType(NotificationFactory.getNotification("EMAIL"));
        ob1.addNotificationType(NotificationFactory.getNotification("PUSH"));

        Observer ob2 = new User("mark", 102);

        ob2.addNotificationType(NotificationFactory.getNotification("SMS"));
        ob2.addNotificationType(NotificationFactory.getNotification("EMAIL"));

        Observer ob3 = new User("june", 103);

        ob3.addNotificationType(new SMSNotification());

        subject.addObserver(ob1);
        subject.addObserver(ob2);

        subject.setLatestContent("New model of Asus is Dropped check it out..");


        //TimeStamp + desc notification message

        subject.setNotification(new TimeStampNotificationMessage(new DescriptionNotificationMessage(new SimpleNotificationMessage(subject.getLatestContent()))));
        subject.getNotification();

    }
}