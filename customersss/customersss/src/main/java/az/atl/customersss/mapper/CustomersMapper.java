package az.atl.customersss.mapper;


import az.atl.customersss.dto.CustomersDto;
import az.atl.customersss.model.Customerss;
import az.atl.customersss.request.CustomersRequest;
import org.mapstruct.Mapper;

@Mapper
public interface CustomersMapper {

    CustomersDto entityToDto(Customerss customers);

    Customerss requestToModel(CustomersRequest customerRequest);

}
