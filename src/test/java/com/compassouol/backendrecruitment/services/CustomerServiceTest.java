package com.compassouol.backendrecruitment.services;

import static org.mockito.Mockito.times;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.compassouol.backendrecruitment.dtos.request.customer.CreateCustomerRequestDTO;
import com.compassouol.backendrecruitment.dtos.request.customer.UpdateCustomerRequestDTO;
import com.compassouol.backendrecruitment.models.City;
import com.compassouol.backendrecruitment.models.Customer;
import com.compassouol.backendrecruitment.models.Gender;
import com.compassouol.backendrecruitment.models.State;
import com.compassouol.backendrecruitment.repositories.CustomerRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {
    @TestConfiguration
    static class CustomerServiceTestConfiguration {
        @Bean
        public CustomerService customerService() {
            return new CustomerService();
        }
    }

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
        // states
        State stateOfSP = new State(1L, "São Paulo", "sao_paulo", "SP");
        State stateOfPR = new State(2L, "Paraná", "parana", "PR");

        // cities
        City londrinaCity = new City(1L, "Londrina", "londrina", LocalDateTime.now(), LocalDateTime.now(), stateOfPR);
        City cornelioProcopioCity = new City(2L, "Cornélio Procópio", "cornelio_procopio", LocalDateTime.now(),
                LocalDateTime.now(), stateOfPR);
        City santosCity = new City(3L, "Santos", "santos", LocalDateTime.now(), LocalDateTime.now(), stateOfSP);

        // genders
        Gender male = new Gender(1L, "Masculino");
        Gender female = new Gender(2L, "Feminino");
        Gender notInformed = new Gender(3L, "Prefiro não declarar");

        // customers
        Customer eduardoCustomer = new Customer(1L, "Eduardo Spagna", "eduardo_spagna", LocalDate.parse("1999-06-05"),
                LocalDateTime.now(), LocalDateTime.now(), male, cornelioProcopioCity);
        Customer testerCompassoCustomer = new Customer(2L, "Tester da Compasso", "tester_da_compasso",
                LocalDate.parse("1997-01-01"), LocalDateTime.now(), LocalDateTime.now(), notInformed, santosCity);
        Customer catarinaMariaCustomer = new Customer(3L, "Catarina Maria", "catarina_maria",
                LocalDate.parse("1974-08-25"), LocalDateTime.now(), LocalDateTime.now(), female, londrinaCity);

        // all customers
        List<Customer> allCustomers = new ArrayList<Customer>();

        allCustomers.add(eduardoCustomer);
        allCustomers.add(testerCompassoCustomer);
        allCustomers.add(catarinaMariaCustomer);

        // customers with search
        List<Customer> customersWithSearchByName = new ArrayList<Customer>();
        customersWithSearchByName.add(catarinaMariaCustomer);

        Mockito.when(customerRepository.findById(eduardoCustomer.getCustomerId()))
                .thenReturn(java.util.Optional.of(eduardoCustomer));
        Mockito.when(customerRepository.findAllWithSearch("")).thenReturn(allCustomers);
        Mockito.when(customerRepository.findAllWithSearch("ina_mar")).thenReturn(customersWithSearchByName);
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    @DisplayName("Should return a customer named Eduardo Spagna")
    public void shouldReturnEduardoSpagna() {
        Customer findCustomerById = customerService.findById(1L);
        Assertions.assertEquals("Eduardo Spagna", findCustomerById.getCustomerName());
    }

    @Test
    @DisplayName("Should return null when it does not find the customer")
    public void shouldReturnNullWhenDoesntExist() {
        Customer findCustomerById = customerService.findById(9L);
        Assertions.assertNull(findCustomerById);
    }

    @Test
    @DisplayName("Should return three customers when not searched by name")
    public void shouldReturnThreeCustomersWithoutSearch() {
        List<Customer> allCustomers = customerService.findAllWithSearch(null);
        Assertions.assertEquals(3, allCustomers.size());
    }

    @Test
    @DisplayName("Should return one customer when searched by name with 'ina Mar'")
    public void shouldReturnOneCustomerWithSearch() {
        List<Customer> allCustomers = customerService.findAllWithSearch("ina Mar");
        Assertions.assertEquals(1, allCustomers.size());
    }

    @Test
    @DisplayName("Should create a customer")
    public void shouldCreateACustomer() {
        State stateOfRS = new State(3L, "Rio Grande do Sul", "rio_grande_do_sul", "RS");
        City portoAlegreCity = new City(4L, "Porto Alegre", "porto_alegre", LocalDateTime.now(), LocalDateTime.now(),
                stateOfRS);

        Gender male = new Gender(1L, "Masculino");

        CreateCustomerRequestDTO createCustomerRequestDTO = new CreateCustomerRequestDTO("Neymar Júnior",
                LocalDate.parse("1992-02-05"), male.getGenderId(), portoAlegreCity.getCityId());

        Customer newCustomer = customerService.create(createCustomerRequestDTO, male, portoAlegreCity);

        Assertions.assertEquals("Neymar Júnior", newCustomer.getCustomerName());
        Assertions.assertEquals("neymar_junior", newCustomer.getCustomerNameNormalized());
    }

    @Test
    @DisplayName("Should update a customer")
    public void shouldUpdateACustomer() {
        UpdateCustomerRequestDTO updateCustomerRequestDTO = new UpdateCustomerRequestDTO("Pelé");
        Customer currentCustomer = customerService.findById(1L);

        Customer updatedCustomer = customerService.update(updateCustomerRequestDTO, currentCustomer);

        Assertions.assertEquals("Pelé", updatedCustomer.getCustomerName());
        Assertions.assertEquals("pele", updatedCustomer.getCustomerNameNormalized());
        Assertions.assertEquals(1L, updatedCustomer.getCustomerId());
    }

    @Test
    @DisplayName("Should delete a customer")
    public void shouldDeleteACustomer() {
        Customer customer = customerService.findById(1L);
        customerService.delete(customer);
        Mockito.verify(customerRepository, times(1)).delete(customer);
    }
}
