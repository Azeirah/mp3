public class ConsoleInput {

//  public static void main(String[] args) {
//    // Local variable
//    int swValue;
//
//    // Display menu graphics
//    System.out.println("============================");
//    System.out.println("|   MENU SELECTION DEMO    |");
//    System.out.println("============================");
//    System.out.println("| Options:                 |");
//    System.out.println("|        1. Option 1       |");
//    System.out.println("|        2. Option 2       |");
//    System.out.println("|        3. Exit           |");
//    System.out.println("============================");
//    swValue = Keyin.inInt(" Select option: ");
//
//    // Switch construct
//    switch (swValue) {
//    case 1:
//      System.out.println("Option 1 selected");
//      break;
//    case 2:
//      System.out.println("Option 2 selected");
//      break;
//    case 3:
//      System.out.println("Exit selected");
//      break;
//    default:
//      System.out.println("Invalid selection");
//      break; // This break is not really necessary
//    }
//  }
//}

//**********************************************************
//**********************************************************
//Program: Keyin
//Reference: Session 20
//Topics:
// 1. Using the read() method of the ImputStream class
//    in the java.io package
// 2. Developing a class for performing basic console
//    input of character and numeric types
//**********************************************************
//**********************************************************

//Method to display the user's prompt string
  public void printPrompt(String prompt) {
    System.out.print(prompt + " ");
    System.out.flush();
  }

  //Method to make sure no data is available in the
  //input stream
  public void inputFlush() {
    int dummy;
    int bAvail;

    try {
      while ((System.in.available()) != 0)
        dummy = System.in.read();
    } catch (java.io.IOException e) {
      System.out.println("Input error");
    }
  }

  //********************************
  //  data input methods for
  //string, int, char, and double
  //********************************
  public String inString(String prompt) {
    inputFlush();
    printPrompt(prompt);
    return inString();
  }

  public String inString() {
    int aChar;
    String s = "";
    boolean finished = false;

    while (!finished) {
      try {
        aChar = System.in.read();
        if (aChar < 0 || (char) aChar == '\n')
          finished = true;
        else if ((char) aChar != '\r')
          s = s + (char) aChar; // Enter into string
      }

      catch (java.io.IOException e) {
        System.out.println("Input error");
        finished = true;
      }
    }
    return s;
  }

  public int inInt(String prompt) {
    while (true) {
      inputFlush();
      printPrompt(prompt);
      try {
        return Integer.valueOf(inString().trim()).intValue();
      }

      catch (NumberFormatException e) {
        System.out.println("Invalid input. Not an integer");
      }
    }
  }

  public  char inChar(String prompt) {
    int aChar = 0;

    inputFlush();
    printPrompt(prompt);

    try {
      aChar = System.in.read();
    }

    catch (java.io.IOException e) {
      System.out.println("Input error");
    }
    inputFlush();
    return (char) aChar;
  }

  public double inDouble(String prompt) {
    while (true) {
      inputFlush();
      printPrompt(prompt);
      try {
        return Double.valueOf(inString().trim()).doubleValue();
      }

      catch (NumberFormatException e) {
        System.out.println("Invalid input. Not a floating point number");
      }
    }
  }
}