package lk.ijse.orderservice.dto;

public record BookDto(
    Long id,
    String title,
    String author,
    String isbn,
    Integer quantity
) {}
