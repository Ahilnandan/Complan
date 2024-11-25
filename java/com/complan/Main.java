package com.complan;

import java.util.Scanner;
import com.complan.function_handler.UserLogger;

public class Main {
    public static void main(String[] args) {
        UserLogger UL = new UserLogger();
        Scanner sc = new Scanner(System.in);
        UL.get_executeCommands(sc);
    }
}
