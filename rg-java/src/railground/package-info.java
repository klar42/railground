/**
 * Simple Java implementation of the RailGround Event-B model.
 * 
 * This implementation reads the commands from the stdin and writes the output to the stdout.
 * 
 * Input in the event name with (space separated) parameters in the format name=value.
 * Output is
 * * Character '+' followed by the variable values
 * * Character '-' followed by an explanatory string in case of error (unknown event, missing parameter)
 * * Character '!' if triggering a disable event.
 * 
 * Variable values is a semicolon separated list of values in the format variable name, colon, list of values enclosed
 * in '[' and ']'. The values can be either identifiers or name=value
 * 
 * Command: <Event-Name> [ <Parameter-name> '=' <Parameter-Value> ]*
 * Variable-List: <Variable> [ ';' <Variable> ]*
 * Variable: <Variable-Name> ':' '[' <Value-List>? ']'
 * Value-List: <Value> [ ', ' <Value> ]*
 * Value: <ID> | <ID> '=' <ID>
 *
 * @author tofische
*/
package railground;
