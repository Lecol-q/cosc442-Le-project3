# AI Test Review

Overall, the AI-generated tests provided a useful 
starting point by covering expected behaviors such as 
adding and removing items, inserting money, making 
purchases, and returning change. However, the initial 
coverage for `VendingMachine.java` was only 4/43 lines 
and 1/20 branches. After reviewing the source code, we 
identified a constructor bug caused by an off-by-one 
error in the loop, which prevented the vending machine 
from initializing correctly and stopped tests from 
reaching most of its methods. The AI helped identify 
this issue, but its suggestions still needed to be 
reviewed to ensure they matched the actual code and 
requirements.

Compared with my manually designed suite from Project 
2/Task 1, the initial AI-generated suite offered 
additional test ideas, but it did not initially 
achieve good coverage of `VendingMachine.java`. This 
showed that having many tests does not necessarily 
mean the important code is being tested. 
Coverage-guided prompting improved the suite because I 
provided the initial coverage results and asked the AI 
to focus on uncovered behavior. For example, 
identifying and fixing the constructor's off-by-one 
error allowed the tests to reach the other methods, 
and the final coverage reached 100%. I reviewed the 
generated tests rather than accepting them blindly, 
checking that assertions tested meaningful outcomes 
and did not duplicate existing tests or assume 
incorrect behavior.

This exercise demonstrated that AI can generate useful 
test cases quickly and improve them when given 
specific coverage feedback. However, AI-generated 
tests are not automatically correct or comprehensive. 
Incorrect assumptions, redundant tests, and weak 
assertions can still occur, so developers must compare 
the tests against the requirements and inspect what 
each assertion actually verifies. Reaching 100% 
coverage is a positive result because all measured 
lines and branches were exercised, but it does not 
guarantee that every possible defect has been found. 
Human review remains important for producing reliable 
software tests.



# AI Interaction Transcript — Initial Test Generation

**User request:** Generate JUnit 5 tests for the `VendingMachine` and `VendingMachineItem` classes based on the provided source code.

**AI response summary:** The AI generated two JUnit 5 test classes.

- `VendingMachineItemTest.java`: tested construction with positive, zero, and negative prices, and verified the name and price getters.
- `VendingMachineTest.java`: tested initialization, slot constants, adding and removing items, occupied and invalid slots, money insertion, balance retrieval, purchase conditions, free items, and returning change. Parameterized tests were used to check multiple slot codes.

**Coverage-guided follow-up:** After running the tests, the initial coverage for `VendingMachine.java` was 4/43 lines and 1/20 branches. I provided those results to the AI and asked it to identify uncovered behavior and improve the suite. The AI identified an off-by-one error in the constructor loop that prevented the machine from initializing correctly. After the issue was fixed and coverage was rerun, the final coverage reached 100%.

**Review:** The AI provided a useful starting point, but its generated tests and suggestions required review against the source code and assignment requirements.