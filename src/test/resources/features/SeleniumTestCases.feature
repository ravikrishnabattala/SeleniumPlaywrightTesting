Feature: Run testcases of Selenium

  Background:

  Scenario Outline: scenario outline
    Given Prerequisites to run test cases '<testCaseId>'
    Given Send message '<message>' to user '<userId>' on instagram

    Examples:
      | testCaseId | message | userId              |
      | 1234567    | Hii     | memes matrame pampu |

  Scenario: scenario
    Given Run selenium facebook test case