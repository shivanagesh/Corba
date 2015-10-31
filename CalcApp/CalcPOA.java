package CalcApp;


/**
* CalcApp/CalcPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Calc.idl
* Thursday, October 22, 2015 6:12:35 PM PDT
*/

public abstract class CalcPOA extends org.omg.PortableServer.Servant
 implements CalcApp.CalcOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("calculate", new java.lang.Integer (0));
    _methods.put ("shutdown", new java.lang.Integer (1));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // CalcApp/Calc/calculate
       {
         try {
           String opcode = in.read_string ();
           double op1 = in.read_double ();
           double op2 = in.read_double ();
           double $result = (double)0;
           $result = this.calculate (opcode, op1, op2);
           out = $rh.createReply();
           out.write_double ($result);
         } catch (CalcApp.CalcPackage.DivisionByZero $ex) {
           out = $rh.createExceptionReply ();
           CalcApp.CalcPackage.DivisionByZeroHelper.write (out, $ex);
         } catch (CalcApp.CalcPackage.CalcError $ex) {
           out = $rh.createExceptionReply ();
           CalcApp.CalcPackage.CalcErrorHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // CalcApp/Calc/shutdown
       {
         this.shutdown ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CalcApp/Calc:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Calc _this() 
  {
    return CalcHelper.narrow(
    super._this_object());
  }

  public Calc _this(org.omg.CORBA.ORB orb) 
  {
    return CalcHelper.narrow(
    super._this_object(orb));
  }


} // class CalcPOA
