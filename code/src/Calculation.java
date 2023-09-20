class Calculation {
    int a, b, c, d, m, n;
    static String answer;

    public Calculation(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    //分式约分
    public void reduceFraction(int m, int n) {
        int x = Math.abs(m);
        int y = Math.abs(n);
        if (x < y) {
            int t = x;
            x = y;
            y = t;
        }
        while (y != 0) {
            int k = x % y;
            x = y;
            y = k;
        }
        m = m / x;
        n = n / x;
        if (n == 1) answer = m + "";
        else answer = m + "/" + n;
    }

    // 四则运算
    private void performOperation(int m, int n, char operator) {
        switch (operator) {
            case '+':
                m = a * d + b * c;
                n = b * d;
                break;
            case '-':
                m = a * d - b * c;
                n = b * d;
                break;
            case '*':
                m = a * c;
                n = b * d;
                break;
            case '/':
                m = a * d;
                n = b * c;
                break;
        }
        reduceFraction(m, n);
    }

    // 执行四则运算
    public void operate(String s) {
        for (int i = 0; i < s.length(); i++) {
            char operator = s.charAt(i);
            if (operator == '+' || operator == '-' || operator == '*' || operator == '/') {
                performOperation(a, b, operator);
                break;
            }
        }
    }
}





