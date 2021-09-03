package week11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class RedisPubSubController {
    private final RedisPubSubService redisPubSubService;

    @GetMapping("/publishMessage")
    public String publishMessage(@RequestParam(required = false, defaultValue = "order-channel") String channel) {
        return redisPubSubService.publishMessage(channel);
    }

}
