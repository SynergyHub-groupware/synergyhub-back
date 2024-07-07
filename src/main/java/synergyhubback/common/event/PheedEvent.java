package synergyhubback.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import synergyhubback.pheed.domain.entity.Pheed;
import synergyhubback.pheed.dto.response.PheedResponse;
import synergyhubback.pheed.presentation.PheedController;
import synergyhubback.pheed.service.PheedService;

import java.io.IOException;

@Component
public class PheedEvent {

    private final PheedService pheedService;

    public PheedEvent(PheedService pheedService) {
        this.pheedService = pheedService;
    }

    @EventListener
    public void handlePheedCreatedEvent(PheedCreatedEvent event) {
        Pheed createdPheed = event.getCreatedPheed();

        // Send new pheed notification to clients
        sendNewPheedNotificationToClients(createdPheed);
    }

    private void sendNewPheedNotificationToClients(Pheed pheed) {
        // Get SSE emitters for all subscribers
        for (SseEmitter sseEmitter : PheedController.sseEmitters.values()) {
            try {
                sseEmitter.send(SseEmitter.event()
                        .name("newPheed")
                        .data(convertToPheedResponse(pheed)));
            } catch (IOException e) {
                // Handle exception, possibly close the emitter
                sseEmitter.completeWithError(e);
            }
        }
    }

    private PheedResponse convertToPheedResponse(Pheed pheed) {
        // Convert Pheed entity to PheedResponse DTO
        return PheedResponse.builder()
                .pheedCode(pheed.getPheedCode())
                .pheedCon(pheed.getPheedCon())
                .creStatus(pheed.getCreStatus())
                .readStatus(pheed.getReadStatus())
                .deStatus(pheed.getDeStatus())
                .pheedSort(pheed.getPheedSort())
                .empCode(pheed.getEmployee().getEmp_code())
                .url(pheed.getUrl())
                .build();
    }
}
