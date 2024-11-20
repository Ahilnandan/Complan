package com.complan.function_handler;

import java.time.LocalDateTime;
//import java.util.Vector;
import java.util.LinkedHashMap;
import com.complan.basic_classes.*;

public class UserHandler {
    private LinkedHashMap<String, User> Users;
    private LinkedHashMap<String, WMSlot> WMSlots;
    private LinkedHashMap<String, VPBooking> VPBookings;
    private LinkedHashMap<String, Requests> RequestsList;
    private String WMStatus;
    private boolean isLoggedIn = false;
    private User currentUser;

    public UserHandler() {
        this.Users = new LinkedHashMap<String, User>();
        this.WMSlots = new LinkedHashMap<String, WMSlot>();
        this.VPBookings = new LinkedHashMap<String, VPBooking>();
        this.RequestsList = new LinkedHashMap<String, Requests>();
        this.isLoggedIn = false;
        this.currentUser = new User();
        /* DB acquire data and put in these Vectors logic */
    }

    // GETTERS AND SETTERS
    public LinkedHashMap<String, User> getUsers() {
        return Users;
    }

    public void setUsers(LinkedHashMap<String, User> users) {
        Users = users;
    }

    public LinkedHashMap<String, Requests> getRequests() {
        return RequestsList;
    }

    public void setRequests(LinkedHashMap<String, Requests> rl) {
        this.RequestsList = rl;
    }

    public LinkedHashMap<String, WMSlot> getWMSlots() {
        return WMSlots;
    }

    public void setWMSlots(LinkedHashMap<String, WMSlot> wMSlots) {
        WMSlots = wMSlots;
    }

    public LinkedHashMap<String, VPBooking> getVPBookings() {
        return VPBookings;
    }

    public void setVPBookings(LinkedHashMap<String, VPBooking> vPBookings) {
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

        if (Users.get(emailID) != null) {
            return 1;
        }

        String Uid = "U" + Integer.toString(Users.size());
        User user = new User(name, emailID, MobileNumber, encryptPassword(password), Uid);
        Users.put(emailID, user);
        return 0;
    }

    public int Login(String email, String password) {
        /*
         * Status Codes
         * 0--> Succesfull Login
         * 1-->Incorrect password
         * 2-->User not found
         * 3 --> already logged in
         */

        User u = new User();
        if (u.equals(currentUser) == false) {
            return 3;
        }

        if (Users.get(email) == null) {
            return 2;
        }

        User u1 = Users.get(email);
        if (encryptPassword(password).equals(u1.getPassword())) {
            this.isLoggedIn = true;
            this.currentUser = u1;
            return 0;
        } else {
            return 1;
        }
    }

    public int Logout() {
        if (isLoggedIn == false) {
            return 1;
        }
        isLoggedIn = false;
        Users.get(currentUser.getEmail()).copy(currentUser);
        currentUser = new User();
        return 0;
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
         * 3 --> Invalid/old timing
         */
        LocalDateTime t = LocalDateTime.now();
        if (start_Time.isBefore(t)) {
            return 3;
        }

        if (currentUser.getCredits() > 0) {// checking for sufficient credit
            currentUser.setCredits(currentUser.getCredits() - 1);// decremnt credit
        } else {
            return 1;
        }
        LocalDateTime end_Time = start_Time.plusHours(1);

        for (String i : WMSlots.keySet()) { // checking for time clash b/w slots
            LocalDateTime st = WMSlots.get(i).getStartTime().minusMinutes(15);
            LocalDateTime et = WMSlots.get(i).getEndTime().plusMinutes(15);
            if (start_Time.isBefore(et) && st.isBefore(end_Time)) {
                return 2;
            }
        }

        String WMid = "WM" + Integer.toString(WMSlots.size());
        WMSlot wmslot = new WMSlot(currentUser, start_Time, end_Time, generateOTP(4), WMid);
        WMSlots.put(WMid, wmslot);
        return 0;
    }

    public int deleteWMSlot(String slotID, LocalDateTime start_Time) {
        /*
         * Status Codes
         * 0 --> Deleted successfully
         * 1 --> Slot not found
         * 2 --> Cannot delete slot for less than 2 hours prior to start time
         */

        if (WMSlots.get(slotID) == null) {// slot does not exist
            return 1;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(start_Time.minusHours(2)) == true) {// check if delete time is more than 2 hours prior to slot
            currentUser.setPoints(currentUser.getPoints() - 10);
            WMSlots.remove(slotID);// removing slot // start time
            return 2;
        }

        WMSlots.remove(slotID);// removing slot
        currentUser.setCredits(currentUser.getCredits() + 1);// increasing credit back
        return 0;
    }

    public int giveWMSlot(String slotID, String to_email, LocalDateTime start_Time) {
        /*
         * Status Codes
         * 0 --> Request sent successfully
         * 1 --> Slot not found
         * 2 --> User not found
         */

        if (WMSlots.get(slotID) == null) {// slot does not exist
            return 1;
        }

        if (Users.get(to_email) == null) {// user does not exist
            return 2;
        }

        LocalDateTime now = LocalDateTime.now();
        String Rid = "R" + Integer.toString(RequestsList.size());
        Requests request = new Requests(currentUser.getEmail(), to_email, "give WMSlot", now, Rid,
                slotID);
        RequestsList.put(Rid, request);
        return 0;
    }

    public int receive_WMSlot(String requestID, boolean accept, boolean isWMR) {
        /*
         * Status Code
         * 1 --> request not found
         * 2 --> user does not exist
         * 3 --> give request rejected
         * 4 --> receiver does not have enough credits to accept request
         */

        if (RequestsList.get(requestID) == null) {// checking if request exists
            return 1;
        }

        if (Users.get(RequestsList.get(requestID).getFrom()) == null) {// user does not exist
            return 2;
        }

        if (accept == true) {// request accepted
            if (isWMR == true) {
                if (currentUser.getCredits() <= 0) {
                    return 4;
                }
                WMSlots.get(RequestsList.get(requestID).getSlotId()).setUser(currentUser);// set to current user
                WMSlots.get(RequestsList.get(requestID).getSlotId()).setOTP(generateOTP(4));// generate new OTP
                Users.get(RequestsList.get(requestID).getFrom())
                        .setCredits(Users.get(RequestsList.get(requestID).getFrom()).getCredits() + 1);// increment
                                                                                                       // sender's
                                                                                                       // credit
                currentUser.setCredits(currentUser.getCredits() - 1);// decrement receiver's credits
                return 0;
            } else {
                RequestsList.get(requestID).setAccept(true);
                VPBookings.get(RequestsList.get(requestID).getSlotId())
                        .addPartner(Users.get(RequestsList.get(requestID).getFrom()));
                return 0;
            }
        }

        return 3;// request rejected
    }

    public int useWMSlot(String slotID, LocalDateTime start_Time, int OTP) {
        /*
         * Status Codes
         * 0 --> WM started running
         * 1 --> Slot not found
         * 2 --> Wrong OTP
         * 3 --> Too early to start slot
         * 4 --> Too late to start slot
         */

        if (WMSlots.get(slotID) == null) {// slot does not exist
            return 1;
        }

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(start_Time) && now.isBefore(start_Time.plusMinutes(15))) {
            if (WMSlots.get(slotID).getOTP() == OTP) {
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
        LinkedHashMap<String, WMSlot> allslots = new LinkedHashMap<String, WMSlot>();
        for (String i : WMSlots.keySet()) {
            LocalDateTime date = WMSlots.get(i).getStartTime();
            if (date.getDayOfMonth() == day.getDayOfMonth() && date.getMonth() == day.getMonth()
                    && date.getYear() == day.getYear()) {
                if (yourslot == true) {
                    if (WMSlots.get(i).getUser().getName() == currentUser.getName()) {
                        allslots.put(i, WMSlots.get(i));
                    }
                } else {
                    allslots.put(i, WMSlots.get(i));
                }
            }
        }

        // display slots jni code (Cpp)
    }

    public void viewVPSlotsOnDay(LocalDateTime day, String from, String to, boolean inSlot) {
        LinkedHashMap<String, VPBooking> vpbookings = new LinkedHashMap<String, VPBooking>();

        for (String i : VPBookings.keySet()) {
            VPBooking vp = VPBookings.get(i);
            if (vp.getDeparture() == day && vp.getFromLocation() == from && vp.getToLocation() == to) {
                vpbookings.put(vp.getVPID(), vp);
            }
        }

        // display slots JNI Code
    }

    public int createSlot(LocalDateTime day, String from, String to) {
        /*
         * Status Codes
         * 0 --> slot created
         * 1 --> invalid/old date time
         */
        LocalDateTime t = LocalDateTime.now();
        if (day.isBefore(t) == true) {
            return 1;
        }

        String VPid = "VP" + Integer.toString(VPBookings.size());
        VPBooking vp = new VPBooking(currentUser, day, from, to, VPid);
        VPBookings.put(VPid, vp);
        return 0;
    }

    public int sendJoinRequest(String id) {
        /*
         * Status Codes
         * 1 --> Invalid Slot ID
         * 0 --> Request sent
         */
        if (VPBookings.get(id) == null) {
            return 1;
        }

        String rID = "R" + Integer.toString(RequestsList.size());
        Requests r = new Requests(currentUser.getName(), VPBookings.get(id).getOwner().getName(), "VP Join Request",
                LocalDateTime.now(), rID, id);
        RequestsList.put(rID, r);
        return 0;
    }

    public int removePartner(String partnerName, String slotID) {
        /*
         * Status Code
         * 1 --> invalid slot
         * 2 --> partner unavailable
         * 3 --> not owner
         */

        if (VPBookings.get(slotID) == null) {
            return 1;
        }

        if (VPBookings.get(slotID).getOwner().equals(currentUser) == false) {
            return 3;
        }

        int isPartner = -1;
        for (int i = 0; i < VPBookings.get(slotID).getPartners().size(); i++) {
            if (VPBookings.get(slotID).getPartners().get(i).getName().equals(partnerName)) {
                isPartner = i;
            }
        }

        if (isPartner == -1) {
            return 2;
        }

        VPBookings.get(slotID).getPartners().remove(isPartner);
        return 0;
    }

    public int leaveVPSlot(String slotID) {
        /*
         * Status Codes
         * 1 --> invalid slot id
         * 2 --> owner left slot. randomly assigned new owner
         * 3 --> not found in slot
         * 0 --> left slot
         */

        if (VPBookings.get(slotID) == null) {// vp slot not found
            return 1;
        }

        if (VPBookings.get(slotID).getOwner() == currentUser) {// if owner change owner
            int Index = (int) Math.random() * VPBookings.get(slotID).getPartners().size();
            VPBookings.get(slotID).setOwner(VPBookings.get(slotID).getPartners().get(Index));
            VPBookings.get(slotID).getPartners().remove(Index);
            return 2;
        }

        int UserIndex = -1;
        for (int i = 0; i < VPBookings.get(slotID).getPartners().size(); i++) {
            if (VPBookings.get(slotID).getPartners().get(i) == currentUser) {
                UserIndex = i;
            }
        }

        if (UserIndex == -1) {// partner not found
            return 3;
        }

        VPBookings.get(slotID).getPartners().remove(UserIndex);
        return 0;
    }

    public int deleteVPSlot(String slotID) {
        /*
         * Status Code
         * 0 --> slot deleted successfully
         * 1 --> invalid slot id
         * 2 --> not owner;
         */

        if (VPBookings.get(slotID) == null) {// slot not found
            return 1;
        }

        if (VPBookings.get(slotID).getOwner() != currentUser) {// not owner
            return 2;
        }

        VPBookings.remove(slotID);
        return 0;
    }
}
