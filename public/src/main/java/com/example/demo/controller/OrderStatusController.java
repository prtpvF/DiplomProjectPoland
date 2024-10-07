package com.example.demo.controller;

import com.example.demo.dto.StatusDto;
import com.example.demo.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/order-status")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @GetMapping("/all")
    public List<StatusDto> getAllStatuses(@RequestHeader("token") String token) {
        return orderStatusService.getAllAvailableStatus(token);
    }
}
