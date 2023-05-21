Feature: Bank account promos

  Scenario: Successfully promo applied when depositing more than 2000 (top 500)
    Given Account with a balance of 0
    When Trying to deposit 2000
    Then Account balance should be 2200

  Scenario: Successfully promo applied when depositing more than 2000 (top 500)
    Given Account with a balance of 0
    When Trying to deposit 6000
    Then Account balance should be 6500

  Scenario: Successfully promo applied when depositing more than 2000 (top 500)
    Given Account with a balance of 0
    When Trying to deposit 1000
    Then Account balance should be 1000
