import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int queries = Integer.parseInt(in.readLine());

        StringTokenizer tk;

        for (int i = 0; i < queries; i++) {
            tk = new StringTokenizer(in.readLine());
            long n = Long.parseLong(tk.nextToken());
            long a = Long.parseLong(tk.nextToken());
            long b = Long.parseLong(tk.nextToken());

            ArrayList<Long> factors = primeFactors(n);

            long sum = incExc(a, b, 1, factors);

            if(sum < 0)
                sum += 1000000007l;

            System.out.println(sum);
        }
    }

    static ArrayList<Long> primeFactors(long n) {
        ArrayList<Long> factors = new ArrayList<Long>();
        long md = 2;
        if (n % md == 0l) {
            factors.add(md);
            while (true) {
                n /= md;
                if (n % md != 0l)
                    break;
            }
        }
        md = 3;
        while (md <= Math.sqrt(n) + 1) {
            if (n % md == 0l) {
                factors.add(md);
                while (true) {
                    n /= md;
                    if (n % md != 0l)
                        break;
                }
            }
            md += 2;
        }
        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }

    static long sumUpTo(long k) {
        long smallk = k % 1000000007l;
        long sum = ((smallk * (smallk+1))/ 2).toBigInteger() % 1000000007l;
        return sum;
    }

    static long incExc(long a, long b, long k, List<Long> factors) {
        long smallk = k % 1000000007l;
        long s1 = (sumUpTo((b/k).longValue()) * smallk) % 1000000007;
        long s2 = (sumUpTo(((a-1)/k).longValue()) * smallk) % 1000000007;

        long sum = (s1 - s2) % 1000000007l;

        for (int i = 0; i < factors.size(); i++) {
            sum -= incExc(a, b, (k * factors.get(i)).longValue(), factors.subList(i+1, factors.size()));
            sum %= 1000000007l;
        }

        return sum;
    }
}