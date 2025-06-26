#include<iostream>
#include<fstream>
using namespace std;
fstream out("test.txt", ios::app);
int* next(string s);
int* next(string s)
{
	int size = s.size();
	int* p = new int[size];
	p[0] = -1;
	int j = 0, k = -1;
	while (j < size - 1)
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
	int sum = 0;
	int* n = next(p);
	int ssize = s.size(), i = 0;
	int psize = p.size(), j = 0;
	while (j < psize && i < ssize)
	{
		sum++;
		if (-1 == j || s[i] == p[j])
		{
			i++; j++;
		}
		else j = n[j];
	}
	out << "kmp compare" << sum;
	delete[]n;
	return i - j;

}
int* badchar(string p)
{
	int lenth = p.length();
	int* badc = new int[126];
	for (int i = 0; i < 126; i++)
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
	for (int i = 0; i < lenth - 1; i++)
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
	int sum = 0;
	while (ps < strlenth)
	{

		for (; pp >= 0; pp--)
		{
			sum++;
			if (str[ps - (plenth - pp - 1)] != p[pp]) break;
		}
		if (pp == -1)
		{
			delete[]badc;
			delete[]gs;
			out << "bm compare:" << sum;
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
	out << "bm compare:" << sum;
	return -1;
}
int main()
{
	string S, P;
	double t = 0;
	cin >> S >> P;
	int pos = findp(P, S);
	out << endl;
	int position = kmp(S, P);
	out << endl;

	return 0;
}
