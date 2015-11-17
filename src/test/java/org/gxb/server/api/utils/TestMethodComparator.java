/*  1:   */package org.gxb.server.api.utils;

/*  2:   */
/*  3:   */import java.util.Comparator;
/*  4:   */
import org.testng.ITestNGMethod;

/*  5:   */
/*  6:   */class TestMethodComparator
/* 7: */implements Comparator<ITestNGMethod>
/* 8: */{
	/* 9: */public int compare(ITestNGMethod method1, ITestNGMethod method2)
	/* 10: */{
		/* 11:31 */int compare = method1.getRealClass().getName()
				.compareTo(method2.getRealClass().getName());
		/* 12:32 */if (compare == 0) {
			/* 13:34 */compare = method1.getMethodName().compareTo(
					method2.getMethodName());
			/* 14: */}
		/* 15:36 */return compare;
		/* 16: */}
	/* 17: */
}

/*
 * Location: C:\Users\dell\Desktop\reportng-1.1.4.jar
 * 
 * Qualified Name: org.uncommons.reportng.TestMethodComparator
 * 
 * JD-Core Version: 0.7.0.1
 */