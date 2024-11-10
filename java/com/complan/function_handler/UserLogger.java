package com.complan.function_handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Command Format
 * LOGIN <user-email> <password>
 * LOGOUT
 * REGISTER <name> <password> <mobile_number> <email-id>
 * CREATE_WMSLOT <date(dd-mm-yy)> <time(hh:mm)>
 * DELETE_WMSLOT <date(dd-mm-yy)> <time(hh:mm)>
 * GIVE_WMSLOT <to email-id> <date(dd-mm-yy)> <time(hh:mm)>
 * RECEIVE_WMSLOT <request-id> <accept(y/n)>
 * USE_WMSLOT <date(dd-mm-yy)> <time(hh:mm)> <otp>
 * DISPLAY_WMSLOTS <date<dd-mm-yy> <display only your slots(y/n)> 
 */

public class UserLogger {
    private UserHandler Handler;
    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";

    public UserLogger() {
        Handler = new UserHandler();
    }

    public LocalDateTime parseDate(String date, String time) {
        try {
            ArrayList<Integer> Date_Indices = new ArrayList<Integer>();
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

    public ArrayList<String> parseCommands(String command) {
        ArrayList<String> tokens = new ArrayList<String>();
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
        while (scanner.hasNextLine() == true) {
            String input = scanner.nextLine();
            ArrayList<String> commands = parseCommands(input);
            String command = commands.get(0);

            if (command.equals("LOGIN")) {// LOGIN
                if (commands.size() < 3) {
                    System.out.println(RED + "Invalid Arguments.." + RESET);
                    return;
                }
                int response = Handler.Login(commands.get(1), commands.get(2));
                if (response == 0) {
                    System.out.println(GREEN + "Welcome " + Handler.getCurrentUser().getName() + RESET);
                    return;
                } else if (response == 1) {
                    System.out.println(RED + "Incorrect password. Please try again." + RESET);
                    return;
                } else if (response == 2) {
                    System.out.println(RED + "Invalid user email or user does not exist." + RESET);
                    return;
                }
            } else if (command.equals("LOGOUT")) {// LOGOUT
                Handler.Logout();
                System.out.println(GREEN + "Logged out successfully..." + RESET);
            } else if (command.equals("REGISTER")) {// REGISTER
                if (commands.size() < 5) {
                    System.out.println(RED + "Invalid Arguments.." + RESET);
                    return;
                }
                int response = Handler.Register(commands.get(1), commands.get(2), commands.get(3), commands.get(4));

                if (response == 1) {
                    System.out.println(RED + "User already exists..." + RESET);
                    return;
                } else if (response == 0) {
                    System.out.println(GREEN + "Registered successfully..." + RESET);
                    return;
                }
            } else if (command.equals("CREATE_WMSLOT")) {
                String date = commands.get(1);
                String time = commands.get(2);
                LocalDateTime dt = parseDate(date, time);
                int response = Handler.createWMSlot(dt);
                if (response == 0) {
                    System.out.println(GREEN + "Slot booked succesfully" + RESET);
                    return;
                } else if (response == 1) {
                    System.out.println(RED + "insufficient credits" + RESET);
                    return;
                } else if (response == 2) {
                    System.out.println(RED + "Timing not available" + RESET);
                    return;
                }
            }
        }

    }
}
