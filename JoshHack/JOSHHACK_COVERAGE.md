# JoshHack Inventory Test Coverage

## Objective

Improve statement/line and branch coverage for `Inventory.java` by creating JUnit 5 tests for its methods, normal behavior, and boundary cases.

## Baseline Coverage

Baseline coverage was measured before adding the new `InventoryTest.java` tests.

| Metric                  | Baseline Coverage |
| ----------------------- | ----------------- |
| Statement/Line Coverage | 24/24             |
| Branch Coverage         | 18/18             |

## Tests Added

Created `InventoryTest.java` to test the following behaviors:

- **Constructor:** Verifies that an inventory has the requested capacity and begins with empty slots.
    
- **Adding items:** Checks that items are added to the first available slot and that items are not added when the inventory is full.
    
- **Removing items:** Verifies that removing a stored item clears its slot and that attempting to remove an absent item does not change the inventory.
    
- **Item identity:** Confirms that removal uses object identity rather than treating a different item with the same name as the stored item.
    
- **Contains:** Tests whether the inventory correctly identifies present and absent items.
    
- **Full inventory:** Checks both full and partially empty inventories.
    
- **Zero capacity:** Tests the behavior of an inventory with no available slots.
    
- **Reusing slots:** Verifies that an item can be added after another item is removed.
    

## Final Coverage

Coverage was measured again after adding and running the new tests.

|Metric|Baseline Coverage|Final Coverage|
|---|---|---|
|Statement/Line Coverage|TBD|TBD|
|Branch Coverage|TBD|TBD|

## Remaining Uncovered Behavior

Review the VS Code coverage report for any lines or branches in `Inventory.java` that remain uncovered. Record them here.

- TBD — Update this section based on the final coverage report.
    

## Conclusion

The additional JUnit 5 tests exercise the main inventory operations, including adding and removing items, checking membership, determining whether the inventory is full, and handling boundary cases. The final coverage report will show how much these tests improved coverage and whether any behavior remains untested.

High coverage indicates that more of the implementation has been exercised, but it does not guarantee that every possible defect has been detected.

