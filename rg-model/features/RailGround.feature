Feature: Railground
  As an user
  I want to have a simple railway model
  In order to try out formal modeling

  Background:
    Given machine "DEMO_Station.bum"

  Scenario: Intial value
    Then expression "PATH_CURR = {}" is TRUE
    And expression "PATH_REQ = {}" is TRUE
    And expression "PATH_REL = {}" is TRUE
    And event "add_PATH_CURR" is disabled
    And event "rem_PATH_CURR" is disabled
    And event "rem_PATH_REQ" is disabled
    And event "set_PATH_REL" is disabled
    And event "add_PATH_REQ" with "path = R01" is enabled
    And event "add_PATH_REQ" with "path = R02" is enabled
    And event "add_PATH_REQ" with "path = R03" is enabled
    And event "add_PATH_REQ" with "path = R04" is enabled
    And event "add_PATH_REQ" with "path = R05" is enabled
    And event "add_PATH_REQ" with "path = R06" is enabled
    And event "add_PATH_REQ" with "path = R07" is enabled
    And event "add_PATH_REQ" with "path = R08" is enabled
    And event "add_PATH_REQ" with "path = R09" is enabled
    And event "add_PATH_REQ" with "path = R10" is enabled
    And event "add_PATH_REQ" with "path = R11" is enabled
    And event "add_PATH_REQ" with "path = R12" is enabled
    And event "add_PATH_REQ" with "path = R13" is enabled
    And event "add_PATH_REQ" with "path = R14" is enabled

  Scenario Outline: Request Path
    When fire event "add_PATH_REQ" with "path = <path>"
    Then expression "<path> : PATH_REQ" is TRUE
    And event "rem_PATH_REQ" with "path = <path>" is enabled
    And event "add_PATH_REQ" with "path = <path>" is disabled

  Examples:
    | path |
    | R01  |
    | R02  |
    | R03  |
    | R04  |
    | R05  |
    | R06  |
    | R07  |
    | R08  |
    | R09  |
    | R10  |
    | R11  |
    | R12  |
    | R13  |
    | R14  |
