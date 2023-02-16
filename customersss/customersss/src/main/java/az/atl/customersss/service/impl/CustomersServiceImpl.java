package az.atl.customersss.service.impl;

import az.atl.customersss.dto.CustomersDto;
import az.atl.customersss.exception.CustomerAlreadyExistsException;
import az.atl.customersss.exception.CustomerNotFoundException;
import az.atl.customersss.mapper.CustomersMapper;
import az.atl.customersss.model.Customerss;
import az.atl.customersss.repository.CustomersRepository;
import az.atl.customersss.request.CustomersRequest;
import az.atl.customersss.service.CustomersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomersServiceImpl implements CustomersService {
    private final CustomersRepository customerRepository;
    private final CustomersMapper customersMapper = Mappers.getMapper(CustomersMapper.class);

    @Transactional
    @Override
    public CustomersDto create(CustomersRequest customerRequest) {
        try {
            Customerss customerss = customerRepository.saveAndFlush(customersMapper.requestToModel(customerRequest));
            return customersMapper.entityToDto(customerss);
        } catch (Exception e) {
            log.error("Exception caught");
            e.printStackTrace();
            throw new CustomerAlreadyExistsException("Customer already exists!");
        }
    }

    @Transactional
    @Override
    public CustomersDto findById(Long id) {
        var customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customersMapper.entityToDto(customer.get());
        } else {
            throw new CustomerNotFoundException("No customer present with id= " + id);
        }
    }

    @Transactional
    @Override
    public List<CustomersDto> findAll() {
        var customers = customerRepository.findAll();
        return customers.stream().map(customersMapper::entityToDto).collect(Collectors.toList());
    }

    @Transactional
    public void decreaseBalance(Long id, Long price) {
        var customers = customerRepository.findById(id).get();
        if (customers.getBalance() < price)
            throw new RuntimeException("Not enough balance");
        customers.setBalance(customers.getBalance() - price);
        customerRepository.save(customers);
    }


}
