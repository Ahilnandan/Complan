#include<bits/stdc++.h>
#include<fstream>
#include<jni.h>
#define FMT_HEADER_ONLY
#include <fmt/core.h>
#include "com_complan_function_handler_UserHandler.h"
#include<cstdlib>

using namespace std;

string GREYBG = "\033[1;100m";
string REDBG = "\033[1;101m";
string GREENBG ="\033[1;102m";
string YELLOWBG = "\033[1;103m";
string BLUEBG ="\033[1;44m";
string CYANBG = "\033[1;106m";
string BLACK = "\033[1;30m";
string BOLD = "\e[1m";
string RESET = "\033[0m";

class Display{
    public:
    	
    	static vector<string> split(string S, char c='\n'){					//function to split a string into a vector of strings
    		vector<string> ans;
    		if (S.size()==0)
    			return ans;
    		int start = 0;
    		for (int i=0;i<S.size();i++){
    			if (S[i]==c){
    				ans.push_back(S.substr(start,i-start));
    				start = i + 1;
    			}
    		}
    		ans.push_back(S.substr(start, S.size()-start));
    		return ans;
    	}
    	
    	
    	
    	
        static void view_WMSlots(string email, string data, int you){		//function to view washing machneslots
        	vector<string> raw = Display::split(data);
        	sort(raw.begin(), raw.end());
        	vector<vector<string>>slots;
        	for (int i=0;i<raw.size();i++){
        		vector<string> temp = Display::split(raw[i], ';');
        		slots.push_back(temp);
        	}
        	cout << "+--------------------------------------------------+" << endl;
        	
        	if (you){
        		if (slots.size()==0){
        			cout << fmt::format("|"+REDBG+BOLD+"{:^50}"+RESET+"|", "You do not have any WMSlots on the given date") << endl;
        		}
        		else{
		    		cout << fmt::format("|" + BOLD + "{:^50}" + RESET + "|", "Your Slots on " + slots[0][0].substr(0,10)) << endl;
		    		for (int i=0;i<slots.size();i++){
		    			cout << "+--------------------------------------------------+" << endl;
		    			string myOut = "Slot from " + slots[i][0].substr(11,5) + " to " + slots[i][1].substr(11,5);
		    			cout << fmt::format("|" + GREENBG + BLACK + "{:^50}" + RESET + "|", myOut) << endl;
		    		}
		    	}
        	}
        	else{
        		if (slots.size()==0){
        			cout << fmt::format("|"+REDBG+BOLD+"{:^50}"+RESET+"|", "There are no WMSlots on the given date") << endl;
        		}
        		else{
		    		cout << fmt::format("|" + BOLD + "{:^50}" + RESET + "|", "All Slots on " + slots[0][0].substr(0,10)) << endl;
		    		for (int i=0;i<slots.size();i++){
		    			cout << "+--------------------------------------------------+" << endl;
		    			string myOut = slots[i][4] + "'s slot from " + slots[i][0].substr(11,5) + " to " + slots[i][0].substr(11,5);
		    			if (email != slots[i][3]){
		    				cout << fmt::format("|" + BLUEBG + "{:^50}" + RESET + "|", myOut) << endl;
		    			}
		    			else{
		    				cout << fmt::format("|" + CYANBG + BLACK + BOLD + "{:^50}" + RESET + "|", myOut) << endl;
		    			}
		    		}
		    	}
        	}
        	cout << "+--------------------------------------------------+" << endl;    	
        }
        
        
        
        
        static void view_VPSlots(string data, int you){					//function to view vehicle pooling slots
        	vector<string> raw = Display::split(data);
        	vector<vector<string>> slots;
        	for (int i=0;i<raw.size();i++){
        		vector<string> temp = Display::split(raw[i], ';');
        		slots.push_back(temp);
        	}
        	cout << "+---------------------------------------------------------------------------+" << endl;
        	if (slots.size()==0){
        		if (you)
        			cout << fmt::format("|"+REDBG+BOLD+"{:^75}"+RESET+"|", "You do not have any VPSlots on the given date") << endl;
        		else
        			cout << fmt::format("|"+REDBG+BOLD+"{:^75}"+RESET+"|", "There are no VPSlots on the given date") << endl;
        	}
        	else{
        		for (int i=0;i<slots.size();i++){
        			cout << "+---------------------------------------------------------------------------+" << endl;
        			cout << fmt::format("|" + BLUEBG + "{:<25}", "From: " + slots[i][3]) << fmt::format("{:<25}" , "To: " + slots[i][4]);
        			cout << fmt::format("{:<25}" + RESET + "|", "Date: " + slots[i][0].substr(0,10) )  << endl;
        			cout << fmt::format("|" + BLUEBG + "{:<25}", "SlotID: " + slots[i][1]) << fmt::format("{:<25}", "Owner: " + slots[i][2]);
        			cout << fmt::format("{:<25}" + RESET + "|", "Time: " + slots[i][0].substr(11,5) )  << endl;
        		}
        	}
        	cout << "+---------------------------------------------------------------------------+" << endl;
        	
        }
        
        
        
        
        static void view_Leaderboard(string data){						//function to view Leaderboard
        	vector<string> raw = Display::split(data);
        	vector<pair<int,string>> users;
        	for (int i=0;i<raw.size();i++){
        		pair<int,string> user;
        		vector<string> temp = Display::split(raw[i], ';');
        		user.first = stoi(temp[1]);
        		user.second = temp[0];
        		users.push_back(user);
        	}
        	sort(users.begin(), users.end());
        	cout << "+--------------------------------------------------+" << endl;
        	cout << fmt::format("|\e[1m{^50}\e[0m|", "LEADERBOARD") << endl;
        	cout << "+------------------------------------------+-------+" << endl;
        	cout << "|                   NAME                   |  CMS  |" << endl;
        	cout << "+------------------------------------------+-------+" << endl;
        	for (int i=users.size();i>=0;i--){
        		if (i==0)
        			cout << fmt::format("|{:^41}🥇️|", users[i].second) << fmt::format("{:^7}|", to_string(users[i].first)) << endl;
        		else if (i==1)
        			cout << fmt::format("|{:^41}🥈️|", users[i].second) << fmt::format("{:^7}|", to_string(users[i].first)) << endl;
        		else if (i==2)
        			cout << fmt::format("|{:^41}🥉️|", users[i].second) << fmt::format("{:^7}|", to_string(users[i].first)) << endl;
        		else
        			cout << fmt::format("|{:^42}|", users[i].second) << fmt::format("{:^7}|", to_string(users[i].first)) << endl;
        		cout << "+------------------------------------------+-------+" << endl;
        	}
        	cout << endl;
        }
};

extern "C" JNIEXPORT void JNICALL Java_UserHandler_WMview(JNIEnv *env, jobject thiz, jstring email, jstring data, jint onlyme){
    const char* nativeData = env -> GetStringUTFChars(data, NULL);
    const char* nativeEmail = env -> GetStringUTFChars(email, NULL);
    Display::view_WMSlots(string(nativeEmail), string(nativeData), onlyme);
    env -> ReleaseStringUTFChars(data, nativeData);
    env -> ReleaseStringUTFChars(email, nativeEmail);
}

extern "C" JNIEXPORT void JNICALL Java_UserHandler_VPview(JNIEnv *env, jobject thiz, jstring data, jint onlyme){
    const char* nativeData = env -> GetStringUTFChars(data, NULL);
    Display::view_VPSlots(string(nativeData), onlyme);
    env -> ReleaseStringUTFChars(data, nativeData);
}

extern "C" JNIEXPORT void JNICALL Java_UserHandler_LBview(JNIEnv *env, jobject thiz, jstring data){
    const char* nativeData = env -> GetStringUTFChars(data, NULL);
    Display::view_Leaderboard( string(nativeData));
    env -> ReleaseStringUTFChars(data, nativeData);
}

