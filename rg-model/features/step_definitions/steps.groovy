import de.prob.Main
import de.prob.scripting.Api
import de.prob.statespace.State
import de.prob.statespace.StateSpace

import cucumber.api.DataTable

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Api api = Main.getInjector().getInstance(Api.class)
String eventb = System.getProperty("eventb")
StateSpace space = api.eventb_load(eventb) 
State state

Given(~/^machine$/) { ->
    state = space.getRoot()
    trans = state.findTransition("\$setup_constants")
    if (trans != null) {
        state = trans.getDestination()
    }
    trans = state.findTransition("\$initialise_machine")
    if (trans != null) {
        state = trans.getDestination()
    }
}

Given(~/^machine with "([^"]*)"$/) { String expr ->
    state = space.getRoot()
    trans = state.findTransition("\$setup_constants", expr)
    if (trans != null) {
        state = trans.getDestination()
    }
    trans = state.findTransition("\$initialise_machine")
    if (trans != null) {
        state = trans.getDestination()
    }
}

When(~/^fire event "([^"]*)"$/) { String eventName ->
    trans = state.findTransition(eventName)
    state = trans.getDestination()
}

When(~/^fire event "([^"]*)" with "([^"]*)"$/) { String eventName, String expr ->
    trans = state.findTransition(eventName, expr)
    state = trans.getDestination()
}

Then(~/^event "([^"]*)" is enabled$/) { String eventName ->                                                                                                                                                                                          
    trans = state.findTransition(eventName)
    assert trans != null
}

Then(~/^event "([^"]*)" with "([^"]*)" is enabled$/) { String eventName, String expr ->                                                                                                                                                                                          
    trans = state.findTransition(eventName, expr)
    assert trans != null
}

Then(~/^event "([^"]*)" is disabled$/) { String eventName ->
    trans = state.findTransition(eventName)
    assert trans == null
}

Then(~/^event "([^"]*)" with "([^"]*)" is disabled$/) { String eventName, String expr ->
    trans = state.findTransition(eventName, expr)
    assert trans == null
}

Then(~/^expression "([^"]*)" is TRUE$/) { String expr ->
    assert state.eval(expr).toString() == "TRUE"
}

Then(~/^expression "([^"]*)" is FALSE$/) { String expr ->
    assert state.eval(expr).toString() == "FALSE"
}
