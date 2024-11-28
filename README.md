
# Complan Beta  🧺🧼👕 🚃

## About our project

Complan Community Planning helps you collaborate with your peers in the college in terms of carpooling, shared resource utilization.
We will start the implementation of this project by implementing only the vehicle pooling and effective shared utilization of the public washing machine.
A leaderboard will display all the students on the basis of the points received from the Vehicle pooling and Washing Machine Booking. This will encourage correct usage of these services. 




## Features

- Authentication through secure login/registration with the help of password encryption
- Efficient booking of Washing machine slots 
- Easily find partners for travel to and fro locations
- Make your place at the top of the leaderboard by completing Washing Slots on time


## Tech Stack

**CLI UI and Database access:** C++ file I/O, fmt library

**Project Core Logic:** Java 




## User Manual        

| Instructions Format|
|:-----|
|```LOGIN <user-email> <password>```|
|```LOGOUT```|
|```REGISTER <name> <password> <mobile_number> <email-id>```|
|```CREATE_WMSLOT <date(dd-mm-yy)> <time(hh:mm)>```|
|```DELETE_WMSLOT <slot ID> <password>```|
|```ASK_WMSLOT <slot ID>```|
|```RESPOND_REQUEST <requestID> <accept(y/n)>```|
|```USE_WMSLOT <slot ID>  <otp>```|
|```DISPLAY_WMSLOTS <date(dd-mm-yy)> <display only your slots(y/n)>```| 
|```DISPLAY_VPSLOTS <date(dd-mm-yy)> <only slots in which I am present y/n>```|
|```CREATE_VPSLOT <date(dd-mm-yy)> <time(hh:mm)> <from> <to>```|
|```JOIN_VPSLOT <slotID>```|
|```REMOVE_PARTNER <slotID> <partner email> <password>```|
|```LEAVE_VPSLOT <slotID> <password>```|
|```DELETE_VPSLOT <slotID> <password>```|
|```DISPLAY_WMSTATUS```|
|```AVAIL_CREDIT <password>```|
|```DISPLAY_CREDITS```|
|```DISPLAY_POINTS```|
|```DISPLAY_CMS```|
|```DISPLAY_LEADERBOARD```|
|```HELP```|
|```EXIT```|
|```RULES```|

##

## Run Locally

Clone the project

```bash
  git clone https://github.com/Ahilnandan/Complan_Beta.git
```

Go to the project directory

```bash
  cd Complan_Beta
```

Compile all files


Linux and Mac OS
```bash
  make compilelinux
```

Windows
```bash
    make compilewin
```

Start the CLI application

```bash
  make run
```

## Authors

- S Santhosh Kiran (IMT2023065) [@kiranamourshaa](https://github.com/kiranamourshaa)
- Sriram Srikanth (IMT2023115) [@Humanoid2005](https://github.com/Humanoid2005)
- R.Vaikunth (IMT2023566) [@Vaiking06](https://github.com/Vaiking06)
- Pravin Kumar V (IMT2023595) 
- Shudharshan.A (IMT2023602) [@T0nyStark5](https://github.com/T0nyStark5)
- Ahilnandan Kabilan (IMT2023614) [@Ahilnandan](https://github.com/Ahilnandan)
- Surya Sumeet Singh (IMT2023590) 

## Screenshots

![Help](/docs/Help.jpeg)
![WMSlots](/docs/WMSlots.jpeg)
![VPSlots](/docs/VPSlots.png)
![Requests](/docs/Requests.jpeg)
![Leaderboard](/docs/Leaderboard.jpeg)
![Profile](/docs/Profile.jpeg)
![Rules](/docs/Rules.jpeg)
