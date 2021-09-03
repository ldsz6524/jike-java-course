package week11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderInventoryService orderInventoryService;


    @GetMapping("/saveOrder")
    public String saveOrder() {
        return orderInventoryService.saveOrder("orderId:o1", "order:userId:u1");
    }


    @GetMapping("/saveOrderRedisson")
    public String saveOrderRedisson() {
        return orderInventoryService.saveOrderRedisson("orderId:o2", "order:userId:u2");
    }

}
