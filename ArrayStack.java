/** Array implementation of Stack ADT */
import javax.swing.JOptionPane;
import java.util.Stack;

public class ArrayStack
{
    protected int capacity; // The actual capacity of the stack array
    protected static final int CAPACITY = 1000; // default array capacity
    protected Object S[]; // array used to implement the stack
    protected int top = -1; // index for the top of the stack
    protected static String input;
    protected Character inputToArray[]; 
    protected static int INPUT_LENGTH;
    protected boolean validExpression = false;
    protected String output = new String();
    public ArrayStack() {
        // default constructor: creates stack with default capacity
        this(CAPACITY);
    }
    public ArrayStack(int cap) {
        // this constructor allows you to specify capacity of stack
        capacity = (cap > 0) ? cap : CAPACITY;
        S = new Object[capacity];
    }
    
    public void push(Object element) {
        if (isFull()) {
            JOptionPane.showMessageDialog(null, "ERROR: Stack is full.");
            return;
        }
        top++;
        S[top] = element;
    }
    public Object pop() {
        Object element;
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "ERROR: Stack is empty.");
            return null;
        }
        element = S[top];
        S[top] = null;
        top--;
        return element;
    }
    public Object top() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "ERROR: Stack is empty.");
            return null;
        }
        return S[top];
    }
    public boolean isEmpty() {
        return (top < 0);
    }
    public boolean isFull() {
        return (top == capacity-1);
    }
    public int size() {
        return (top + 1);
    }
}