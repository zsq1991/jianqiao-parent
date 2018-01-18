package com.zc.common.core.compile;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * 
 * 
 * @author zhangkaoqing
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-4-11 下午08:42:09
 * 
 */
public class StringObject extends SimpleJavaFileObject {
	private String contents = null;

	public StringObject(String className, String contents) throws Exception {
		super(new URI(className), Kind.SOURCE);
		this.contents = contents;
	}

	@Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return contents;
	}
}
