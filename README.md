# Physio Connect

This project is a collection of services that work together to create a Physiotherapy management system. It consists of the following services:

-   postgres: A PostgreSQL database container for storing data related to the Physio Connect application.
-   physio-connect-api: An API service for handling backend logic and communication with the database.
-   mail-hog: A service for capturing and viewing emails sent by the application for demo purposes.
-   physio-connect-manager: A web-based user interface for phyisiotherapists.
-   physio-connect-patient: A web-based user interface for patients.

## Requirements

-   Docker installed on your machine.

## Deployment

Follow the steps below to deploy the Physio Connect application using Docker Compose:

1. Clone the project repository to your local machine.
2. Navigate to the physio-connect-docker directory containing the `docker-compose.yml` file.
```
cd physio-connect-docker
```
3. Open a terminal and run the following command to start the services:

```
docker-compose up -d
```

This will start the PostgreSQL database, API service, MailHog service, Manager UI, and Patient UI containers in the background.

4. Wait for the services to start and initialize. You can check the status of the containers using the following command:

```
docker-compose ps
```

5. Once all the services are up and running, you can access the following URLs in your web browser:

-   Manager UI: http://localhost:4200
-   Patient UI: http://localhost:4300
-   MailHog UI: http://localhost:8025

That's it! You have successfully deployed the Physio Connect application using Docker Compose. You can now use the Manager UI and Patient UI to interact with the application, and use the MailHog UI to view captured emails for testing purposes.

## Usage

-   There is default account created that can be used for login into Manager UI for demo purposes
    -   username: admin@email.com
    -   password: admin
