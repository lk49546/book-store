package org.kelava.bookstore.inventory.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class InventoryApplicationController {

    @GetMapping("")
    public String getAppRoot(){
        return "Inventory REST API";
    }
}
