#include<bits/stdc++.h>
#define FMT_HEADER_ONLY
#include <fmt/core.h>
using namespace std;

auto s = chrono::system_clock::now();
time_t mytime = chrono::system_clock::to_time_t(s);
string x = ctime(&mytime);

int geth(){	
        string month = x.substr(4,3);
        string date = x.substr(8,2);
        if (date[0]==' '){
                string temp = date.substr(1,1);
                date = "0" + temp;
        }
        string hours = x.substr(11,2);
	int h=0;
	if (hours[0]!=' ')
		h = h*10 + (hours[0]-'0');
	h = h*10 + (hours[1]-'0');
	return h;
}

int main(){
	int h = geth();
	for (int i=0;i<24;i++){
		string o = to_string(i) + " to " + to_string(i+1);
		if (i<h){
			cout << "+----------------------------------------+" << endl;
			cout << fmt::format("|\033[1;100m\e[1m\033[1;30m{:^40}\033[0m|\n", o);
			//cout << "| \033[1;100m     \e[1m" << i << " to " << i+1 << "      \033[0m |" << endl;
			//cout << "+--------------------+" << endl << endl;
		}
		else if (i==h){
			//cout << "+--------------------+" << endl;
			cout << "+----------------------------------------+" << endl;
			cout << fmt::format("|\033[1;102m\e[1m\033[1;30m{:^40}\033[0m|\n", o);
			//cout << "| \033[1;102m     \e[1m\033[1;30m" << i << " to " << i+1 << "    \033[0m |" << endl;
			//cout << "+--------------------+" << endl << endl;
		}
		else{
			//cout << "+--------------------+" << endl;
                        cout << "+----------------------------------------+" << endl;
			cout << fmt::format("|\033[1;104m\e[1m{:^40}\033[0m|\n", o);
			//cout << "| \033[1;104m     \e[1m\033[1;30m" << i << " to " << i+1 << "    \033[0m |" << endl;
                        //cout << "+--------------------+" << endl << endl;
		}
	}
}
