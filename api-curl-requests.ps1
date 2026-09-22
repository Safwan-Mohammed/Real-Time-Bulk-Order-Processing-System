# Start the API with the local H2 profile before running these requests.
# Replace the UUID placeholders after creating a product or order.

$baseUrl = "http://localhost:8080"
$productId = "<PRODUCT_UUID>"
$orderId = "<ORDER_UUID>"

# User endpoints
curl.exe -i -X POST "$baseUrl/user/create" -H "Content-Type: application/json" --data '{"email":"tester@example.com","password":"Password123!","name":"Test User"}'

curl.exe -i -X PUT "$baseUrl/user/update" -H "Content-Type: application/json" --data '{"email":"tester@example.com","password":"Password123!","name":"Updated Test User"}'

curl.exe -i -X POST "$baseUrl/user/login" -H "Content-Type: application/json" --data '{"email":"tester@example.com","password":"Password123!","name":"Updated Test User"}'

curl.exe -i -X DELETE "$baseUrl/user/delete" -H "Content-Type: application/json" --data '{"email":"tester@example.com","password":"Password123!","name":"Updated Test User"}'

# Product endpoints
curl.exe -i "$baseUrl/product/"

curl.exe -i -X POST "$baseUrl/product/create" -H "Content-Type: application/json" --data '{"productCode":"SKU-001","productName":"Test Product","productDesc":"Created by cURL","stockQty":25,"price":49.99}'

curl.exe -i "$baseUrl/product/$productId"

curl.exe -i -X PUT "$baseUrl/product/update" -H "Content-Type: application/json" --data "{\"productId\":\"$productId\",\"productCode\":\"SKU-001\",\"productName\":\"Updated Test Product\",\"productDesc\":\"Updated by cURL\",\"stockQty\":20,\"price\":59.99}"

curl.exe -i -X DELETE "$baseUrl/product/delete/$productId"

# Order endpoints
# Use an existing product UUID in both $productId and this request body.
curl.exe -i -X POST "$baseUrl/order/create" -H "Content-Type: application/json" --data "{\"orderEmail\":\"tester@example.com\",\"totalAmount\":59.99,\"items\":[{\"productId\":\"$productId\",\"quantity\":1,\"unitPrice\":59.99}]}"

curl.exe -i "$baseUrl/order/fetchAllOrders"

curl.exe -i "$baseUrl/order/fetchOrder/$orderId"
