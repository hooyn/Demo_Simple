package solution.demo.domain.webChat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessage {

    private String from;
    private String type;
    private String data;
    private Object candidate;
    private Object sdp;
}
