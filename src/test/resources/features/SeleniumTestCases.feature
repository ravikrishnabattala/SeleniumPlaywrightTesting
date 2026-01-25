Feature: Run testcases of Selenium

  Background:

  Scenario Outline: scenario outline
    Given Prerequisites to run test cases '<testCaseId>'
    Given Run selenium google test case

    Examples:
      | testCaseId |
      | 1234567    |

  Scenario: scenario
    Given Run selenium facebook test case