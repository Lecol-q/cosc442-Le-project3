# Bugs Found and Fixed

## Bug 1: VendingMachine Constructor Array Bouds

**Observed failure:**
The test `testInitialBalance` failed before the bug was fixed.

**Test exposing the bug was:**
`VendingMachineTest.testInitialBalance`

**Source fault:**
The constructor used:

```java

for(int i = 0; i <= NUM_SLOTS; i++)

changed to 

for(int i = 0; i < NUM_SLOTS; i++)


## Bug 2: insertMoney Rejected Zero
```
**Observed Failure:**
testInsertZeroMoney failed because inserting 0 threw
a VendingMachineException

**Test exposing the bug**
`VendingMachineTest.testInsertZeroMoney`

**Source fault**
The method contained

```Java

if(amount < 1)

changed to

if(amount < 0)
```

this rejected zero even though the specificed valid
amount was >= 0

## Test Sensitivity Experiment

**Injected fault:**

In `VendingMachine.makePurchase()`, the balance calculation was temporarily changed from: 

```java

this.balance -= item.getPrice();

to 

this.balance += item.getPrice();

```

Test detecting the fault:
- testMakePurchaseWithSufficientBalance
- testMakePurchaseWithExactBalance

**Observed failure:**
The tests failed because the vending machine increased the balance instead of substracting the item's price after a successful purchase.

**Why the tests detected it:**
The tests verify that the balance decreases by the item's price after a successful purchase. The injected fault caused the balance to increase, so teh expected balance did not match the actual balance

**Correction:**
the correction would be to change it from += to -=