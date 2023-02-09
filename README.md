# Email-Server
A simple web-based email program developed using Angular Framework and Spring Boot.

## Contents:
- [Contributers](#Contributers)
- [Frameworks and technology used](#Frameworks-and-technology-used)
- [How to run](#How-to-run)
- [Used design patterns](#used-design-patterns)
- [UML class diagram](#UML-class-diagram)
- [Snapshots of our UI and a user guide](#Snapshots-of-our-UI-and-a-user-guide)
- [Demo Video for using the app](https://drive.google.com/file/d/1edCtHUrJhFHZAs-abk5kfMnM-qpbTsW4/view?usp=share_link)
---
## Contributers:
* [Adel Mahmoud](https://github.com/Adel-Mahmoud-Mohamed)
* [Mohamed Hassan](https://github.com/mohamedhassan279)
* [Mahmoud Attia](https://github.com/mahmoudattia12)
* [Mahmoud Ghlab](https://github.com/Mahmoudjobdis)
---
## Frameworks and technology used:
- For the frontend part (view part), we used HTML, CSS, and typescript through angular framework.
- For the backend (model and controller), we used Java language through spring framework.
---
## How to run:
- Note: Make sure you have downloaded NodeJs and Angular-CLI.
- extract the compressed project folder.
#### Back-end part:
- Open the EmailBackend folder using IntelliJ IDE, and run the EmailBackendApplication.java class on port 9080. you can change the port from the project resources at application.properties (server.port = …..) if the 9080 port was already used in your device but in this case, you will need to change it in all http requests in the angular folder.
#### Front-end part:
- open the emailfrontend folder using visual studio IDE, then open the terminal of the IDE, and write:
- npm install in the terminal.
- npm install jquery
- npm install@types/jquery
- ng serve --open in the terminal to open the project, on port “http://localhost:4200/”-. Note: if you needed to change port 4200 as it was already in use then you will need to change it in the EmailBackend folder in the controller class.
- If you want to open more than one server to use the email application between multiple users at the same time, all you should do after running the first server is to go back to the emailfrontend folder in visual studio IDE and open a new terminal then write ng serve --open and it will ask you for another port enter Y (yes) and copy the opened port link from your browser.Then go back to the backend folder in IntelliJ and open EmailBackendApplication.java then add the port to the following line:
![image](https://user-images.githubusercontent.com/96317608/217441322-99936a39-34f0-4ffb-b410-454ce1d02cf7.png)
![image](https://user-images.githubusercontent.com/96317608/217441453-64124c3a-c73f-44ca-9d98-58c25dee8947.png)
---
## Used design patterns:
### 1- Filter Design Pattern:
We used this Design pattern to help us filter emails that meet the criteria that the user chose and get all the e-mails containing or having the same characteristics as user input, here’s the part of this design pattern in the UML:
![image](https://user-images.githubusercontent.com/96317608/217441728-820f840f-53b6-44a0-8294-547d513a7ea4.png)
![image](https://user-images.githubusercontent.com/96317608/217441749-d4ad6d4b-c197-4d2f-b63d-9b33a81162a4.png)
### 2- Builder Design Pattern:
to make the client separated from the complex steps to create a profile, we’ve used the builder design pattern to build the whole user profile attributes like the user’s username, password, trash folder, etc.

![image](https://user-images.githubusercontent.com/96317608/217441809-8dbb46a3-3155-498e-bc5d-876b2e779e12.png)
### 3- Singleton Design pattern:
In order not to consume the memory and for the sake of reusability, we used this design pattern to build the huge classes that we only need just one instance of them then this instance is used again and again.
![image](https://user-images.githubusercontent.com/96317608/217441939-83e1b746-f464-4ad9-886b-24be4f6b66c3.png)
![image](https://user-images.githubusercontent.com/96317608/217441955-a641505b-f6f8-44f6-946d-881e1767f98d.png)
![image](https://user-images.githubusercontent.com/96317608/217441971-c1eca1f8-4470-4e0a-ba2b-773f569731ec.png)
### 4- Chain of responsibility:
This design pattern is used to organize the server requests to specify which handle it should operate by making all the handlers implement a certain interface and then passing the concern as a string to the first handler and if it is not the first handler’s responsibility then the concern is passed to the second handler and so on till the request is fulfilled by the very specific handler.
![image](https://user-images.githubusercontent.com/96317608/217442139-4a3fad30-d750-46a9-befa-5e69718caaec.png)
### 5- Façade:
to simplify the processes for the client furthermore, we used this design pattern by creating a class called creator and the concern of this class is to make use of the builder design pattern and then create different files for the user profile like the inbox file, etc.
![image](https://user-images.githubusercontent.com/96317608/217442200-f71e38f9-c8ad-4eaa-9248-28c43c21a6d9.png)
### 6- Adapter Design Pattern:
In order to sort the Emails according to something, the view layer sends the emails in Array List Form which we pass to the class which converts it to Array form to be able to sort the list easily, then we return it in Array list form, to view the sorted emails or contacts.
![image](https://user-images.githubusercontent.com/96317608/217442281-32ca51a1-bcfe-475e-8140-313965fb22c3.png)

---
## UML class diagram:
- [link to the uml class diagram](https://drive.google.com/file/d/1lc6Dt8ateEJRGqP8FJWiq-h6v7QSehwY/view?usp=sharing)
---
## Snapshots of our UI and a user guide:
1. signup page: first sign up if you don’t have an account.
- The username should have at least 4 characters/symbols.
- The password should have at least 8 characters/symbols.

![image](https://user-images.githubusercontent.com/96317608/217443298-3764c931-405d-4f6f-9583-7ea24f90e586.png)

2. Login page: if you have already an account go to the login page

![image](https://user-images.githubusercontent.com/96317608/217443363-8fb5467e-d411-4856-af6e-240aed4685c7.png)

3. After signing up/logging in: the home page loads which shows your inbox folder by default

![image](https://user-images.githubusercontent.com/96317608/217443441-39e63d62-3fc4-4049-acc2-ffc3985b34a2.png)

4. To send an email: click on the compose button and the following window will appear:

![image](https://user-images.githubusercontent.com/96317608/217443761-6baf2cd6-3d3b-4340-a5b0-e36ff0817983.png)

- Write the user name of the profile to which you want to send an email in the “To” field, you can insert multiple receivers by clicking simply on “+ receiver” button.
- Write the subject of the email in the “Subject” field.
- Choose the priority from the drop-down list.
- Add attachments if you want, you can send multiple attachments.
- And if you need to edit or delete the attachments or you need to do is to click on the button again and choose the files you want or choose nothing.
- Then write the body of the email.
- To complete sending the email, press send E-mail button.
- If you want to save the email to your draft, press move to draft button.
- If you clicked on the (x) button to close the window before sending it, the email will be directed to the draft.

![image](https://user-images.githubusercontent.com/96317608/217443904-11d583f0-c5bb-420d-9eb0-4e77532eec9c.png)

- To check for new emails press on the refresh button, and here is the email in “atteia” inbox:

![image](https://user-images.githubusercontent.com/96317608/217444027-9bfa0e53-2703-4c1f-a9e0-146ce0089abf.png)

To show the details of the email, just click on it:

![image](https://user-images.githubusercontent.com/96317608/217444097-e3c9ad19-7d3a-4255-accc-c76bd1453645.png)

Press on the attachment file, and the browser will download it to your device and you can open it after that.
Here is the email in “mohassan” sent folder:

![image](https://user-images.githubusercontent.com/96317608/217444145-f3892977-b652-4270-9c72-efab064c48a2.png)

You can save the email in the draft instead of sending it, and if you clicked the x button without sending the email, it will also be saved in your draft automatically:

![image](https://user-images.githubusercontent.com/96317608/217444217-9f94939d-5270-486f-977b-ef115b904f4e.png)
![image](https://user-images.githubusercontent.com/96317608/217444249-f5aa9e43-c0be-4449-8151-4e2bcc313352.png)

5. To select an email for example to delete it, just check the box next to it.
* Note: you can select multiple emails and delete them simultaneously.
6. You can delete an email by sending it to the trash folder:

![image](https://user-images.githubusercontent.com/96317608/217444336-8f640e2a-442b-4086-a8a5-e6b9e2be311c.png)

7. You can create a new folder by clicking on + add folder and enter the name of the folder then pressing create:

![image](https://user-images.githubusercontent.com/96317608/217444408-b460a707-25f3-49b7-ba00-1dc6cf5e32a1.png)
![image](https://user-images.githubusercontent.com/96317608/217444438-e04de782-b999-4467-85c3-45acad071ece.png)

You can find your folders by clicking on folders in the menu and selecting it from the drop-down list:

![image](https://user-images.githubusercontent.com/96317608/217444472-fdd890d7-2364-4b07-a159-acd021e37dfd.png)

Any time you want to add an email in certain folder such select the email/emails and press move to folder and enter the name of the desired folder:

![image](https://user-images.githubusercontent.com/96317608/217444512-603ecd12-5295-4580-8a80-9f52562c16a3.png)

When you select a folder you have 3 choices:
- Delete the folder.
- Rename the folder.
- View emails that are in the folder.

![image](https://user-images.githubusercontent.com/96317608/217444616-f214140f-26ba-4d6f-aaeb-9d2bc9723142.png)

You can rename the folder:

![image](https://user-images.githubusercontent.com/96317608/217444681-4696e689-f8d8-4f64-92d1-c8b6fec3f73d.png)
![image](https://user-images.githubusercontent.com/96317608/217444706-ec2d9a91-ed8f-47cb-8b79-88d5d426c127.png)
![image](https://user-images.githubusercontent.com/96317608/217444729-bc78a8b8-1dbc-4e31-91c1-81609eff7e65.png)

8. search: you can search in inbox, sent, draft, trash, contacts, and new folders by entering the search key in the search field then hit the search icon and it will show the matches of that search key.
9. sort: you can sort your emails in any folder by date/priority/sender/receivers/subject/body/attachment size by selecting the option from the list, then choose the order: ascendingly or descending then finally hit the button sort and then BOOM the emails are sorted as you wish.
10. Move To Folder: If you’ve created a folder already, you can select the emails you want to move and then click on move to folder and enter the name of the folder you want to move to:

![image](https://user-images.githubusercontent.com/96317608/217446173-2b522a33-0d4a-4d53-bc14-4b406d4a1705.png)
![image](https://user-images.githubusercontent.com/96317608/217446197-49c3fdf9-49d1-4146-9f23-f6ddf5a6dc1d.png)
![image](https://user-images.githubusercontent.com/96317608/217446247-b7cf6c3d-92b2-4633-8e96-4b26899b2408.png)
![image](https://user-images.githubusercontent.com/96317608/217446340-fa810b09-a7a4-46f4-9e78-68b55181c4c7.png)

11. filter: you can filter the emails in any page of emails (Inbox, Sent, Trash, Draft, and emails in the folders page) according to the subject or the sender:

![image](https://user-images.githubusercontent.com/96317608/217446460-40937924-1099-4dde-b4dd-9b7d7b790144.png)
![image](https://user-images.githubusercontent.com/96317608/217446485-08d0e3f8-d829-43b2-83d2-3e4e2876834d.png)

12.	To add a contact: click on add contact button and enter contact name and any number of emails you want the create.

![image](https://user-images.githubusercontent.com/96317608/217446614-9026f78b-e481-4a23-a13d-46ef6f030b0e.png)
![image](https://user-images.githubusercontent.com/96317608/217446629-f9311765-d48b-496b-aaec-526052978a3f.png)
![image](https://user-images.githubusercontent.com/96317608/217446657-6b2e817c-5cd3-4d3b-bb7e-deb059e32e8d.png)

You can view contact information by clicking on it:

![image](https://user-images.githubusercontent.com/96317608/217446710-b2ddb470-4b11-42da-a82b-c8e5f43e9762.png)

You can edit the contact information by clicking on the edit button and rename, add or/and delete emails: 
here we renamed the contact and remove the second email and add 2 more new emails.

![image](https://user-images.githubusercontent.com/96317608/217446758-b874ecba-97d6-4d45-ad9b-13b1308d8cb7.png)

Then hit the Edit button to save the changes:

![image](https://user-images.githubusercontent.com/96317608/217446811-a7576481-5fe2-4c8e-9055-01fe48a4049f.png)

You can search the contacts by entering the contact's name on the search field:

![image](https://user-images.githubusercontent.com/96317608/217446889-2563d133-d5e7-491c-9c64-f7c881712821.png)
![image](https://user-images.githubusercontent.com/96317608/217446908-881b18f1-8279-402e-a970-2adf4c452d09.png)

You can sort them by contact name ascendingly or descending:

![image](https://user-images.githubusercontent.com/96317608/217446972-26593924-6d67-44fd-aff4-51f59e16d783.png)
![image](https://user-images.githubusercontent.com/96317608/217447000-2eec6ca5-7fbb-4cd8-ad35-f6768e52d1be.png)

You can delete the contact by selecting it and clicking on the delete button:

![image](https://user-images.githubusercontent.com/96317608/217447053-a18f6b70-34f1-4307-81eb-701b375e5bc8.png)
![image](https://user-images.githubusercontent.com/96317608/217447091-26fae769-e52b-4373-a16c-4529b1b19e63.png)

---
## Demo Video for using the app:
- [link to the video](https://drive.google.com/file/d/1edCtHUrJhFHZAs-abk5kfMnM-qpbTsW4/view?usp=share_link)
