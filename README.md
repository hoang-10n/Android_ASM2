# Android Assignment 2 - s3749795 - Doan Luong Hoang

## Functionality

This assignment requires the students to build an Android app to guide the volunteers to find the testing sites. The app is integrated with Google Maps for location and SQLite for database storage. The app should have the following functionalities:

    - A map with all sites that user can click.
    - User can filter, search and sort all the sites.
    - Notifications are sent when there are changes in databases.
    - User can login or register into the app.

There are total of 3 users. They can use different functions of the app:

    - Volunteer:
            - View registered locations on the map
            - View info of the locations
            - Register them and their friends to the sites.
    - Site leader:
            - Set up locations
            - Edit info of the locations
            - See list of people joining the sites
            - Input reports after ending sites.
    - Admin: view and edit all reports and all zones.

Non-functional requirements:

    - Condition checking with try/catch, comments and good code quality.
    - Consistent design and user-friendly interfaces.
    - Easy to use, navigate between activities and fragments.
    - Allow storing data and send notifications.

The flow of the app is like below:

    1. The list of sites is displayed when the app starts.
    2. The user clicks on any sites to change to login/register menu.
    3. The user login/register.
    4. There is a navigation bar at the bottom.
    5. The admin can navigate between the list of sites, users and reports, while the volunteers/leaders can navigate through site list, map and account info.
    6. There are options to search, filter and sort through the list.

## Database and backend

The data is published on the backend server on Heroku. The application will fetch the data from the server when it starts. In this app, I use SQLite database to store the data received from the request. There are 3 types of entities in the app:

    - The 'zone' table is used to store the site information.
    - The 'user' table is used to store info about the 2 types of users (the *admin* user is hardcoded).
    - The 'report' table is used to store the report data after the sites are closed.

## Technology

Beside using Java for coding and Gradle for the main application, there are some services and packages that I use in my application to increase the efficiency of the coding process:

    - SQLite database: to store the data fetch from the server.
    - Volley (in Gradle): to generate requests more efficiently.
    - Gson (in Gradle): to handle the JSONObject and JSONArray datatype, process the data from Volley.
    - QRGenearator (in Gradle) (extra): to generate QR code.
    - Spring Boot application (for backend): to act as a server to distribute and modify data.
    - Heroku: to deploy the Spring Boot application.
    - Google Play Service (in Gradle): for map and location.

## Design

There are total of 6 activities and 12 fragments that the users can navaigate through. There are also other components included in the interfaces such as dialogs for creating dialogs and popups, adapters for creating list of objects.
There are some other classes that work in the backend. The Controller classes will initialize the Volley requests and the Database classes will process the data into Model entities and store them. Finally the Helper classes are responsible for various tasks: broadcasting, sending notifications, writing to files...
