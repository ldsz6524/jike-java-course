import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ActiveMqController {
    private final ActiveMqService activeMqService;

    @GetMapping("/sendQueueMessage")
    public void sendQueueMessage() {
        activeMqService.sendQueueMessage();
    }

    @GetMapping("/sendTopicMessage")
    public void sendTopicMessage() {
        activeMqService.sendTopicMessage();
    }

}
