/*
 * CalcClient.java
 * This is file contains code to take instruction from client, send requests to server and once it got solution from server,
 * it will show to users
 */

/**
 *
 * @author Shivanagesh Chandra <schandra@scu.edu>
 */
//Imports
import CalcApp.*;
import CalcApp.CalcPackage.*;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//CalClient Class 
public class CalcClient
{

    //Properties 
    static Calc calimpl;

    //Declaring BufferReader object with inputStreamReader, It will used for taking inputs from user
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // Main method defination
    public static void main(String args[])
    {
        // Getting POA and Naming Serveice will give an exception using try to handle those
        try {
            //Create ORB and initialize ORB. This is medium to connect with server
            ORB orb = ORB.init(args, null);

            //Get rootnaming Context 
            org.omg.CORBA.Object objRef
                    = orb.resolve_initial_references("NameService");
            // Use NamingContextExt instead of NamingContext. This is 
            // part of the Interoperable naming Service.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            //Get object refernce from naming service
            String name = "Calc";
            calimpl = CalcHelper.narrow(ncRef.resolve_str(name));

            //Giving instruction to users
            System.out.println("Client Ready to take instruction");
            System.out.println("Please enter 'Exit' as instruction to close server connection");

            //Infinate loop, this is used taking further instrutions. if user types exit, this loop will break. 
            while (true) {
                System.out.print("Client:");
                //Reading input from user 
                String instruction = br.readLine();

                //Using Java StringTokenizer to split the given instruction 
                StringTokenizer st = new StringTokenizer(instruction);
                String[] tokens = new String[st.countTokens()];
                int j = 0;
                //Storing each into one location in array
                while (st.hasMoreTokens()) {
                    tokens[j++] = st.nextToken();
                }
                // If user enters more two operands and ask them to re enter
                if(tokens.length > 3){
                    System.out.println("Client: Invaild Syntax, Please Enter like this <operation_code> <operand 1> <operand 2>");
                    //Start from top loop
                    continue;
                }

                /**
                 * If First token is exit, Then convert to Exit. Possible case
                 * of exit: 1.exit 2.Exit 3.EXIT User can type any of these,
                 * Convert them to into our standard that is Exit.
                 *
                 */
                String firstToken = tokens[0];
                firstToken = firstToken.substring(0, 1).toUpperCase() + firstToken.substring(1).toLowerCase();

                //If first token is Exit command, exit loop
                if (firstToken.equals("Exit")) {
                    break;
                }

                try {
                    //Calling Calcualte method to perform operations 
                    double result = calimpl.calculate(tokens[0], Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
                    if ((result - (int) result) != 0) {
                        System.out.println("Server: " + result);
                    } else {
                        System.out.println("Server: " + (int) result);
                    }
                } // Handlling Division by zero exception 
                catch (DivisionByZero de) {
                    System.out.println("Server: Division by zero!!!");
                } // End of Division by zero exception catch block
                // Handlling CalcError exception, its occur when operator is not valid
                catch (CalcError de) {
                    System.out.println("Server: Invaild Operator!!!");
                }// End of CalcError exception catch block
            }// End of while loop

            // Closing server by calling Shutdown method
            System.out.println("Server:I am out");
            calimpl.shutdown();

        }//End of Try block 
        catch (Exception e) {
            System.out.println("Error message:" + e.getMessage());
            e.printStackTrace();
        }//End of catch block 
    }
    //End of main method

}
//End of CalcClient class 
