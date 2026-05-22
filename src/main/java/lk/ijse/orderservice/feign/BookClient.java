package lk.ijse.orderservice.feign;

import lk.ijse.orderservice.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "book-service")
public interface BookClient {
    @GetMapping("/api/v1/books/{id}")
    BookDto getBookById(@PathVariable("id") Long id);

    @PutMapping("/api/v1/books/{id}/reduce-stock")
    Boolean reduceStock(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity);
}
