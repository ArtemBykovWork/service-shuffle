# Service Shuffle

## Description
`service-shuffle` is a microservice that provides an API to generate a shuffled array of integers from 1 to a given number (between 1 and 1000). Additionally, it sends a log request to another microservice (`service-log`) to log the request.

## API Endpoints
### POST /shuffle
- **Description**: Generates a shuffled array of integers.
- **Request**: JSON object containing a single integer field `number` (1-1000).
- **Response**: JSON array of shuffled integers from 1 to the given number.
- **Example**:
  ```json
  // Request
  {
    "number": 5
  }
  // Response
  [4, 2, 1, 5, 3]
