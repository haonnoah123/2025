import java.util.List;

import Tools.Tools;

class Day01 {

    public static void main(String[] args) {
        List<String> input = Tools.getInput("Day01/Day01Input.txt");
        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }

    public static int partOne(List<String> input) {
        int count = 0;
        int degree = 50;

        for(String str : input) {
            int angle = Integer.parseInt(str.substring(1));
            degree = (degree + ((str.charAt(0) == 'L' ? -1 : 1) * angle)) % 100;

            if (degree == 0) count++;
        }
        return count;
    }

    public static int partTwo(List<String> input) {
        int count = 0;
        int degree = 50;

        for(String str : input) {
            int angle = Integer.parseInt(str.substring(1));
            int prevDegree = degree;
            degree += ((str.charAt(0) == 'L' ? -1 : 1) * angle);
            int tempDegree = ((degree % 100) + 100) % 100;
            if((tempDegree != degree && (prevDegree != 0 || Math.abs(degree) >= 100)) || tempDegree == 0) {
                if(Math.abs(degree) == degree) {
                    count += Math.max(degree / 100, 1);
                } else {
                    System.out.println(degree);
                    count += ((-1 * degree) + 100) / 100;
                    if(prevDegree == 0) count--;
                }
            }
            degree = tempDegree;
        }
        return count;
    }
    
}
