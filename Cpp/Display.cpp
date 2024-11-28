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
string DARKREDBG = "\033[1;41m";
string GREENBG ="\033[1;102m";
string DARKYELLOWBG = "\033[1;43m";
string YELLOWBG = "\033[1;103m";
string PINKBG = "\033[1;105m";
string MAGENTABG = "\033[1;45m";
string BLUEBG ="\033[1;44m";
string LIGHTBLUEBG = "\033[1;104m";
string WHITEBG = "\033[1;107m";
string DARKCYANBG = "\033[1;46m";
string CYANBG = "\033[1;106m";
string BLACK = "\033[1;30m";
string BLACKBG = "\033[1;40m";
string WHITE = "\033[1;37m";
string BOLD = "\e[1m";
string UNBOLD = "\e[22m";
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
        	cout << "+----------------------------------------------------------------------------------------------------+" << endl;
        	
        	if (you){
        		if (slots.size()==0){
        			cout << fmt::format("|"+REDBG+BOLD+"{:^100}"+RESET+"|", "You do not have any WMSlots on the given date") << endl;
        		}
        		else{
		    		cout << fmt::format("|" + BOLD + "{:^100}" + RESET + "|", "Your Slots on " + slots[0][0].substr(0,10)) << endl;
		    		for (int i=0;i<slots.size();i++){
        				cout << "+----------------------------------------------------------------------------------------------------+" << endl;
		    			string myOut1 = slots[i][4] + "'s slot from " + slots[i][0].substr(11,5) + " to " + slots[i][1].substr(11,5);
		    			cout << fmt::format("|" + GREENBG + BLACK + "{:^100}" + RESET + "|", myOut1) << endl;
						myOut1 = "OTP: " + slots[i][5];
						string myOut2 = "SlotID: " + slots[i][2];
						cout << fmt::format("|" + GREENBG + BLACK + "{:<50}", myOut2);
						cout << fmt::format("{:<50}" + RESET + "|", myOut1) << endl;
		    		}
		    	}
        	}
        	else{
        		if (slots.size()==0){
        			cout << fmt::format("|"+REDBG+BOLD+"{:^100}"+RESET+"|", "There are no WMSlots on the given date") << endl;
        		}
        		else{
		    		cout << fmt::format("|" + BOLD + "{:^100}" + RESET + "|", "All Slots on " + slots[0][0].substr(0,10)) << endl;
		    		for (int i=0;i<slots.size();i++){
		    			cout << "+----------------------------------------------------------------------------------------------------+" << endl;
		    			string myOut = slots[i][4] + "'s slot from " + slots[i][0].substr(11,5) + " to " + slots[i][1].substr(11,5);
		    			if (email != slots[i][3]){
		    				cout << fmt::format("|" + BLUEBG + "{:^100}" + RESET + "|", myOut) << endl;
							string myOut2 = "SlotID: " + slots[i][2];
							cout << fmt::format("|" + BLUEBG + "{:^100}" + RESET + "|", myOut2) << endl;
		    			}
		    			else{
		    				cout << fmt::format("|" + CYANBG + BLACK + BOLD + "{:^100}" + RESET + "|", myOut) << endl;
							string myOut2 = "SlotID: " + slots[i][2];
							cout << fmt::format("|" + CYANBG + BLACK + BOLD + "{:^100}" + RESET + "|", myOut2) << endl;
		    			}
		    		}
		    	}
        	}
        	cout << "+----------------------------------------------------------------------------------------------------+" << endl;    	
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
        		cout << fmt::format("|" + WHITEBG + BLACK + BOLD + "{:^75}" + RESET + "|", "VEHICLE POOLING SLOTS ON " + slots[0][0].substr(0,10)) << endl; 
        		for (int i=0;i<slots.size();i++){
        			cout << "+---------------------------------------------------------------------------+" << endl;
        			cout << fmt::format("|" + LIGHTBLUEBG + BLACK + "{:<25}", "From: " + slots[i][3]);
        			cout << fmt::format(BLACK + "{:<25}" , "To: " + slots[i][4]);
        			cout << fmt::format(BLACK + "{:<25}" + RESET + "|", "Date: " + slots[i][0].substr(0,10) )  << endl;
        			cout << fmt::format("|" + BLACK + LIGHTBLUEBG + "{:<25}", "SlotID: " + slots[i][1]);
        			cout << fmt::format(BLACK + "{:<25}", "Owner: " + slots[i][2]);
        			cout << fmt::format(BLACK + "{:<25}" + RESET + "|", "Time: " + slots[i][0].substr(11,5) )  << endl;
					vector<string> partners = Display::split(slots[i][5], '+');
					cout << fmt::format("|" + LIGHTBLUEBG + BLACK + "{:<75}" + RESET + "|", "Number of Partners: " +   to_string(partners.size())) << endl;
					for (int j=0;j<partners.size();j++){
						vector<string> details = Display::split(partners[j], '^');
						cout << fmt::format("|" + LIGHTBLUEBG + BLACK + "{:<37}", "Name: " + details[0]);
						cout << fmt::format(BLACK + "{:<38}" + RESET + "|", "EmailID: " + details[1]) << endl;
					}
        		}
        	}
        	cout << "+---------------------------------------------------------------------------+" << endl;
        	
        }
        
        
        
        
        static void view_Leaderboard(string data){						//function to view Leaderboard
        	if (data.size()==0){
        		cout << "+----------------------------------+" << endl;
        		cout << "|" + REDBG + BOLD + "   There are no users added yet   " + RESET + "|" << endl;
        		cout << "+----------------------------------+" << endl;
        		return;
        	}
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
        	cout << fmt::format("|" + REDBG + BOLD + BLACK + "{:^52}" + RESET + "|", "🥇️ LEADERBOARD 🥇️") << endl;
        	cout << "+------+-----------------------------------+-------+" << endl;
        	cout << "|" + MAGENTABG + BLACK + " Rank " + RESET + "|" + LIGHTBLUEBG + BLACK +"                NAME               " + RESET + "|" + DARKYELLOWBG + BLACK + "  CMS  " + RESET + "|" << endl;
        	cout << "+------+-----------------------------------+-------+" << endl;
        	for (int i=users.size()-1;i>=0;i--){
				cout << fmt::format("|"+PINKBG+"{:^6}" + RESET, to_string(users.size()-i));
        		if (users.size()-1-i==0){
        			cout << fmt::format("|"+BLACKBG+"  {:<31}🥇️"+RESET+"|", users[i].second);
        			cout << fmt::format(YELLOWBG + "{:^7}" + RESET + "|", to_string(users[i].first)) << endl;
        		}
        		else if (users.size()-1-i==1){
        			cout << fmt::format("|"+BLACKBG+"  {:<31}🥈️"+RESET+"|", users[i].second);
        			cout << fmt::format(YELLOWBG + "{:^7}" + RESET + "|", to_string(users[i].first)) << endl;
        		}
        		else if (users.size()-1-i==2){
        			cout << fmt::format("|"+BLACKBG+"  {:<31}🥉️"+RESET+"|", users[i].second);
        			cout << fmt::format(YELLOWBG + "{:^7}"+RESET+"|", to_string(users[i].first)) << endl;
        		}
        		else{
        			cout << fmt::format("|"+BLACKBG+"  {:<33}"+RESET+"|", users[i].second);
        			cout << fmt::format(YELLOWBG + "{:^7}"+RESET+"|", to_string(users[i].first)) << endl;
        		}
        		cout << "+------+-----------------------------------+-------+" << endl;
        	}
        	cout << endl;
        }

		static void user_profile(string data){
        	vector<string> temp = Display::split(data, ';');
        	
        	cout << "+---------------------------------------------------------------+" << endl;
			cout << "|" << BLACK << BLUEBG  << BOLD << "                             PROFILE                           " + RESET +  "|" << endl;
			cout << "+---------------------------------------------+-----------------+" << endl;
			cout << "|" << CYANBG << BLACK << " Name: " << fmt::format("{:<38}", temp[0])<< RESET << "|      +---+      |" << endl;
			cout << "|" << CYANBG << BLACK << " email: "<< fmt::format("{:<37}", temp[1])<< RESET << "|      |o o|      |" << endl; 
			cout << "|" << CYANBG << BLACK << " UserID: "<< fmt::format("{:<36}", temp[2])<< RESET << "|      ++-++      |" << endl;
			cout << "|" << CYANBG << BLACK << " Mobile No: "<< fmt::format("{:<33}", temp[3])<< RESET << "|       | |       |" << endl;
			cout << "|" << GREENBG << BLACK << " Points: " << fmt::format("{:<36}", temp[4])<< RESET << "|   +---+-+---+   |" << endl;
			cout << "|" << GREENBG << BLACK << " Cms: " << fmt::format("{:<39}", temp[5]) << RESET << "|   |         |   |" << endl;
			cout << "|" << GREENBG << BLACK << " Credits: " << fmt::format("{:<35}", temp[6])<<RESET <<"|   |         |   |" << endl;
			cout << "+---------------------------------------------+-----------------+" << endl;
        }

		static void view_Requests(string data){
        	if (data.size()==0){
        		cout << "+----------------------------------------------------------------------------------------------------+" << endl;
        		cout << fmt::format("|" + REDBG + BOLD + "{:^100}" + RESET + "|", "YOU HAVE NO REQUESTS") << endl;
        		cout << "+----------------------------------------------------------------------------------------------------+" << endl;
        		return;
        	}
        	vector<string> raw = Display::split(data);
        	vector<vector<string>> requests;
        	for (int i=0;i<raw.size();i++){
        		vector<string> temp = Display::split(raw[i], ';');
        		requests.push_back(temp);
        	}
        	cout << "+----------------------------------------------------------------------------------------------------+" << endl;
        	cout << fmt::format("|" + REDBG + BOLD + "{:^100}" + RESET + "|", "YOUR REQUESTS") << endl;
        	for (int i=0;i<requests.size();i++){
        		cout << "+----------------------------------------------------------------------------------------------------+" << endl;
        		cout << fmt::format("|" + LIGHTBLUEBG + BLACK +  "{:<50}", "RequestID: " + requests[i][2]);
				cout << fmt::format("{:<50}" +  RESET + "|", "Request Type: " + requests[i][5]) << endl;
			cout << fmt::format("|" + LIGHTBLUEBG + BLACK +  "{:<50}", "Slot ID: " + requests[i][6]);
        		cout << fmt::format("{:<50}" +  RESET + "|", "Slot Date: " + requests[i][7].substr(0,10) )<< endl;
				cout << fmt::format("|" + LIGHTBLUEBG + BLACK +  "{:<50}", "From: " + requests[i][0]);
        		cout << fmt::format("{:<50}" +  RESET + "|", "To: " + requests[i][1]) << endl;
        		cout << fmt::format("|" + LIGHTBLUEBG + BLACK+"{:<50}", "HasResponded: " + requests[i][3]);
				cout << fmt::format("{:<50}" + RESET + "|", "Response: " + requests[i][4] ) << endl;
            }
        	cout << "+----------------------------------------------------------------------------------------------------+" << endl;

		}
};

extern "C" JNIEXPORT void JNICALL Java_com_complan_function_1handler_UserHandler_WMview(JNIEnv *env, jclass thiz, jstring email, jstring data, jint onlyme){
    const char* nativeData = env -> GetStringUTFChars(data, NULL);
    const char* nativeEmail = env -> GetStringUTFChars(email, NULL);
    Display::view_WMSlots(string(nativeEmail), string(nativeData), onlyme);
    env -> ReleaseStringUTFChars(data, nativeData);
    env -> ReleaseStringUTFChars(email, nativeEmail);
}

extern "C" JNIEXPORT void JNICALL Java_com_complan_function_1handler_UserHandler_VPview(JNIEnv *env, jclass thiz, jstring data, jint onlyme){
    const char* nativeData = env -> GetStringUTFChars(data, NULL);
    Display::view_VPSlots(string(nativeData), onlyme);
    env -> ReleaseStringUTFChars(data, nativeData);
}

extern "C" JNIEXPORT void JNICALL Java_com_complan_function_1handler_UserHandler_LBview(JNIEnv *env, jclass thiz, jstring data){
    const char* nativeData = env -> GetStringUTFChars(data, NULL);
    Display::view_Leaderboard( string(nativeData));
    env -> ReleaseStringUTFChars(data, nativeData);
}

extern "C" JNIEXPORT void JNICALL Java_com_complan_function_1handler_UserHandler_ProfileView(JNIEnv *env,jobject thiz,jstring data){
   const char* nativeData = env-> GetStringUTFChars(data,NULL);
   Display::user_profile(string(nativeData));
   env-> ReleaseStringUTFChars(data,nativeData);
}

extern "C" JNIEXPORT void JNICALL Java_com_complan_function_1handler_UserHandler_RQview(JNIEnv *env, jclass thiz, jstring data){
	const char* nativeData = env -> GetStringUTFChars(data, NULL);
    Display::view_Requests(string(nativeData));
    env -> ReleaseStringUTFChars(data, nativeData);
}
