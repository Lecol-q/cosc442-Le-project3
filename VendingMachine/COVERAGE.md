## Condition-Coverage Analysis

In `makePurchase`, the Boolean conditions are `(item != null)` and `(this.balance >= item.getPrice())`. My tests cover both outcomes of these conditions. The test `testMakePurchaseFromEmptySlot()` checks the case where `item` is `null`. Other tests check when the balance is sufficient to purchase an item and when the balance is less than the item's price. These tests help verify that the conditions behave correctly for valid purchases and unsuccessful purchases.

## Test-Sensitivity Experiment

I temporarily injected a fault into `VendingMachine.java` for the coverage experiment. After running the complete JUnit test suite, the test `testMakePurchaseWithExactBalance` failed. This demonstrates that the test suite can detect the injected fault when the balance exactly equals the item's price. After the experiment, I removed the injected fault and reran the tests to verify that the implementation passed.
