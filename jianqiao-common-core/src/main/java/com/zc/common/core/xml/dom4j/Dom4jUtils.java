package com.zc.common.core.xml.dom4j;

import com.google.common.collect.Maps;
import com.zc.common.core.convert.ConvertUtils;
import com.zc.common.core.reflection.ReflectionUtils;
import com.zc.common.core.utils.MyObjectUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.*;

/**
 * dom4j工具类只能解析全属性或者全标签类型的xml
 * 
 * @author zhangkaoqing
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-5-4 下午02:46:04
 * 
 */
public class Dom4jUtils {
	private static DocumentFactory documentFactory = null;
	static {
		documentFactory = DocumentFactory.getInstance();
	}

	private Dom4jUtils() {
	}

	/**
	 * 建立xml文本对象
	 * 
	 * @return
	 */
	public static Document getDocument() {
		return documentFactory.createDocument();
	}

	/**
	 * 建立根节点xml元素
	 * 
	 * @param document
	 * @param rootName
	 * @return
	 */
	public static Element createRootElement(Document document, String rootName) {
		Element element = documentFactory.createElement(rootName);
		document.setRootElement(element);
		return element;
	}

	/**
	 * 在元素下面添加元素
	 * 
	 * @param element
	 * @param elementName
	 * @return
	 */
	public static Element createCommonElement(Element element, String elementName) {
		return element.addElement(elementName);
	}

	/**
	 * 添加元素的属性
	 * 
	 * @param element
	 * @param map
	 */
	public static void addAttributr(Element element, Map<?, ?> map) {
		Object[] objects = map.keySet().toArray();
		for (Object object : objects) {
			element.addAttribute(MyObjectUtils.toString(object),
					MyObjectUtils.toString(map.get(MyObjectUtils.toString(object))));
		}
	}

	/**
	 * 往元素下面添加文本内容
	 * 
	 * @param element
	 * @param text
	 */
	public static void addText(Element element, String text) {
		element.addText(text);
	}

	/**
	 * 增加CDATA
	 * 
	 * @param element
	 * @param text
	 */
	public static void addCDTA(Element element, String text) {
		element.addCDATA(text);
	}

	/**
	 * 向 XML 文档中增加文档类型说明：
	 * 
	 * @param document
	 * @param name
	 * @param publicId
	 * @param systemId
	 */
	public static void addDocType(Document document, String name, String publicId, String systemId) {
		document.addDocType(name, publicId, systemId);
	}

	/**
	 * 写出xml文件
	 * 
	 * @param document
	 * @param file
	 */
	public static void createXmlFile(final Document document, final File file) {
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setEncoding("UTF-8");
		XMLWriter xmlWriter = null;
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			xmlWriter = new XMLWriter(outputStream, outputFormat);
			xmlWriter.write(document);
			System.out.println("创建文件" + file.getName() + "成功...");
		} catch (UnsupportedEncodingException e) {
			System.out.println("创建文件" + file.getName() + "失败...");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("创建文件" + file.getName() + "失败...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("创建文件" + file.getName() + "失败...");
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
				xmlWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将XML以String的形式写出
	 * 
	 * @param document
	 * @return
	 */
	public static String writeXmlToString(final Document document) {
		String result = null;
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setEncoding("UTF-8");
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		XMLWriter xmlWriter = null;
		try {
			xmlWriter = new XMLWriter(byteArrayOutputStream, outputFormat);
			xmlWriter.write(document);
			result = byteArrayOutputStream.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				byteArrayOutputStream.close();
				xmlWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取读取的文档对象
	 * 
	 * @param file
	 * @return
	 * @throws DocumentException
	 */
	public static Document getReadDocument(final File file) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		return saxReader.read(file);
	}

	/**
	 * 获取读取的文档对象
	 * 
	 * @param string
	 * @return
	 * @throws DocumentException
	 */
	public static Document getReadDocument(final InputStream inputStream) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		return saxReader.read(inputStream);
	}

	/**
	 * 通过xpath语句读取下面单元的文档
	 * 
	 * @param document
	 * @param xpath
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getNoteText(final Document document, final String xpath) {
		List list = document.selectNodes(xpath);
		String string = null;
		for (Object object : list) {
			Element element = (Element) object;
			string = element.getTextTrim();
		}
		return string;
	}

	/**
	 * 将xml中的内容放入实体中
	 * 
	 * @param <T>
	 * @param document
	 * @param xPath
	 * @param t
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T getXmlToBean(final Document document, final String xPath, final T t) {
		List list = document.selectNodes(xPath);
		for (Object object : list) {
			Element element = (Element) object;
			List elements = element.elements();
			for (Object object2 : elements) {
				Element element2 = (Element) object2;
				try {
					ReflectionUtils.invokeSetterMethod(
							t,
							element2.getName(),
							ConvertUtils.convertStringToObject(element2.getTextTrim(), t.getClass()
									.getDeclaredField(element2.getName()).getType()));
				} catch (NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
			}

		}
		return t;
	}

	/**
	 * 获取Map类型的xml结果将结果选择后返回
	 * 
	 * @param document Dom4j的Document对象
	 * @param xPath 对象的xpath地址选择
	 * @param strings 这个数组为取得的名称列表，如果为null就取出全部的
	 * @param replaceStrings 当为null时不进行取值替换，取值替换公式为 :标签名@标签值:替换值(如果有多个请用;隔开)
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getXmlToMap(final Document document, final String xPath,
			final String[] strings, final String[] replaceStrings) {
		Map<String, Object> map = Maps.newHashMap();
		List list = document.selectNodes(xPath);
		for (Object object : list) {
			Element element = (Element) object;
			List elements = element.elements();
			for (Object object2 : elements) {
				Element element2 = (Element) object2;
				if (strings == null) {
					parseReplace(replaceStrings, element2, map);
				} else {
					if (ArrayUtils.contains(strings, element2.getName())) {
						parseReplace(replaceStrings, element2, map);
					}
				}
			}

		}
		return map;
	}

	/**
	 * 将xml直接转成list
	 * 
	 * @param <T>
	 * @param document
	 * @param xPathList
	 * @param objectName
	 * @param class1
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	public static <T> List<T> getXmlToList(final Document document, final String xPathList,
			final String objectName, final Class<T> class1) throws InstantiationException,
			IllegalAccessException {
		List<T> ts = new ArrayList<T>();
		List list = document.selectNodes(xPathList);
		for (Object object : list) {
			Element element = (Element) object;
			List list2 = element.elements(objectName);
			for (Object object2 : list2) {
				Element element2 = (Element) object2;
				List list3 = element2.elements();
				T t = class1.newInstance();
				for (Object object3 : list3) {
					Element element4 = (Element) object3;
					try {
						ReflectionUtils
								.invokeSetterMethod(t, element2.getName(), ConvertUtils
										.convertStringToObject(element4.getTextTrim(), t.getClass()
												.getDeclaredField(element4.getName()).getType()));
					} catch (NoSuchFieldException | SecurityException e) {
						e.printStackTrace();
					}
					ts.add(t);
				}
			}
		}
		return ts;
	}

	/**
	 * 将xml转换成List中放Map的形式
	 * 
	 * @param document dom4j的对象
	 * @param xPathList 取得list的xpath语句
	 * @param objectName 作为对象的标签名称
	 * @param strings 这个数组为取得的名称列表，如果为null就取出全部的
	 * @param replaceStrings 当为null时不进行取值替换，取值替换公式为 :标签名@标签值:替换值(如果有多个请用;隔开)
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map<String, Object>> getXmlToList(final Document document,
			final String xPathList, final String objectName, final String[] strings,
			final String[] replaceStrings) throws InstantiationException, IllegalAccessException {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		List list = document.selectNodes(xPathList);
		for (Object object : list) {
			Element element = (Element) object;
			List list2 = element.elements(objectName);
			for (Object object2 : list2) {
				Element element2 = (Element) object2;
				List list3 = element2.elements();
				Map<String, Object> map = Maps.newHashMap();
				for (Object object3 : list3) {
					Element element4 = (Element) object3;
					if (strings == null) {
						parseReplace(replaceStrings, element4, map);
					} else {
						if (ArrayUtils.contains(strings, element4.getName())) {
							parseReplace(replaceStrings, element4, map);
						}
					}
					maps.add(map);
				}
			}
		}
		return maps;
	}

	/**
	 * 将element元素按照指定的方式替换
	 * 
	 * @param replaceStrings 当为null时不进行取值替换，取值替换公式为 :标签名@标签值:替换值(如果有多个请用;隔开)
	 * @param element4 Element对象
	 * @param map Map对象存取数据
	 */
	private static void parseReplace(String[] replaceStrings, Element element4,
			Map<String, Object> map) {
		if (replaceStrings == null) {
			map.put(element4.getName(), element4.getTextTrim());
		} else {
			boolean result = false;
			for (String string : replaceStrings) {
				if (StringUtils
						.equals(StringUtils.substringBefore(string, "@"), element4.getName())) {
					String replace = StringUtils.substringAfter(string, "@");
					String[] strings2 = StringUtils.split(replace, ";");
					for (String string2 : strings2) {
						if (StringUtils.equals(StringUtils.substringBefore(string2, ":"),
								element4.getTextTrim())) {
							map.put(element4.getName(), StringUtils.substringAfter(string2, ":"));
							result = true;
							break;
						}
					}
				}
				if (result) {
					break;
				}
			}
			if (!result) {
				map.put(element4.getName(), element4.getTextTrim());
			}
		}
	}

	/**
	 * 获取某个标签的全部值，放在List<String>里面
	 * 
	 * @param document dom4j对象
	 * @param xPath 标签的xpath语句
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> getXmlToListString(Document document, String xPath) {
		List<String> strings = new ArrayList<String>();
		List list = document.selectNodes(xPath);
		for (Object object : list) {
			Element element = (Element) object;
			strings.add(element.getTextTrim());
		}
		return strings;
	}

	/**
	 * 将Map转变成xml
	 * 
	 * @param map
	 * @param rootName
	 * @return
	 */
	public static Document getMapToXml(final Map<String, String> map, final String rootName) {
		Document document = getDocument();
		Element element = createRootElement(document, rootName);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			Element element2 = createCommonElement(element, entry.getKey());
			addText(element2, entry.getValue());
		}
		return document;
	}

	/**
	 * 
	 * 将xml属性转换成map对象
	 * 
	 * @param document
	 * @param xpathString xpath语句
	 * @param strings 所要取得的值，如果为null取出全部的属性
	 * @param replaceStrings 当为null时不进行取值替换，取值替换公式为 :属性名@标签值:替换值(如果有多个请用;隔开)
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getXmlToMapByAttribute(final Document document,
			final String xpathString, final String[] strings, final String[] replaceStrings) {
		Map<String, Object> map = Maps.newHashMap();
		// 获取到指定节点名称的单元
		List list = document.selectNodes(xpathString);
		for (Object object : list) {
			// 获取指定单元
			Element element = (Element) object;
			// 如果数组为空我们就取出所有的属性值
			if (strings == null) {
				// 遍历单元属性键值对设置到map中
				Iterator iterator = element.attributeIterator();
				while (iterator.hasNext()) {
					Attribute attribute = (Attribute) iterator.next();
					parseReplaceAttribute(replaceStrings, attribute, map);
				}
			} else {
				// 如果数组不为空，取出数组指定的属性键值对
				for (String string : strings) {
					Attribute attribute = element.attribute(string);
					parseReplaceAttribute(replaceStrings, attribute, map);
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * 将xml属性转换成map对象
	 * 
	 * @param document
	 * @param xpathString xpath语句
	 * @param strings 所要取得的值，如果为null取出全部的属性
	 * @param replaceStrings 当为null时不进行取值替换，取值替换公式为 :属性名@标签值:替换值(如果有多个请用;隔开)
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map<String, Object>> getXmlToListByAttribute(final Document document,
			final String xpathString, final String[] strings, final String[] replaceStrings) {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		// 获取到指定节点名称的单元
		List list = document.selectNodes(xpathString);
		for (Object object : list) {
			Map<String, Object> map = Maps.newHashMap();
			// 获取指定单元
			Element element = (Element) object;
			// 如果数组为空我们就取出所有的属性值
			if (strings == null) {
				// 遍历单元属性键值对设置到map中
				Iterator iterator = element.attributeIterator();
				while (iterator.hasNext()) {
					Attribute attribute = (Attribute) iterator.next();
					parseReplaceAttribute(replaceStrings, attribute, map);
				}
				maps.add(map);
			} else {
				// 如果数组不为空，取出数组指定的属性键值对
				for (String string : strings) {
					Attribute attribute = element.attribute(string);
					parseReplaceAttribute(replaceStrings, attribute, map);
				}
				maps.add(map);
			}
		}
		return maps;
	}

	/**
	 * 将attribute元素按照指定的方式替换
	 * 
	 * @param replaceStrings 当为null时不进行取值替换，取值替换公式为 :标签名@标签值:替换值(如果有多个请用;隔开)
	 * @param attribute 属性对象
	 * @param map Map对象存取数据
	 */
	private static void parseReplaceAttribute(String[] replaceStrings, Attribute attribute,
			Map<String, Object> map) {
		if (replaceStrings == null) {
			map.put(attribute.getName(), attribute.getValue());
		} else {
			boolean result = false;
			for (String string : replaceStrings) {
				if (StringUtils.equals(StringUtils.substringBefore(string, "@"),
						attribute.getName())) {
					String replace = StringUtils.substringAfter(string, "@");
					String[] strings2 = StringUtils.split(replace, ";");
					for (String string2 : strings2) {
						if (StringUtils.equals(StringUtils.substringBefore(string2, ":"),
								attribute.getValue())) {
							map.put(attribute.getName(), StringUtils.substringAfter(string2, ":"));
							result = true;
							break;
						}
					}
				}
				if (result) {
					break;
				}
			}
			if (!result) {
				map.put(attribute.getName(), attribute.getValue());
			}
		}
	}

}
