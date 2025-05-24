package ma.nouroun.ebankingbackend;

import ma.nouroun.ebankingbackend.dto.CustomerDto;
import ma.nouroun.ebankingbackend.dto.CurrentBankAccountDto;
import ma.nouroun.ebankingbackend.dto.SavingBankAccountDto;
import ma.nouroun.ebankingbackend.entities.AccountOperation;
import ma.nouroun.ebankingbackend.entities.CurrentAccount;
import ma.nouroun.ebankingbackend.entities.Customer;
import ma.nouroun.ebankingbackend.entities.SavingAccount;
import ma.nouroun.ebankingbackend.enums.AccountStatus;
import ma.nouroun.ebankingbackend.enums.OperationType;
import ma.nouroun.ebankingbackend.repositories.AccountOperationRepository;
import ma.nouroun.ebankingbackend.repositories.BankAccountRepository;
import ma.nouroun.ebankingbackend.repositories.CustomerRepository;
import ma.nouroun.ebankingbackend.services.IBankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

//    @Bean
    CommandLineRunner commandLineRunner(
            BankAccountRepository bankAccountRepository,
            CustomerRepository customerRepository,
            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Ahmed", "Said", "Fatima", "Nour").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                    CurrentAccount currentAccount = new CurrentAccount();
                    currentAccount.setId(UUID.randomUUID().toString());
                    currentAccount.setBalance(Math.random() * 900000);
                    currentAccount.setCreatedAt(new Date());
                    currentAccount.setCustomer(customer);
                    currentAccount.setStatus(AccountStatus.CREATED);
                    currentAccount.setOverDraft(9000);
                    bankAccountRepository.save(currentAccount);

                    SavingAccount savingAccount = new SavingAccount();
                    savingAccount.setId(UUID.randomUUID().toString());
                    savingAccount.setBalance(Math.random() * 900000);
                    savingAccount.setCreatedAt(new Date());
                    savingAccount.setCustomer(customer);
                    savingAccount.setStatus(AccountStatus.CREATED);
                    savingAccount.setInterestRate(5.5);
                    bankAccountRepository.save(savingAccount);

            });

            bankAccountRepository.findAll().forEach(account -> {
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setBankAccount(account);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

//    @Bean
    CommandLineRunner commandLineRunner2(
            IBankAccountService bankAccountService) {
        return args -> {
            Stream.of("Hassan", "Imane", "Sami").forEach(name -> {
                CustomerDto customer = new CustomerDto();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(
                    customer -> {
                        bankAccountService.saveCurrentBankAccount(100000, 2000, customer.getId());
                        bankAccountService.saveSavingBankAccount(200000, 5.5, customer.getId());
                        bankAccountService.bankAccountList().forEach(
                                account -> {
                                    String accountId;
                                    if (account instanceof CurrentBankAccountDto) {
                                        accountId = ((CurrentBankAccountDto) account).getId();
                                    } else if (account instanceof SavingBankAccountDto) {
                                        accountId = ((SavingBankAccountDto) account).getId();
                                    } else {
                                        return; // Skip if not a concrete account type
                                    }
                                    
                                    for (int i = 0; i < 10; i++) {
                                        bankAccountService.credit(accountId, Math.random() * 900, "credit");
                                        bankAccountService.debit(accountId, Math.random() * 900, "debit");
                                    }
                                }
                        );
                    }
            );
        };
    }
}
