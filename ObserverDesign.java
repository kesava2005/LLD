import java.util.*;

interface Observer{

    void update(String channelName, String latestVideoTitle);

}

interface Subject{

    void addSubscriber(Observer observer);
    void removeSubscriber(Observer observer);
    void getNotified();

}

// ConcreteImplementation of Subject
class YoutubeChannel implements Subject{

    private List<Observer> subscribers = new ArrayList<>();
    private String latestVideoTitle;

    private String channelName;

    YoutubeChannel(String channelName){

        this.channelName = channelName;

    }

    public void addSubscriber(Observer observer){

        subscribers.add(observer);

    }

    public void removeSubscriber(Observer observer){

        subscribers.remove(observer);

    }

    public void addLatestVideo(String title){

        this.latestVideoTitle = title;
        getNotified();

    }

    public void getNotified(){

        for(Observer observer : subscribers){

            observer.update(channelName, latestVideoTitle);

        }

    }

}

// Concrete Implementation of Observer
class User implements Observer{

    private String userName;

    User(String userName){

        this.userName = userName;

    }

    public void update(String channelName, String latestVideoTitle){

        System.out.println("user : " + userName + " got notified from " + channelName + " about " + latestVideoTitle);

    }

}

public class ObserverDesign {

    public static void main(String[] args) {

        // Create Subject (YouTube Channel)
        YoutubeChannel channel = new YoutubeChannel("TechWithKesava");

        // Create Observers (Users)
        Observer user1 = new User("Ravi");
        Observer user2 = new User("Sita");
        Observer user3 = new User("Arjun");

        // Subscribe users to the channel
        channel.addSubscriber(user1);
        channel.addSubscriber(user2);
        channel.addSubscriber(user3);

        // Upload video (this will notify all subscribers)
        channel.addLatestVideo("Observer Pattern Explained");

        System.out.println("----- After removing one subscriber -----");

        // Unsubscribe one user
        channel.removeSubscriber(user2);

        // Upload another video
        channel.addLatestVideo("Decorator Pattern Explained");

    }
    
}