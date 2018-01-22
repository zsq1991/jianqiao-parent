package com.zc.common.core.compile;

import org.apache.commons.lang.StringUtils;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.util.Arrays;

/**
 * 动态编译工具
 * 
 * @author zhangkaoqing
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-4-11 下午12:50:21
 * 
 */
public class CompileUtils {
	private CompileUtils() {
	}

	/**
	 * 编译字符串类型的代码
	 * 
	 * @param so StringObject so = new StringObject("demo.CalculatorTest",
	 *            "package demo;" + "  class CalculatorTest {" +
	 *            "	public int multiply(int multiplicand, int multiplier) {" +
	 *            "		System.out.println(multiplicand);" +
	 *            "		System.out.println(multiplier);" +
	 *            "		return multiplicand * multiplier;" + "	}" + "}");
	 * 
	 * @param fileName .class文件的存放位置 只要指定目录文件的路径即可 如果文件名为空就是默认的
	 * @return
	 */
	public static Boolean compileStringJava(StringObject so, String fileName) {

		// 通过系统工具提供者获得动态编译器
		javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		// 获得一个文件管理器，它的功能主要是提供所有文件操作的规则，
		// 如源代码路径、编译的classpath，class文件目标目录等，其相关属性都提供默认值
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		JavaFileObject file = so;
		// 指定 javac 的命令行参数
		// 注意：最好将编译后的.class文件放到当前JVM实例的类路径上，否则加载不鸟
		Iterable<String> options = null;
		if (StringUtils.isNotBlank(fileName)) {
			options = Arrays.asList("-d", fileName);
		}
		// 指定有哪些源文件需要被编译
		Iterable<? extends JavaFileObject> files = Arrays.asList(file);

		javax.tools.JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null,
				options, null, files);

		Boolean result = task.call();
		return result;
	}
}
