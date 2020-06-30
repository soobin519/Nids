#### README

Welcome to goormIDE!
Start your SpringMVC project with a simple server time application.

#### How to Run
- You must build the project before you run to deploy the project to the Tomcat path.
- You can check the result of Tomcat server creation by accessing the URL address after domain registration (if there is no domain registered already).

#### Build
- At the time of build, the process of creating .war file, deleting the previously deployed application, and deploying the generated .war file to Tomcat's webapps directory will proceed.
- If you are building during the server is running, wait a few seconds after completing the build and Tomcat will automatically restart the application.

#### Setting Environment Variables
- The environment variables such as Tomcat and Maven path are written in the ~/.profile file. If you need to set it directly, you can edit it by opening it with an editor such as vim.

#### Setting Directory Role
- Sources Root path is set as default by src/main/java and can be set by modifying 'Source Path' in Project Properties.

