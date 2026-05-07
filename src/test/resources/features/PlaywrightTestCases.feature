Feature: Run testcases of playwright

  Background: background

  Scenario: Login into Instagram
    Given Login to Instagram userId = 'userId' and password = 'password' playwright


  Scenario Outline: chatting.....
    Given Login to Instagram playwright
    Then Send message '<message>' to user '<userId>' on instagram playwright

    Examples:
      | testCaseId | message         | userId     |
      | 1234567    | Hii Googles.... | Zoozubieee |

  Scenario Outline: Login into Linkedin
    Given Login to Linkedin username='<username>' & password='<passcode>'
    Examples:
      | username                          | passcode |
      | ravikrishnabattala5@gmail.com     | #####    |


  Scenario: Apply for Jobs
    Given Apply recommended jobs


  Scenario Outline: Login to Naukri
    Given Login to Naukri userId = '<username>' and password = '<passcode>'

    Examples:
      | username                          | passcode |
      | ravikrishnabattala5@gmail.com     | #####    |


  Scenario: Apply Jobs
    Given Apply naukri jobs

  Scenario: Share Interest
    Given Share Interest to Job