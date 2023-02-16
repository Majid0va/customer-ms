package az.atl.customersss.service;

import az.atl.customersss.dto.CustomersDto;
import az.atl.customersss.model.Customerss;
import az.atl.customersss.request.CustomersRequest;

import java.util.List;

public interface CustomersService {
    CustomersDto create(CustomersRequest customerRequest);

    CustomersDto findById(Long id);

    List<CustomersDto> findAll();

    void decreaseBalance(Long id, Long price);
}
