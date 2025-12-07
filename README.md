# fileshare
Test repository for a lightweight file upload and download API

This doc will go over the frameworks I have used and my reasons for use.

I decided early on in the process that I would use the language of Java.
This was because java is very interconnected and has many existing frameworks to make it easier to make APIs and hand files of all types.
Java is also quite a popular language which means that there are more examples of comprehensive docs and troublshooting for many of its tools.

Once my language had been decided I needed a way to not only use that language, track the dependancies I would have but also pick a suitable framework taht already had many of the tools I would need. 

To track the dependancies I choce to use Maven as opposed to groovy, mostly because of my previous familiarity with Maven as well as teh fact that this would be a relatively light project not needing much complex configuration of the pom.xml file.

I chose Spring Boot as my choice of framework as it is built for the creation of web applications and would make expansion of this project in future much easier. It also works well with Rest calls and has a vast array of Documentation. 
I have had only limited experience with Spring Boot and web develiopment in general so I knew I would need comprehensive documentation.

Once I had made ny initial choices I began to work on the code, I decide to seperate out my code into three main sections the Application, the Controller and the Service. This was done to ease congestion of teh code and segment the project into smaller steps that could be handles one at a time.

The Application portion was the easiest as it is a simple call to start the SpringBoot application. 

The Controller is where I chose to map my logic to the calls I had to create. This section is what dictated my inputs and the endpoint calls. Contextually returning values if necessary. I also wrapped much of the code in try/catch blocks paired with a logger to assure that even if these calls failed the application would continue to run. 

The Service is where I declare the ultimate storage directory, for purposes of this project I created a file locally and used the directory "C:\\upload" but this can be changed if a none local endpoint is added. I left it as local as I believed the project didn't requre further features, i.e building a web based ui and endpoints.
I made sure to safeguard against null values where possible as wll as some simple security checks for possible malicious use inputs.


Bugs/issues:
When encountering issues with parts of springboot that were unfamiliar to me I either serced the official docs, looked for related questions on stack overflow or partially viewed tutorials such as "https://www.youtube.com/watch?v=wW0nVc2NlhA"
I had to do this many times, this include when I was having a bean decleration issue as my @Autowired annotation was causing teh spring boot aplication to not understand what it was trying to connect to and could not initialise the bean. This was solved by including a methos that returned the class being accessed by the bean and annotating that methos with @Bean. I had assumed that the @Component annotation would be able to be recongines and it would not have caused this issue in the first place.

Another issue I had encountered was that in my method handleFileUpload I had tried to return the file name and this used the methode file.getName() however after trial and error as well as Springboot docs I realised that what I was actually looking for was available through the method file.getOriginalFileName()

Before useing the java logger I had issues where the application would stop when an invalide request was made which made testing very difficult. I looked up how best to manage error handling with Rest endpoints and the logger was suggested in tandem with try/catch statments. 

I was habving issues related to trying to save copied files as there were duplicated this was easy to resolve by adding a StandardCopyOption.REPLACE_EXISTING param to the copy function.

I encountered an issue of when downloading a file it would not retain the name of teh file, in order to fix thsi I watched a tutorial where it was mentioned that a header had to be included in the response entity which resulted in the file retaining it's name. 


Testing:
Most testing was done manually throughout the exercise as I wes quite unfamilar with how to test enpoints and integrating JUnit with Spring Boot however once I had some time towards the end of this project I added a proof of concept test. From Spring boot docs I found that I could creat a mock MultipartFile object and test one of teh end points for my expected result. 


