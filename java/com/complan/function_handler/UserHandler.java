package com.complan.function_handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import com.complan.basic_classes.*;

public class UserHandler {
    private ArrayList<User> Users;
    private ArrayList<User> Admins;
    private ArrayList<WMSlot> WMSlots;
    private ArrayList<VPBooking> VPBookings;
    private ArrayList<Requests> RequestsList;
    private String WMStatus;
    private boolean isLoggedIn = false;
    private User currentUser;

    public UserHandler() {
        this.Users = new ArrayList<User>();
        this.WMSlots = new ArrayList<WMSlot>();
        this.VPBookings = new ArrayList<VPBooking>();
        this.RequestsList = new ArrayList<Requests>();
        this.isLoggedIn = false;
        this.currentUser = new User();
        /* DB acquire data and put in these arraylists logic */
    }

    // GETTERS AND SETTERS
    public ArrayList<User> getUsers() {
        return Users;
    }

    public void setUsers(ArrayList<User> users) {
        Users = users;
    }

    public ArrayList<Requests> getRequests() {
        return RequestsList;
    }

    public void setRequests(ArrayList<Requests> rl) {
        this.RequestsList = rl;
    }

    public ArrayList<WMSlot> getWMSlots() {
        return WMSlots;
    }

    public void setWMSlots(ArrayList<WMSlot> wMSlots) {
        WMSlots = wMSlots;
    }

    public ArrayList<VPBooking> getVPBookings() {
        return VPBookings;
    }

    public void setVPBookings(ArrayList<VPBooking> vPBookings) {
        VPBookings = vPBookings;
    }

    public String getWMStatus() {
        return WMStatus;
    }

    public void setWMStatus(String wMStatus) {
        WMStatus = wMStatus;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    // MAIN LOGIC
    public String encryptPassword(String password) {
        int x = 0;
        for (int i = 0; i < password.length(); i++) {
            x += Math.abs(password.charAt(i) - 'a') * Math.pow(10, i);
        }
        return Integer.toString(x);
    }

    public int generateOTP(int size) {
        int OTP = 0;
        for (int i = 0; i < size; i++) {
            OTP = OTP * 10;
            OTP = OTP + (int) Math.floor(Math.random() * 10);
        }
        return OTP;
    }

    public int Register(String name, String password, String MobileNumber, String emailID) {
        /*
         * Status Code
         * 1 --> User already exists
         * 0 --> Registered successfully
         */

        for (int i = 0; i < Users.size(); i++) {
            if (Users.get(i).getEmail() == emailID) {
                return 1;
            }
        }
        User user = new User(name, emailID, MobileNumber, encryptPassword(password), Users.size() + 1);
        Users.add(user);
        return 0;
    }

    public int Login(String email, String password) {
        /*
         * Status Codes
         * 0--> Succesfull Login
         * 1-->Incorrect password
         * 2-->User not found
         */
        for (int i = 0; i < Users.size(); i++) {
            if (Users.get(i).getEmail().equals(email)) {
                if (encryptPassword(password).equals(Users.get(i).getPassword())) {
                    this.isLoggedIn = true;
                    this.currentUser = Users.get(i);
                    return 0;
                } else {
                    return 1;
                }
            }
        }

        return 2;
    }

    public void Logout() {
        isLoggedIn = false;
        for (int i = 0; i < Users.size(); i++) {
            if (currentUser.sameUser(Users.get(i)) == true) {
                Users.get(i).copy(currentUser);
                break;
            }
        }
        currentUser = new User();
    }

    public void Exit() {
        /* Write back to DB function */
    }

    public int createWMSlot(LocalDateTime start_Time) {
        /*
         * Status Codes
         * 0 --> Successfull Booking
         * 1 --> Insufficient Credits
         * 2 --> Timing not available
         */
        if (currentUser.getCredits() > 0) {// checking for sufficient credit
            currentUser.setCredits(currentUser.getCredits() - 1);// decremnt credit
        } else {
            return 1;
        }
        LocalDateTime end_Time = start_Time.plusHours(1);

        for (int i = 0; i < WMSlots.size(); i++) { // checking for time clash b/w slots
            LocalDateTime st = WMSlots.get(i).getStartTime().minusMinutes(15);
            LocalDateTime et = WMSlots.get(i).getEndTime().plusMinutes(15);
            if (start_Time.isBefore(et) && st.isBefore(end_Time)) {
                return 2;
            }
        }

        WMSlot wmslot = new WMSlot(currentUser, start_Time, end_Time, generateOTP(4), WMSlots.size() + 1);
        WMSlots.add(wmslot);
        return 0;
    }

    public int deleteWMSlot(LocalDateTime start_Time) {
        /*
         * Status Codes
         * 0 --> Deleted successfully
         * 1 --> Slot not found
         * 2 --> Cannot delete slot for less than 2 hours prior to start time
         */

        int SlotIndex = -1;

        for (int i = 0; i < WMSlots.size(); i++) {
            if (WMSlots.get(i).getUser().getEmail() == currentUser.getEmail()
                    && WMSlots.get(i).getStartTime() == start_Time) {
                SlotIndex = i;
            }
        }

        if (SlotIndex == -1) {// checking if slot exists
            return 1;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(start_Time.minusHours(2)) == true) {// check if delete time is more than 2 hours prior to slot
            WMSlots.remove(SlotIndex);// removing slot // start time
            return 2;
        }

        WMSlots.remove(SlotIndex);// removing slot
        currentUser.setCredits(currentUser.getCredits() + 1);// increasing credit back
        return 0;
    }

    public int giveWMSlot(String to_email, LocalDateTime start_Time) {
        /*
         * Status Codes
         * 0 --> Request sent successfully
         * 1 --> Slot not found
         * 2 --> User not found
         */

        int SlotIndex = -1;

        for (int i = 0; i < WMSlots.size(); i++) {
            if (WMSlots.get(i).getUser().getEmail() == currentUser.getEmail()
                    && WMSlots.get(i).getStartTime() == start_Time) {
                SlotIndex = i;
            }
        }

        if (SlotIndex == -1) {// checking if slot exists
            return 1;
        }

        int UserIndex = -1;
        for (int i = 0; i < Users.size(); i++) {
            if (Users.get(i).getEmail() == to_email) {
                UserIndex = i;
            }
        }

        if (UserIndex == -1) {// user does not exist
            return 2;
        }

        LocalDateTime now = LocalDateTime.now();
        Requests request = new Requests(currentUser.getEmail(), to_email, "give WMSlot", now, RequestsList.size() + 1,
                WMSlots.get(SlotIndex).getSlotId());
        RequestsList.add(request);
        return 0;
    }

    public int receiveWMSlot(int requestID, boolean accept) {
        /*
         * Status Code
         * 1 --> request not found
         * 2 --> user does not exist
         * 3 --> give request rejected
         */
        int RequestIndex = -1;
        for (int i = 0; i < RequestsList.size(); i++) {
            if (RequestsList.get(i).getRequestId() == requestID) {
                RequestIndex = i;
            }
        }

        if (RequestIndex == -1) {// checking if request exists
            return 1;
        }

        int UserIndex = -1;
        for (int i = 0; i < Users.size(); i++) {
            if (Users.get(i).getEmail() == RequestsList.get(UserIndex).getFrom()) {
                UserIndex = i;
            }
        }

        if (UserIndex == -1) {// user does not exist
            return 2;
        }

        if (accept == true) {// request accepted
            WMSlot oldSlot = WMSlots.get(RequestsList.get(RequestIndex).getSlotId() - 1);// get old slot details
            WMSlot wmslot = new WMSlot(currentUser, oldSlot.getStartTime(), oldSlot.getEndTime(), generateOTP(4),
                    WMSlots.size());// create now slot with same timings but new user,otp,id
            WMSlots.remove(RequestsList.get(RequestIndex).getSlotId() - 1);
            WMSlots.add(wmslot);
            Users.get(UserIndex).setCredits(Users.get(UserIndex).getCredits() + 1);
            currentUser.setCredits(currentUser.getCredits() - 1);
            return 0;
        }

        return 3;// request rejected
    }

    public int useWMSlot(LocalDateTime start_Time, int OTP) {
        /*
         * Status Codes
         * 0 --> WM started running
         * 1 --> Slot not found
         * 2 --> Wrong OTP
         * 3 --> Too early to start slot
         * 4 --> Too late to start slot
         */

        int SlotIndex = -1;

        for (int i = 0; i < WMSlots.size(); i++) {
            if (WMSlots.get(i).getUser().getEmail() == currentUser.getEmail()
                    && WMSlots.get(i).getStartTime() == start_Time) {
                SlotIndex = i;
            }
        }

        if (SlotIndex == -1) {// checking if slot exists
            return 1;
        }

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(start_Time) && now.isBefore(start_Time.plusMinutes(15))) {
            if (WMSlots.get(SlotIndex).getOTP() == OTP) {
                this.WMStatus = "In use";
                currentUser.setPoints(currentUser.getPoints() + 10);
                return 0;
            } else {
                return 2;
            }
        } else if (now.isBefore(start_Time)) {
            return 3;
        } else if (now.isAfter(start_Time.plusMinutes(15))) {
            return 4;
        }

        return -1;
    }

    public void displayAllSlotsOnDay(LocalDateTime day, boolean yourslot) {
        ArrayList<WMSlot> allslots = new ArrayList<>();
        for (int i = 0; i < WMSlots.size(); i++) {
            LocalDateTime date = WMSlots.get(i).getStartTime();
            if (date.getDayOfMonth() == day.getDayOfMonth() && date.getMonth() == day.getMonth()
                    && date.getYear() == day.getYear()) {
                if (yourslot == true) {
                    if (WMSlots.get(i).getUser().getName() == currentUser.getName()) {
                        allslots.add(WMSlots.get(i));
                    }
                } else {
                    allslots.add(WMSlots.get(i));
                }
            }
        }

        // display slots jni code
    }

    public void viewVPSlotsOnDay(LocalDateTime day, String from, String to) {

    }
}
