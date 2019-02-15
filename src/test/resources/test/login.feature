Feature: Verification of user login and management of the website

  @LoginAdmin
  Scenario: Login of Admin
    Given We navigate to NCS homepage
    When We click on the button ANMELDEN
    And Enter the Benutzername "admin" with the related Passwort "!NCS2019"
    Then The login is successful and the Dashboard will be shown