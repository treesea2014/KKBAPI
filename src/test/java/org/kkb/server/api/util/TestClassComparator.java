/*  1:   */package org.kkb.server.api.util;

/*  2:   */
/*  3:   */import java.util.Comparator;
/*  4:   */
import org.testng.IClass;

/*  5:   */
/*  6:   */class TestClassComparator
/* 7: */implements Comparator<IClass>
/* 8: */{
	/* 9: */public int compare(IClass class1, IClass class2)
	/* 10: */{
		/* 11:29 */return class1.getName().compareTo(class2.getName());
		/* 12: */}
	/* 13: */
}

/*
 * Location: C:\Users\dell\Desktop\reportng-1.1.4.jar
 * 
 * Qualified Name: org.uncommons.reportng.TestClassComparator
 * 
 * JD-Core Version: 0.7.0.1
 */