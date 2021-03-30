# Step by step

1. Identify the use cases for consumer
1. Write consumer pacts
1. Write consumer tests
1. Generate the pact file and upload to pact broker
1. Write provider states
1. Verify against the pact file from pact broker

# Notes:

- Separate class for a pact test
- Pact has changed since last successful verification by Weather Service
- tear down

# Questions:
1. Why the heck do we need the mock in the consumer? Why not to just declare the pact and publish?
1. Shared pact broker instance in Ocado
1. Which teams already use it?
1. Integrate the pact consumer tests into existing tests / use wiremock?


# To follow up:
1. Pipelines
1. Tags
1. Webhooks
1. Other features
1. Alternative - Spring?


# Use cases
## Weather Service

 - Get current weather
 - Get forecast by day

## Weather Sensor

 - Post weather forecast
 - Update Weather forecast (not implemented yet)
 
# Weather DTO

 - Temperature
 - Air Pressure
 - Humidity
 - Wind
 - Is safe to go outside
 
 
# Prevented incidents

- Weather Service changes  constraint on airPressure
- Weather Service changes the field name
- Weather Service adds one more not null field
- Weather Service changes the date format

# Presentation Agenda

## What is Pact
## Terminology
## How Pact Works
### Consumer
### Provider
### States
### Matching
### Pact File
### Pact broker
### More Features
#### Tags
#### Webhooks
## Demo example
### Architecture intro
### Code walk through
- Consumer tests
- Provider Tests
- Pact broker overview
## How to get started
## Ocado teams that use Pact

