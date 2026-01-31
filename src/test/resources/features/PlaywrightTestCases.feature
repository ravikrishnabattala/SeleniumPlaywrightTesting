Feature: Run testcases of playwright

  Background: background

  Scenario: Login into Instagram
    Given Login to Instagram userId = 'userId' and password = 'password' playwright


  Scenario Outline: scenario outline
    Given Login to Instagram playwright

    Examples:
      | a |
      | 1 |
