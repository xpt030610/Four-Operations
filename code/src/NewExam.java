import java.io.IOException;
import java.util.*;
public class NewExam {
    // 生成题目的个数
    private static int NUM_OF_QUESTIONS = 50;

    // 题目中数值（自然数、真分数和真分数分母）的范围
    private static int MAX_NUMBER = 100;

    // 可以出现的最小数值
    private static final int MIN_NUMBER = 1;

    // 操作符数量
    private static final int OPERATORS_COUNT = 4;

    // 可用的操作符
    private static final char[] OPERATORS = {'+', '-', '*', '/'};

    private static final Random random = new Random();
    private static final StringBuffer answer = new StringBuffer();
    private static final StringBuffer test = new StringBuffer();

    public static void main(String[] args) throws IOException {
        String check = args[0];
        //生成题目与答案
        if (check.equals("NEW")) {
            //改最大题目数量
            NUM_OF_QUESTIONS = Integer.parseInt(args[1]);
            //改最大数据
            MAX_NUMBER = Integer.parseInt(args[2]);
            List<String> intQuestionList = newExam();
            List<String> fractionQuestionList = newFraExam();
            FileIO.writeFile("Exercises.txt", test.toString());
            FileIO.writeFile("Answers.txt", answer.toString());
        }
        //程序支持对给定的题目文件和答案文件，判定答案中的对错并进行数量统计
        if (check.equals("JUDGE")) {
            String[] exercise = FileIO.readFile("MyAnswer.txt").split("\n");
            String[] answer = FileIO.readFile("Answers.txt").split("\n");
            check(exercise, answer);
            String content = "正确题目：" + correctList.toString() + "\n"
                    + "错误题目：" + wrongList.toString();
            FileIO.writeFile("Grade.txt", content);
        }
    }


    static List<String> correctList = new ArrayList<>();
    static List<String> wrongList = new ArrayList<>();

    //检查并输出Grade.txt
    private static void check(String[] exercise, String[] answer) {

        for (int i = 0; i < exercise.length; i++) {
            if (Objects.equals(exercise[i], answer[i])) {
                correctList.add((i + 1) + "");
            } else {
                wrongList.add((i + 1) + "");
            }

        }
    }


    //获取到整数的answer
    public static String intAnswer(char operator, int a, int b) {
        int answer = 0;
        switch (operator) {
            case '+':
                answer = a + b;
                break;
            case '-':
                answer = a - b;
                break;
            case '*':
                answer = a * b;
                break;
            case '/':
                answer = a / b;
                break;
        }
        return answer + "";
    }


    public static List<String> newExam() {
        List<String> questions = new ArrayList<>();
        int count = 0; // 生成的题目数量
        while (count < NUM_OF_QUESTIONS) {
            int a = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
            int b = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
            char operator = OPERATORS[random.nextInt(OPERATORS_COUNT)];
            if (operator == '/' && (b == 0 || a % b != 0)) {
                continue;
            }
            if (operator == '-' && (a < b)) {
                continue;
            }
            String question = "(" + (count + 1) + "):" + a + " " + operator + " " + b + " = ";
            boolean isDuplicate = false;
            for (String q : questions) {
                if (isEquivalentQuestion(question, q)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                questions.add(question);
                if (count < 9) {
                    answer.append("0");
                    test.append("0");
                }
                answer.append((count + 1) + ".  " + intAnswer(operator, a, b) + "\n");
                test.append((count + 1) + ".  " + a + " " + operator + " " + b + " = " + "\n");
            }
            count++;
        }
        return questions;
    }

    public static List<String> newFraExam() {
        List<String> questions = new ArrayList<>();
        StringBuilder answer = new StringBuilder();
        StringBuilder test = new StringBuilder();
        Random random = new Random();
        for (int count = 0; count < NUM_OF_QUESTIONS; count++) {
            int a = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
            int b = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER; // 随机分数a/b
            int c = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
            int d = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER; // c/d
            char operator = OPERATORS[random.nextInt(OPERATORS_COUNT)];
            if (operator == '/' && (b == 0 || a % b != 0)) {
                count--;
                continue;
            }
            Calculation calculation = new Calculation(a, b, c, d);
            calculation.operate(a + "/" + b + operator + c + "/" + d);
            String question = String.format("%02d. %d/%d %c %d/%d = %s", count + 1, a, b, operator, c, d, Calculation.answer);
            questions.add(question);
            answer.append(String.format("%02d.  %s\n", count + 1 + NUM_OF_QUESTIONS, Calculation.answer));
            test.append(String.format("%02d.  %d/%d %c %d/%d = \n", count + 1 + NUM_OF_QUESTIONS, a, b, operator, c, d));
        }
        return questions;
    }

    public static boolean isEquivalentQuestion(String question1, String question2) {
        String[] tokens1 = question1.split(" ");
        String[] tokens2 = question2.split(" ");
        if (tokens1.length != tokens2.length) {
            return false;
        }
        int numPlus1 = 0, numPlus2 = 0, numTimes1 = 0, numTimes2 = 0;
        List<Integer> plusPositions1 = new ArrayList<>();
        List<Integer> plusPositions2 = new ArrayList<>();
        List<Integer> timesPositions1 = new ArrayList<>();
        List<Integer> timesPositions2 = new ArrayList<>();
        for (int i = 1; i < tokens1.length - 1; i += 2) {
            if (tokens1[i].equals("+")) {
                numPlus1++;
                plusPositions1.add(i);
            } else if (tokens1[i].equals("*")) {
                numTimes1++;
                timesPositions1.add(i);
            }
            if (tokens2[i].equals("+")) {
                numPlus2++;
                plusPositions2.add(i);
            } else if (tokens2[i].equals("*")) {
                numTimes2++;
                timesPositions2.add(i);
            }
        }
        if (numPlus1 != numPlus2 || numTimes1 != numTimes2) {
            return false;
        }
        if (!plusPositions1.equals(plusPositions2) || !timesPositions1.equals(timesPositions2)) {
            return false;
        }
        for (int i = 0; i < tokens1.length; i += 2) {
            if (!tokens1[i].equals(tokens2[i])) {
                return false;
            }
        }
        return true;
    }
}



