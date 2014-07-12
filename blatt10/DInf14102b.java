/* 
  compile with

  javac DInf14102b.java
  
  run with
  
  java DInf14102b
*/
public class DInf14102b {
  public static int fakeOptimum = 0;
  public static int dimensions  = 0;

  // simulates A for a given optimum and k
  public static boolean A(int k) {
    return fakeOptimum >= k;
  }

  // algorithm
  public static int algorithm(int n) {
    
    // initialization
    int[] ks = new int[n]; 
    for(int i = 0; i < n; ks[i] = ++i);
    int left = 0; 
    int right = n-1; 
    int middle = (left+right)/2;
    
    // binary search
    while(left < right) {
      if( A(ks[middle]) ) {
        // new boundaries
        left   = middle + 1;
        middle = (left + right) / 2;
        
        // decision problem is >=, so we need to check this as well
        if( !A(ks[left]) ) {
          // safety check in case left is smaller 0
          return left - 1 >= 0? ks[left - 1] : -1;
        }
      } else {
        // new boundaries
        right  = middle - 1;
        middle = (left + right) / 2;
      }
    }
    // check if this value is valid, otherwise no optimazation possible
    if( A(ks[middle]) )
      return ks[middle];
    return -1;
  }
  
  public static void test(int n, int o, int c) {
    fakeOptimum = o;
    dimensions  = n;
    int res = algorithm(dimensions);
    System.out.println("n: " + n + " c'x: " + o + " algo: " + res + " passed: " + ((res == c)?"YES":"NO"));
  }
  
  public static void main(String[] args) {
    test( 4,  3,   3);
    test(50,  1,   1);
    test( 2,  5,   2);
    test(10, -3,  -1);
    test(50, 12,  12);
    test(50, 50,  50);
    test(13,  4,   4);
    test(50, 35,  35);
  }
}