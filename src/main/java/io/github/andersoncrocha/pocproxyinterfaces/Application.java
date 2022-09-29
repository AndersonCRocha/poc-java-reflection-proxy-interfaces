package io.github.andersoncrocha.pocproxyinterfaces;

import io.github.andersoncrocha.pocproxyinterfaces.core.RepositoryContainer;
import io.github.andersoncrocha.pocproxyinterfaces.core.RepositoryProxyInitializer;
import io.github.andersoncrocha.pocproxyinterfaces.demo.models.Company;
import io.github.andersoncrocha.pocproxyinterfaces.demo.models.Person;
import io.github.andersoncrocha.pocproxyinterfaces.demo.repositories.CompanyRepository;
import io.github.andersoncrocha.pocproxyinterfaces.demo.repositories.PersonRepository;

public class Application {

    public static void main(String[] args) {
        RepositoryProxyInitializer.initialize(Application.class);

        PersonRepository personRepository = RepositoryContainer.get(PersonRepository.class);
        CompanyRepository companyRepository = RepositoryContainer.get(CompanyRepository.class);

        companyRepository.save(new Company("Apple", "123456"));
        companyRepository.findAll()
                .forEach(company -> System.out.printf("Company: %s%n", company.getName()));

        personRepository.save(new Person("John Doe", "john@doe.com"));
        personRepository.findAll()
                .forEach(person -> System.out.printf("Person: %s%n", person.getName()));
    }

}
