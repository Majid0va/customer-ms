package az.atl.customersss.controller;

import az.atl.customersss.dto.CustomersDto;
import az.atl.customersss.request.CustomersRequest;
import az.atl.customersss.service.CustomersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CustomersController {
    private final CustomersService customerService;

    @PostMapping
    public void save(@RequestBody @Valid CustomersRequest customerRequest) {
        customerService.create(customerRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomersDto> getById(@PathVariable Long id) {
        log.info("afsafsaa");
        var a = customerService.findById(id);
       log.info("a : {}",a);
        return ResponseEntity.ok(a);
    }

    @GetMapping
    public ResponseEntity<List<CustomersDto>> getAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @PatchMapping("/{id}/price")
    public void balanceLogic(@PathVariable Long id, @RequestParam Long price) {
        customerService.decreaseBalance(id, price);
    }

}
