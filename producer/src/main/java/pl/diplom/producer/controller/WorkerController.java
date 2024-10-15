package pl.diplom.producer.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.diplom.producer.dto.PersonOrderDto;
import pl.diplom.producer.service.WorkerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
public class WorkerController {

        private final WorkerService workerService;

        @PatchMapping("/status/{id}")
        public HttpStatus changePersonOrderStatus(@PathVariable("id") Integer orderId,
                                                  @RequestHeader("token") String token) {
            return workerService.changeOrderStatus(orderId, token);
        }

        @GetMapping("/all")
        public Page<PersonOrderDto> getAllOrders(@RequestHeader("token") String token,
                                                 Pageable pageable) {
            return workerService.getOrderPage(pageable);
        }
}
