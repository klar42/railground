Feature: Railground
  As an user
  I want to have a simple railway model
  In order to try out formal modeling

  Background:
    Given machine

  Scenario: Intial value
    Then expression "PATH_CURR = {}" is TRUE
    And expression "PATH_REQ = {}" is TRUE
    And expression "PATH_REL = {}" is TRUE
    And event "add_PATH_CURR" is disabled
    And event "rem_PATH_CURR" is disabled
    And event "rem_PATH_REQ" is disabled
    And event "set_PATH_REL" is disabled

  Scenario Outline: Request path and cancel the reservation again
    Given event "add_PATH_REQ" with "path = <path>" is enabled
    And event "rem_PATH_REQ" with "path = <path>" is disabled
    And expression "<path> : PATH_REQ" is FALSE
    When fire event "add_PATH_REQ" with "path = <path>"
    Then expression "<path> : PATH_REQ" is TRUE
    And event "rem_PATH_REQ" with "path = <path>" is enabled
    # No conflicting path can be requested
    And event "add_PATH_REQ" with "path : <paths>" is disabled
    When fire event "rem_PATH_REQ" with "path = <path>"
    Then expression "<path> : PATH_REQ" is FALSE
    And event "rem_PATH_REQ" with "path = <path>" is disabled
    And event "add_PATH_REQ" with "path = <path>" is enabled

  Examples:
    | path | paths                                         |
    | R01  | {R01, R02, R04, R05, R06, R09, R10, R11, R13} |
    | R02  | {R01, R02, R09, R10, R13}                     |
    | R03  | {R03, R06, R07}                               |
    | R04  | {R01, R04, R05, R06, R07, R10, R11, R14}      |
    | R05  | {R01, R04, R05, R06, R10, R11}                |
    | R06  | {R01, R03, R04, R05, R06, R07, R10, R11, R14} |
    | R07  | {R03, R04, R06, R07, R14}                     |
    | R08  | {R08}                                         |
    | R09  | {R01, R02, R09}                               |
    | R10  | {R01, R02, R04, R05, R06, R10, R11, R13}      |
    | R11  | {R01, R04, R05, R06, R10, R11}                |
    | R12  | {R12}                                         |
    | R13  | {R01, R02, R10, R13}                          |
    | R14  | {R04, R06, R07, R14}                          |

    
  Scenario Outline: Request Path with points
    Given fire event "add_PATH_REQ" with "path = <path>"
    And event "add_PATH_CURR" is disabled
    And event "rem_PATH_CURR" is disabled
    And event "set_RAIL_ELEM_PATH" is enabled

  Examples:
    | path |
    | R01  |
    | R02  |
    | R04  |
    | R05  |
    | R06  |
    | R07  |
    | R10  |
    | R11  |
    | R13  |
    | R14  |

  Scenario Outline: Request Path without points
    Given fire event "add_PATH_REQ" with "path = <path>"
    Then event "add_PATH_CURR" with "path = <path>" is enabled
    And event "rem_PATH_CURR" is disabled
    And event "set_RAIL_ELEM_PATH" is disabled
    When fire event "add_PATH_CURR" with "path = <path>"
    Then expression "<path> : PATH_REQ" is FALSE
    And expression "<path> : PATH_CURR" is TRUE
    And event "set_PATH_REL" with "path = <path>" is enabled
    And event "set_SIGNAL_ASPECT_PROCEED" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED" is enabled
    When fire event "set_SIGNAL_ASPECT_PROCEED" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED"
    Then event "set_PATH_REL" with "path = <path>" is disabled
    And event "set_SIGNAL_ASPECT_DEFAULT" with "sig = <signal>" is enabled
    When fire event "set_SIGNAL_ASPECT_DEFAULT" with "sig = <signal>"
    Then event "set_PATH_REL" with "path = <path>" is enabled
    When fire event "set_PATH_REL" with "path = <path>"
    Then expression "<path> : PATH_CURR" is TRUE
    And expression "<path> : PATH_REL" is TRUE
    And event "rem_PATH_CURR" with "path = <path>" is enabled
    And event "set_SIGNAL_ASPECT_PROCEED" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED" is disabled
    When fire event "rem_PATH_CURR" with "path = <path>"
    Then expression "<path> : PATH_CURR" is FALSE
    And expression "<path> : PATH_REL" is FALSE

  Examples:
    | path | signal |
    | R03  | S2     |
    | R08  | S4     |
    | R09  | S6     |
    | R12  | S8     |
