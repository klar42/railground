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

  Scenario Outline: Request Path with points
    When fire event "add_PATH_REQ" with "path = <path>"
    Then expression "<path> : PATH_REQ" is TRUE
    And event "add_PATH_CURR" is disabled
    And event "rem_PATH_CURR" is disabled
    And event "add_PATH_REQ" with "path : <paths>" is disabled
    And event "rem_PATH_REQ" with "path = <path>" is enabled
    And event "set_RAIL_ELEM_PATH" is enabled

  Examples:
    | path | paths |
    | R01  | {R01, R02, R04, R05, R06, R09, R10, R11, R13} |
    | R02  | {R01, R02, R09, R10, R13} |
    | R04  | {R01, R04, R05, R06, R07, R10, R11, R14} |
    | R05  | {R01, R04, R05, R06, R10, R11} |
    | R06  | {R01, R03, R04, R05, R06, R07, R10, R11, R14} |
    | R07  | {R03, R04, R06, R07, R14} |
    | R10  | {R01, R02, R04, R05, R06, R10, R11, R13} |
    | R11  | {R01, R04, R05, R06, R10, R11} |
    | R13  | {R01, R02, R10, R13} |
    | R14  | {R04, R06, R07, R14} |

  Scenario Outline: Request Path without points
    When fire event "add_PATH_REQ" with "path = <path>"
    Then expression "<path> : PATH_REQ" is TRUE
    And event "add_PATH_CURR" with "path = <path>" is enabled
    And event "rem_PATH_CURR" is disabled
    And event "add_PATH_REQ" with "path : <paths>" is disabled
    And event "rem_PATH_REQ" with "path = <path>" is enabled
    And event "set_RAIL_ELEM_PATH" is disabled

  Examples:
    | path | paths |
    | R03  | {R03, R06, R07} |
    | R08  | {R08} |
    | R09  | {R01, R02, R09} |
    | R12  | {R12} |
