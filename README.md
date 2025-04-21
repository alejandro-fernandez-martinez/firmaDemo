# firmaDemo

Demo application to digitally sign documents.

## System Requirements

- Java 17
- Maven 3.9
- Modern web browser

**Note:** You can check what version of Maven and Java you have with the command 
```bash
mvn -v
```

## Installation and Running Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/alejandro-fernandez-martinez/firmaDemo.git
   cd firmaDemo
   ```

2. **Build the project using Maven**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The server will start on `http://localhost:9000`.

4. **Access the API Documentation**
   - Visit: [http://localhost:9000/swagger-ui/index.html](http://localhost:9000/swagger-ui/index.html)
   - From here, you can not only access the API documentation but also interact with the API directly, testing endpoints and sending requests via the Swagger UI interface.

5. **Access the Graphical Interface**
   - Visit: [http://localhost:9000](http://localhost:9000)
   - This is a web interface built with Bootstrap for interacting with the system.
  
   ![imagen](https://github.com/user-attachments/assets/f02ea8f8-4e30-4c15-b80b-272821a7ba91)


## Examples of how to use each endpoint with Postman

**POST** `/api/createSignatory` → Returns publicKey
![imagen](https://github.com/user-attachments/assets/32672794-d2c9-4897-b094-3259a2192bc8)

**POST** `/api/createSignatureWithBase64Document` → Returns signatureBase64
![imagen](https://github.com/user-attachments/assets/3b88204d-c97f-43d0-892a-2c616e3b0f41)

**POST** `/api/verifySignatureWithDocument` 
→ Returns `Valid signature`
![imagen](https://github.com/user-attachments/assets/02d30c47-cdf8-4505-85ca-00916d3e5372)

→ Returns `Invalid signature`
![imagen](https://github.com/user-attachments/assets/9a5c83a1-c8ed-42bd-99d4-f1580713dab2)

**POST** `/api/createSignatureWithDocument` → Returns signature (object)
![imagen](https://github.com/user-attachments/assets/5e1f3329-8261-4e56-9a17-eab66ece7238)

If the provided signatoryId does not exist → `KeysNotFoundException`
![imagen](https://github.com/user-attachments/assets/868ca21a-3a51-4c63-9d6f-e88da9cf06f1)



## Technical Details

- **Backend Framework**: Spring Boot (v3.4.4)
- **Embedded Server**: Apache Tomcat (default with Spring Boot)
- **Database**: H2 (in-memory)
  - Console available at: [http://localhost:9000/h2-console](http://localhost:9000/h2-console)
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `test`

## Cryptography

- Uses **AES** for private key encryption. The secret key for AES encryption is stored in the `application.properties` file under the property `crypto.secret-key`.
- Uses **JCA** (`SHA256withRSA`) to generate digital signatures from the decrypted private key and document hash.

