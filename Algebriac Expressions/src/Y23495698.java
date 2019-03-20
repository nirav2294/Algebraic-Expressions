import java.util.ArrayList;
import java.util.Stack;

public class Y23495698 extends ExpressionTree {
	
	public static void main(String args[]) throws Exception {
		Y23495698 y = new Y23495698("5 + (x / y + z * 7) - 2");
		Utility.print(y);
		y = new  Y23495698(Utility.getInput());
		Utility.print(y);
	}
	

	public String fullyParenthesised() {

		return fullyParenthesised((BNode<String>) root);
	}

	private String fullyParenthesised(BNode<String> current) {
		if (current == null) //if null
			return "";
		else {
			
			BNode<String> left = current.getLeft();
			BNode<String> right = current.getRight();

			if (left == null && right == null)
				return current.getData();
			else
				return "(" + fullyParenthesised(left) + " " + current.getData() + " " + fullyParenthesised(right) + ")";
		}
	}



	public Y23495698(String s) {
		super();

		ArrayList<String> tokens = parseTokens(s);
		ArrayList<String> postfix = toPostfix(tokens);
		Stack<BNode<String>> stack = new Stack<>();

		for (String token : postfix) {
			if (isOpertor(token)) { 
				BNode<String> temp2 = stack.pop();
				BNode<String> temp1 = stack.pop();

				BNode<String> newNode = new BNode<>(token, null, temp1, temp2);
				temp1.setParent(newNode);
				temp2.setParent(newNode);

				stack.push(newNode);
			} else {

				stack.push(new BNode<>(token, null, null, null));
			}
		}

		root = stack.pop();

	}

	private ArrayList<String> toPostfix(ArrayList<String> tokens) {

		Stack<String> operatorStack = new Stack<>();

		ArrayList<String> postfix = new ArrayList<>();

		for (String token : tokens) {
			if (token.equals("(")) { 
				operatorStack.push(token);
			} else if (token.equals(")")) { 

				String popped = operatorStack.pop();
				while (!popped.equals("(")) {
					postfix.add(popped);
					popped = operatorStack.pop();
				}

			} else if (isOpertor(token)) { 

				while (!operatorStack.isEmpty() && precedence(operatorStack.peek().charAt(0)) >= precedence(token.charAt(0))) {
					postfix.add(operatorStack.pop());
				}


				operatorStack.push(token);
			} else
				postfix.add(token);
		}

		while (!operatorStack.isEmpty()) {
			postfix.add(operatorStack.pop());
		}

		return postfix;
	}


	private ArrayList<String> parseTokens(String infix) {

		ArrayList<String> tokens = new ArrayList<>();

		String currentToken = "";

		for (int i = 0; i < infix.length(); i++) {
			char ch = infix.charAt(i);

			if (ch == ' ') {

				if (!currentToken.isEmpty()) {
					tokens.add(currentToken);
				}

				currentToken = "";
			}

			else if (ch == '(' || ch == ')' || isOpertor(ch)) {


				if (!currentToken.isEmpty()) {
					tokens.add(currentToken);

				}

				tokens.add(ch + "");

				currentToken = "";
			} else
				currentToken += ch;
		}

		if (!currentToken.isEmpty()) {
			tokens.add(currentToken);

		}

		return tokens;
	}

	private boolean isOpertor(String s) {
		return s.length() == 1 && isOpertor(s.charAt(0));
	}

	private boolean isOpertor(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/';
	}


	private int precedence(char operand) {
		if (operand == '/')
			return 3;
		else if (operand == '*')
			return 3;
		else if (operand == '+')
			return 2;
		else if (operand == '-')
			return 2;
		else if (operand == '(')
			return 1;
		else
			return -1;

	}
}

