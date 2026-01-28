Feature: Run testcases of Selenium

  Background:

  Scenario Outline: scenario outline
    Given Prerequisites to run test cases '<testCaseId>'
    Given Login to Instagram
    Then Send message '<message>' to user '<userId>' on instagram

    Examples:
      | testCaseId | message         | userId     |
      | 1234567    | Hii buddies.... | Zoozubieee |

  Scenario: Login
    Given Login to Instagram userId = 'userId' and password = 'password'