# Quickstart Guide to Set up Dev Workspace
1. Git clone.
2. Make sure you have Java SE 1.8 or higher installed and have set your `$JAVA_HOME` to it.
3. Make sure you have MySQL server installed and your authentication (database name, username, 
and password) is in accordance with that in `application.conf` on line [60-62](/conf/application.conf#L60-62). 
Do not worry about creating database table, Play Evolutions will handle it. But you might need to re-run the app 
if not redirected to the app directly after applying the evolutions scripts.
4. Run `$ ./gradlew runPlayBinary` to run the application.
5. The application is ready on `localhost:9000` (notes: you can check available routes on routes [file](/conf/routes)) 
6. For further explanation about Play Framework, please see the documentation at https://www.playframework.com/documentation/latest/Home

# Project Structure 

Things that not included nor explained in this Structure are stuffs which were generated by the Play project template.
```
.
├── app                              > Folder containing your source code packages or source code file
├── conf
│   ├── evolutions.default           > Folder containing list of SQL script for schema migrations
│   ├── META-INF
│   │   └── persistance.xml          > JPA configurations file
│   ├── application.conf             > Main configuration for Play App
│   ├── logback.xml
│   └── routes                       > Where you define your REST ROUTING
├── gradle
├── logs
├── project
├── public                           > Folder containing public assets
│   ├── images
│   ├── javascripts
│   └── stylesheets
├── scripts
├── test                             > Folder containing your test's source code packages or source code file
├── build.gradle                     > Where you define properies for your gradle project
├── gradlew                          > Gradle executeable
├── gradlew.bat                      > Gradle executeable
└── README.md 

```

# Project Component

Basically, this project is separated into 4 main components and 1 extra components: 
1. Controller : responsible on preparing the inputs and serving the outputs and the one to call the business logic code.
2. Manager : responsible on handling the main core of the business logic. (ex: `UserManager.java, ProductManager.java, etc`)
3. Model : responsible on containing object & entity binding. (ex: `User.java, Product.java, etc`)
4. DAO : responsible on containing DB query. (ex: `UserDAO.java, ProductDAO.java, etc`)
5. (extra) Request: object that represents the data from the requests for each API.

On this project, the code are separated by its entity. So, each entity will have a controller, manager, DAO, and the model.

The DAO layers are represented using interface with each has default concrete / implementation class implemented for using MySQL with JPA as the storage.
The concrete class will be directly implemented using injection by Guice. This practice helps separating business logic and persistence layer. 
You can add other persistence layer or modify the existing without worrying about making changes in the business logic

