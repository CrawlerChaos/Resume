# Mysql

## 8.0 Command

//创建用户 %标示任意ip
`CREATE USER 'user'@'%'' IDENTIFIED BY 'password'`  
---
//只读权限
`GRANT SELECT ON dbname.tablename TO 'user'@'%';`
---
//所有权限
`GRANT ALL PRIVILEGES ON *.* TO 'user'@'%' WITH GRANT OPTION;`
---
//授权刷新
`ALTER USER 'user'@'%' IDENTIFIED WITH mysql_native_password BY 'password';`
---
//Your password does not satisfy the current policy requirements(去除密码过于简单提示)
(```)
set global validate_password.policy=0;
set global validate_password.length=4;
(```)
---

