import cucumber.api.*
import static cucumber.api.groovy.EN.*
import static cucumber.api.groovy.Hooks.*

def quoted = /(?:"|')([^"']*)(?:"|')/

Given(~/^can request path ${quoted}$/) { List<String> pathList ->
    boolean result = true
    for (path in pathList) {
      result &= isEventEnabled("add_PATH_REQ", "path = ${path}")
    }
}

Given(~/^can not request path ${quoted}$/) { List<String> pathList ->
    boolean result = true
    for (path in pathList) {
        result &= isEventDisabled("add_PATH_REQ", "path = ${path}")
    }
}

When(~/^request path ${quoted}$/) { String path ->
    fireEvent("add_PATH_REQ", "path = ${path}")
}

Given(~/^can cancel path request ${quoted}$/) { String path ->
    isEventEnabled("rem_PATH_REQ", "path = ${path}")
}

Given(~/^can not cancel path request ${quoted}$/) { String path ->
    isEventDisabled("rem_PATH_REQ", "path = ${path}")
}

When(~/^cancel path request ${quoted}$/) { String path ->
    fireEvent("rem_PATH_REQ", "path = ${path}")
}

Then(~/^is requested path ${quoted}$/) { String path ->
    isExpressionTRUE("${path} : PATH_REQ")
}

Then(~/^is not requested path ${quoted}$/) { String path ->
    isExpressionFALSE("${path} : PATH_REQ")
}


Given(~/^can lock path ${quoted}$/) { String path ->
    isEventEnabled("add_PATH_CURR", "path = ${path}")
}

Given(~/^can not lock path ${quoted}$/) { String path ->
    isEventDisabled("add_PATH_CURR", "path = ${path}")
}

When(~/^lock path ${quoted}$/) { String path ->
    fireEvent("add_PATH_CURR", "path = ${path}")
}

Given(~/^can unlock path ${quoted}$/) { String path ->
    isEventEnabled("rem_PATH_CURR", "path = ${path}")
}

Given(~/^can not unlock path ${quoted}$/) { String path ->
    isEventDisabled("rem_PATH_CURR", "path = ${path}")
}

When(~/^unlock path ${quoted}$/) { String path ->
    fireEvent("rem_PATH_CURR", "path = ${path}")
}

Then(~/^is locked path ${quoted}$/) { String path ->
    isExpressionTRUE("${path} : PATH_CURR")
}

Then(~/^is not locked path ${quoted}$/) { String path ->
    isExpressionFALSE("${path} : PATH_CURR")
}


Given(~/^can release path ${quoted}$/) { String path ->
    isEventEnabled("set_PATH_REL", "path = ${path}")
}

Given(~/^can not release path ${quoted}$/) { String path ->
    isEventDisabled("set_PATH_REL", "path = ${path}")
}

When(~/^release path ${quoted}$/) { String path ->
    fireEvent("set_PATH_REL", "path = ${path}")
}

Then(~/^is released path ${quoted}$/) { String path ->
    isExpressionTRUE("${path} : PATH_REL")
}

Then(~/^is not released path ${quoted}$/) { String path ->
    isExpressionFALSE("${path} : PATH_REL")
}

