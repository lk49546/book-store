package org.kelava.bookstore.inventory.domain.book.core.command;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Map;

@Builder
@RequiredArgsConstructor
@Getter
public class DecrementStockCommand {
    private final Map<String, Integer> stockUpdateMap;
}
