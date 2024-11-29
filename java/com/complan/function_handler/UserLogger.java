package com.complan.function_handler;

import java.time.LocalDateTime;
import java.util.Vector;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Command Format
 * LOGIN <user-email> <password>
 * LOGOUT
 * REGISTER <name> <password> <mobile_number> <email-id>
 * CREATE_WMSLOT <date(dd-mm-yy)> <time(hh:mm)>
 * DELETE_WMSLOT <slot ID> <password>
 * ASK_WMSLOT <slot ID>
 * RESPOND_REQUEST <requestID> <accept(y/n)> //types --> "VP Join Request" , "give WMSlot"
 * USE_WMSLOT <slot ID>  <otp>
 * DISPLAY_WMSLOTS <date(dd-mm-yy)> <display only your slots(y/n)> 
 * DISPLAY_VPSLOTS <date(dd-mm-yy)> <your slots(slots in which you are present) y/n>
 * CREATE_VPSLOT <date(dd-mm-yy)> <time(hh:mm)> <from> <to>
 * JOIN_VPSLOT <slotID>
 * REMOVE_PARTNER <slotID> <partner email> <password>
 * LEAVE_VPSLOT <slotID> <password>
 * DELETE_VPSLOT <slotID> <password>
 * DISPLAY_WMSTATUS
 * AVAIL_CREDIT <password>
 * DISPLAY_CREDITS
 * DISPLAY_POINTS
 * DISPLAY_CMS
 * DISPLAY_LEADERBOARD
 * HELP
 * EXIT
 * RULES
 * On entering invalid command display "Please enter HELP to know command formats"
 */

public class UserLogger {
    private UserHandler Handler;
    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[93m";
    String BLUE = "\u001B[34m";
    String BLACK = "\u001B[30m";
    String GREENBG = "\u001B[102m";
    String BLUEBG = "\u001B[104m";
    String YELLOWBG = "\u001B[103m";
    String BLACKBG = "\u001B[40m";
    String BOLD = "\u001B[1m";

    public UserLogger() {
        Handler = new UserHandler();
        Handler.FetchUserData();
        Handler.FetchWMSlots();
        Handler.FetchVPBookings();
        Handler.FetchRequests();
    }

    public LocalDateTime parseDate(String date, String time) {
        try {
            Vector<Integer> Date_Indices = new Vector<Integer>();
            for (int i = 0; i < date.length(); i++) {
                if (date.charAt(i) == '-') {
                    Date_Indices.add(i);
                }
            }
            int day = Integer.parseInt(date.substring(0, Date_Indices.get(0)));
            int month = Integer.parseInt(date.substring(Date_Indices.get(0) + 1, Date_Indices.get(1)));
            int year = Integer.parseInt(date.substring(Date_Indices.get(1) + 1));

            int idx = time.indexOf(':', 0);
            int hour = Integer.parseInt(time.substring(0, idx));
            int minute = Integer.parseInt(time.substring(idx + 1));
            LocalDateTime dt = LocalDateTime.of(year, month, day, hour, minute, 0);
            return dt;

        } catch (Error e) {
            System.out.println(RED + "Invalid arguments..." + RESET);
            return LocalDateTime.of(1, 1, 1, 1, 1, 1);
        }
    }

    public Vector<String> parseCommands(String command) {
        Vector<String> tokens = new Vector<String>();
        Matcher matcher = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(command);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                tokens.add(matcher.group(1));
            } else {
                tokens.add(matcher.group(2));
            }
        }
        return tokens;
    }

    public void get_executeCommands(Scanner scanner) {
        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.println("|" + BLACKBG + "                          🔥️ " + BOLD + "WELCOME TO ComPlan"
                + " 🔥️                              " + RESET + "|");
        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.println();
        System.out.println("Please enter " + BOLD + "HELP" + RESET + " to see valid command formats");
        while (scanner.hasNextLine() == true) {
            try {
                Handler.BackgroundTaskRunner();
                String input = scanner.nextLine();
                Vector<String> commands = parseCommands(input);
                String command = commands.get(0);

                if (command.equals("LOGIN")) {// LOGIN
                    int response = Handler.Login(commands.get(1), commands.get(2));
                    if (response == 0) {
                        System.out.println(GREEN + "Welcome " + Handler.getCurrentUser().getName() + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "Incorrect password. Please try again." + RESET);
                        continue;
                    } else if (response == 2) {
                        System.out.println(RED + "Invalid user email or user does not exist." + RESET);
                        continue;
                    } else if (response == 3) {
                        System.out.println(RED + "User already logged in.." + RESET);
                        continue;
                    } else if (response == 4) {
                        System.out.println(RED + Handler.getCurrentUser().getName() + " is logged in..." + RESET);
                        continue;
                    }
                } else if (command.equals("LOGOUT")) {// LOGOUT
                    int LOresponse = Handler.Logout();
                    if (LOresponse == 0) {
                        System.out.println(GREEN + "Logged out successfully..." + RESET);
                    } else if (LOresponse == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                    }
                } else if (command.equals("REGISTER")) {// REGISTER
                    /*
                     * if (commands.size() < 5) {
                     * System.out.println(RED + "Invalid Arguments.." + RESET);
                     * continue;
                     * }
                     */
                    int response = Handler.Register(commands.get(1), commands.get(2), Long.parseLong(commands.get(3)),
                            commands.get(4));

                    if (response == 1) {
                        System.out.println(RED + "User already exists..." + RESET);
                        continue;
                    } else if (response == 0) {
                        System.out.println(GREEN + "Registered successfully..." + RESET);
                        continue;
                    } else if (response == 2) {
                        System.out.println(RED + "Invalid Email ID..." + RESET);
                        continue;
                    } else if (response == 3) {
                        System.out.println(RED + "Password should be atleast 9 charecters long..." + RESET);
                        continue;
                    }
                    else if(response==4){
                        System.out.println(RED +"User already logged in"+RESET);
                        continue;
                    }
                } else if (command.equals("CREATE_WMSLOT")) {
                    String date = commands.get(1);
                    String time = commands.get(2);
                    LocalDateTime dt = parseDate(date, time);
                    int response = Handler.createWMSlot(dt);
                    if (response == 0) {
                        System.out.println(GREEN + "Slot booked succesfully" + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "insufficient credits" + RESET);
                        continue;
                    } else if (response == 2) {
                        System.out.println(RED + "Timing not available" + RESET);
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    }
                    else if(response==3){
                        System.out.println(RED+"Invalid old timing"+RESET);
                        continue;
                    }
                } else if (command.equals("DELETE_WMSLOT")) {
                    String slotId = commands.get(1);
                    String password = commands.get(2);
                    int response = Handler.deleteWMSlot(slotId, password);
                    if (response == 0) {
                        System.out.println(GREEN + "Slot deleted succesfully" + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "Slot does not exist..." + RESET);
                        continue;
                    } else if (response == 2) {
                        System.out.println(YELLOW + "Slot deleted, but 10 points reduced..." + RESET);
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    } else if (response == 3) {
                        System.out.println(RED + "Wrong password" + RESET);
                        continue;
                    }
                    else if(response ==4){
                        System.out.println(RED+"Washing Machine already used"+RESET);
                        continue;
                    }
                    else if(response==5){
                        System.out.println(RED+"Slot cannot be deleted after start time"+RESET);
                        continue;
                    }
                } else if (command.equals("AVAIL_CREDIT")) {
                    String password = commands.get(1);
                    int response = Handler.availCredit(password);
                    if (response == 0) {
                        System.out.println(GREEN + "Credit availed successfully" + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "Insufficient points to avail credit..." + RESET);
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    } else if (response == 3) {
                        System.out.println(RED + "Wrong Password" + RESET);
                    }
                } else if (command.equals("DISPLAY_CREDITS")) {
                    int response = Handler.viewCredit();
                    if (response != -1) {
                        double credits = response;
                        System.out.println("+--------------------+");
                        System.out.println("|" + GREENBG + BLACK + BOLD + "  Your credits: "
                                + String.format("%2.0f", credits) + "  " + RESET + "|");
                        System.out.println("+--------------------+");
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    }
                } else if (command.equals("DISPLAY_POINTS")) {
                    int response = Handler.viewPoints();
                    if (response != -1) {
                        double points = response;
                        System.out.println("+--------------------+");
                        System.out.println("|" + BLUEBG + BLACK + BOLD + "  Your points: "
                                + String.format("%3.0f", points) + "  " + RESET + "|");
                        System.out.println("+--------------------+");
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    }
                } else if (command.equals("DISPLAY_CMS")) {
                    int response = Handler.viewCms();
                    if (response != -1) {
                        double cms = response;
                        System.out.println("+--------------------+");
                        System.out.println("|" + YELLOWBG + BLACK + BOLD + "   Your Cms: " + String.format("%3.0f", cms)
                                + "    " + RESET + "|");
                        System.out.println("+--------------------+");
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    }
                } else if (command.equals("DISPLAY_WMSTATUS")) {
                    String response = Handler.viewWMStatus();
                    if (response.equals("WM is in use")) {
                        System.out.println(RED + response + "..." + RESET);
                    } else if (response.equals("WM is free to use")) {
                        System.out.println(GREEN + response + "!" + RESET);
                    }
                } else if (command.equals("ASK_WMSLOT")) {
                    String slotId = commands.get(1);
                    int response = Handler.askWMSlot(slotId);
                    if (response == 0) {
                        System.out.println(GREEN + "Request sent successfully" + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "Slot not found..." + RESET);
                        continue;
                    } else if (response == 2) {
                        System.out.println(RED + "User not found..." + RESET);
                        continue;
                    } else if (response == 3) {
                        System.out.println(RED + "Insufficient credits to ask for slot..." + RESET);
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    } else if (response == 4) {
                        System.out.println(RED + "Cannot send request to yourself..." + RESET);
                        continue;
                    }
                } else if (command.equals("RESPOND_REQUEST")) {
                    String requestId = commands.get(1);
                    boolean isaccepted = commands.get(2).equals("y");
                    int response = Handler.receive_WMSlot(requestId, isaccepted);
                    if (response == 0) {
                        System.out.println(GREEN + "Request has been accepted and confirmed" + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "Request does not exist..." + RESET);
                        continue;
                    } else if (response == 2) {
                        System.out.println(RED + "User does not exist..." + RESET);
                        continue;
                    } else if (response == 3) {
                        System.out.println(RED + "Request rejected" + RESET);
                        continue;
                    } else if (response == 4) {
                        System.out.println(RED + "Insufficient credits to accept request..." + RESET);
                        continue;
                    } else if (response == 5) {
                        System.out.println(RED + "Already responded to request" + RESET);
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    } else if (response == 6){
                        System.out.println(RED+"Slot does not exist..."+RESET);
                        continue;
                    } else if (response == 7) {
                    	System.out.println(RED + "Request is not yours to repond..." + RESET);
                    	continue;
                    }
                } else if (command.equals("USE_WMSLOT")) {
                    String slotId = commands.get(1);
                    int OTP = Integer.parseInt(commands.get(2));
                    int response = Handler.useWMSlot(slotId, OTP);
                    if (response == 0) {
                        System.out.println(GREEN + "Washing Machine started running" + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "Slot not found..." + RESET);
                        continue;
                    } else if (response == 2) {
                        System.out.println(RED + "Wrong OTP..." + RESET);
                        continue;
                    } else if (response == 3) {
                        System.out.println(RED + "Too early to start slot..." + RESET);
                        continue;
                    } else if (response == 4) {
                        System.out.println(RED + "Too late to start slot..." + RESET);
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    } else if (response == 5) {
                        System.out.println(RED + "Washing Machine run process already initiated previously" + RESET);
                        continue;
                    }
                } else if (command.equals("DISPLAY_WMSLOTS")) {
                    String date = commands.get(1);
                    LocalDateTime dt = parseDate(date, "00:00");
                    boolean onlyme;
                    if (commands.get(2).equals("y")) {
                        onlyme = true;
                    } else if (commands.get(2).equals("n")) {
                        onlyme = false;
                    } else {
                        System.out.println(RED + "Invalid parameter.. Mention y/n" + RESET);
                        continue;
                    }
                    Handler.displayAllSlotsOnDay(dt, onlyme);
                } else if (command.equals("DISPLAY_VPSLOTS")) {
                    String date = commands.get(1);
                    boolean yourslots = commands.get(2).equals("y");
                    LocalDateTime dt = parseDate(date,"0:00");
                    int response = Handler.viewVPSlotsOnDay(dt,yourslots);
                    if (response == -1) {
                    	System.out.println(RED + "Uer is not loggied in..." + RESET);
                    	continue;
                    }
                } else if (command.equals("CREATE_VPSLOT")) {
                    String date = commands.get(1);
                    String time = commands.get(2);
                    String from = commands.get(3);
                    String to = commands.get(4);
                    LocalDateTime dt = parseDate(date, time);
                    int response = Handler.createSlot(dt, from, to);
                    if (response == 0) {
                        System.out.println(GREEN + "Slot created successfully" + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "invalid/old date time..." + RESET);
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    }
                } else if (command.equals("JOIN_VPSLOT")) {
                    String slotId = commands.get(1);
                    int response = Handler.sendJoinRequest(slotId);
                    if (response == 0) {
                        System.out.println(GREEN + "Join request sent successfully" + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "Invalid Slot ID..." + RESET);
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    } else if (response == 2) {
                        System.out.println(RED + "You are already present in this VP slot" + RESET);
                        continue;
                    } else if (response == 3) {
                        System.out.println(RED + "Cannot send request to yourself.." + RESET);
                        continue;
                    }
                } else if (command.equals("REMOVE_PARTNER")) {
                    String slotId = commands.get(1);
                    String email = commands.get(2);
                    String password = commands.get(3);
                    int response = Handler.removePartner(email, slotId, password);
                    if (response == 0) {
                        System.out.println(GREEN + "Partner has been removed successfully" + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "Invalid slot..." + RESET);
                        continue;
                    } else if (response == 2) {
                        System.out.println(RED + "Partner unavailable..." + RESET);
                        continue;
                    } else if (response == 3) {
                        System.out.println(RED + "You are not an owner, you cannot remove a partner..." + RESET);
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    } else if (response == 4) {
                        System.out.println(RED + "Wrong password" + RESET);
                        continue;
                    }
                } else if (command.equals("LEAVE_VPSLOT")) {
                    String slotId = commands.get(1);
                    String password = commands.get(2);
                    int response = Handler.leaveVPSlot(slotId, password);
                    if (response == 0) {
                        System.out.println(GREEN + "Partner removed successfully" + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "Invalid slot id..." + RESET);
                        continue;
                    } else if (response == 2) {
                        System.out.println(YELLOW + "owner left slot. randomly assigned new owner..." + RESET);
                        continue;
                    } else if (response == 3) {
                        System.out.println(RED + "You are not present in the slot..." + RESET);
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    } else if (response == 4) {
                        System.out.println(RED + "Wrong Password" + RESET);
                        continue;
                    }
                } else if (command.equals("DELETE_VPSLOT")) {
                    String slotId = commands.get(1);
                    String password = commands.get(2);
                    int response = Handler.deleteVPSlot(slotId, password);
                    if (response == 0) {
                        System.out.println(GREEN + "slot deleted successfully" + RESET);
                        continue;
                    } else if (response == 1) {
                        System.out.println(RED + "Invalid slot id..." + RESET);
                        continue;
                    } else if (response == 2) {
                        System.out.println(RED + "You cannot delete this slot as you are not it's Owner..." + RESET);
                        continue;
                    } else if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    } else if (response == 3) {
                        System.out.println(RED + "Wrong password" + RESET);
                        continue;
                    }
                } else if (command.equals("DISPLAY_LEADERBOARD")) {
                    Handler.displayLeaderBoard();
                } else if (command.equals("HELP")) {
                    System.out.println(BOLD
                            + "+----------------------------------------------------------------------------------------------+");
                    System.out.println(
                            "|                                     *** COMMAND FORMAT ***                                   |"
                                    + RESET);
                    System.out.println(
                            "+----------------------------------------------------------------------------------------------+");
                    System.out.println(
                            "| * LOGIN <user-email> <password>                                                              |");
                    System.out.println(
                            "| * LOGOUT                                                                                     |");
                    System.out.println(
                            "| * REGISTER <name> <password> <mobile_number> <email-id>                                      |");
                    System.out.println(
                            "| * CREATE_WMSLOT <date(dd-mm-yy)> <time(hh:mm)>                                               |");
                    System.out.println(
                            "| * DELETE_WMSLOT <slot ID> <password>                                                         |");
                    System.out.println(
                            "| * ASK_WMSLOT <slot ID>                                                                       |");
                    System.out.println(
                            "| * RESPOND_REQUEST <requestID> <accept(y/n)>                                                  |");
                    System.out.println(
                            "| * DISPLAY_REQUESTS                                                                           |");
                    System.out.println(
                            "| * USE_WMSLOT <slot ID>  <otp>                                                                |");
                    System.out.println(
                            "| * DISPLAY_WMSLOTS <date(dd-mm-yy)> <display only your slots(y/n)>                            |");
                    System.out.println(
                            "| * DISPLAY_VPSLOTS <date(dd-mm-yy)> <display only your slots(y/n)>                            |");
                    System.out.println(
                            "| * CREATE_VPSLOT <date(dd-mm-yy)> <time(hh:mm)> <from> <to>                                   |");
                    System.out.println(
                            "| * JOIN_VPSLOT <slotID>                                                                       |");
                    System.out.println(
                            "| * REMOVE_PARTNER <slotID> <partner email>  <password>                                        |");
                    System.out.println(
                            "| * LEAVE_VPSLOT <slotID> <password>                                                           |");
                    System.out.println(
                            "| * DELETE_VPSLOT <slotID> <password>                                                          |");
                    System.out.println(
                            "| * DISPLAY_WMSTATUS                                                                           |");
                    System.out.println(
                            "| * AVAIL_CREDIT <password>                                                                    |");
                    System.out.println(
                            "| * DISPLAY_PROFILE                                                                            |");
                    System.out.println(
                            "| * DISPLAY_CREDITS                                                                            |");
                    System.out.println(
                            "| * DISPLAY_POINTS                                                                             |");
                    System.out.println(
                            "| * DISPLAY_CMS                                                                                |");
                    System.out.println(
                            "| * DISPLAY_LEADERBOARD                                                                        |");
                    System.out.println(
                            "| * HELP                                                                                       |");
                    System.out.println(
                            "| * EXIT                                                                                       |");
                    System.out.println(
                            "| * RULES                                                                                      |");
                    System.out.println(
                            "+----------------------------------------------------------------------------------------------+");
                    System.out.println();
                } else if (command.equals("RULES")) {
                    System.out.println(BOLD
                            + "+-------------------------------------------------------------------------------------------------------------------+");
                    System.out.println(
                            "|                                                  *** RULES ***                                                    |");
                    System.out.println(
                            "+-------------------------------------------------------------------------------------------------------------------+"
                                    + RESET);
                    System.out.println("| " + BLUE + BOLD
                            + "WASHING MACHINE RULES                                                                                             "
                            + RESET + "|");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "1. Points for Washes                                                                                              "
                            + RESET + "|");
                    System.out.println(
                            "|  -> Users earn 10 points for each successful wash                                                                 |");
                    System.out.println(
                            "|  -> 10 points are deducted for unsuccessful washes                                                                |");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "2. Semester Credits                                                                                               "
                            + RESET + "|");
                    System.out.println(
                            "|  -> Each user receives 12 credits per semester initially                                                          |");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "3. Availing Credits                                                                                               "
                            + RESET + "|");
                    System.out.println(
                            "|  -> Users can avail 1 credit using 50 points (which can be earned from 5 successful washes)                       |");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "4. Credit Deduction                                                                                               "
                            + RESET + "|");
                    System.out.println(
                            "|  -> 1 credit is deducted each time a user books a wash regardless of outcome of wash (successful or unsuccessful) |");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "5. Asking for Slots                                                                                               "
                            + RESET + "|");
                    System.out.println(
                            "|  -> A user can ask for a slot from another user by requesting for it                                              |");
                    System.out.println(
                            "|  -> The user who gave their slot regains back 1 credit                                                            |");
                    System.out.println(
                            "|  -> The user who received the slot loses 1 credit                                                                 |");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "6. Slot Deletion                                                                                                  "
                            + RESET + "|");
                    System.out.println(
                            "|  -> Users can cancel their booking anytime before 2 hours prior to their slot time                                |");
                    System.out.println(
                            "|  -> On doing so, they will regain back their 1 credit                                                             |");
                    System.out.println(
                            "|  -> You can also delete a slot less than 2 hours prior to your booking time                                       |");
                    System.out.println(
                            "|  -> In such a case, you would get back your 1 credit, but 10 points will be deducted                              |");
                    System.out.println(
                            "+-------------------------------------------------------------------------------------------------------------------+");
                    System.out.println("| " + BLUE + BOLD
                            + "VEHICLE POOLING RULES                                                                                             "
                            + RESET + "|");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "1. Joining a Pool                                                                                                 "
                            + RESET + "|");
                    System.out.println(
                            "|  -> To join a pool, users must send a join request                                                                |");
                    System.out.println(
                            "|  -> Once accepted by the pool owneer, the user is added to the pool                                               |");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "2. Multiple Pools                                                                                                 "
                            + RESET + "|");
                    System.out.println(
                            "|  -> Users are allowed to join multiple pools                                                                      |");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "3. Points                                                                                                         "
                            + RESET + "|");
                    System.out.println(
                            "|  -> No points are awarded for participating in vehicle pooling                                                    |");
                    System.out.println(
                            "+-------------------------------------------------------------------------------------------------------------------+");
                    System.out.println("| " + BLUE + BOLD
                            + "LEADERBOARD                                                                                                       "
                            + RESET + "|");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "1. Leaderboard Display                                                                                            "
                            + RESET + "|");
                    System.out.println(
                            "|  -> The leaderboard shows the rankings of users registeres in ComPlan                                             |");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "2. Ranking Criteria                                                                                               "
                            + RESET + "|");
                    System.out.println(
                            "|  -> Rankings are based on each user's total points including those exchanged for credits                          |");
                    System.out.println(
                            "|  -> Points deducted due to deletion of washing machine slot will also be deducted in the total leaderboard points |");
                    System.out.println(
                            "|                                                                                                                   |");
                    System.out.println("| " + YELLOW + BOLD
                            + "3. Example                                                                                                        "
                            + RESET + "|");
                    System.out.println(
                            "|  -> If a user has 90 points, and they used 50 of those to buy an extra credit                                     |");
                    System.out.println(
                            "|  -> Then, they will have only 40 credits, but their leaderboard ranking is based on original 90 points            |");
                    System.out.println(
                            "|  -> If a user has 90 points, and they lost 20 points for leaving an pool late                                     |");
                    System.out.println(
                            "|  -> Their leaderboard ranking is based on 70 points                                                               |");
                    System.out.println(
                            "+-------------------------------------------------------------------------------------------------------------------+");
                    System.out.println();
                } else if (command.equals("EXIT")) {
                    int response = Handler.Exit();
                    return;
                } else if (command.equals("DISPLAY_REQUESTS")) {
                    int response = Handler.viewRequests();
                    if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    }
                } else if (command.equals("DISPLAY_PROFILE")) {
                    int response = Handler.viewProfile();
                    if (response == -1) {
                        System.out.println(RED + "User is not logged in..." + RESET);
                        continue;
                    }
                } else {
                    System.out.println(RED + "Invalid command format..." + RESET);
                    System.out.println("Please enter " + BOLD + "HELP" + RESET + " to see valid command formats");
                }
            } catch (Exception e) {
                System.out.println(RED + "Invalid command format... " + e + RESET);
                System.out.println("Please enter " + BOLD + "HELP" + RESET + " to see valid command formats");
            }
        }
    }
}
