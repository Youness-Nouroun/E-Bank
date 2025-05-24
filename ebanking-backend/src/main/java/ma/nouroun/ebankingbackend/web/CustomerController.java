package ma.nouroun.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.nouroun.ebankingbackend.dto.CustomerDto;
import ma.nouroun.ebankingbackend.services.IBankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CustomerController {

    private IBankAccountService bankAccountService;

    // Get all customers
    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers() {
        return bankAccountService.listCustomers();
    }

    // Search customers by keyword
    @GetMapping("/customers/search")
    public List<CustomerDto> searchCustomers(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return bankAccountService.searchCustomers(keyword);
    }

    // Get a specific customer by ID
    @GetMapping("/customers/{id}")
    public CustomerDto getCustomer(@PathVariable Long id) {
        return bankAccountService.getCustomer(id);
    }

    // Save a new customer (Admin only)
    @PostMapping("/customers")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto) {
        return bankAccountService.saveCustomer(customerDto);
    }

    // Update an existing customer (Admin only)
    @PutMapping("/customers/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        customerDto.setId(id);
        return bankAccountService.updateCustomer(customerDto);
    }

    // Delete a customer (Admin only)
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustomer(id);
    }
}
