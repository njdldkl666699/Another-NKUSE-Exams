#include<iostream>
#include<string>
#include<fstream>
#include<cmath>
#include<sys/time.h>
using namespace std;
int* badchar(string p)
{
	int lenth = p.length();
	int* badc = new int[123];
	for (int i = 0; i < 123; i++)
	{
		badc[i] = -1;
	}
	for (int i = 0; i < lenth; i++)
	{
		badc[p[i]] = i;
	}
	return badc;
}
int* buildSS(string p)
{
	int lenth = p.length();
	int* ss = new int[lenth];
	for (int i = 0; i < lenth; i++) {
		int match = 0;
		for (int j = 0; j <= i; j++)
		{
			if (p[i - j] == p[lenth - 1 - j]) {
				match++;
			}
			else break;
		}
		ss[i] = match;
	}
	return ss;
}
int* buildGS(int* ss, int lenth)
{
	int* gs = new int[lenth];
	for (int i = 0; i < lenth; i++)
	{
		gs[i] = lenth;
	}
	int pos = 0;
	for (int i = lenth - 1; i >= 0; i--)
	{
		if (ss[i] == i + 1)
		{
			int j = pos;
			for (; j < lenth - i - 1; j++)
			{
				gs[j] = lenth - 1 - i;
			}
			pos = j;
		}
	}
	for (int i = 0; i < lenth; i++)
	{
		gs[lenth - 1 - ss[i]] = lenth - 1 - i;
	}
	delete[]ss;
	return gs;
}
int* buildSSByStr(string s)
{
	int* ss = buildSS(s);
	int* gs = buildGS(ss, s.length());
	return gs;
}
int findp(string p, string str)
{
	int* badc = badchar(p);
	int* gs = buildSSByStr(p);
	int ps = p.length() - 1;
	int pp = ps;
	int strlenth = str.length();
	int plenth = p.length();
	while (ps < strlenth)
	{
		for (; pp >= 0; pp--)
		{
			if (str[ps - (plenth - pp - 1)] != p[pp])
				break;
		}
		if (pp == -1)
		{
			delete[]badc;
			delete[]gs;
			return ps - (plenth - 1);
		}
		char value = str[ps - (plenth - pp - 1)];
		int cshift = badc[value];
		if (cshift > pp)
		{
			cshift = 1;
		}
		else
		{
			cshift = pp - cshift;
		}
		int gsshift = gs[pp];
		ps = ps + max(cshift, gsshift);
		pp = plenth - 1;
	}
	delete[]badc;
	delete[]gs;
	return -1;
}
int main()
{
	string p, str;
	fstream out("test.txt", ios::app);
	cin >> str >> p;
	out << str << endl << p << endl;
	struct timeval sTime, eTime;
	gettimeofday(&sTime, NULL);
	int pos = findp(p, str);
	if (pos > -1) out << pos<<endl;
	else out << "no"<<endl;
	gettimeofday(&eTime, NULL);
	//sleep(2); sleep函数占据的时间不会被clock或者gettimeofday函数所算入
	long exeTime = (eTime.tv_sec - sTime.tv_sec) * 1000000 + (eTime.tv_usec - sTime.tv_usec);
	double extime = static_cast<double> (exeTime);
	out << "gettimeofday  " << extime/1000 << "ms" << endl;

	return 0;
}
