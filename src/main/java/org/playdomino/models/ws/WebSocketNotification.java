package org.playdomino.models.ws;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public final class WebSocketNotification {
    @NotNull
    private String messageId;
    @NotNull
    private String messageType;
    @NotNull
    private String message;
    @NotNull
    private Object data;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long notifyId;
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private NotificationTopic topic;
}
