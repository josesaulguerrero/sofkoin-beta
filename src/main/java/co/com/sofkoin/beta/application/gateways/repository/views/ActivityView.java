package co.com.sofkoin.beta.application.gateways.repository.views;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActivityView {
    private String activityId;
    private LocalDateTime timestamp;
    private String type;
}
