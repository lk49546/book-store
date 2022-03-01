package org.kelava.bookstore.purchase.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class OrderApplicationController {

    @GetMapping("")
    public String getAppRoot(){
        return "Order REST API";
    }
}
