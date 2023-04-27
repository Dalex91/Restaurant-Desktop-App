package com.example.restaurantdemoapp;

import com.example.restaurantdemoapp.user_interface.LoginForm;
import com.example.restaurantdemoapp.utils.BaseForm;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@NoArgsConstructor
@SpringBootApplication(scanBasePackages={"com.example.restaurantdemoapp"})
public class RestaurantDemoAppApplication extends BaseForm implements CommandLineRunner {

    @Autowired
    private LoginForm loginForm;

    public static void main (String[] args) {
        SpringApplication.run (RestaurantDemoAppApplication.class, args);
    }

    @Override
    public void run (String... args) {
        setContentPane(loginForm.getPanel1());
        setVisible (true);
    }
}
