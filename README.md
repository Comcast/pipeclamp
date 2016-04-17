# Pipeclamp

A tool for validating structured data and providing metrics on data streams. It works 
primarily with Avro/JSON data for now but can be extended to support other schemas.

### Example

You can augment an existing Avro schema by including new tags that reference
type-specific constraints. In the partial Person schema below we'd like to ensure that 
first name values are kept between 2 and 30 characters in length:

```json
		fields : {
			"name": "firstname",	
			"type": "string",
			"doc": "Person's first name",
			"constraints" : [
			{ 
				"function" : "length", 
				"id" : "firstNameLength",
				"args" : [
					{ "name" : "min", "value" : "2"}, 
					{ "name" : "max", "value" : "30"} 
					] 
				} ]  
		},
```

You can then use the modified schema to configure a validator that checks all
firstname values and returns any violations found in a map keyed by paths to
the offending field:

```java
	validator = new AvroValidator(Person.getClassSchema(), false);
	
	Person p = newPerson(……);			// deserialized from JSON/Avro
	Map<Path, Collection<Violation>> issues = validator.validate(p);

	if (issues.isEmpty()) return;

	// else simple issuehandler that prints them out
	for (Entry<Path,Collection<Violation>> errors : issues.entrySet()) {
		System.err.println(……);
		}
	
```

Like XML, as long as your schema is syntactically correct, Json parsers will ignore 
new tags leaving you free to fully document & constraint your schemas and ensure that
everyone that references them is on the same page.

A full listing of the available constraints and how to write your own are listed in 
the /docs/manual.pdf