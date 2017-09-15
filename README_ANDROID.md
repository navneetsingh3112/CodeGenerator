## Steps to be followed, after generating code:

1. Copy CodeGenerator/output/AndroidApp/storm/NovopayPlatformService to storm/NovopayPlatformService folder and merge the folders

2. Open CodeGenerator/output/AndroidApp/<<EntityName>>Common.tx and copy code snippet to respective files as mentioned there

3. Run the Code in AndroidStudio


## input.json

{
	"table":"general_ledger",
	"table_display_Name": "General Ledger",
	"service":"accounting",								// ServiceAndUserStoryUtils.java
	"service_display_name": "Accounting",				// ServiceAndUserStoryUtils.java
	"author":"anandparmar",
	"table_comment" : "General Ledger for Accounting", 	// SERVER SIDE FIELD
	"userStory":"generalledger",						// SERVER SIDE FIELD
	"fields": [
		{
			"name": "gl_code",
			"display_name" : "Code",
			"java_type": "String",
			"max_length": 8,
			"is_mandatory": true,
			"is_primary": true,
			"is_secondary": true,
			"is_searchable": true,						// SERVER SIDE FIELD
			"comment": "Code",							// SERVER SIDE FIELD
			"sql_type": "VARCHAR"						// SERVER SIDE FIELD
		},
		{
			"name": "created_on",
			"java_type": "Date",
			"sql_type": "DATETIME",
			"is_mandatory": true,
			"comment": "Created on"
		},
		{
			"name": "created_by",
			"java_type": "String",
			"sql_type": "VARCHAR",
			"max_length": 64,
			"is_mandatory": true,
			"comment": "Created by"
		},
		{
			"name": "updated_on",
			"java_type": "Date",
			"sql_type": "DATETIME",
			"is_mandatory": true,
			"comment": "Updated on"
		},
		{
			"name": "updated_by",
			"java_type": "String",
			"sql_type": "VARCHAR",
			"max_length": 64,
			"is_mandatory": true,
			"comment": "Updated on"
		}
	]
}

