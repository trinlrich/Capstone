Original App Design Project - README Template
===

# APP_NAME_HERE (TBD)

## Table of Contents
1. [Overview](#Overview)
2. [Product Spec](#Product-Spec)
3. [Wireframes](#Wireframes)
4. [Schema](#Schema)

## Overview
### Description
This app is a productivity tool for people who are applying to college. You can search through schools based on certain criteria, favorite certain colleges that you're interested, and track tasks that you need to complete in order to complete the entire application process.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Productivity
- **Mobile:** This app would be developed for mobile.
- **Story:** Dashboard for college applicants to view and organize all required parts of applying to college.
- **Market:** Individuals who are applying to college.
- **Habit:** This app can be used very often for a portion of a user's life when they're applying to college.
- **Scope:** TBD

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User logs in to view dashboard of information (TBD)
* User can view and edit profile information (Name, GPA, Degree Seaking)
* User can search through list of colleges based on selected filters
* User selects schools they have applied to
    * User can track what part of the aplication process they're on
* Settings (Accesibility, Notification, General, etc.)

**Optional Nice-to-have Stories**

* User can favorite schools they like
* User can get matched with schools based on profile information
* User can turn on notifications for college/schooarship application deadlines
* After signing up, the user will fill out a form with their education background information to fill out profile. 
* User inputs scholarships they have applied for
    * User can track what part of the aplication process they're on

### 2. Screen Archetypes

1. Login
2. College List Screen
     * Allows user to view a list of colleges and preview info
     * College Detail Screen:
         * Allows user to view more details about selected college
3. College Status Screen
     * Allows user to view the list of colleges they have applied to
     * Shows status in application process
     * Add navigation to College Detail Screen
4. Scholarship Status Screen
     * Allows user to view the list of scholarships they have applied for
     * Shows status in application process
     * Scholarship Detail Screen:
         * Allows user to view more details about selected scholarship
5. Profile Screen
     * Allows user to upload a photo and fill in information that is important for college and scholarship selection
6. Register - User signs up or logs into their account
   * Upon Download/Reopening of the application, the user is prompted to log in to gain access to their profile information to be properly matched with another person. 
7. Dashboard
    * User can view important information and analytics (TBD)
8. Settings Screen
     * Lets people change language, and app notification settings.

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Profile
* Dashboard
* College List Screen
* User Status Screen
* Settings

**Flow Navigation** (Screen to Screen)

* Forced Log-in
    * Account creation if no log in is available
* * Profile
    * Text field to be modified.
* Dashboard
    * TBD
* College List Screen
    * College Detail Screen
* User Status Screen
    * College Status Screen
    * Scholarship Status Screen
* Settings
    * Toggle settings
## Wireframes
[Add picture of your hand sketched wireframes in this section]
![](https://i.imgur.com/u3DICth.jpg)


### [BONUS] Digital Wireframes & Mockups
![](https://i.imgur.com/tEkqJAA.jpg)


### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models

#### User
| Property         | Type          | Description |
| ---------------- | ------------- | ----------- |
| userId           | String        | unique ID for the user (default field) |
| firstName        | String        | user's first name |
| lastName         | String        | user's last name |
| email            | String        | user's email address |
| highestEdLevel   | String        | user's highest level education |
| gpa              | Double        | user's grade point average after highestEdLevel |
| graduationYear   | int           | user's expected graduation year from current schooling if applicable |
| degreeSeaking    | String        | degree that user is applying to college to obtain |
| favoriteColleges | List(College) | list of colleges that user has favorited |


#### College
| Property      | Type    | Description |
| ------------- | ------- | ----------- |
| collegeId     | String  | unique ID for the college (default field) |
| name          | String  | name of the college |
| state         | String  | state that college is located in (parse from address) |
| city          | String  | city that college is located in (parse from address) |
| type          | String  | public / private |
| gradRate      | int     | percentage of students who graduate from the college |
| averageGpa    | Double  | average grade point average of incoming students |
| averageCost   | Double  | average cost of attendance for incoming students |
| isFavorited   | Boolean | true if user has favorited the college |
| appType       | String  | Early Decision / Regular Decision |
| appEdDeadline | Date    | date that early decicion application is due |
| appRdDeadline | Date    | date the regular decision application is due |
| applied       | Boolean | true if user has submitted college application |

#### Scholarship
| Property      | Type         | Description |
| ------------- | ------------ | ----------- |
| scholarshipId | String       | unique ID for the scholarship (default field) |
| name          | String       | name of scholarship |
| amount        | int          | dollar amount of scholarship |
| category      | String       | Merit / Need-Based / Federal / Identity-Based / Athletic / Activity-Based / Military / Empolyer-Sponsored |
| isRenewable   | Boolean      | true if the scholarship is renewable (not one-time) |
| maxYears      | int          | number of years scholarship can be renewed |
| hasStateCstr  | Boolean      | true if scholarship can only be used in certain states |
| stateCstr     | List(String) | list of states scholarship can be used in |
| appDeadline   | Date         | date the application is due |
| applied       | Boolean      | true if user has submitted scholarship application |

### Networking
- Login Screen
    - TBD
- Dashboard Screen
    - TBD (Not sure what all will be on the dashboard screen)
- College Search Screen
    - (Read/GET) Pull all colleges where filter constraints are met
    - (Create/POST) Create a new like on college
    - (Delete) Delet existing like
- My Colleges Screen
    - (Read/GET) Pull all colleges that user has favorited
    - (Update/PUT) Update application status (Add more of these for documents )
    - (Delete) Delete existing like
- My Scholarships Screen
    - (Read/GET) Pull all scholarships that user has added
    - (Create/POST) Create new scholarship
    - (Update/PUT) Update application status (Add more of these for documents )
    - (Delete) Delete existing scholarship
