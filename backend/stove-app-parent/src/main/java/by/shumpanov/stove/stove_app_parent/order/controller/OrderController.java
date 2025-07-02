package by.shumpanov.stove.stove_app_parent.order.controller;

import by.shumpanov.stove.stove_app_parent.order.dto.CreateOrderRequest;
import by.shumpanov.stove.stove_app_parent.order.dto.OrderResponse;
import by.shumpanov.stove.stove_app_parent.order.service.OrderService;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import by.shumpanov.stove.stove_app_parent.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        OrderResponse response = orderService.createOrder(request, currentUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<OrderResponse>> getUserOrders(
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        List<OrderResponse> orders = orderService.findUserOrders(currentUser);
        return ResponseEntity.ok(orders);
    }
}
