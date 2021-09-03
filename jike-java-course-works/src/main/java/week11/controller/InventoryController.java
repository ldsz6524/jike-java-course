package week11.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class InventoryController {
    private final OrderInventoryService orderInventoryService;


    @GetMapping("/deductionInventory")
    public String deductionInventory() {
        return orderInventoryService.deductionInventory("p1", 2);
    }


    @GetMapping("/setProductInventory")
    public String setProductInventory() {
        return orderInventoryService.setProductInventory(50);
    }

}
