# Hotel Tourist Management API

A Java 17 / Spring Boot REST API that updates a tourist's destination and contact details after validating a private request token. The local profile uses an in-memory H2 database and seeds tourist ID `1` for demonstration.

## Requirements

- JDK 17 or newer
- Maven 3.6.3 or newer (or use the Maven wrapper if one is added)

## Run from VS Code

1. Open this project folder in VS Code and install the **Extension Pack for Java** if needed.
2. Set the private token in the VS Code integrated terminal. Do not place a real token in source control:

   ```powershell
   $env:TOURIST_API_TOKEN = "replace-with-a-private-local-token"
   ```

3. Start the app from that same terminal:

   ```powershell
   mvn spring-boot:run
   ```

   Or use the VS Code Java extension's `HotelTouristApplication` run action after setting the environment variable in the VS Code process.

4. Run automated tests:

   ```powershell
   mvn test
   ```

The application starts at `http://localhost:8080`. It intentionally fails to start if `TOURIST_API_TOKEN` is not configured.

## Try it in Swagger UI

1. Open `http://localhost:8080/swagger-ui.html`.
2. Expand **PUT /api/tourist/{id}** and click **Try it out**.
3. Enter `1` for `id`, then use **Authorize** and enter the token set in `TOURIST_API_TOKEN`. Swagger sends it in the `X-Request-Token` header.
4. Use this JSON body and click **Execute**:

   ```json
   {
     "name": "Aarav Sharma",
     "destination": "Udaipur",
     "contactNumber": "+919876543211",
     "email": "aarav.updated@example.com"
   }
   ```

5. A successful response is HTTP `200`:

   ```json
   {
     "message": "Tourist updated successfully"
   }
   ```

   The in-memory DB is seeded with ID `1` (`Aarav Sharma`, `Jaipur`, `+919876543210`, `aarav@example.com`) each time the app starts. You can verify the updated values in the H2 console at `http://localhost:8080/h2-console`, using JDBC URL `jdbc:h2:mem:touristdb`, username `sa`, and an empty password.

## Request and error examples

The request header is `X-Request-Token`; this curl example uses a placeholder token:

```powershell
curl.exe -X PUT "http://localhost:8080/api/tourist/1" `
  -H "Content-Type: application/json" `
  -H "X-Request-Token: replace-with-a-private-local-token" `
  -d '{ "name":"Aarav Sharma", "destination":"Udaipur", "contactNumber":"+919876543211", "email":"aarav.updated@example.com" }'
```

Responses:

- Valid token and existing ID: HTTP `200`, `{"message":"Tourist updated successfully"}`
- Missing or invalid token: HTTP `401`, `{"message":"Invalid or missing request token"}`
- ID does not exist: HTTP `404`, `{"message":"Tourist not found"}`
- Invalid JSON or field values: HTTP `400`, `{"message":"Invalid request body"}`
- Unexpected server error: HTTP `500`, `{"message":"Internal server error"}`

## Project structure

- `entity/Tourist`: JPA table mapping
- `repository/TouristRepository`: Spring Data persistence
- `service/TouristService`: loads and updates an existing tourist in a transaction
- `controller/TouristController`: PUT REST endpoint and token-before-service gate
- `security/RequestTokenValidator`: compares the supplied header to configured secret
- `exception/GlobalExceptionHandler`: maps failures to HTTP responses
- `config/OpenApiConfig`: Swagger metadata and header authorization scheme
- `src/test`: API tests for success, unauthorized no-write behavior, and not-found handling

The private token is read from the `TOURIST_API_TOKEN` environment variable rather than being stored as a literal in the application configuration or source. The local H2 database is in-memory; use a production database and secret manager for deployment.
"# 2400030171_SOAEndSemSkill" 
