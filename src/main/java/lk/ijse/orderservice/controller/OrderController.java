package lk.ijse.orderservice.controller;

import lk.ijse.orderservice.dto.BookDto;
import lk.ijse.orderservice.dto.MemberDto;
import lk.ijse.orderservice.entity.Order;
import lk.ijse.orderservice.feign.BookClient;
import lk.ijse.orderservice.feign.MemberClient;
import lk.ijse.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookClient bookClient;

    @Autowired
    private MemberClient memberClient;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        // 1. Verify member exists
        try {
            MemberDto member = memberClient.getMemberById(order.getMemberId());
            if (member == null) {
                return ResponseEntity.badRequest().body("Member not found with ID: " + order.getMemberId());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to verify member: " + e.getMessage());
        }

        // 2. Verify book exists and has enough stock
        BookDto book;
        try {
            book = bookClient.getBookById(order.getBookId());
            if (book == null) {
                return ResponseEntity.badRequest().body("Book not found with ID: " + order.getBookId());
            }
            if (book.quantity() < order.getQuantity()) {
                return ResponseEntity.badRequest().body("Not enough book stock. Available: " + book.quantity());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to verify book: " + e.getMessage());
        }

        // 3. Reduce stock
        try {
            Boolean stockReduced = bookClient.reduceStock(order.getBookId(), order.getQuantity());
            if (stockReduced == null || !stockReduced) {
                return ResponseEntity.badRequest().body("Failed to reduce book stock");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating book stock: " + e.getMessage());
        }

        // 4. Save order
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
