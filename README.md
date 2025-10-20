# Expass 7
I added docker by looking at the template from lecture 15 about containers. 
I choose to use my restAPI version from expass 2. No issues already had docker installed. 
To run use ``docker build -t pollapp .`` when inside the project directory. And run it with ``docker run -d -p 8080:8080 pollapp``
Used ``docker ps`` to see that it was running and tested it by visiting ``http://localhost:8080``