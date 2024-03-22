public class LogarithmicSpacing {
    public static void main(String[] args) {
        int minN = 5;
        int maxN = 50000;
        double logFactor = Math.log10(maxN) / 10.0; // Calculate logarithmic scale factor

        System.out.println("Generated values of n on a logarithmic scale:");
        for (int i = 0; i <= 9; i++) {
            int n = (int) Math.round(minN * Math.pow(10, i * logFactor)); // Calculate n value
            System.out.println(n);
        }
    }
}