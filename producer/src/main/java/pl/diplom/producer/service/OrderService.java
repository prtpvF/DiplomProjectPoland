package pl.diplom.producer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.diplom.common.repository.AdminOrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final AdminOrderRepository adminOrderRepository;

    public HttpStatus deleteOrder(String token, int orderId){
        return HttpStatus.OK;
    }

}
