
package org.kkb.server.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IClass;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;
import org.uncommons.reportng.AbstractReporter;
import org.uncommons.reportng.ReportNGException;

/**
 * Enhanced HTML reporter for TestNG that uses Velocity templates to generate its
 * output.
 * @author treesea888@qq.com override 
 */
public class ReporterListener extends AbstractReporter
{
  


	private static final String FRAMES_PROPERTY = "org.uncommons.reportng.frames";
    private static final String ONLY_FAILURES_PROPERTY = "org.uncommons.reportng.failures-only";

    private static final String TEMPLATES_PATH = "org/uncommons/reportng/templates/html/";
    private static final String INDEX_FILE = "index.html";
    private static final String SUITES_FILE = "suites.html";
    private static final String OVERVIEW_FILE = "overview.html";
    private static final String GROUPS_FILE = "groups.html";
    private static final String RESULTS_FILE = "results.html";
    private static final String OUTPUT_FILE = "output.html";
    private static final String CUSTOM_STYLE_FILE = "custom.css";

    private static final String SUITE_KEY = "suite";
    private static final String SUITES_KEY = "suites";
    private static final String GROUPS_KEY = "groups";
    private static final String RESULT_KEY = "result";
    private static final String FAILED_CONFIG_KEY = "failedConfigurations";
    private static final String SKIPPED_CONFIG_KEY = "skippedConfigurations";
    private static final String FAILED_TESTS_KEY = "failedTests";
    private static final String SKIPPED_TESTS_KEY = "skippedTests";
    private static final String PASSED_TESTS_KEY = "passedTests";
    private static final String ONLY_FAILURES_KEY = "onlyReportFailures";

    private static final String REPORT_DIRECTORY = "html";

    private static final Comparator<ITestNGMethod> METHOD_COMPARATOR = new TestMethodComparator();
    private static final Comparator<ITestResult> RESULT_COMPARATOR = new TestResultComparator();
    private static final Comparator<IClass> CLASS_COMPARATOR = new TestClassComparator();
    protected static final Logger logger = LoggerFactory.getLogger(ReporterListener.class);

    public ReporterListener()
    {
        super(TEMPLATES_PATH);
    }

    
    /**
     * Generates a set of HTML files that contain data about the outcome of
     * the specified test suites.
     * @param suites Data about the test runs.
     * @param outputDirectoryName The directory in which to create the report.
     */
    public void generateReport(List<XmlSuite> xmlSuites,
                               List<ISuite> suites,
                               String outputDirectoryName)
    {
        removeEmptyDirectories(new File(outputDirectoryName));
        
        boolean useFrames = System.getProperty(FRAMES_PROPERTY, "true").equals("true");
        boolean onlyFailures = System.getProperty(ONLY_FAILURES_PROPERTY, "false").equals("true");

        File outputDirectory = new File(outputDirectoryName, REPORT_DIRECTORY);
        outputDirectory.mkdirs();

        try
        {
            if (useFrames)
            {
                createFrameset(outputDirectory);
            }
            createOverview(suites, outputDirectory, !useFrames, onlyFailures);
            createSuiteList(suites, outputDirectory, onlyFailures);
            createGroups(suites, outputDirectory);
            createResults(suites, outputDirectory, onlyFailures);
            createLog(outputDirectory, onlyFailures);
            copyResources(outputDirectory);
    		
            Date date = new Date();
            String path = ResourceBundle.getBundle("api").getString("tomcatPath");
    		String current = new SimpleDateFormat("yyyyMMddHHmmss")
    				.format(date);
            copyFileRecursively(outputDirectory, new File(path+File.separator+current));
			generateMailHtml(suites,date);
			logger.info("测试报告部署完成");
            
        }
        catch (Exception ex)
        {
            throw new ReportNGException("Failed generating HTML report.", ex);
        }
    }

    /**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	private static void generateMailHtml(List<ISuite> suites, Date reportTime) {
		ResourceBundle bundle = ResourceBundle.getBundle("api");
		String to = bundle.getString("toMail");
		if (to == null) {
			return;
		}
		String[] toMail = bundle.getString("toMail").split(";");
		String env = bundle.getString("env");
		String url = "http://" + bundle.getString("server") + ":8080"
				+ bundle.getString("htmlPath") 
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(reportTime)
				+ "/html/" + INDEX_FILE;

		int passed = 0;
		int fail = 0;
		int skip = 0;

		StringBuilder html = new StringBuilder("<html>");

		StringBuilder sb = new StringBuilder("");
		sb.append("<h4>" + "详细结果：" + url + "</h4><br>");
		int lineCount = 0;

		for (ISuite suite : suites) {
			if (suite.getAllMethods() == null
					|| suite.getAllMethods().size() == 0) {
				continue;
			}
			if (lineCount == 0) {
				sb.append("<h2 style='margin-left:40px;'>");
			} else {
				sb.append("<h2>");
			}
			sb.append("suite 模块：" + suite.getName()).append("<h2>");
			Map<String, ISuiteResult> results = suite.getResults();
			Iterator<String> it = suite.getResults().keySet().iterator();
			while (it.hasNext()) {
				ISuiteResult result = results.get(it.next());
				ITestContext testContext = result.getTestContext();
				passed = passed + testContext.getPassedTests().size();
				fail = fail + testContext.getFailedTests().size();
				skip = skip + testContext.getSkippedTests().size();

				int total = testContext.getFailedTests().size()
						+ testContext.getPassedTests().size()
						+ testContext.getSkippedTests().size();

				 sb.append("<h3 style='margin-left:80px;'>" + "test 模块："
				 + testContext.getName() + "\t总计：" + total + " \t通过："
				 + testContext.getPassedTests().size() + " \t失败："
				 + testContext.getFailedTests().size() + " \t跳过："
				 + testContext.getSkippedTests().size() + " \t通过率："
				 + (testContext.getPassedTests().size() * 100 / total)
				 + " %" + "</h3>");
				/*sb.append("<h3 style='margin-left:80px;'>" + "test 模块："
						+ testContext.getName() + "</h3>");*/
			}
			lineCount++;
		}
		String per = "<div style=\"height:20px;width:400px;\">" +
			"<div style=\"height:20px;width:"+(passed * 100 / (passed + fail + skip))+"%;background-color:green;float:left\"></div>"+
			"<div style=\"height:20px;width:"+(skip * 100 / (passed + fail + skip))+"%;background-color:yellow;float:left\"></div>"+
			"<div style=\"height:20px;width:"+(fail * 100 / (passed + fail + skip))+"%;background-color:red;float:left\"></div>"+
		"</div>";
		html.append(
				"<h4 style='color:red;'>" + env + "环境     结果统计："
						+ (passed + fail + skip) + "\t通过:" + passed
						+ "&nbsp;&nbsp;失败:" + fail + "&nbsp;&nbsp;跳过:" + skip
						+ "&nbsp;&nbsp;通过率:"
						+ (passed * 100 / (passed + fail + skip)) + " %</h4>").append(per)
				.append(sb.toString()).append("</html>");

		String subject = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒")
				.format(reportTime)
				+ "  "
				+ env
				+ "环境      "
				+ bundle.getString("subject");
		HtmlMail.send(toMail, subject, html.toString());
	}
	
    /**
     * Create the index file that sets up the frameset.
     * @param outputDirectory The target directory for the generated file(s).
     */
    private void createFrameset(File outputDirectory) throws Exception
    {
        VelocityContext context = createContext();
        generateFile(new File(outputDirectory, INDEX_FILE),
                     INDEX_FILE + TEMPLATE_EXTENSION,
                     context);
    }


    private void createOverview(List<ISuite> suites,
                                File outputDirectory,
                                boolean isIndex,
                                boolean onlyFailures) throws Exception
    {
        VelocityContext context = createContext();
        context.put(SUITES_KEY, suites);
        context.put(ONLY_FAILURES_KEY, onlyFailures);
        generateFile(new File(outputDirectory, isIndex ? INDEX_FILE : OVERVIEW_FILE),
                     OVERVIEW_FILE + TEMPLATE_EXTENSION,
                     context);
    }


    /**
     * Create the navigation frame.
     * @param outputDirectory The target directory for the generated file(s).
     */
    private void createSuiteList(List<ISuite> suites,
                                 File outputDirectory,
                                 boolean onlyFailures) throws Exception
    {
        VelocityContext context = createContext();
        context.put(SUITES_KEY, suites);
        context.put(ONLY_FAILURES_KEY, onlyFailures);
        generateFile(new File(outputDirectory, SUITES_FILE),
                     SUITES_FILE + TEMPLATE_EXTENSION,
                     context);
    }


    /**
     * Generate a results file for each test in each suite.
     * @param outputDirectory The target directory for the generated file(s).
     */
    private void createResults(List<ISuite> suites,
                               File outputDirectory,
                               boolean onlyShowFailures) throws Exception
    {
        int index = 1;
        for (ISuite suite : suites)
        {
            int index2 = 1;
            for (ISuiteResult result : suite.getResults().values())
            {
                boolean failuresExist = result.getTestContext().getFailedTests().size() > 0
                                        || result.getTestContext().getFailedConfigurations().size() > 0;
                if (!onlyShowFailures || failuresExist)
                {
                    VelocityContext context = createContext();
                    context.put(RESULT_KEY, result);
                    context.put(FAILED_CONFIG_KEY, sortByTestClass(result.getTestContext().getFailedConfigurations()));
                    context.put(SKIPPED_CONFIG_KEY, sortByTestClass(result.getTestContext().getSkippedConfigurations()));
                    context.put(FAILED_TESTS_KEY, sortByTestClass(result.getTestContext().getFailedTests()));
                    context.put(SKIPPED_TESTS_KEY, sortByTestClass(result.getTestContext().getSkippedTests()));
                    context.put(PASSED_TESTS_KEY, sortByTestClass(result.getTestContext().getPassedTests()));
                    String fileName = String.format("suite%d_test%d_%s", index, index2, RESULTS_FILE);
                    generateFile(new File(outputDirectory, fileName),
                                 RESULTS_FILE + TEMPLATE_EXTENSION,
                                 context);
                }
                ++index2;
            }
            ++index;
        }
    }


    /**
     * Group test methods by class and sort alphabetically.
     */ 
    private SortedMap<IClass, List<ITestResult>> sortByTestClass(IResultMap results)
    {
        SortedMap<IClass, List<ITestResult>> sortedResults = new TreeMap<IClass, List<ITestResult>>(CLASS_COMPARATOR);
        for (ITestResult result : results.getAllResults())
        {
            List<ITestResult> resultsForClass = sortedResults.get(result.getTestClass());
            if (resultsForClass == null)
            {
                resultsForClass = new ArrayList<ITestResult>();
                sortedResults.put(result.getTestClass(), resultsForClass);
            }
            int index = Collections.binarySearch(resultsForClass, result, RESULT_COMPARATOR);
            if (index < 0)
            {
                index = Math.abs(index + 1);
            }
            resultsForClass.add(index, result);
        }
        return sortedResults;
    }



    /**
     * Generate a groups list for each suite.
     * @param outputDirectory The target directory for the generated file(s).
     */
    private void createGroups(List<ISuite> suites,
                              File outputDirectory) throws Exception
    {
        int index = 1;
        for (ISuite suite : suites)
        {
            SortedMap<String, SortedSet<ITestNGMethod>> groups = sortGroups(suite.getMethodsByGroups());
            if (!groups.isEmpty())
            {
                VelocityContext context = createContext();
                context.put(SUITE_KEY, suite);
                context.put(GROUPS_KEY, groups);
                String fileName = String.format("suite%d_%s", index, GROUPS_FILE);
                generateFile(new File(outputDirectory, fileName),
                             GROUPS_FILE + TEMPLATE_EXTENSION,
                             context);                
            }
            ++index;
        }
    }


    /**
     * Generate a groups list for each suite.
     * @param outputDirectory The target directory for the generated file(s).
     */
    private void createLog(File outputDirectory, boolean onlyFailures) throws Exception
    {
        if (!Reporter.getOutput().isEmpty())
        {
            VelocityContext context = createContext();
            context.put(ONLY_FAILURES_KEY, onlyFailures);
            generateFile(new File(outputDirectory, OUTPUT_FILE),
                         OUTPUT_FILE + TEMPLATE_EXTENSION,
                         context);
        }
    }


    /**
     * Sorts groups alphabetically and also sorts methods within groups alphabetically
     * (class name first, then method name).  Also eliminates duplicate entries.
     */
    private SortedMap<String, SortedSet<ITestNGMethod>> sortGroups(Map<String, Collection<ITestNGMethod>> groups)
    {
        SortedMap<String, SortedSet<ITestNGMethod>> sortedGroups = new TreeMap<String, SortedSet<ITestNGMethod>>();
        for (Map.Entry<String, Collection<ITestNGMethod>> entry : groups.entrySet())
        {
            SortedSet<ITestNGMethod> methods = new TreeSet<ITestNGMethod>(METHOD_COMPARATOR);
            methods.addAll(entry.getValue());
            sortedGroups.put(entry.getKey(), methods);
        }
        return sortedGroups;
    }


    /**
     * Reads the CSS and JavaScript files from the JAR file and writes them to
     * the output directory.
     * @param outputDirectory Where to put the resources.
     * @throws IOException If the resources can't be read or written.
     */
    private void copyResources(File outputDirectory) throws IOException
    {
        copyClasspathResource(outputDirectory, "reportng.css", "reportng.css");
        copyClasspathResource(outputDirectory, "reportng.js", "reportng.js");
        // If there is a custom stylesheet, copy that.
        File customStylesheet = META.getStylesheetPath();

        if (customStylesheet != null)
        {
            if (customStylesheet.exists())
            {
                copyFile(outputDirectory, customStylesheet, CUSTOM_STYLE_FILE);
            }
            else
            {
                // If not found, try to read the file as a resource on the classpath
                // useful when reportng is called by a jarred up library
                InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(customStylesheet.getPath());
                if (stream != null)
                {
                    copyStream(outputDirectory, stream, CUSTOM_STYLE_FILE);
                }
            }
        }
    }
    
    public static boolean copyFile(File srcFile, File destFile)
    		   throws IOException {
    		  if (!srcFile.exists() || !srcFile.isFile() || !srcFile.canRead()) {
    		   throw new FileNotFoundException(srcFile.getAbsolutePath());
    		  }
    		  if (!destFile.exists()) {
    		   if (destFile.getParentFile() != null) {
    		    destFile.getParentFile().mkdirs();
    		   }
    		   destFile.createNewFile();
    		  } else if (destFile.isDirectory()) {
    		   destFile = new File(destFile, srcFile.getName());
    		  }

    		  if (srcFile.equals(destFile)) {
    		   return false;
    		  }

    		  FileChannel src = null;
    		  FileChannel dst = null;

    		  try {
    		   src = new FileInputStream(srcFile).getChannel();
    		   dst = new FileOutputStream(destFile).getChannel();
    		   long total = src.size();
    		   long curr = 0L;
    		   do {
    		    // curr += src.transferTo(curr, total - curr, dst);
    		    curr += dst.transferFrom(src, curr, total - curr);
    		   } while (curr < total);
    		  } finally {
    		   if (src != null) {
    		    try {
    		     src.close();
    		    } catch (Exception e) {
    		    }
    		   }
    		   if (dst != null) {
    		    try {
    		     dst.close();
    		    } catch (Exception e) {
    		    }
    		   }
    		   destFile.setLastModified(srcFile.lastModified());
    		  }
    		  return true;
    		 }

    		 public static void copyFileRecursively(File src, File dst)
    		   throws IOException {
    		  if (src == null) {
    		   throw new IllegalArgumentException("src null");
    		  }

    		  if (dst == null) {
    		   throw new IllegalArgumentException("dst null");
    		  }

    		  if (src.equals(dst)) {
    		   return;
    		  }

    		  if (!src.exists() || !src.canRead()) {
    		   throw new IllegalStateException("File: " + src.getAbsolutePath()
    		     + " can't read or exist");
    		  }

    		  if (src.isDirectory() && dst.isFile()) {
    		   throw new IllegalStateException("File: " + src.getAbsolutePath()
    		     + " is directory while File: " + dst.getAbsolutePath()
    		     + " is file");
    		  }

    		  if (!dst.exists() && dst.isDirectory()) {
    		   logger.info("create dst >>> " + dst.getAbsolutePath());
    		   dst.getParentFile().mkdirs();
    		   dst.mkdir();
    		  }

    		  if (src.isDirectory()) {
    		   File[] files = src.listFiles();
    		   for (File file : files) {
    		    copyFileRecursively(file, new File(dst, file.getName()));
    		   }
    		  } else {
    		   logger.info("copy from {" + src.getAbsolutePath() + " to "
    		     + dst.getAbsolutePath() + "}");
    		   copyFile(src, dst);
    		  }
    		 }
}
