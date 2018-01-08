**Subject`**

Write a nice web application in Scala that will ask the user for two actions : Query or Reports.

2.1 Query Option will ask the user for the country name or code and print the airports & runways at each airport. 
The input can be country code or country name. For bonus points make the test partial/fuzzy. e.g. entering zimb will result in Zimbabwe :)

2.2 Choosing Reports will print the following:

10 countries with highest number of airports (with count) and countries with lowest number of airports.
Type of runways (as indicated in "surface" column) per country
Bonus: Print the top 10 most common runway identifications (indicated in "le_ident" column)
​

Feel free to use any library/framework as necessary but write it as a web application.

Please write the code as if you are writing production code, possibly with tests.

Share the code on Github/Bitbucket at least 3h before your review so that we have enough time to review and test before the call.


I would suggest if possible that you use Play2 Scala and Play Framework. However if you prefer to do a Node.JS / React / Redux solution, that's ok.
All in all : we want to confirm your skills and so, try to build a complete application. Don't forget to include a basic README in your project.

### TODO

- [X] Create App and Api skeleton
- [X] Write api logic draft
- [ ] Write api tests
- [ ] Integrate Swagger
- [ ] Organise configuration
- [ ] Build Front
- [ ] Set up docker-stack
