package tech.murilo.springmusic.customersaveapi.gateway.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.murilo.springmusic.customersaveapi.service.SaveCustomerService;
import tech.murilo.springmusic.musicdata.customer.Customer;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    @Autowired
    private SaveCustomerService saveCustomerService;

    @PostMapping("/")
    public String create(@RequestBody Customer customer) throws ExecutionException, InterruptedException, JsonProcessingException {
        return saveCustomerService.save(customer);
    }

}
