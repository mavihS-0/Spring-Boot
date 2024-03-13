package com.mavcorp.beanbasics;

import com.mavcorp.beanbasics.config.PizzaConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrintController {

    private final PizzaConfig pizzaConfig;

    public PrintController(PizzaConfig pizzaConfig){
        this.pizzaConfig = pizzaConfig;
    }
    @GetMapping(path="/")
    public String printPizzaDetails(){
        return String.format("I want a %s crust pizza, with %s and %s sauce",pizzaConfig.getCrust(),pizzaConfig.getTopping(),pizzaConfig.getSauce());
    }
}
