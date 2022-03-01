package org.kelava.bookstore.loyalty.domain.loyalty.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loyalty {

    private String customerId;
    private Integer count;
}
