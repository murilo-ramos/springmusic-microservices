package tech.murilo.springmusic.customersaveservice.repository;

import org.springframework.data.repository.CrudRepository;
import tech.murilo.springmusic.customersaveservice.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {
}
