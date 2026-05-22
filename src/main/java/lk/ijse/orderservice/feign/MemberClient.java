package lk.ijse.orderservice.feign;

import lk.ijse.orderservice.dto.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberClient {
    @GetMapping("/api/v1/members/{id}")
    MemberDto getMemberById(@PathVariable("id") String id);
}
