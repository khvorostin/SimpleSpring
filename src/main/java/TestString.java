import java.util.*;

public class TestString {

    public static void main(String[] args) {
        Map<String, Boolean > testCases = new HashMap<>();
        testCases.put("([{}])", true);
        testCases.put(") (", false);
        testCases.put("(([] {} [()]))", true);

        for (Map.Entry<String, Boolean> entry : testCases.entrySet()) {
            String string = entry.getKey();
            boolean expectedTestResult = entry.getValue();

             if (expectedTestResult == testString(string)) {
//            if (expectedTestResult == isStackValid(string)) {
                System.out.println(string + " valid");
            } else {
                System.out.println(string + " invalid");
            }
        }

    }

    private static boolean testString(String string) {

        List<Character> openingBraces = new ArrayList<>(Arrays.asList('(', '{', '['));
        List<Character> closingBraces = new ArrayList<>(Arrays.asList(')', '}', ']'));

        Stack<Character> stack = new Stack<>();
        char currChar;

        for (int i = 0; i < string.length(); i++) {
            currChar = string.charAt(i);

            if (openingBraces.contains(currChar)) {
                stack.push(closingBraces.get(openingBraces.indexOf(currChar)));
                continue;
            }

            if (closingBraces.contains(currChar) && (stack.isEmpty() || currChar != stack.pop())) {
                return false;
            }
        }

        return stack.isEmpty();
    }

//    public static boolean isStackValid(String str){
//        Stack<Character> stack = new Stack<>();
//        HashMap<Character, Character> map = new HashMap<>();
//        map.put(']','[');
//        map.put('}','{');
//        map.put(')','(');
//        Character s;
//
//        for(int i=0; i<str.length();i++){
//
//            s = str.charAt(i);
//            if (s != '}' && s != ']' && s != ')'){
//                stack.add(s);
//                continue;
//            }else if(stack.peek() == map.get(s)){
//                stack.pop();
//                continue;
//            }else{
//                return false;
//            }
//        }
//        return stack.isEmpty();
//    }
}
