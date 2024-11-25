#include<bits/stdc++.h>
#include<fstream>
#include<jni.h>
#include "com_complan_function_handler_UserHandler.h"
#include<cstdlib>

using namespace std;

class Access_DB{
    public:
        static void Write(string data, string filename){
           ofstream ofile;
           ofile.open(filename);
           ofile << data;
           ofile.close();
        }
        static string Read(string filename){
            ifstream infile(filename);
            string data;
            vector<string> info;
            while (getline(infile, data)){
                info.push_back(data);
            }
            infile.close();
            string allUserData = "";
            for (string txt : info){
                allUserData = allUserData + txt + "\n";
            }
            return allUserData;
        }
};

extern "C" JNIEXPORT void JNICALL Java_com_complan_function_1handler_UserHandler_WriteToDB(JNIEnv *env, jclass thiz, jstring data,jstring filename){
    const char* nativeData = env -> GetStringUTFChars(data, NULL);
    const char* nativeFilename =  env -> GetStringUTFChars(filename, NULL);
    Access_DB::Write( string(nativeData), string(nativeFilename));
    env -> ReleaseStringUTFChars(data, nativeData);
    env -> ReleaseStringUTFChars(filename, nativeFilename);
}

extern "C" JNIEXPORT jstring JNICALL Java_com_complan_function_1handler_UserHandler_ReadFromDB(JNIEnv *env, jclass thiz, jstring filename){
    const char* nativeFilename = env -> GetStringUTFChars(filename, NULL);
    string data = Access_DB::Read ( string(nativeFilename));
    env -> ReleaseStringUTFChars(filename, nativeFilename);

    return env -> NewStringUTF(data.c_str());
}
