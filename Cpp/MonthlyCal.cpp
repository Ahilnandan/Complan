#include<bits/stdc++.h>
#define FMT_HEADER_ONLY
#include <fmt/core.h>
using namespace std;

auto s = chrono::system_clock::now();
time_t mytime = chrono::system_clock::to_time_t(s);
string x = ctime(&mytime);
map<string,string> colourstart;
map<string,string> colourend;

string monthnumber(string month){
	string m;
	if (month=="Jan")
		m="01";
	else if (month=="Feb")
		m="02";
	else if (month=="Mar")
		m="03";
	else if (month=="Apr")
		m="04";
	else if (month=="May")
		m="05";
	else if (month=="Jun")
		m="06";
	else if (month=="Jul")
		m="07";
	else if (month=="Aug")
		m="08";
	else if (month=="Sep")
		m="09";
	else if (month=="Oct")
		m="10";
	else if (month=="Nov")
		m="11";
	else if (month=="Dec")
		m="12";
	return m;
}

string gettoday(){
	string month = x.substr(4,3);
	string date = x.substr(8,2);
	if (date[0]==' '){
		string temp = date.substr(1,1);
		date = "0" + temp;
	}
	string hours = x.substr(11,2);
	string mins = x.substr(14,2);
	string year = x.substr(20,4);
	string m = monthnumber(month);
	string D = year + "-" + m + "-" + date;
	return D;
}

int dayNumber(int day, int month, int year){
    vector<int> t = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };
    year -= month < 3;
    return ( year + year/4 - year/100 + year/400 + t[month-1] + day) % 7;
}

int number_of_days(int year, int month){
        if ((month==4) || (month==6) || (month==9) || (month==11))
                return 30;
        else if (month==2){
                if (year%4)
                        return 28;
                else
                        return 29;
        }
        else
                return 31;
}

string nameofmonth(int n){
	switch (n){
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		default:
			return "";
	}
}
		
string getday(int n){
	switch (n){
		case 1:
			return "Sun";
		case 2:
			return "Mon";
		case 3:
			return "Tue";
		case 4:
			return "Wed";
		case 5:
			return "Thu";
		case 6:
			return "Fri";
		case 7:
			return "Sat";
		default:
			return "";
	}
}

void monthly(int year, int month){
	string Status;
	string yyyy = to_string(year);
	string mm;
	if (month<10)
		mm = "0" + to_string(month);
	else
		mm = to_string(month);
	string dd;
	string today = gettoday();
	cout << "+-------------------------------------------------------------------------------------------------+" << endl;
	cout << fmt::format("|\e[1m{:^97}\e[0m|\n", nameofmonth(month));
	cout << "+-------------+-------------+-------------+-------------+-------------+-------------+-------------+" << endl;
	for (int i=1;i<8;i++){
		cout << "|     " << "\e[1m" << getday(i) << "\e[0m" << "     ";
	}
	cout << "|" << endl;
	cout << "+-------------+-------------+-------------+-------------+-------------+-------------+-------------+" << endl;
	int n = number_of_days(year, month);
	int start = dayNumber(1, month, year);
	int l=0;
	int mainstart=0;
	bool breaksixth=false;
	for (int i=0;i<6;i++){
		if (breaksixth)
			break;
		for (int k=0;k<4;k++){
			int startl=mainstart;
			cout << "|" ;
			for (int j=0;j<7;j++){
				if (i==0 && j<start){
					cout << "             |";
				}
				else{
					if (j==0)
						l = startl;
					l++;
					if (l==n)
						breaksixth = true;
					if (l<10)
						dd = "0" + to_string(l);
					else
						dd = to_string(l);
					string day = yyyy + "-" + mm + "-" + dd;
					if (day<today)
						Status = "before";
					else if (day==today)
						Status = "today";
					else
						Status = "after";
					if (l>n)
						Status = "empty";
					if (k==3){
						if (l<=n){
							cout << colourstart[Status] + fmt::format("{:>13}",to_string(l)) + colourend[Status];
						}
						else
							cout << colourstart[Status] + "             " + colourend[Status];
					}
					else{
						cout << colourstart[Status] + "             " + colourend[Status];
					}
					if (k==3 && j==6)
						mainstart = l;
					else if (j==6)
						l = mainstart;
				}
			}
			cout << endl;
		}
		cout << "+-------------+-------------+-------------+-------------+-------------+-------------+-------------+" << endl;
	}
}

int main(){
	colourstart["empty"]="";
	colourstart["before"]="\033[1;100m\e[1m\033[1;30m";
	colourstart["today"]="\033[1;102m\e[1m\033[1;30m";
	colourstart["after"]="\033[1;104m\e[1m";
	colourend["empty"] = "|";
	colourend["before"] = "\033[0m|";
	colourend["today"] = "\033[0m|";
	colourend["after"] = "\033[0m|";
	monthly(2024,11);
}
