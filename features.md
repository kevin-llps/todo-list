## Features

### Description
These features allow users to manage Todo items in the Todo list.

### Endpoints
- `POST /todos`: Create a new Todo item.
If it succeeds, it should return :
    - 201 HTTP status
    - New Todo as body response
A Todo item is created according to several rules :
- Mandatory fields must be validated in the API input.
- If mandatory fields are missing, it should return :
    - 400 HTTP status
    - An explicit error message as body response

- `GET /todos`: Get all Todo items.
If it succeeds, it should return :
    - 200 HTTP status
    - Todo item list as body response : Todo items must be ordered by fields expiryDate, completed, title

- `GET /todos/{id}`: Get Todo item by id.
If it succeeds, it should return :
    - 200 HTTP status
    - Todo item as body response
If the Todo item does not exist, it should return :
    - 404 HTTP status
    - An error message "Todo item was not found"

- `PATCH /todos/{id}`: Update Todo item by id.
If it succeeds, it should return :
    - 200 HTTP status
    - Updated Todo item as body response

- `DELETE /todos/{id}`: Remove Todo item by id.
If it succeeds, it should return :
    - 204 HTTP status
    - No one body response

### Database
- The `todo` table will be used to store Todo items.

