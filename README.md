# Final Project by Zeliang Yin
## Database setup
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