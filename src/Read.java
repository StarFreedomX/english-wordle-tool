import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Read {
    boolean debug = false;
    ArrayList<String> debugList = new ArrayList<>();
    boolean kaiqi = false;
    //字母个数
    int size = 9;
    //存储排除的字母
    char[] paichuchar = "FEGLWVEGHELYU".toCharArray();
    //存储包含的字母
    char[] hanyouchar = "ATONISC".toCharArray();

    //存储包含的位置和字母（进阶）
    ArrayList<String> hanyouList = new ArrayList<>();
    //存储位置确定的字母
    char[] positionchar = "CONS#R###".toCharArray();

    public void read(boolean kaiqi, int size, char[] paichuchar,
                     char[] hanyouchar, ArrayList<String> hanyouList,
                     char[] positionchar) {

        //读取words.txt文件并存储到集合中
        ArrayList<String> wordList = new ArrayList<>();
        if (!debug) {
            File file = new File("words.txt");
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    wordList.add(line);
                }
                br.close();
                fr.close();
            } catch (IOException e) {
                System.out.println("读取文件失败！");
                e.printStackTrace();
            }
        } else {
            wordList = debugList;
        }


        //位置确定的字母
        for (int i = 0; i < wordList.size(); i++) {

            String word = wordList.get(i);
            boolean fuhe = true;
            if (word.length() == size) {
                //遍历word的每个字符
                for (int j = 0; j < word.length(); j++) {
                    //判断是否在位置确定的字母中
                    if ((!(word.charAt(j) + "").equalsIgnoreCase(positionchar[j] + "")) && positionchar[j] != '#') {
                        fuhe = false;
                        break;
                    }
                }
                byte[] bytes = word.getBytes();
                char[] chars = word.toCharArray();
                //排除字符
                for (byte b : bytes) {
                    for (int k = 0; k < paichuchar.length; k++) {
                        if (((char) b + "").equalsIgnoreCase(paichuchar[k] + "")) {
                            fuhe = false;
                            break;
                        }
                    }
                }
                if (!kaiqi) {

                    for (int k = 0; k < hanyouchar.length; k++) {
                        boolean flag = false;
                        for (char c : chars) {
                            if (hanyouchar[k] == '#') continue;
                            if ((c + "").equalsIgnoreCase(hanyouchar[k] + "")) {
                                flag = true;
                            }
                        }
                        if (!flag) {
                            fuhe = false;
                            break;
                        }
                    }
                } else {
                    //遍历携带者包含的字母信息的字符串（取出的是字符串如 ##jk###k# ）
                    for (int k = 0; k < hanyouList.size(); k++) {
                        String hanyouAtK = hanyouList.get(k);//获取携带者包含的字母信息的字符串
                        boolean outerFlag = true;//默认这个单词符合
                        //遍历这个格式字符串包含的字母（取出的是字符 如#）
                        mLoop:
                        for (int m = 0; m < hanyouAtK.length(); m++) {
                            //判断该字符是否为#，如果是，则跳过
                            if (hanyouAtK.charAt(m) == '#') continue;
                            //判断该字母是否在word中被包含，如果没有，则这个单词不符合要求
                            boolean flag = false;//默认不包含
                            //遍历word的每个字符
                            for (char c : chars) {
                                if ((hanyouAtK.charAt(m) + "").equalsIgnoreCase(c + "")) {
                                    flag = true;//一旦有一个符合，则跳出循环，表明该字母被含有
                                    break;
                                }
                            }
                            //判断该字母是否与word的该位置的字母恰好相同，如果是，则这个单词不符合要求
                            if (hanyouAtK.charAt(m) == word.charAt(m)) {
                                outerFlag = false;
                                break;
                            }
                            //如果这个单词对某个字母不符合要求，则跳出循环，表明这个单词不符合要求
                            if (!flag) {
                                outerFlag = false;
                                break;
                            }
                        }
                        //如果这个单词对所有字母都符合要求，表明这个单词对这个格式符合要求
                        //一但存在一个格式不符合，则跳出循环，表明这个单词不符合要求
                        if (!outerFlag) {
                            fuhe = false;
                            break;
                        }
                    }
                }

                if (debug) {
                    System.out.println(Script.YELLOW + "output>" + Script.RESET + word + " " + Script.YELLOW + fuhe + Script.RESET);
                } else {
                    //若到了此处fuhe仍然为true，表明该单词符合要求
                    if (fuhe) {
                        System.out.println(word);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        Read read = new Read();
        read.read(read.kaiqi, read.size, read.paichuchar, read.hanyouchar, read.hanyouList, read.positionchar);
    }

    public void read(int size, char[] paichuchar,
                     char[] hanyouchar, char[] positionchar) {
        read(false, size, paichuchar, hanyouchar, null, positionchar);
    }

    public void read(int size, char[] paichuchar,
                     ArrayList<String> hanyouList,
                     char[] positionchar) {
        read(true, size, paichuchar, null, hanyouList, positionchar);
    }

}
