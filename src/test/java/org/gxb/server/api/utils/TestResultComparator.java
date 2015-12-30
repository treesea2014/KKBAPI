/*  1:   */package org.gxb.server.api.utils;

/*  2:   */
/*  3:   */import java.util.Comparator;

/*  4:   */
import org.testng.ITestResult;

/*  5:   */
/*  6:   */class TestResultComparator/* 7: */implements Comparator<ITestResult>
/* 8: */ {
	/* 9: */public int compare(ITestResult result1, ITestResult result2)
	/* 10: */ {
		/* 11:29 */return result1.getName().compareTo(result2.getName());
		/* 12: */}
	/* 13: */
}

/*
 * Location: C:\Users\dell\Desktop\reportng-1.1.4.jar
 * 
 * Qualified Name: org.uncommons.reportng.TestResultComparator
 * 
 * JD-Core Version: 0.7.0.1
 */