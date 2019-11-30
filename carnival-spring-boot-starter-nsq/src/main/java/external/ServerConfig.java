package external;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * nsqd settings that can't be changed by the client.
 * returned in response to IDENTIFY command.
 */
@Getter
@Setter
@ToString
class ServerConfig extends Config {

    private String version;
    private Integer maxRdyCount;
    private Integer maxMsgTimeout;
    private Integer maxDeflateLevel;
    private Boolean authRequired;

}
