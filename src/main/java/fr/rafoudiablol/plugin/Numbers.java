package fr.rafoudiablol.plugin;

public class Numbers {
    private Numbers() {}

    public static String forceSign(double d) {
        return d < 0 ? String.valueOf(d) : "+" + d;
    }

    public static String forceSign(int i) {
        return i < 0 ? String.valueOf(i) : "+" + i;
    }

    public static int clamp(int min, int max, int x) {
        return Math.max(min, Math.min(x, max));
    }

    public static double clamp(double min, double max, double x) {
        return Math.max(min, Math.min(x, max));
    }
}
