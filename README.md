# Final Project by Zeliang Yin
## Database Setup
### MySQL
#### Create Schema
```sql
drop schema if exists eshop;
create schema eshop;
```
#### Create & Grant Privileges
```sql
CREATE USER 'admin'@'%' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON eshop.* TO 'admin'@'%';
FLUSH PRIVILEGES;
```
### MongoDB
Install MongoDB
## Project Details
### API Gateway
#### Main Functions
-  Authentication
#### Structure
![](./img/api_gateway_files.png)
### Account Service
#### Main Functions
1. Authorization (login/signup) and generate JWT
2. User CRUD
#### Structure
![](./img/account_files_1.png)
![](./img/account_files_2.png)