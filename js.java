import java.io.*;
import java.util.*;
public class js
{
int i;
int top;
js()
{
i=0;
top=-1;
}
public void push(char st[],char e)
{
st[top+1]=e;
}


public char pop(char st[])
{
return st[top];
}

public void val(char st[],String fn)
{
char ch=fn.charAt(i);
int k=0;
if(ch=='"')
{
k=1;
}
while((ch!=','||ch!='}'||ch!=']')&&(i<fn.length()))
{
ch=fn.charAt(i);
i++;
if(ch=='{')
{
this.push(st,ch);
i++;
this.obj(st,fn);
}
if(ch=='[')
{
this.push(st,ch);
top++;
this.arr(st,fn);
}
if(ch=='"'&&k==1)
{
return;
}
}
}

public int nv(char st[],String fn)
{
char ch=fn.charAt(i);
int k=0;
while(k==0&&(i<fn.length()))
{
ch=fn.charAt(i);
i++;
if(ch=='"')
{
if(i>=2&&fn.charAt(i-2)==92)
{
continue;
}
else if(st[top]=='"')
{
this.pop(st);
top--;
}
}
if(ch==','||ch=='}'||ch==':')
{
System.out.println("Not a valid json file");
return 1;
}
}
return 0;
}
int obj(char st[],String fn)
{
char ch=fn.charAt(i);
int k=0;
while(k==0)
{
i++;
if(ch==' ')
{
continue;
}
else if(ch=='"')
{
this.push(st,ch);
top++;
this.nv(st,fn);
}
else if(ch==':')
{
this.val(st,fn);
}
else if((fn.charAt(i))=='}')
{
k=1;
break;
}
else if(fn.length()==i-1)
{
k=2;
}
else
{
k=2;
break;
}
ch=fn.charAt(i);
}
if(k==2)
{
System.out.println("Not a valid json file");
return 1;
}
this.pop(st);
top--;
return 0;
}
int arr(char st[],String fn)
{
char ch=fn.charAt(i);
int k=0;
while(k==0)
{
i++;
if(ch==' ')
{
continue;
}
if(ch=='"')
{
this.val(st,fn);
}
if(ch=='[')
{
this.push(st,ch);
top++;
k=this.arr(st,fn);
}
if(ch=='{')
{
this.push(st,ch);
top++;
k=this.obj(st,fn);
}
if(ch==']')
{
k=1;
break;
}
if(i==(fn.length()-1))
{
k=1;
break;
}
ch=fn.charAt(i);
}
if(ch!=']')
{
System.out.println("Not a valid json file");
return 1;
}
this.pop(st);
top--;
return 0;
}

public static void main(String[] args)throws IOException
{
js ob=new js();
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
String fn;
System.out.println("Enter code");
fn=br.readLine();
char st[]=new char[10000];
int i=0;
int top=-1;
char ch=' ';
int flag=0;
while((ob.i<fn.length())&&flag==0)
{
ch=fn.charAt(ob.i);
ob.i=ob.i+1;
if(ch=='{')
{
if(ob.top==-1&&ch!='{')
{
flag=1;
System.out.println("Not a valid json file");
break;
}
else
{
ob.push(st,ch);
ob.top++;
}
}
if(ch=='}')
{
if(ch=='}'&&st[ob.top]!='{')
{
flag=1;
System.out.println("Not a valid json file");
break;
}
else if(ch=='}')
{
char cv=ob.pop(st);
ob.top--;
}
}
if(ch=='"')
{
char cv=' ';
if(ob.i>2)
{
 cv=fn.charAt((ob.i)-2);
}
if(cv==92)
{
continue;
}
else if(st[ob.top]=='"')
{
ob.pop(st);
ob.top--;
}
else
{
ob.push(st,cv);
ob.top++;
flag=ob.nv(st,fn);
ob.top--;
}
}
if(ch==':')
{
ob.val(st,fn);
}
if(ch=='[')
{
ob.push(st,ch);
ob.top++;
flag=ob.arr(st,fn);
}
if(ch==',')
{
char cv=fn.charAt(ob.i);int k=0;
while(k==0)
{
ob.i++;
if(cv=='"')
{
k=1;
ob.nv(st,fn);
}
else if(cv==' ')
{
continue;
}
else
{
System.out.println("Not a valid json file");
flag=1;
}
cv=fn.charAt(ob.i);
}
}
}
if(ob.top>=0)
{
flag=1;
}
if(flag ==0)
{
System.out.println("Valid json");
}
else
{
System.out.println("Not a valid json");
}
}
}