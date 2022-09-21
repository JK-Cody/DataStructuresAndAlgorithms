package org.data_structures_and_algorithms.stack;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * 栈类型
 */
public class StackTypeClass {

    /**
     * 計算器,根据字符串运算,不计算小数
     */
    public int calculateExpression(String expression){
//预设数组长度
        int length = expression.length();
        Double d=length*0.6;  //数字最多占字符串大约六成
        int numberRange = d.intValue();
        int operationRange=length-numberRange+1;
        //数字的栈
        ArrayTypeStackClass numberArray=new ArrayTypeStackClass(numberRange);
        //运算符合的栈
        ArrayTypeStackClass operationArray=new ArrayTypeStackClass(operationRange);
        //连接数字的字符
        StringBuilder builder = new StringBuilder();
        //连接小括号内的数字和字符
        StringBuilder smallBracketsBuilder = new StringBuilder();
        int result=0;  //保存每一轮的计算值
        int Oper=0 ;
        int firstNum=0;
        int secondNum=0;
        int calculation=0;
        char index=' ';    // char的默认值 '\u0000';
        int subLength=0;
        String str="";
/* 方式一 */
        for (int i = 0; i <expression.length(); i++) {
            index= expression.charAt(i);
//收集数字
            if (Character.isDigit(index)) {
                //连接字符
                builder.append(index);
                //当遍历到最后一个数字时，continue导致没有下一轮的判断，所以补充
                if(i==expression.length()-1){
                    int number = Integer.parseInt(builder.toString());
//                    numberArray.push(number);
                    //对上一轮的运算符号的判断优先级
                    if (operationArray.judgeOperationPriority(operationArray.checkTop()) == 1) {
                        Oper = operationArray.pop();
                        firstNum = numberArray.pop();
                        calculation = operationArray.calculation(number, firstNum, Oper);
                        //结果入栈
                        numberArray.push(calculation);
                    }else {
                        //结果入栈
                        numberArray.push(number);
                    }
                    builder.delete(0,builder.length());
                    break;
                }
                continue;
            }else if(operationArray.isOperation(index)){
                //避免小括号后面的运算符号引起格式错误
                if(builder.length()!=0) {
                    //还原为数字
                    int number = Integer.parseInt(builder.toString());
                    numberArray.push(number);
                    //清空字符串
                    builder.delete(0, builder.length());
                }
            }
//计算小括号的内容
            else{
                while(true){
                    i++;
                    index = expression.charAt(i);
                    if(operationArray.isSmallBrackets(index)) {
                        break;
                    }
                    smallBracketsBuilder.append(index);
                }
                int smallBracketsResult = new StackTypeClass().calculateExpression(smallBracketsBuilder.toString());
                numberArray.push(smallBracketsResult);
                smallBracketsBuilder.delete(0,builder.length());
            }
//对上一轮的运算符号的判断优先级,如果是乘除则直接运算并入栈
            if(!operationArray.ifEmpty()) {
                if (operationArray.judgeOperationPriority(operationArray.checkTop()) == 1) {
                    //计算最近入栈的数字
                    Oper = operationArray.pop();
                    firstNum = numberArray.pop();
                    secondNum = numberArray.pop();
                    //乘除按顺序排列，所以 secondNum / firstNum
                    calculation = operationArray.calculation(firstNum, secondNum, Oper);
                    //结果入栈
                    numberArray.push(calculation);
                }
            }
//收集运算符号
            if(index=='+'||index=='-'||index=='*'||index=='/') {
                operationArray.push(index);
            }
        }
//计算剩余的加减
        while (!numberArray.ifEmpty()) {
            firstNum = numberArray.pop();
            if(operationArray.ifEmpty()){
                result=result+firstNum;
                break;
            }
            Oper = operationArray.pop();
            calculation = operationArray.calculation(firstNum, result, Oper);
            result = calculation;
        }
        return result;
    }

    /**
     * 逆波兰表达式计算器
     * 使用Stack类
     */
    public int calculatePolandNatationExpression(String expression){
        //把字符串分隔开
        String[] split = expression.split(" ");
        //新列表保存
        List<String> saveList = new ArrayList<String>();
        for (String str:split){
            saveList.add(str);
        }
        Stack<String> stack = new Stack<String>();
        //获取字符串内容
        for(String item:saveList){
            //使用正则表达式取出数
            if (item.matches("\\d+")){ //匹配多位数
                stack.push(item);
            }else{
                //pop出两个数并且运算
                int number2 = Integer.parseInt(stack.pop());
                int number1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")){
                    res = number1+number2;
                }else if(item.equals("-")){
                    res = number1-number2;
                }else if(item.equals("*")){
                    res = number1*number2;
                }else if(item.equals("/")){
                    res = number1/number2;
                }else{
                    throw new RuntimeException("运算符有误");
                }
                //再入栈
                stack.push(String.valueOf(res));
            }
        }
        return Integer.parseInt(stack.pop());
    }

    /**
     * 中缀表达式转换为后缀表达式
     * https://blog.csdn.net/qq_43167873/article/details/121134905
     */
    public  List<String> parseSuffixIntoPolandNatation(String expression){
//截取字符串
        List<String> list = new ArrayList<String>();
        int i = 0;
        String str;
        char c;
        do{
            //非数字时
            if ((c = expression.charAt(i)) <48 || (c = expression.charAt(i)) >57){ //字符区间
                list.add(String.valueOf(c));
                i++;
            //数字时
            }else{
                str = ""; //清空
                //连接字符
                while(i<expression.length() && (c = expression.charAt(i)) >= 48 && (c = expression.charAt(i)) <= 57){
                    str += c;
                    i++;
                }
                list.add(str);
            }
        }while (i < expression.length());
//中缀表达式转换为后缀表达式的list
        Stack<String> stackOne = new Stack<String>();
        //第二个栈没有出栈，只有进栈，用列表替代
        List<String> anotherList = new ArrayList<String>();
        //使用正则表达式取出数
        for (String item:list){
            if (item.matches("\\d+")){  //匹配多位数
                anotherList.add(item);
        //处理表达式的小括号
            }else if(item.equals("(")){
                stackOne.push(item);
            }else if(item.equals(")")){
                while(!stackOne.peek().equals("(")){
                    anotherList.add(stackOne.pop());
                }
                stackOne.pop();
            }else{
                //添加运算符，根据运算符号返回代号比较优先级,
                while (stackOne.size()!= 0 && Operation.getValue(stackOne.peek()) >= Operation.getValue(item)){
                    anotherList.add(stackOne.pop());
                }
                stackOne.push(item);
            }
        }
        //将栈1剩余的运算符添加到栈2
        while(stackOne.size()!= 0){
            anotherList.add(stackOne.pop());
        }
        return anotherList;
    }

    public static void main(String[] args) {

        StackTypeClass stackTypeClass = new StackTypeClass();

        System.out.println("***********  test 1 **********");
        int result = stackTypeClass.calculateExpression("7+2/2-5+0-5*13*(12/3+5)*4/2-4-7*5");
        System.out.println(result);

        System.out.println("***********  test 2 **********");
        int r = stackTypeClass.calculatePolandNatationExpression("4 5 * 8 - 60 + 8 2 / +");  //4*5-8+60+8/2
        System.out.println(r);

        System.out.println("***********  test 3 **********");
        List<String> list = stackTypeClass.parseSuffixIntoPolandNatation("4*5-8+60+8/2");
        System.out.println("中缀表达式转换为后缀表达式："+list);
    }
}

/**
 * 数组模拟栈
 */
class ArrayTypeStackClass {

    private int maxsize;
    private int[] array;
    private int top = -1; //首值的前一个值

    public ArrayTypeStackClass(int maxsize) {

        this.maxsize = maxsize;
        array = new int[this.maxsize];
    }

    public boolean isFull() {

        return top == this.maxsize - 1;
    }

    public boolean ifEmpty() {

        return top == -1;
    }

    public void push(int value) {

        if (this.isFull()) {
            System.out.println("stack is full");
            return;
        }
        top++;
        this.array[top] = value;
    }

    public int pop() {

        if (this.ifEmpty()) {
            System.out.println("stack is empty");
            return -1;
        }
        return this.array[top--];
    }

    public int checkTop(){
        return this.array[top];
    }

    public void showData() {

        if (this.ifEmpty()) {
            System.out.println("stack is empty");
            return;
        }
        for (int i = 0; i <= top; i++) {
            System.out.printf("array[%d]=[%d]\n", i, this.array[i]);
        }
    }

    /**
     * 是否为运算符
     */
    public boolean isOperation(char oper){
        return oper == '+'||oper == '-'||oper == '*'||oper == '/';
    }

    /**
     * 是否为小括号
     */
    public boolean isSmallBrackets(char oper){
        return oper == '(' || oper == ')';
    }

    /**
     * 判断运算符合的优先级，乘除优先加减
     */
    public int judgeOperationPriority(int operation) {

        if (operation == '*' || operation == '/') {
            return 1;
        } else if (operation == '+' || operation == '-') {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 计算两个数字的结果
     */
    public int calculation(int num1,int num2,int operation){

        int result = 0;
        switch (operation){
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num2-num1;  //原来的形式
                break;
            case '*':
                result = num1*num2;
                break;
            //忽略精度缺少
            case '/':
                result = num2/num1;
                break;
            default:
                break;
        }
        return result;
    }

    public static void main(String[] args) {

        ArrayTypeStackClass arrayTypeStackClass = new ArrayTypeStackClass(4);
        boolean open = true;
        while (open) {
            System.out.println("please select a choice");
            System.out.println("***********************");
            System.out.println("u:push");
            System.out.println("o:pop");
            System.out.println("s:showData");
            System.out.println("e:exit");
            Scanner scanner = new Scanner(System.in);
            char next = scanner.next().charAt(0);
            switch (next) {
                case 'u':
                    System.out.println("please set a number");
                    int i = scanner.nextInt();
                    arrayTypeStackClass.push(i);
                    break;
                case 'o':
                    arrayTypeStackClass.pop();
                    break;
                case 's':
                    arrayTypeStackClass.showData();
                    break;
                case 'e':
                    scanner.close();
                    open = false;
                    break;
                default:
                    break;
            }
            System.out.println("\n" + "log out");
        }
    }
}

/**
 * 单链表模拟栈
 */
class SimpleLinkedListTypeStack{

    private int maxSize = 0;     //栈大小
    private int top = -1;
    Node head = new Node();  //头节点

    public SimpleLinkedListTypeStack(int maxSize){

        this.maxSize=maxSize;
    }

    public boolean isFull(){

        return top == maxSize-1;
    }

    public boolean isEmpty(){

        return top == -1;
    }

    public void push(int value){

        Node temp = head;
        if (isFull()){
            return;
        }
        while (temp.getNext()!=null){
            temp = temp.getNext();
        }
        Node res = new Node(value);
        temp.setNext(res);
        top ++;
    }

    public int pop(){

        Node temp = head;
        if (isEmpty()){
            return -1;
        }
        top --;
        temp = temp.getNext();
        int res  = temp.getData();
        System.out.println("出栈的数为" + res);
        return res;
    }

    public void showData(){

        Node temp = head.getNext();
        if (isEmpty()){
            return;
        }
        while (temp != null){
            System.out.print(temp.getData() + " ");
            temp = temp.getNext();
        }
        System.out.println();
    }
}

@Data
class Node{
    private int data;
    private Node next;

    public Node() {
    }

    public Node(int data) {
        this.data = data;
    }

    public Node(Node next) {
        this.next = next;
    }

}

/**
 * 根据运算符号返回优先级
 */
class Operation{
    //运算符
    private  static  int add = 1;  //优先级
    private  static  int sub = 1;
    private  static  int mul = 2;
    private  static  int div = 2;

    public static int  getValue(String operation){
        int result= 0;
        switch (operation){
            case "+":
                result = add;
                break;
            case "-":
                result = sub;
                break;
            case "*":
                result = mul;
                break;
            case "/":
                result = div;
                break;
            default:
                break;
        }
        return result;
    }
}