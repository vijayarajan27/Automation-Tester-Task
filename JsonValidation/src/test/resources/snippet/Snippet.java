package snippet;

public class Snippet {
	{
	  "$schema": "http://json-schema.org/draft-07/schema#",
	  "title": "UserRegistration",
	  "type": "object",
	  "properties": {
	    "username": {
	      "type": "string",
	      "minLength": 3,
	      "maxLength": 30
	    },
	    "email": {
	      "type": "string",
	      "format": "email"
	    },
	    "age": {
	      "type": "integer",
	      "minimum": 13
	    },
	    "roles": {
	      "type": "array",
	      "items": { "type": "string" }
	    }
	  },
	  "required": ["username", "email", "roles"],
	  "additionalProperties": false
	}
	
}

