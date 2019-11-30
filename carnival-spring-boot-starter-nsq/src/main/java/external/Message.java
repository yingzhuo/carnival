package external;

public interface Message {

    String getTopic();

    byte[] getData();

    String getId();

    int getAttempts();

    long getTimestamp();

    void finish();

    void requeue();

    void touch();

}
