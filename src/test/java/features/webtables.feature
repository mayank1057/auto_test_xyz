Feature: WebTables Automation

Scenario Outline: Add a user and validate the user has been added to the table
  Given I am on the webtables page
  When I add a user with the following details:
    | <firstName>  |
    | <lastName>   |
    | <username>   |
    | <password>   |
    | <email>      |
    | <phone>      |
    | <customer>   |
    | <role>       |
  Then I should see the user "<username>" in the webtables

Examples:
  | firstName | lastName | username | password   | email           | phone     | customer    | role  |
  | James     | Bond     | jamesb   | password1  | bond@mail.com   | 123456789 | Company AAA | Admin |

Scenario Outline: Delete a user from the table and validate the user has been deleted
  Given I am on the webtables page
  When I delete the user "<username>"
  Then I should not see the user "<username>" in the webtables

Examples:
  | username |
  | novak    |