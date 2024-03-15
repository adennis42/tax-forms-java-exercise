# Java Assessment

This exercise is designed to evaluate your understanding of Java, SQL, code organization, and RESTful API design concepts.

## Requirements

1. Java 21
1. PostgreSQL 15

## Setup instructions

1. Create a `tax_forms_dev` database.
2. Copy [application-example.properties](src/main/resources/application-example.properties) to `application-dev.properties` in the `src/main/resources` directory.
3. Create a run configuration for the IDE of your choosing(IntelliJ, Eclipse, etc.).

## Sample Application Description

This application sample is the API portion of a form workflow application for users to fill out tax form information
and submit to a separate reviewing user group which may either return the submitted form information to the user, or accept the form.

Authentication, authorization, and users are outside the scope of the exercise.

One entity and table is provided: `TaxForm` [See src/main/java/consulting/reason/taxformsexample/entity/TaxForm.java](src/main/java/consulting/reason/tax_forms_api/entity/TaxForm.java).

This entity has the following columns:
- id (Integer and primary key)
- form_year (Integer)
- form_name (String)
- status (Enum, one of 'NOT_STARTED', 'IN_PROGRESS', 'SUBMITTED', 'RETURNED', or 'ACCEPTED'. The initial status of a form is 'NOT_STARTED')
- details (JSONB, represents a JSON object used for storing inputted information from a UI)
- created_at (ZonedDateTime)
- updated_at (ZonedDateTime)

There are 3 provided endpoints implemented:
1. `GET /forms?year=:year` Used for listing all forms by year
1. `GET /forms/:id` Used for finding a form by id
  - If a form with the provided id does not exist, a 404 response is returned
1. `PATCH /forms/:id` Saving the form and updating the `details` column of the record
  - If a form with the provided id does not exist, a 404 response is returned
  - If a form status is 'NOT_STARTED', the form status is updated to 'IN_PROGRESS'
  - If a form status is 'SUBMITTED', 'RETURNED' or 'ACCEPTED' the request fails and an exception is returned

An Insomnia REST client JSON file is included with the exercise `TaxForms_Insomnia.json` to help facilitate development.

# Assessment Tasks and Instructions

5 assessment tasks are provided targeted towards adding features to an existing application.
Tasks 4 and 5 are entirely optional.

## Task 1. Add validation to the save endpoint

- [ ] Add the following validation constraints to the `TaxFormDetailsRequest` class used as the request body in the `PATCH /forms/:id` endpoint:
  - assessedValue: Required; Must be in between 0 and 100,000
  - appraisedValue: Not Required; Must be in between 0 and 100,000
  - ratio: Required; Must be in between 0 and 1
  - comments: Max length of 500 characters

- [ ] Add relevant tests to `TaxFormControllerTest`
- [ ] Add relevant tests to `TaxFormServiceTest`

## Task 2. Add a TaxFormHistory entity and table
- [ ] Create a new database table and `Entity` `TaxFormHistory`. 
  - The entity should have a `ManyToOne` relationship to a `TaxForm` record and contain the following fields:
  - tax_form_id
  - createdAt
  - type (One of SUBMITTED, RETURNED, or ACCEPTED)

- [ ] Create a new DTO class for the new `TaxFormHistory` and add a new variable to `TaxFormDto` containing a list of the new DTO objects. 

Reference `ModelMapperConfig` on how to map the entity instance to a DTO instance.

- [ ] Add relevant tests to `TaxFormServiceTest`

## Task 3. Add an endpoint to submit a Tax Form
- [ ] Update `TaxFormStatusUtils` to handle the status change to `SUBMITTED` using the provided status workflow diagram as a reference
- [ ] Create a new endpoint in `TaxFormController` that accepts id as a path variable.
  - Endpoint must update a `TaxForm` record status to `SUBMITTED` if permitted.
  - Endpoint must create a new `TaxFormHistory` record for the respective `TaxForm` record with a type of `SUBMITTED`
  - If the status change is not permitted or does not follow the status workflow, throw an exception similar to the save endpoint
- [ ] Add relevant tests to `TaxFormControllerTest`
- [ ] Add relevant tests to `TaxFormServiceTest`
- [ ] Add relevant tests to `TaxFormStatusUtilsTest`

## Task 4. Add an endpoint to return a Tax Form (optional)
- [ ] Update `TaxFormStatusUtils` to handle the status change to `RETURNED` using the provided status workflow diagram as a reference
- [ ] Create a new endpoint in `TaxFormController` that accepts id as a path variable.
  - Endpoint must update a `TaxForm` record status to `RETURNED` if permitted.
  - Endpoint must create a new `TaxFormHistory` record for the respective `TaxForm` record with a type of `RETURNED`
  - If the status change is not permitted or does not follow the status workflow, throw an exception similar to the save endpoint

- [ ] Add relevant tests to `TaxFormControllerTest`
- [ ] Add relevant tests to `TaxFormServiceTest`
- [ ] Add relevant tests to `TaxFormStatusUtilsTest`

## Task 5. Add an endpoint to accept a Tax Form (optional)
- [ ] Update `TaxFormStatusUtils` to handle the status change to `ACCEPTED` using the provided status workflow diagram as a reference
- [ ] Create a new endpoint in `TaxFormController` that accepts id as a path variable.
  - Endpoint must update a `TaxForm` record status to `ACCEPTED` if permitted.
  - Endpoint must create a new `TaxFormHistory` record for the respective `TaxForm` record with a type of `ACCEPTED`
  - If the status change is not permitted or does not follow the status workflow, throw an exception similar to the save endpoint

Reference the provided workflow diagram to valid the status change; if it is not permitted or does not follow the status workflow, throw an exception similar to the save endpoint

- [ ] Add relevant tests to `TaxFormControllerTest`
- [ ] Add relevant tests to `TaxFormServiceTest`
- [ ] Add relevant tests to `TaxFormStatusUtilsTest`

### Status Workflow Diagram

![status-workflow.png](status-workflow.png)
