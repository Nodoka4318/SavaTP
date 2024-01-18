# SavaTP
 a simple teleport command plugin for spigot servers

## Commands

| Command             | Description                                      |
|---------------------|--------------------------------------------------|
| /savatp \<name>     | Teleport to the point specified in arg-1         |
| /savatp set \<name> | Add current location to teleport points as arg-1 |
| /savatp list        | List all teleport points                         |
| /savatp delete      | Delete the point specified in arg-1              |
| /savaregister       | Create player's api key                          |

## REST-API

Each contexts can be customized by `plugins/SavaTP/config.json`.
### `GET /savapoints`

- **Description:** Retrieve information about saved points.

- **Request:**
    - Method: `GET`
    - URL: `/savapoints`

- **Response:**
    - Body:
      ```json
      {
        "Locations": [
          {
            "X": <value>,
            "Y": <value>,
            "Z": <value>,
            "Yaw": <value>,
            "Pitch": <value>,
            "World": "<world-name>",
            "Name": "<point-name>",
            "CreatorUUID": "<uuid>"
          },
          // Additional points if available
        ]
      }
      ```

### `GET /deletepoint`

- **Description:** Delete a saved point.

- **Request:**
    - Method: `GET`
    - URL: `/deletepoint?key=<api-key>&point=<point-name>`

- **Response:**
    - Body:
      ```json
      {
        "status": "ok"
      }
      ```
      or
      ```json
      {
        "status": "ng"
      }
      ```
        - Status "ok" indicates successful deletion, and "ng" indicates failure.
