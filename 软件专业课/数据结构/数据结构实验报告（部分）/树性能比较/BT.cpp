#include<iostream>
#include<fstream>
#include<sstream>
#include<vector>
#include<Windows.h>
#include<random>
using namespace std;

/**抽象类型函数
void init(t);                                       //初始化B树
void searchBTree(BTree t,int k,result &r);          //在B树中查找关键字k，r.tag==1为找到
                                                    //r.tag==0为找不到
void search(BTree p,int k);                         //在结点p中找关键字k的位置
void newBTreeroot(BTree &t,BTree p,int k,BTree q);  //新建根节点t
void insertBTree(BTree &t,int k);                   //在B中加入关键字k
void insert(BTree p,int k,BTree ap,int i);          //在p结点中插入关键字k
void spilt(BTree p,BTree ap,int s);                 //将结点以位置s为中心拆成两部分
Remove(BTree p,int k);                              //在结点p中删除关键字k
void findNode(BTree p,int k,int &i);                //在结点p中寻找k的位置
void deleteBTree(BTree t,int k);                    //在B树中删除关键字k
int deleteNode(BTree p,int k);                      //在B树删除操作的总接口
void substitution(BTree p,int i);                   //向右子树借最小值
void adjustBTree(BTree p,int i);                    //结点长度小于Min进行调整
void leftMove(BTree p,int i);                       //向右结点借值
void rightMove(BTree p,int i);                      //向左结点借值
void combine(BTree p,int i);                        //左右不够借，合并三个元素
void DestoryBTree(BTree &t);                        //销毁B树
void printBTree(BTree t);                           //用队列输出层序遍历的B树
**/

/**B树的抽象实现**/
const int m = 512;              //B树的阶树
const int Max = m - 1;          //最大的关键字个数
const int Min = (m - 1) / 2;      //最小的关键字个数

typedef struct rt {          //定义结点信息
    int key[m + 1];           //结点关键字数组，key[0]不用
    int keynum;             //结点关键字个数
    struct rt* parent;      //双亲结点指针
    struct rt* ptr[m + 1];    //孩子结点指针
}BNT, * BTree;

typedef struct op {          //查找结果
    int i;                  //结点的关键字位置或需插入位置
    int tag;                //成功与否标志
    BTree pt;               //找到的结点
}result;

typedef struct qp {          //模拟队列
    BTree pt;               //指向B树结点（数据域）
    struct qp* next;        //下一个队列指针（指针域）
}queueBNT, * queueBTree;

int search(BTree p, int k) { //在子树p中寻找结点k
    int i = 1;
    while (i <= p->keynum && p->key[i] < k)i++;
    return i;
}

void init(BTree& t) {         //初始化B树
    t == NULL;
}

void searchBTree(BTree t, int k, result& r) { //在B树t中查找结点
    int finshed = 0, i = 0;
    BTree p = t, q = NULL;
    while (p != NULL && finshed == 0) {
        i = search(p, k);
        if (i <= p->keynum && p->key[i] == k)finshed = 1;
        else {
            q = p;
            p = p->ptr[i - 1];
        }
    }
    if (finshed == 1) {                         //找到结点
        r.tag = 1; r.i = i; r.pt = q;
    }
    else {                                   //找不到结点
        r.tag = 0; r.i = i; r.pt = q;
    }
}

void newBTreeroot(BTree& t, BTree p, int k, BTree aq) {
    t = (BTree)malloc(sizeof(BNT));
    t->key[1] = k; t->keynum = 1;
    t->ptr[0] = p; t->ptr[1] = aq;
    if (p != NULL)p->parent = t;
    if (aq != NULL)aq->parent = t;
    t->parent = NULL;
}

void insert(BTree p, int k, BTree& ap, int i) {
    int j;
    for (j = p->keynum; j >= i; j--) {
        p->key[j + 1] = p->key[j];
        p->ptr[j + 1] = p->ptr[j];
    }
    p->key[i] = k; p->ptr[i] = ap;
    if (ap != NULL)ap->parent = p;
    p->keynum++;
}

void spilt(BTree p, BTree& ap, int s) {
    int i, j;
    ap = (BTree)malloc(sizeof(BNT));
    ap->ptr[0] = p->ptr[s];
    for (j = s + 1, i = 1; j <= p->keynum; i++, j++) {
        ap->key[i] = p->key[j];
        ap->ptr[i] = p->ptr[j];
    }
    ap->keynum = p->keynum - s;
    ap->parent = p->parent;
    for (i = 0; i <= ap->keynum; i++) {
        if (ap->ptr[i] != NULL)ap->ptr[i]->parent = ap;
    }
    p->keynum = s - 1;
}

void insertBTree(BTree& t, int k) {//在B树中添加k关键字
    result r;
    searchBTree(t, k, r);
    if (r.tag == 1)return;//关键字已经存在
    BTree p = r.pt; int i = r.i;
    BTree ap; int x, s, finshed, needNewroot;
    x = k; ap = NULL; finshed = needNewroot = 0;
    if (p == NULL) newBTreeroot(t, NULL, k, NULL);
    else {
        while (finshed == 0 && needNewroot == 0) {
            insert(p, x, ap, i);
            if (p->keynum <= Max)finshed = 1;
            else {
                s = (m + 1) >> 1; spilt(p, ap, s);
                x = p->key[s];
                if (p->parent != NULL) {
                    p = p->parent;
                    i = search(p, x);
                }
                else needNewroot = 1;
            }
        }
        if (needNewroot == 1) newBTreeroot(t, p, x, ap);
    }
}

void findNode(BTree p, int k, int& i) {
    i = p->keynum;
    while (i > 0 && p->key[i] > k)i--;
}

void substitution(BTree p, int i) {
    BTree ap = p->ptr[i];
    while (ap->ptr[0]) {
        ap = ap->ptr[0];
    }
    p->key[i] = ap->key[1];
}

void remove(BTree p, int i) {
    int j;
    p->keynum--;
    for (j = i; j <= p->keynum; j++) {
        p->key[j] = p->key[j + 1];
        p->ptr[j] = p->ptr[j + 1];
    }
}

void leftMove(BTree p, int i) {
    int j;
    BTree q = p->ptr[i - 1], ap = p->ptr[i];
    q->keynum++;
    q->key[q->keynum] = p->key[i];
    q->ptr[q->keynum] = ap->ptr[0];
    if (ap->ptr[0] != NULL)ap->ptr[0]->parent = p->ptr[i - 1];

    p->key[i] = ap->key[1];
    ap->ptr[0] = ap->ptr[1];
    ap->keynum--;
    for (j = 1; j <= ap->keynum; j++) {
        ap->key[j] = ap->key[j + 1];
        ap->ptr[j] = ap->ptr[j + 1];
    }
}

void rightMove(BTree p, int i) {
    int j;
    BTree q = p->ptr[i - 1], ap = p->ptr[i];
    ap->keynum++;
    for (j = ap->keynum; j > 1; j--) {
        ap->key[j] = ap->key[j - 1];
        ap->ptr[j] = ap->ptr[j - 1];
    }
    ap->key[1] = p->key[i]; ap->ptr[1] = ap->ptr[0];
    ap->ptr[0] = q->ptr[q->keynum];
    if (q->ptr[q->keynum] != NULL)q->ptr[q->keynum]->parent = p->ptr[i];

    p->key[i] = q->key[q->keynum];
    q->keynum--;
}

void combine(BTree p, int i) {
    int j, k;
    BTree q = p->ptr[i - 1], ap = p->ptr[i];
    j = q->keynum;
    q->key[++j] = p->key[i]; q->ptr[j] = ap->ptr[0];
    for (k = 1; k <= ap->keynum; k++) {
        q->key[++j] = ap->key[k];
        q->ptr[j] = ap->ptr[k];
    }
    for (k = q->keynum + 1; k <= j; k++) {
        if (q->ptr[k] != NULL)q->ptr[k]->parent = p->ptr[i - 1];
    }
    q->keynum = j;

    p->keynum--;
    for (j = i; j <= p->keynum; j++) {
        p->key[j] = p->key[j + 1];
        p->ptr[j] = p->ptr[j + 1];
    }
    free(ap);
}

void adjustBTree(BTree p, int i) {
    if (i == 0) {
        if (p->ptr[1]->keynum > Min)leftMove(p, 1);
        else combine(p, 1);
    }
    else if (i == p->keynum) {
        if (p->ptr[i - 1]->keynum > Min)rightMove(p, i);
        else combine(p, i);
    }
    else {
        if (p->ptr[i - 1]->keynum > Min)rightMove(p, i);
        else if (p->ptr[i + 1]->keynum > Min)leftMove(p, i + 1);
        else combine(p, i);
    }
}

int deleteNode(BTree p, int k) {
    if (p == NULL)return 0;
    int i, flag = 0;
    findNode(p, k, i);
    if (i > 0 && p->key[i] == k) {
        flag = 1;
        if (p->ptr[i - 1] != NULL) {
            substitution(p, i);
            deleteNode(p->ptr[i], p->key[i]);
        }
        else {
            remove(p, i);
        }
    }
    else flag = deleteNode(p->ptr[i], k);
    if (flag) {
        if (p->ptr[i] != NULL) {
            if (p->ptr[i]->keynum < Min) {
                adjustBTree(p, i);
            }
        }
    }
    return flag;
}

int deleteBTree(BTree& t, int k) {//删除操作
    int flag;
    BTree p;
    flag = deleteNode(t, k);
    if (flag == 0)return 0;//该关键字不在t中
    if (t->keynum == 0) {
        p = t; t = t->ptr[0]; 
        if (t == NULL) return 1;
        t->parent = NULL;
    }
    return 1;
}

void DestoryBTree(BTree& t) {
    if (t == NULL)return;
    int i;
    BTree p;
    queueBTree pl, pr, ap, q;
    pl = (queueBTree)malloc(sizeof(queueBNT));
    pr = (queueBTree)malloc(sizeof(queueBNT));
    pl->pt = t; pl->next = NULL; pr = pl;
    while (pl) {
        p = pl->pt;
        for (i = 0; i <= p->keynum; i++) {
            if (p->ptr[i]) {
                ap = (queueBTree)malloc(sizeof(queueBNT));
                ap->pt = p->ptr[i]; ap->next = NULL;

                pr->next = ap; pr = ap;
            }
        }
        q = pl;
        pl = pl->next;
        free(q->pt);
        free(q);
        q = NULL;
    }
    t = NULL;
}
void printBTree(BTree t) {
    if (t == NULL) {
        printf("\n");
        printf("BTree is empty.");
        printf("\n");
    }
    else {
        int i, j;
        BTree p;
        queueBTree root, pl, pr, ap, q, dp;
        pl = (queueBTree)malloc(sizeof(queueBNT));
        root = pl;
        pl->pt = t; pl->next = NULL; pr = pl;

        p = pl->pt;
        printf("[ ");
        for (i = 1; i <= t->keynum; i++) {
            printf("%d ", p->key[i]);
        }
        printf("]\n");
        dp = pl;
        int flag = 1;
        while (pl) {
            p = pl->pt;
            if (flag && p->ptr[0] == NULL && p != t) {
                while (dp != NULL && dp->pt != p) {
                    BTree tmp = dp->pt;
                    for (i = 0; i <= tmp->keynum; i++) {
                        printf("[ ");
                        for (j = 1; j <= tmp->ptr[i]->keynum; j++) {
                            printf("%d ", tmp->ptr[i]->key[j]);
                        }
                        printf("] ");
                    }
                    dp = dp->next;
                }
                printf("\n");
                flag = 0;
            }
            else if (p == dp->pt->ptr[0]) {
                while (dp->pt != p) {
                    BTree tmp = dp->pt;
                    for (i = 0; i <= tmp->keynum; i++) {
                        printf("[ ");
                        for (j = 1; j <= tmp->ptr[i]->keynum; j++) {
                            printf("%d ", tmp->ptr[i]->key[j]);
                        }
                        printf("] ");
                    }
                    dp = dp->next;
                }
                printf("\n");
            }
            for (i = 0; i <= p->keynum; i++) {
                if (p->ptr[i]) {
                    ap = (queueBTree)malloc(sizeof(queueBNT));
                    ap->pt = p->ptr[i]; ap->next = NULL;

                    pr->next = ap; pr = ap;
                }
            }
            pl = pl->next;
        }
        while (root != NULL) {
            q = root;
            root = root->next;
            free(q);
        }
    }
}
int main() {
    LARGE_INTEGER nFreq;
    LARGE_INTEGER nBeginTime;
    LARGE_INTEGER nEndTime;
    fstream in("test.txt", ios::in);
    fstream o("BT.txt", ios::app);
    std::mt19937 generator(0);
    string input;
    vector<int> inp;
    while (getline(in, input))
    {
        stringstream ss(input);
        int x;
        while (ss >> x)
        {
            inp.push_back(x);
        }
    }
    BTree t = NULL;
    int size = inp.size();
    std::uniform_int_distribution<int> distribution(0, size - 1);
    vector<int> find;
    for (int i = 0; i < 1000; i++)
    {
        find.push_back(distribution(generator));
    }
    o << size << " insert:" << endl;
    QueryPerformanceFrequency(&nFreq);
    QueryPerformanceCounter(&nBeginTime);
    for (int i = 0; i < size; i++)
    {
        insertBTree(t, inp[i]);
    }
    QueryPerformanceCounter(&nEndTime);
    double ti;
    ti = (double)(nEndTime.QuadPart - nBeginTime.QuadPart) / (double)(nFreq.QuadPart);
    //计算程序执行时间单位为秒数  
    o << "gettimeofday  " << ti * 1000 << "ms" << endl;
    o << "search:" << endl;
    QueryPerformanceFrequency(&nFreq);
    QueryPerformanceCounter(&nBeginTime);
    for (int i = 0; i < 1000; i++)
    {
        search(t, inp[find[i]]);
    }
    QueryPerformanceCounter(&nEndTime);
    ti = (double)(nEndTime.QuadPart - nBeginTime.QuadPart) / (double)(nFreq.QuadPart);
    o << "gettimeofday  " << ti * 1000 << "ms" << endl;
    o << "delete:" << endl;
    QueryPerformanceFrequency(&nFreq);
    QueryPerformanceCounter(&nBeginTime);
    for (int i = 0; i < size; i++)
    {
        deleteBTree(t, inp[i]);
    }
    QueryPerformanceCounter(&nEndTime);
    ti = (double)(nEndTime.QuadPart - nBeginTime.QuadPart) / (double)(nFreq.QuadPart);
    o << "gettimeofday  " << ti * 1000 << "ms" << endl;
    return 0;
}
