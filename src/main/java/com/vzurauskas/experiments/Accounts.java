package com.vzurauskas.experiments;

import java.util.Optional;
import java.util.UUID;

public interface Accounts {
    UUID open(String iban, String currency);
    Optional<Account> byId(UUID id);
    Optional<Account> byIban(String iban);
}
