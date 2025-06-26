#include<iostream>
#include<Windows.h>
#include<fstream>
using namespace std;
int* next(string s);
int* next(string s)
{
	int size = s.size();
	int* p = new int[size];
	p[0] = -1;
	int j = 0,k=-1;
	while (j < size-1)
	{
		if (k == -1 || s[j] == s[k])
		{
			p[++j] = ++k;
		}
		else k = p[k];
	}
	return p;
}
int kmp(string s, string p)
{
	int* n = next(p);
	int ssize = s.size(),i=0;
	int psize = p.size(),j=0;
	while (j < psize && i < ssize)
	{
		if (-1 == j || s[i] == p[j])
		{
			i++; j++;
		}
		else j = n[j];
	}
	delete[]n;
	return i - j;

}
int main()
{
	fstream out("test.txt", ios::app);
	string S,P;
	cin >> S>>P;
	out << S << endl << P<<endl;
	double t = 0;
	LARGE_INTEGER nFreq;
	LARGE_INTEGER nBeginTime;
	LARGE_INTEGER nEndTime;
	QueryPerformanceFrequency(&nFreq);
	QueryPerformanceCounter(&nBeginTime);
	int position = kmp(S,P);
	if (position > S.size() - P.size()) out << "no";
	else out << position;
	QueryPerformanceCounter(&nEndTime);
	t = (double)(nEndTime.QuadPart - nBeginTime.QuadPart) / (double)(nFreq.QuadPart);
	//计算程序执行时间单位为秒数  
	out << "运行时间：" << t * 1000 << "ms" << endl;
	return 0;
}
