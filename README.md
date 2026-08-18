# backend-shopee

Backend API mẫu cho ứng dụng thương mại điện tử kiểu Shopee.

## Công nghệ
- Java 21
- Spring Boot 3.5.16 (dòng 3.x mới nhất — dòng 3.x đã EOL, khuyến nghị nâng cấp lên 4.x khi có điều kiện)
- Maven
- Spring Web, Spring Data JPA, Spring Security, Validation
- H2 (dev) / MySQL (prod)
- Lombok

## Cấu trúc thư mục
```
src/main/java/com/shopee/backend/
├── controller/    # REST controllers
├── service/       # business logic
├── repository/    # Spring Data JPA repositories
├── model/         # JPA entities
├── dto/           # request/response DTOs
├── exception/     # custom exceptions + global handler
└── config/        # security config, beans...
src/main/resources/
├── application.yml       # cấu hình chung
├── application-dev.yml   # profile dev (H2 in-memory)
└── application-prod.yml  # profile prod (MySQL, đọc từ env vars)
```

## Chạy project

### 1. Yêu cầu
- JDK 21
- Maven 3.9+ (hoặc dùng Maven Wrapper nếu có)

### 2. Chạy ở chế độ dev (mặc định, dùng H2 in-memory)
```bash
mvn spring-boot:run
```
Mặc định `spring.profiles.active=dev` đã được set trong `application.yml`.

Sau khi chạy, kiểm tra:
- Health check: http://localhost:8080/api/v1/health
- H2 console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:shopeedb`
  - Username: `sa` / Password: (để trống)

### 3. Chạy ở chế độ prod (MySQL)
Set các biến môi trường trước khi chạy:
```bash
export DB_URL=jdbc:mysql://localhost:3306/shopee_db?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh
export DB_USERNAME=root
export DB_PASSWORD=yourpassword

mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### 4. Build file JAR
```bash
mvn clean package
java -jar target/backend-shopee-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## API mẫu (Product CRUD)
| Method | Endpoint                  | Mô tả                  |
|--------|----------------------------|-------------------------|
| GET    | /api/v1/products           | Lấy danh sách sản phẩm  |
| GET    | /api/v1/products/{id}      | Lấy chi tiết sản phẩm   |
| POST   | /api/v1/products           | Tạo sản phẩm mới        |
| PUT    | /api/v1/products/{id}      | Cập nhật sản phẩm       |
| DELETE | /api/v1/products/{id}      | Xóa sản phẩm            |

## Bước tiếp theo gợi ý
- Thêm entity `User`, `Order`, `Category`, `Cart`...
- Tích hợp JWT authentication (đã có sẵn `SecurityConfig` để mở rộng)
- Thêm Swagger/OpenAPI (springdoc-openapi)
- Viết unit test cho service/controller
