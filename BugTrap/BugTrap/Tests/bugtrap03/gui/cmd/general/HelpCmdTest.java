package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author Group 03
 */
public class HelpCmdTest {

    /**
     * Test execution when a null was passed to the constructor as the print
     * list.
     */
    @Test
    public void testNullConstructor() {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        HelpCmd cmd = new HelpCmd(null);

        question.add("List of possible commands:");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        Object obj = cmd.exec(scan, null, null);
        assertEquals(obj, null);
    }

    /**
     * Test execution with a null reference for the scanner argument.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullScanExec() {
        HelpCmd cmd = new HelpCmd(null);
        Object obj = cmd.exec(null, null, null);
    }

    @Test
    public void testCommonSuccesScenario() {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        ArrayList<SimpleEntry<String, Cmd>> printList = new ArrayList<>();

        printList.add(new SimpleEntry<>("test", new HelpCmd(null)));
        printList.add(new SimpleEntry<>("getUserOfExactTypeCmd", new GetUserOfExcactTypeCmd<>(User.class)));
        printList.add(new SimpleEntry<>("clear", new ClearCmd()));

        HelpCmd cmd = new HelpCmd(printList);

        question.add("List of possible commands:");
        question.add("0. test");
        question.add("1. getUserOfExactTypeCmd");
        question.add("2. clear");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        Object obj = cmd.exec(scan, null, null);
        assertEquals(obj, null);
    }

}
