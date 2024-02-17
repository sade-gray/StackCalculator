import javax.swing.JOptionPane;

public class Postfix_To_Infix
{
    // This string will be used for the value taken in from the user
    private String input;
    // This will be false and it will be used as a condition for the while loop that is initiated in the constructor
    private boolean validExpression = false;
    // This will store the postfix value
    private String output = new String();
    // This reference will store a stack object
    private ArrayStack S;
    // These will store the local variables and are accessed in the calculation
    private double var1 = 0F;
    private double var2 = 0F;
    
    //For now I will just use a scanner
    //Scanner scan = new Scanner(System.in);
    
    public Postfix_To_Infix()
    {
        // Here I am instantiating an array stack
        S = new ArrayStack();
        // // // //
        
        // This is the loop condition that will continue until the condition is met. This is to prevent an invalid expression from being evaluated by the other methods 
        while (!validExpression) {
            input = JOptionPane.showInputDialog(null, "Please enter input:");
            // Here I am checking if the length is in the right range 3 to 20  and also checking that the user at least input something by checking if input is null
            if (input.length() > 3 && input.length() < 20 && input != null)  {
                // This will provide an additional check that will be updated constantly in the loop. If the flag is set to false, then the condition for 
                // exiting the while loop will not be reached 
                boolean good = false;
                // If that is the case, then I will go through each value and check certain conditions 
                for (int i = 0; i < input.length(); i ++ ) {
                    // First I am checking that all of the characters are correct - either an operator or an operand 
                    if (!(input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/' || input.charAt(i) == '^' || input.charAt(i) == ')' || input.charAt(i) == '(' || Character.isDigit(input.charAt(i)))) {
                        // If not, I clear the input string, print a message, set good to false and break the for loop. There is no need to continue evaluating
                        JOptionPane.showMessageDialog(null, "Make sure that your input contains * / ^ + -  0-9");
                        input = "";
                        good = false;
                        break;
                    }
                    // I am accounting for a case where there are two operators that are placed beside each other in an expression. This of course is not valid and must be evaluated
                    /*else if ((i > 1) && (!Character.isDigit(input.charAt(i - 1)) || (input.charAt(i - 1) == ')') || (input.charAt(i - 1) == '(')) && (!Character.isDigit(input.charAt(i)) || (input.charAt(i) == ')') || (input.charAt(i) == '('))) {
                        System.out.println("Invalid expression");
                        input = "";
                        good = false;
                        break;
                    } */
                    // Otherwise, I set the flag to true
                    else {
                        good = true;
                    }
                }
                // If the flag is true, then the while loop will end
                if (good) {
                    System.out.println("Valid expression");
                    validExpression = true;
                }
            }
            // Check that this expression breaks and evaluates to true when whanted
            else {
                JOptionPane.showMessageDialog(null, "Expression must be between 3-20 characters");
                input = "";
            }
        }
        infixToPostfix();
        calculator();
    }
    // This is used to avoid the repetition of the calling of the same methods that are used in strings as a part of a process of popping the element
    public char popString(String str) { // To avoid repetition
        char element = str.charAt(str.length() -1);
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        else {
            System.out.println("ERROR popping string");
        }
        return element;
    }
    // This is the equivalent of the 'top' method that frank has, but for strings
    public char peekString(String str) { // to avoid repetition
        return str.charAt(str.length() - 1);
    }
    // This will then complete the popping method and update the string by removing the last element 
    public String popStringUpdate(String str) {
        if (str != null && str.length() > 0) {
            return str.substring(0, str.length() - 1);
        } else {
            System.out.println("ERROR popping string");
            return str;
        }
    }

    
   // This method is used for coverting infix to postfix notation
    public String infixToPostfix() { //(3+4)*/8+9*(5-6)
    // These are temporary values that are used to check values 
    String temp = new String(); 
    String first = new String(); 
    String second = new String(); 
    // Then I will loop for the length of the input
    for(int i=0;i < input.length(); i++) { 
        // I am letting ch be the initial value. I am storing it in ch for the sake of modularising my code
        char ch=input.charAt(i); 
        // Temp will be used to deal with operators. Temp will store the bracket rather than adding it to the real output
        if(ch=='(') temp += ch ; 
        // I am then checking if ch is a digit. If it is, due to the nature of stack calculations, I will simply add it to my stack
        else if(Character.isDigit(ch)) {
            output += (ch+"");
        }
        // If it is a closing bracker, I will need to deal with all of the values in the brackets so that they are given priority in the stack and that the
        // values in the brackets are kept altogether
        else if (ch == ')') {
            // If the character is ')', handle the closing brackets case
        
            // Continue until an opening bracket '(' 
            while (peekString(temp) != '(') {
                // Pop the operator and update temporary stack
                char op = popString(temp);
                temp = popStringUpdate(temp);
        
                // Pop operands from output stack and update the string
                first += popString(output);
                output = popStringUpdate(output);
                second += popString(output);
                output = popStringUpdate(output);
        
                // Create a postfix expression and update output string
                String new_postFix = second + first + op;
                output += new_postFix;
        
                // Reset operand variables
                first = "";
                second = "";
            }
        
            // Pop the opening parenthesis '(' from the temporary stack
            popString(temp);
            temp = popStringUpdate(temp);
        }

        else if(ch=='+' || ch=='-' || ch== '*' || ch== '/' || ch == '^') { 
            // If the character is an operator handle the operator

            // Continue while there are operators in the temp string,
            // and the end of the string is not an opening bracket '(',
            // and the precedence of the operator is less than or equal to the precedence of the operator at the end of the string
            while(temp.length()>0 && peekString(temp) !='(' && precedence(ch) <= precedence(peekString(temp))) {
                 // Pop the operator and update
                char op = popString(temp); 
                temp = popStringUpdate(temp);
                first += popString(output); 
                // Pop operands from the output 
                output = popStringUpdate(output);
                second += popString(output); 
                output = popStringUpdate(output);
                String new_postFix = second+first+op; 
                output += new_postFix;  
                // Empty string for the next iteration
                first = "";
                second = "";
            } 
            // Append current character to temp
            temp += ch;               
        }     
    } 
    // Then empty the rest of the string into the output until temp is empty
    while(temp.length()>0) {
        char op = popString(temp);
        temp = popStringUpdate(temp);
        first += popString(output);
        output = popStringUpdate(output);
        second += popString(output); 
        output = popStringUpdate(output);
        String new_postFix = second+first+op; 
        output += new_postFix;
        first = "";
        second = "";
    }
    System.out.println(output);
    return output;       
}
    
    public void calculator () {
        for (int i = 0; i < output.length(); i ++){
            if (precedence(output.charAt(i)) == -1) {
                S.push(output.charAt(i));
            }
            
                if (precedence(output.charAt(i)) == 1) {
                    if (output.charAt(i) == '+') {
                        var2 = Double.parseDouble(String.valueOf(S.pop())); // 4+28-7/4* - number format exception 
                        var1 = Double.parseDouble(String.valueOf(S.pop())); 
                        S.push(Double.toString(var1 + var2));
                        System.out.println(var1 + " + " + var2 + " = " + S.top());
                    }
                    else {
                        var2 = Double.parseDouble(String.valueOf(S.pop()));
                        var1 = Double.parseDouble(String.valueOf(S.pop())); 
                        S.push(Double.toString(var1 - var2));
                        System.out.println(var1 + " - " + var2 + " = " + S.top());
                    }
                }
                if (precedence(output.charAt(i)) == 2) {
                    if (output.charAt(i) == '*') {
                        var2 = Double.parseDouble(String.valueOf(S.pop()));
                        var1 = Double.parseDouble(String.valueOf(S.pop()));
                        S.push(Double.toString(var1 * var2));
                        System.out.println(var1 + " * " + var2 + " = " + S.top());
                    }
                    else {
                        var2 = Double.parseDouble(String.valueOf(S.pop()));
                        var1 = Double.parseDouble(String.valueOf(S.pop()));
                        S.push(Double.toString((var1 / var2)));
                        System.out.println(var1 + " / " + var2 + " = " + S.top());
                    }
                }
                if (precedence(output.charAt(i)) == 3) {
                    var2 = Double.parseDouble(String.valueOf(S.pop()));
                    var1 = Double.parseDouble(String.valueOf(S.pop()));
                    S.push((Double.toString(Math.pow(var1,var2))));
                    System.out.println(var1 + " ^ " + var2 + " = " + S.top());
                }
        }
    }
    // This will return a number depending on the symbol that is taken as an argument 
    private int precedence(Character operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }
    // The entry of the main application
    public static void main(String args[]) {
        Postfix_To_Infix p = new Postfix_To_Infix();
    }
}
