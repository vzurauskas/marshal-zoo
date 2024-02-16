package com.vzurauskas.experiments;

import java.util.UUID;

public final class Transaction {
    private final UUID debtor;
    private final UUID creditor;
    private final Amount amount;

    public Transaction(UUID debtor, UUID creditor, Amount amount) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;
    }

    public Amount amountFor(Account account) {
        if (creditor.equals(account.id())) {
            return amount;
        } else if (debtor.equals(account.id())) {
            return amount.negate();
        } else {
            throw new IllegalArgumentException(
                "This transfer does not relate to account " + account.id()
            );
        }
    }
}
