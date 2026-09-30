# Conception UML — ATM System

Diagrammes de conception du système : cas d'utilisation, classes, et séquence pour le retrait d'espèces.
*(Design diagrams for the system: use case, class, and a sequence diagram for cash withdrawal.)*

> Note : les fonctionnalités de sécurité ajoutées au projet (hachage du PIN, OTP) ne figurent pas encore dans ces diagrammes de conception initiaux — à mettre à jour au fur et à mesure de l'implémentation.

---

## 1. Use Case Diagram / Diagramme de cas d'utilisation

**Actors (Acteurs):** Customer/Client, Bank (external system/système externe), Operator/Opérateur.

```mermaid
graph LR
    Customer((Customer<br/>Client))
    Operator((Operator<br/>Opérateur))
    Bank((( Bank<br/>Banque )))

    UC1([Insert Card &<br/>Authenticate])
    UC2([Balance Inquiry])
    UC3([Deposit Cash])
    UC4([Deposit Check])
    UC5([Withdraw Cash])
    UC6([Transfer Funds])
    UC7([Print Receipt])
    UC8([Cancel / Eject Card])
    UC9([Refill Cash &<br/>Receipts])
    UC10([Review Transaction Log])

    Customer --> UC1
    Customer --> UC2
    Customer --> UC3
    Customer --> UC4
    Customer --> UC5
    Customer --> UC6
    Customer --> UC8

    UC2 -.include.-> UC1
    UC3 -.include.-> UC1
    UC4 -.include.-> UC1
    UC5 -.include.-> UC1
    UC6 -.include.-> UC1
    UC5 -.include.-> UC7

    UC1 --> Bank
    UC2 --> Bank
    UC5 --> Bank
    UC6 --> Bank

    Operator --> UC9
    Operator --> UC10
```

**Notes (EN):** Every money-related use case *includes* "Insert Card & Authenticate" (no transaction without a valid PIN). The ATM talks to the Bank system to authenticate and to post transactions. The Operator has separate use cases (refilling, checking the log) — they don't perform customer transactions.

**Notes (FR) :** Chaque cas d'utilisation lié à l'argent *inclut* « Insérer la carte et s'authentifier » (aucune transaction sans PIN valide). Le GAB communique avec le système bancaire pour authentifier et enregistrer les transactions. L'opérateur a des cas d'utilisation séparés (réapprovisionnement, consultation du journal) — il n'effectue pas les transactions du client.

---

## 2. Class Diagram / Diagramme de classes

```mermaid
classDiagram
    class ATM {
        -atmId: String
        -location: String
        +startSession()
        +endSession()
    }
    class CardReader {
        +readCard(card: Card) Boolean
        +ejectCard()
    }
    class Keypad {
        +enterPIN() String
        +selectOption() String
    }
    class Screen {
        +displayMessage(msg: String)
    }
    class CashDispenser {
        -cashOnHand: float
        +dispense(amount: float) Boolean
    }
    class DepositSlot {
        +acceptDeposit(item: Deposit)
    }
    class Printer {
        +printReceipt(t: Transaction)
    }
    class Session {
        -sessionId: String
        -isAuthenticated: Boolean
        +authenticate(pin: String) Boolean
        +cancel()
    }
    class Card {
        -cardNumber: String
        -expiryDate: Date
    }
    class Customer {
        -customerId: String
        -name: String
    }
    class Bank {
        +verifyPIN(card: Card, pin: String) Boolean
        +postTransaction(t: Transaction) Boolean
    }
    class Account {
        <<abstract>>
        -accountNumber: String
        -balance: float
        +getBalance() float
        +debit(amount: float)
        +credit(amount: float)
    }
    class CheckingAccount {
        +withdrawalLimit: float
    }
    class SavingsAccount {
        +interestRate: float
    }
    class Transaction {
        <<abstract>>
        -transactionId: String
        -timestamp: DateTime
        -status: String
        +execute()
    }
    class BalanceInquiry
    class Deposit {
        -amount: float
        -verified: Boolean
    }
    class Withdrawal {
        -amount: float
    }
    class Transfer {
        -amount: float
        -toAccount: Account
    }
    class TransactionLog {
        +addEntry(t: Transaction)
        +getEntries() List
    }
    class Operator {
        -employeeId: String
        +refillCash()
        +viewLog()
    }

    ATM "1" *-- "1" CardReader
    ATM "1" *-- "1" Keypad
    ATM "1" *-- "1" Screen
    ATM "1" *-- "1" CashDispenser
    ATM "1" *-- "1" DepositSlot
    ATM "1" *-- "1" Printer
    ATM "1" *-- "1" TransactionLog
    ATM "1" -- "0..1" Session
    Session "1" -- "1" Card
    Card "1" -- "1" Customer
    Customer "1" -- "1..2" Account
    Account <|-- CheckingAccount
    Account <|-- SavingsAccount
    Session "1" --> "*" Transaction
    Transaction <|-- BalanceInquiry
    Transaction <|-- Deposit
    Transaction <|-- Withdrawal
    Transaction <|-- Transfer
    Transaction "*" --> "1" Bank
    Operator "1" -- "1" ATM
```

**Notes (EN):** `Account` and `Transaction` are abstract base classes — this keeps the design open for new account or transaction types later without changing existing code. A `Customer` can own 1–2 accounts (Checking, Savings).

**Notes (FR) :** `Account` et `Transaction` sont des classes abstraites — cela permet d'ajouter facilement de nouveaux types de comptes ou de transactions plus tard sans modifier le code existant. Un `Customer` peut posséder 1 à 2 comptes (Courant, Épargne).

---

## 3. Sequence Diagram — Withdraw Cash / Retrait d'argent

```mermaid
sequenceDiagram
    actor C as Customer
    participant CR as CardReader
    participant KP as Keypad
    participant S as Screen
    participant ATM as ATM System
    participant B as Bank
    participant CD as CashDispenser
    participant P as Printer

    C->>CR: Insert card
    CR->>ATM: readCard()
    ATM->>S: "Enter PIN"
    C->>KP: Enter PIN
    KP->>ATM: pin
    ATM->>B: verifyPIN(card, pin)
    B-->>ATM: authenticated = true
    ATM->>S: "Select transaction"
    C->>KP: Select "Withdraw Cash"
    ATM->>S: "Select account & amount"
    C->>KP: Choose account, enter amount
    ATM->>B: checkBalance(account)
    B-->>ATM: balance
    alt sufficient funds
        ATM->>B: postTransaction(withdrawal)
        B-->>ATM: approved
        ATM->>CD: dispense(amount)
        CD-->>ATM: cash dispensed
        ATM->>P: printReceipt(transaction)
        ATM->>S: "Take your cash & receipt"
        ATM->>CR: ejectCard()
    else insufficient funds
        ATM->>S: "Insufficient funds"
        ATM->>CR: ejectCard()
    end
```

**Notes (EN):** This is the "happy path" plus the one alternate branch (insufficient funds). Note the bank is consulted twice: once to authenticate, once to authorize the actual debit — this matches the requirement that "without authentication, the user cannot perform any transaction."

**Notes (FR) :** Ceci est le scénario nominal, plus la branche alternative (fonds insuffisants). La banque est consultée deux fois : une fois pour authentifier, une fois pour autoriser le débit réel — conformément à l'exigence selon laquelle « sans authentification, l'utilisateur ne peut effectuer aucune transaction ».
