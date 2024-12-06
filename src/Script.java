import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Script {
    final static String RESET = "\033[0m";  // Text Reset
    final static String RED = "\033[0;31m"; // RED
    final static String GREEN = "\033[0;32m"; // GREEN
    final static String YELLOW = "\033[0;33m"; // YELLOW
    final static String BLUE = "\033[0;34m"; // BLUE
    final static String PURPLE = "\033[0;35m"; // PURPLE
    final static String CYAN = "\033[0;36m"; // CYAN
    final static String WHITE = "\033[0;37m"; // WHITE

    static boolean debugMode = false;
    static String debugWord = "";

    final static String INPUTSENTENCE = GREEN + "input> " + RESET;
    public static void main(String[] args) {
        ArrayList<String> wordList = new ArrayList<>();
        ArrayList<String> propertyList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int size = 0;
        MainLoop:while (true) {
            if (size == 0) {
                System.out.println(CYAN + "输入当前单词长度：" + RESET);
                do {
                    try {
                        System.out.print(INPUTSENTENCE);
                        size = scanner.nextInt();
                        if (size > 0 && size <= 31) {
                            break;
                        }else{
                            System.out.println(RED + "输入错误，请重新输入(输入数字应为1~31)！" + RESET);
                        }
                    }catch (Exception e){
                        System.out.println(RED + "输入错误，请重新输入！" + RESET);
                        scanner.next();
                    }
                }while (true);
            }
            System.out.println(PURPLE + "请输入一个操作：\n1.加入字符串   2.查看当前单词\n3.运算    4.重置  5.结束程序" + RESET);
            if (debugMode){
                System.out.println(YELLOW + "6.添加debug单词");
            }
            boolean flag = false;
            do {
                flag = false;
                int choice;
                do {
                    try {
                        System.out.print(INPUTSENTENCE);
                        choice = scanner.nextInt();
                        break;
                    }catch (Exception e){
                        System.out.println(RED + "输入错误，请重新输入！" + RESET);
                        scanner.next();
                    }
                }while(true);
                switch (choice) {
                    case 1:
                        Loop1:
                        do {
                            System.out.println(CYAN + "请输入一个单词(-1返回)：" + RESET);
                            System.out.print(INPUTSENTENCE);
                            String word = scanner.next();
                            if (word.equals("-1")) {
                                continue MainLoop;
                            }
                            if (word.length() == size) {
                                //判断每一位是否都是字母
                                for (int j = 0; j < word.length(); j++) {
                                    if (!((word.charAt(j) + "").matches("[a-zA-Z]"))) {
                                        System.out.println(RED + "单词格式错误！" + RESET);
                                        continue Loop1;
                                    }
                                }
                                wordList.add(word);
                                break;
                            } else {
                                System.out.println(RED + "单词长度不正确!" + RESET);
                            }
                        } while (true);

                        Loop1:do {
                            System.out.println(CYAN + "请输入该单词的属性：1代表灰色(不匹配)，2代表黄色(位置不对)，3代表完全正确" + RESET);
                            System.out.print(INPUTSENTENCE);
                            String property = scanner.next();
                            if (property.length() == size) {
                                for (int j = 0; j < property.length(); j++) {
                                    if (!(property.charAt(j) == '1' || property.charAt(j) == '2' || property.charAt(j) == '3')) {
                                        System.out.println(RED + "属性格式错误！" + RESET);
                                        continue Loop1;
                                    }
                                }
                                propertyList.add(property);
                                break;
                            } else {
                                System.out.println(RED + "属性长度不正确!" + RESET);
                            }
                        } while (true);

                        break;


                    case 2:
                        for (int i = 0; i < wordList.size(); i++) {
                            //System.out.println(i + 1 + ". " + wordList.get(i) + " " + propertyList.get(i));
                            //System.out.format("前景色是%d,背景色是%d------\33[%d;%d;4m我是博主\33[0m %n", font, background, font, background);

                            System.out.print(PURPLE + (i + 1) + ". " + RESET);
                            for (int j = 0; j < size; j++){
                                if (propertyList.get(i).charAt(j) == '1'){
                                    System.out.print(WHITE + wordList.get(i).charAt(j) + RESET);
                                    System.out.print(" ");
                                }else if (propertyList.get(i).charAt(j) == '2'){
                                    System.out.print(YELLOW + wordList.get(i).charAt(j) + RESET);
                                    System.out.print(" ");
                                }else{
                                    System.out.print(GREEN + wordList.get(i).charAt(j) + RESET);
                                    System.out.print(" ");
                                }
                            }
                            System.out.println();
                        }
                        System.out.println(CYAN + "请输入你要进行的操作:\n1.删除特定行\n2.返回" + RESET);
                        int choice2;
                        do {
                            try {
                                System.out.print(INPUTSENTENCE);
                                choice2 = scanner.nextInt();
                                if (choice2 > 0 && choice2 <= 2) {
                                    break;
                                }else{
                                    System.out.println(RED + "输入错误，请重新输入！" + RESET);
                                }
                            }catch (Exception e){
                                System.out.println(RED + "输入错误，请重新输入！" + RESET);
                                scanner.next();
                            }
                        }while(true);
                        if (choice2 == 1) {
                            int row = 0;
                            boolean remove = true;
                            Outer:do {
                                System.out.println(CYAN + "请输入要删除的行数(-1返回)：" + RESET);
                                do {
                                    try {
                                        System.out.print(INPUTSENTENCE);
                                        row = scanner.nextInt();
                                        break;
                                    }catch (Exception e){
                                        System.out.println(RED + "输入错误，请重新输入！" + RESET);
                                        scanner.next();
                                    }
                                }while(true);
                                if (row > 0 && row <= wordList.size()) {
                                    break;
                                }else if(row == -1){
                                    remove = false;
                                    break Outer;
                                } else {
                                    System.out.println(RED + "行数不存在！" + RESET);
                                }
                            } while (true);
                            if (remove) {
                                wordList.remove(row - 1);
                                propertyList.remove(row - 1);
                                System.out.println(GREEN + "删除成功！" + RESET);
                            }
                        }
                        break;

                    case 3:

                        Read read = new Read();
                        char[] positionchar = new char[size];
                        ArrayList<String> hanyouList = new ArrayList<>();
                        for (int i = 0; i < size; i++){
                            positionchar[i] = '#';
                        }
                        HashSet<Character> set = new HashSet<>();
                        ArrayList<Character> paichuList = new ArrayList<>();
                        for (int i = 0; i < wordList.size(); i++){
                            String word = wordList.get(i);
                            String property = propertyList.get(i);
                            char[] temp = new char[size];
                            for (int ii = 0; ii < size; ii++){
                                temp[ii] = '#';
                            }
                            for (int j = 0; j < word.length(); j++){
                                if (property.charAt(j) == '3') {
                                    positionchar[j] = word.charAt(j);
                                    set.add(word.charAt(j));
                                }else if (property.charAt(j) == '2'){
                                    temp[j] = word.charAt(j);
                                    set.add(word.charAt(j));
                                }
                            }
                            for (int j = 0; j < size; j++){
                                if(property.charAt(j) == '1' && !set.contains(word.charAt(j))){
                                    paichuList.add(word.charAt(j));
                                }
                            }
                            //System.out.println(Arrays.toString(temp));
                            hanyouList.add(new String(temp));
                        }
                        char[] paichuchar = new char[paichuList.size()];
                        for (int i = 0; i < paichuList.size(); i++){
                            paichuchar[i] = paichuList.get(i);
                        }
                        if(debugMode){
                            System.out.println(YELLOW + "debugMode" + RESET);
                            read.debugList.clear();
                            read.debugList.add(debugWord);
                            read.debug = true;
                            read.read(size, paichuchar, hanyouList, positionchar);
                        }else {
                            read.debug = false;
                            read.read(size, paichuchar, hanyouList, positionchar);
                        }
                        break;


                    case 4:
                        wordList.clear();
                        propertyList.clear();
                        size = 0;
                        System.out.println(GREEN + "重置成功！"+ RESET);
                        break;

                    case 5:
                        System.out.println(RED + "程序结束！" + RESET);
                        return;

                    case 6:
                        Loop1:
                        do {
                            System.out.println(YELLOW + "请输入debug的单词(-1返回)：" + RESET);
                            System.out.print(INPUTSENTENCE);
                            String word = scanner.next();
                            if (word.equals("-1")) {
                                continue MainLoop;
                            }
                            if (word.length() == size) {
                                //判断每一位是否都是字母
                                for (int j = 0; j < word.length(); j++) {
                                    if (!((word.charAt(j) + "").matches("[a-zA-Z]"))) {
                                        System.out.println(YELLOW + "单词格式错误！" + RESET);
                                        continue Loop1;
                                    }
                                }
                                debugWord = word;
                                break;
                            } else {
                                System.out.println(YELLOW + "单词长度不正确!" + RESET);
                            }
                        } while (true);
                        break;


                    case 114514:
                        if (!Script.debugMode) {
                            Script.debugMode = true;
                            System.out.println(YELLOW + "Debug模式已开启！" + RESET);
                        }else {
                            Script.debugMode = false;
                            System.out.println(YELLOW + "Debug模式已关闭！" + RESET);
                        }
                        break;
                    default:
                        System.out.println(RED + "输入错误！" + RESET);
                        flag = true;
                }
            }while(flag);
        }
    }
}
