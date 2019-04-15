package com.basis.java8.optional;

import org.junit.Test;

import java.util.Optional;

public class OptionalTest {
    @Test
    public void f1() {
//        Optional<Car> optCar = Optional.empty();

        Car car = null;
//        Optional<Car> optCar = Optional.of(car);
        Optional<Car> optCar = Optional.ofNullable(car);
    }

    @Test
    public void f2() {
        Insurance insurance = new Insurance();
        Optional<Insurance> optionalInsurance = Optional.ofNullable(insurance);
        Optional<String> s = optionalInsurance.map(Insurance::getName);
    }

    @Test
    public void getCarInsuranceName() {
        Optional<Person> person = Optional.of(new Person());
        String carInsuranceName = person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

}
