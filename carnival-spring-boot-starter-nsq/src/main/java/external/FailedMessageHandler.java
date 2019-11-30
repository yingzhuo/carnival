package external;

public interface FailedMessageHandler {

    void failed(String topic, String channel, Message msg);

}
