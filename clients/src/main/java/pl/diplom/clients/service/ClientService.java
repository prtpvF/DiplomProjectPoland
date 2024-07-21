package pl.diplom.clients.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.diplom.common.jwt.JwtService;
import pl.diplom.common.model.Order;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final JwtService jwtService;

    public void crateOrder(String token, Order order){

    }
}
