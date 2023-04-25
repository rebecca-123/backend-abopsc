package com.nighthawk.spring_portfolio.database;

import com.nighthawk.spring_portfolio.database.person.Person;
import com.nighthawk.spring_portfolio.database.person.PersonJpaRepository;
import com.nighthawk.spring_portfolio.database.role.Role;
import com.nighthawk.spring_portfolio.database.role.RoleJpaRepository;
import com.nighthawk.spring_portfolio.database.car.Car;
import com.nighthawk.spring_portfolio.database.car.CarJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/*
This class has an instance of Java Persistence API (JPA)
-- @Autowired annotation. Allows Spring to resolve and inject collaborating beans into our bean.
-- Spring Data JPA will generate a proxy instance
-- Below are some CRUD methods that we can use with our database
*/
@Service
@Transactional
public class ModelRepository implements UserDetailsService { // "implements" ties ModelRepo to Spring Security
    // Encapsulate many object into a single Bean (Person, Roles, and Scrum)
    @Autowired // Inject PersonJpaRepository
    private PersonJpaRepository personJpaRepository;
    @Autowired // Inject RoleJpaRepository
    private RoleJpaRepository roleJpaRepository;
    @Autowired // Inject CarJpaRepository
    private CarJpaRepository carJpaRepository;

    // Setup Password style for Database storing and lookup
    @Autowired // Inject PasswordEncoder
    private PasswordEncoder passwordEncoder;

    @Bean // Sets up password encoding style
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * UserDetailsService Overrides and maps Person & Roles POJO into Spring
     * Security
     */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Optional<Person> person = personJpaRepository.findByEmail(email); // setting variable user equal to the method finding the
                                                                // username in the database
        if (person == null) {
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        person.get().getRoles().forEach(role -> { // loop through roles
            authorities.add(new SimpleGrantedAuthority(role.getName())); // create a SimpleGrantedAuthority by passed in
                                                                         // role, adding it all to the authorities list,
                                                                         // list of roles gets past in for spring
                                                                         // security
        });
        return new org.springframework.security.core.userdetails.User(person.get().getEmail(), person.get().getPassword(),
                authorities);
    }

    /* Person Section */

    public List<Person> listAll() {
        return personJpaRepository.findAllByOrderByNameAsc();
    }

    // custom query to find anything containing term in name or email ignoring case
    public List<Person> listLike(String term) {
        return personJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }

    // custom query to find anything containing term in name or email ignoring case
    public List<Person> listLikeNative(String term) {
        String like_term = String.format("%%%s%%", term); // Like required % rappers
        return personJpaRepository.findByLikeTermNative(like_term);
    }

    public void save(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personJpaRepository.save(person);
    }

    public Person get(long id) {
        return (personJpaRepository.findById(id).isPresent())
                ? personJpaRepository.findById(id).get()
                : null;
    }

    public Person getByEmail(String email) {
        return (personJpaRepository.findByEmail(email).get());
    }

    public void delete(long id) {
        personJpaRepository.deleteById(id);
    }

    public void defaults(String password, String roleName) {
        for (Person person : listAll()) {
            if (person.getPassword() == null || person.getPassword().isEmpty() || person.getPassword().isBlank()) {
                person.setPassword(passwordEncoder.encode(password));
            }
            if (person.getRoles().isEmpty()) {
                Role role = roleJpaRepository.findByName(roleName);
                if (role != null) { // verify role
                    person.getRoles().add(role);
                }
            }
        }
    }

    /* Roles Section */

    public void saveRole(Role role) {
        Role roleObj = roleJpaRepository.findByName(role.getName());
        if (roleObj == null) { // only add if it is not found
            roleJpaRepository.save(role);
        }
    }

    public List<Role> listAllRoles() {
        return roleJpaRepository.findAll();
    }

    public Role findRole(String roleName) {
        return roleJpaRepository.findByName(roleName);
    }

    public void addRoleToPerson(String email, String roleName) { // by passing in the two strings you are giving the
                                                                 // user that certain role
        Optional<Person> person = personJpaRepository.findByEmail(email);
        if (person != null) { // verify person
            Role role = roleJpaRepository.findByName(roleName);
            if (role != null) { // verify role
                boolean addRole = true;
                for (Role roleObj : person.get().getRoles()) { // only add if user is missing role
                    if (roleObj.getName().equals(roleName)) {
                        addRole = false;
                        break;
                    }
                }
                if (addRole)
                    person.get().getRoles().add(role); // everything is valid for adding role
            }
        }
    }

    /* Car Section */

    public void saveCar(Car car) {
        carJpaRepository.save(car);
    }

    public List<Car> listAllCars() {
        return carJpaRepository.findAll();
    }

    public Car findCar(String carName) {
        return carJpaRepository.findByName(carName);
    }

    public Car getCar(long id) {
        return (carJpaRepository.findById(id).isPresent())
                ? carJpaRepository.findById(id).get()
                : null;
    }

    public void deleteCar(long id) {
        carJpaRepository.deleteById(id);
    }

    // public void updateCar(long id, Car car){
    // carJpaRepository.save(car);
    // }

    // public void deleteCarFromPersonCarList(String email, String carName) {
    // Person person = personJpaRepository.findByEmail(email);
    // if (person != null) { // verify person
    // Car car = carJpaRepository.findByName(carName);
    // if (car != null) { // verify car
    // if (person.getCarList().contains(car)) {
    // person.getCarList().remove(car);
    // }
    // }
    // }
    // }

}
