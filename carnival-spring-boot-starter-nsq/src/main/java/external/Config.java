package external;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration sent to nsqd with the IDENTIFY command
 * http://nsq.io/clients/tcp_protocol_spec.html#identify
 * to negotiate the features to use on a connection.
 */
@Getter
@Setter
public class Config {

    private String clientId;
    private String hostname;
    private Boolean featureNegotiation = true;
    private Integer heartbeatInterval;
    private Integer outputBufferSize;
    private Integer outputBufferTimeout;
    private Boolean tlsV1;
    private Boolean snappy;
    private Boolean deflate;
    private Integer deflateLevel;
    private Integer sampleRate;
    private String userAgent = "nsq-j/1.0";
    private Integer msgTimeout;

}
