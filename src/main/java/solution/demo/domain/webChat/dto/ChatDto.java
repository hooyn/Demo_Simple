package solution.demo.domain.webChat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    public enum MessageType {
        ENTER, TALK, EXIT;
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String time;
}
