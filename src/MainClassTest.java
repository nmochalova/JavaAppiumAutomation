import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class MainClassTest extends MainClass
{
    @Test
    public void testGetLocalNumber()
    {
        Assert.assertTrue("Error: getLocalNumber != 14",this.getLocalNumber()==14);
    }

    @Test
    public void testGetClassNumber()
    {
        Assert.assertTrue("Error: getClassNumber < 45",this.getClassNumber()>45);
    }

    @Test
    public void testGetClassString()
    {
     String textString = this.getClassString().toUpperCase();

     Assert.assertTrue("Error: class_string does not contain 'Hello' or 'hello' ",
             textString.contains("HELLO"));
    }
}
