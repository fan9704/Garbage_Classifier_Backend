# Garbage Classifier Backend
### Yuntech Information management department System Analysis and Design G6 Teamwork

----

[Web Interface Page SwaggerUI](http://localhost:8080/swagger-ui/index.html)

[Remote Web Interface Page SwaggerUI](http://140.125.207.230:8080/swagger-ui/index.html)

----

##### Future Feature

Distribute System Use Messaging Queue

[RabbitMQ Messaging Manage Panel](http://140.125.207.230:15672/)

----

### Structure of Project

> Involve Application Frontend Backend Hardware

[Application Project](https://github.com/lavender0526/Garbage_Classifier_App)

[Frontend Project](https://github.com/fan9704/Garbage_Classifier_Frontend)

[Backend Project](https://github.com/fan9704/Garbage_Classifier_Backend)

[Hardware Project](https://github.com/fan9704/Garbage_Classifier_Hardware)

----

### Software Interaction


![](https://imgdb.net/storage/uploads/164f2fed45f5fcc8494eb9cf7d0856cc2f7d8700ab468a2cdfa540cccc622763.png)

----

![](https://imgdb.net/storage/uploads/8904403f2b093016211626b8dc27a9107a9dc7fea222ef486cdddf2189ce4e41.png)

----

![](https://imgdb.net/storage/uploads/ae3a9af130234554cd6a0b53b1fd118ec4e5a6bea284110905d53620c01132e0.png)

#### Spring Boot JPA MySQL - Building Rest CRUD API

For more detail, please visit:
> [Spring Boot JPA + MySQL - Building Rest CRUD API example](https://www.bezkoder.com/spring-boot-jpa-crud-rest-api/)

More Practice:
> [Spring Boot File upload example with Multipart File](https://www.bezkoder.com/spring-boot-file-upload/)

> [Spring Boot Pagination & Filter example | Spring JPA, Pageable](https://www.bezkoder.com/spring-boot-pagination-filter-jpa-pageable/)

> [Spring Data JPA Sort/Order by multiple Columns | Spring Boot](https://www.bezkoder.com/spring-data-sort-multiple-columns/)

> [Spring Boot Repository Unit Test with @DataJpaTest](https://www.bezkoder.com/spring-boot-unit-test-jpa-repo-datajpatest/)

Associations:
> [Spring Boot One To Many example with Spring JPA, Hibernate](https://www.bezkoder.com/jpa-one-to-many/)

> [Spring Boot Many To Many example with Spring JPA, Hibernate](https://www.bezkoder.com/jpa-many-to-many/)

Security:
> [Spring Boot + Spring Security JWT Authentication & Authorization](https://www.bezkoder.com/spring-boot-jwt-authentication/)

Deployment:
> [Deploy Spring Boot App on AWS – Elastic Beanstalk](https://bezkoder.com/deploy-spring-boot-aws-eb/)

> [Docker Compose Spring Boot and MySQL example](https://www.bezkoder.com/docker-compose-spring-boot-mysql/)

Fullstack:
> [Vue.js + Spring Boot + MySQL example](https://bezkoder.com/spring-boot-vue-js-mysql/)

> [Vue.js + Spring Boot + PostgreSQL example](https://bezkoder.com/spring-boot-vue-js-postgresql/)

----

### Web Interface Detail
#### User
- User Register Post:/api/register
- User Login Post:/api/login
- User ChangePassword Put:/api/changePassword
- User GetUserInfoByUsername Get:/api/userinfo/{username}
- User EditUserInfo Put:/api/EditUserInfo
- Check UserLoginStatus /Get:/api/checkLogin
- User Logout /Get:/api/logout
- **Register Api Will auto create user wallet and account**
#### Wallet
- Get WalletValueByUsername Get:/api/walletValue/{username}
- List WalletInfoByUsername Get:/api/walletInfo/{username}
- Create WalletRecord Post:/api/wallet
#### Machine
- Get All Machines By Location Get:/api/machines/location?location={location}
- Link User Machine Patch:/api/machine/{machineId}/link/{userId}
- UnLink User Machine Patch:/api/machine/{machineId}/unlink
- Lock Machine Patch:/machine/lock/{machineId}
- Unlock Machine Patch:/machine/unlock/{machineId}
- Update Recycle Record Patch:/api/machine/{machineId}
- **Create Machine Will Auto Create Any Garbage Type Of Machine Storage Data**
#### MachineStorage
- Update MachineStorage Patch:/api/machine_storage
- Read MachineStorage By Machine Get:/api/machine_storage/machine/{ID}
- Read MachineStorage By GarbageType Get:/api/machine_storage/garbage_type/{ID}
#### BankAcct
- Read BankAccount By Username Get:/api/back_acct/username/{username}
- Update User BankAccount Patch:/api/bank_acct/user
#### Every API Feature
- Create something Post:/api/something
- Read something By ID  GET:/api/something/{ID}
- Update something Put:/api/something/{ID}
- Delete something Delete:/api/something/{ID}
- Read all something GET:/api/somethings
- Update piece of something Patch:/api/something/{ID}

----

## Run Spring Boot application
```
mvn spring-boot:run
```

## shutdown through bash
```
 sudo lsof -i :8080 | grep LISTEN
 kill <PID>
```

