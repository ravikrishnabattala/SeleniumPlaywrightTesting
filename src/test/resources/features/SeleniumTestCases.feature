Feature: Run testcases of Selenium

  Background:

  Scenario: Login
    Given Login to Instagram userId = 'userId' and password = 'password'

  Scenario Outline: chatting.....
    Given Prerequisites to run test cases '<testCaseId>'
    Given Login to Instagram
    Then Send message '<message>' to user '<userId>' on instagram

    Examples:
      | testCaseId | message         | userId              |
      | 1234567    | Hii Googles.... | memes matrame pampu |

  Scenario Outline: scrolling....
    Given Login to Instagram
    Then Scroll reels per <time> minutes

    Examples:
      | time |
      | 5    |

  Scenario: stories viewer...
    Given Login to Instagram
    Then Watch all stories
