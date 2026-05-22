package lk.ijse.orderservice.dto;

public record MemberDto(
    String id,
    String name,
    String email,
    String membershipType
) {}
