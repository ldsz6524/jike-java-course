package week7;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description:
 * @author:
 * @create:
 * @other:
 **/

@RestController
@RequiredArgsConstructor
public class BatchInsertController {
    private final week7.BatchInsertService readWriteService;

    @GetMapping("/insertData")
    public String insertData() {
        String result = readWriteService.insertData();
        return result;
    }

}
