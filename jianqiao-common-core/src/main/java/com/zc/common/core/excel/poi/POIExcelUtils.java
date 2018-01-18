package com.zc.common.core.excel.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zc.common.core.annotation.AnnotationUtils;
import com.zc.common.core.convert.ConvertUtils;
import com.zc.common.core.reflection.ReflectionUtils;
import com.zc.common.core.utils.MyObjectUtils;
import com.zc.common.core.validate.hibernatevalidator.HibernateValidateUtils;

/**
 * POI解析的读取和写入 这个poi工具类可以解析97到目前的所有版本的excel但是仅仅只限于解析 xls和 xlsx的，
 * 如果出现宏的问题，请获取到第一个sheet没有值的时候继续判断第二个sheet即可
 * 
 * @author 张靠勤
 * @e-mail 627658539
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2011-12-27 下午：21:35
 * 
 */
public class POIExcelUtils {

	private POIExcelUtils() {
		throw new AssertionError();
	}

	private static Logger logger = LoggerFactory.getLogger(POIExcelUtils.class);

	/**
	 * 判断文件类型是2003还是2007
	 * 
	 * @param filePath Excel文件绝对路径
	 * @return
	 * @throws IOException
	 */
	public static Workbook createImportWorkBook(final String filePath) {
		InputStream is = null;
		try {
			is = new FileInputStream(new File(filePath));
			if (filePath.toLowerCase().endsWith("xls")) {
				return new HSSFWorkbook(is);
			} else if (filePath.toLowerCase().endsWith("xlsx")) {
				return new XSSFWorkbook(is);
			}
		} catch (FileNotFoundException e) {
			logger.error("文件：" + filePath + "没有找到");
		} catch (IOException e) {
			logger.error("文件IO错误");
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
		return null;
	}

	/**
	 * 建立写入excel对象工作簿worbook
	 * 
	 * @param version excel的版本号
	 * @return
	 */
	public static Workbook createExportWorkBook(final ExcelVersion excelVersion) {
		Workbook workbook = null;
		if ("2003".equals(excelVersion.getName())) {
			workbook = new HSSFWorkbook();
		} else if ("2007".equals(excelVersion.getName())) {
			workbook = new XSSFWorkbook();
		}
		return workbook;
	}

	/**
	 * 写出excel
	 * 
	 * @param list 每个类型为每行的值 泛型类型的属性值都为String
	 * @param mymap map最好为LinkedHashMap key代表列的名字，value代表list中类字段的名字
	 * @return
	 */
	public static Workbook exportInfo(final List<?> list, final Map<?, ?> mymap) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		String[] strings = new String[mymap.size()];
		int j = 0;
		Row row = sheet.createRow(0);
		for (Map.Entry<?, ?> entry : mymap.entrySet()) {
			row.createCell(j).setCellValue(MyObjectUtils.toString(entry.getKey()));
			strings[j] = MyObjectUtils.toString(entry.getValue());
			j++;
		}
		int i = 0;
		for (Object object : list) {
			Row row2 = sheet.createRow(i + 1);
			for (int m = 0; m < strings.length; m++) {
				row2.createCell(m)
						.setCellValue(
								MyObjectUtils.toString(ReflectionUtils.invokeGetterMethod(object,
										strings[m])));
			}
			i++;
		}
		return workbook;
	}

	/**
	 * 取得列数据
	 * 
	 * @param row Excel行
	 * @param index 第几列
	 * @return
	 * @throws Exception
	 */
	public static Object getCellValue(final Row row, final int index) {
		if (row == null) {
			return null;
		}
		Object value = null;
		Cell cell = row.getCell(index);
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					try{
						value = DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd HH:mm:ss");
					}catch(Exception e){
						value=null;
					}
					
				} else {
					value = new BigDecimal(cell.getNumericCellValue()).toString();
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				value = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_ERROR:
				value = String.valueOf(cell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			default:
				break;
			}
		}
		return StringUtils.trim(MyObjectUtils.toString(value));
	}

	/**
	 * 获取日期的样式
	 * 
	 * @param workbook
	 * @param format 为日期的格式
	 * @return
	 */
	public static CellStyle setDateStyle(final Workbook workbook, final String format) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper creationHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(format));
		return cellStyle;
	}

	/**
	 * 创建标题样式
	 * 
	 * @param workbook
	 * @return
	 */
	public static CellStyle getTitleStyle(final Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();// 设置字体
		font.setFontHeightInPoints((short) 20);// 设置字体大小
		font.setFontName("黑体");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// 设置粗体
		cellStyle.setFont(font);
		// 设置是否自动换行
		// cellStyle.setWrapText(false);
		// 水平居中
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 数值居中
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 设置单元格边缘
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return cellStyle;
	}

	/**
	 * 创建落款样式
	 * 
	 * @param workbook
	 * @return
	 */
	public static CellStyle getTailStyle(final Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		return cellStyle;
	}

	/**
	 * 创建表头局中
	 * 
	 * @param workbook
	 * @return
	 */
	public static CellStyle getHeaderCenterStyle(final Workbook workbook) {
		CellStyle cellStyle = POIExcelUtils.getTitleStyle(workbook);
		cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return cellStyle;
	}

	/**
	 * 创建表内容样式
	 * 
	 * @param work
	 * @return
	 */
	public static CellStyle getContentStyle(final Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(false);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		return cellStyle;
	}

	/**
	 * 获取excel文件的值，转化为java类型
	 * 
	 * @param fileName excel文件地址
	 * @param t 可以为实体bean类型，也可以为Map<String,Object>类型
	 * @param excelFirstCallBack 回调函数，解析第一行
	 * @param keyValues 为map类型的时候需要给定键值，顺序很重要,如果为实体类型可以为null
	 * @param errorsList 错误列表
	 * @return 解析不成功返回null
	 */
	public static <T> List<T> readExcel(final String fileName, final Class<T> t,
			final ExcelFirstCallBack excelFirstCallBack, final String[] keyValues,
			final List<T> errorsList) {
		// 建立获取数据的集合类
		List<T> list = new ArrayList<T>();
		// 建立sheet
		Sheet sheet = getSheet(fileName);
		// 获取sheet的行数
		int count = sheet.getLastRowNum();
		// 判断行数
		if (count > 0) {
			// 获取第一行数据
			Row row = sheet.getRow(0);
			// 回调函数对第一行进行解析并且当返回值为true的时候才继续解析
			if (excelFirstCallBack.paseFirstSheet(row)) {
				// 获取数据
				return getResultPageSile(list, sheet, 1, count, keyValues, t, errorsList);
			} else {
				logger.info("回调函数返回的值为false");
				return null;
			}
		} else {
			// 没有行数
			logger.info("没有行数");
			return null;
		}

	}

	/**
	 * 获取excel文件的值，转化为java类型
	 * 
	 * @param fileName excel文件地址
	 * @param t 可以为实体bean类型，也可以为Map<String,Object>类型
	 * @param excelFirstCallBack 回调函数，解析第一行
	 * @param keyValues 为map类型的时候需要给定键值，顺序很重要,如果为实体类型可以为null
	 * @return 解析不成功返回null
	 */
	public static <T> List<T> readExcel(final String fileName, final Class<T> t,
			final ExcelFirstCallBack excelFirstCallBack, final String[] keyValues) {
		return readExcel(fileName, t, excelFirstCallBack, keyValues, null);
	}

	/**
	 * 获取sheet对象的方法
	 * 
	 * @param fileName
	 * @return
	 */
	public static Sheet getSheet(final String fileName) {
		// 根据excel文件得到workbook对象
		Workbook workbook = createImportWorkBook(fileName);
		// 获取sheet对象
		Sheet sheet = workbook.getSheetAt(0);
		// 如果sheet下面没有内容的话，再读取下一个页面，这个主要是用来防止宏的问题的
		if (sheet.getLastRowNum() < 1) {
			sheet = workbook.getSheetAt(1);
		}
		return sheet;
	}

	/**
	 * 获取excel文件的值，转化为java类型
	 * 
	 * @param fileName excel文件地址
	 * @param t 可以为实体bean类型，也可以为Map<String,Object>类型
	 * @param excelFirstCallBack 回调函数，解析第一行
	 * @param keyValues 为map类型的时候需要给定键值，顺序很重要,实体对于属性名
	 * @param firstRoot 起始的行最小为1
	 * @param getsize 获取的条数
	 * @param errorsList 错误列表
	 * @return
	 */
	public static <T> List<T> readExcel(final String fileName, final Class<T> t,
			final ExcelFirstCallBack excelFirstCallBack, final String[] keyValues,
			final int firstRoot, final int getsize, final List<T> errorsList) {
		// 建立获取数据的集合类
		List<T> list = new ArrayList<T>();
		// 建立sheet
		Sheet sheet = getSheet(fileName);
		// 获取sheet的行数
		int count = sheet.getLastRowNum();
		// 判断行数
		if (count > 0) {
			// 获取第一行数据
			Row row = sheet.getRow(0);
			// 回调函数对第一行进行解析并且当返回值为true的时候才继续解析
			if (excelFirstCallBack.paseFirstSheet(row)) {
				// 获取数据
				return getResultPageSile(list, sheet, firstRoot, getsize, keyValues, t, errorsList);
			} else {
				logger.info("回调函数返回的值为false");
				return null;
			}
		} else {
			// 没有行数
			logger.info("没有行数");
			return null;
		}

	}

	/**
	 * 获取excel文件的值，转化为java类型
	 * 
	 * @param fileName excel文件地址
	 * @param t 可以为实体bean类型，也可以为Map<String,Object>类型
	 * @param excelFirstCallBack 回调函数，解析第一行
	 * @param keyValues 为map类型的时候需要给定键值，顺序很重要,实体对于属性名
	 * @param firstRoot 起始的行最小为1
	 * @param getsize 获取的条数
	 * @return
	 */
	public static <T> List<T> readExcel(final String fileName, final Class<T> t,
			final ExcelFirstCallBack excelFirstCallBack, final String[] keyValues,
			final int firstRoot, final int getsize) {
		return readExcel(fileName, t, excelFirstCallBack, keyValues, firstRoot, getsize, null);
	}

	/**
	 * 将excel解析为list的实体类型
	 * 
	 * @param fileName excel文件地址
	 * @param t 代表实体的类型
	 * @param excelFirstCallBack 回调函数，解析第一行
	 * @param keyValues 为map类型的时候需要给定键值，顺序很重要,实体对于属性名
	 * @return 解析不成功返回null
	 */
	public static <T> List<T> readExcelByList(final String fileName, final Class<T> t,
			final ExcelFirstCallBack excelFirstCallBack, final String[] keyValues) {
		return readExcel(fileName, t, excelFirstCallBack, keyValues);
	}

	/**
	 * 将excel解析为list的实体类型
	 * 
	 * @param fileName excel文件地址
	 * @param t 代表实体的类型
	 * @param excelFirstCallBack 回调函数，解析第一行
	 * @param firstRoot 起始的行最小为1
	 * @param getsize 获取的条数
	 * @param keyValues 为map类型的时候需要给定键值，顺序很重要,实体对于属性名
	 * @return
	 */
	public static <T> List<T> readExcelByList(final String fileName, final Class<T> t,
			final ExcelFirstCallBack excelFirstCallBack, final int firstRoot, final int getsize,
			final String[] keyValues) {
		return readExcel(fileName, t, excelFirstCallBack, keyValues, firstRoot, getsize);
	}

	/**
	 * 将excel解析为list的实体类型
	 * 
	 * @param fileName excel文件地址
	 * @param t 代表实体的类型
	 * @param excelFirstCallBack 回调函数，解析第一行
	 * @param keyValues 为map类型的时候需要给定键值，顺序很重要,实体对于属性名
	 * @param errorsList 错误列表
	 * @return 解析不成功返回null
	 */
	public static <T> List<T> readExcelByList(final String fileName, final Class<T> t,
			final ExcelFirstCallBack excelFirstCallBack, final String[] keyValues,
			final List<T> errorsList) {
		return readExcel(fileName, t, excelFirstCallBack, keyValues, errorsList);
	}

	/**
	 * 将excel解析为list的实体类型
	 * 
	 * @param fileName excel文件地址
	 * @param t 代表实体的类型
	 * @param excelFirstCallBack 回调函数，解析第一行
	 * @param firstRoot 起始的行最小为1
	 * @param getsize 获取的条数
	 * @param keyValues 为map类型的时候需要给定键值，顺序很重要,实体对于属性名
	 * @param errorsList 错误列表
	 * @return
	 */
	public static <T> List<T> readExcelByList(final String fileName, final Class<T> t,
			final ExcelFirstCallBack excelFirstCallBack, final int firstRoot, final int getsize,
			final String[] keyValues, final List<T> errorsList) {
		return readExcel(fileName, t, excelFirstCallBack, keyValues, firstRoot, getsize, errorsList);
	}

	/**
	 * 获取sheet的值类型可以为list,map等不过map必须是Map<String,Object>
	 * 
	 * @param list
	 * @param sheet
	 * @param firstRoot
	 * @param getsize
	 * @param keyValues
	 * @param t
	 * @param errorsList 错误的list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> List<T> getResultPageSile(final List<T> list, final Sheet sheet,
			final int firstRoot, final int getsize, final String[] keyValues, final Class<T> t,
			final List<T> errorsList) {
		// 获取excel下面有多少行的数据
		int count = sheet.getLastRowNum();
		// 判断firstroot的值与count对比
		if (count < firstRoot) {
			logger.info("指定的开始行数比excel的总行数还大");
			return null;
		}
		// 获取的条数
		int pageSize;
		// 获取的最后一行的行数
		int getLastCount = firstRoot + getsize - 1;
		// 比较获取的行数和excel总行数的数据，做出判断
		if (count < getLastCount) {
			pageSize = count;
		} else {
			pageSize = getLastCount;
		}
		// 判断进来的类型是否是指定为map
		if (t == Map.class) {
			if (keyValues != null) {
				// 解析为指定的map类型
				for (int i = firstRoot; i <= pageSize; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 获取当前行
					Row row = sheet.getRow(i);
					int j = 0;
					for (String string : keyValues) {
						// 给map设置值
						map.put(string, getCellValue(row, j));
						j++;
					}
					list.add((T) map);
				}
			} else {
				logger.info("keyValues的值不能为空");
				return null;
			}
		} else {
			// 解析为bean类型
			for (int i = firstRoot; i <= pageSize; i++) {
				try {
					T t2 = t.newInstance();
					// 获取当前行
					Row row = sheet.getRow(i);
					int j = 0;
					int m=0;
					for (String string : keyValues) {
						// 给实体设置值
						ReflectionUtils.invokeSetterMethod(t2, string, ConvertUtils
								.convertStringToObject((String) getCellValue(row, j), t2.getClass()
										.getDeclaredField(string).getType()));
						Object object=ReflectionUtils.invokeGetterMethod(t2, string);
						if(object==null){
							m++;
						}else if(StringUtils.isBlank(MyObjectUtils.toString(object))){
							m++;
						}
						
						j++;
					}
					if(j==m){
						break;
					}
					// 判断是否需要错误信息列表
					if (errorsList == null) {
						list.add(t2);
					} else {
						// 通过验证框架做验证
						Set<ConstraintViolation<T>> constraintViolations = HibernateValidateUtils
								.getValidator(t2);
						// 获取首行错误信息
						String error = constraintViolations.iterator().next().getMessage();
						if (constraintViolations.size() < 1) {
							list.add(t2);
						} else {
							// 获取带注解的field
							List<Field> fields = AnnotationUtils.getHaveAnnotationByField(
									t2.getClass(), ExcelError.class);
							if (fields.size() < 1) {
								// 获取方法上的注解
								List<Method> methods = AnnotationUtils.getHaveAnnotationByMethod(
										t2.getClass(), ExcelError.class);
								if (methods.size() > 0) {
									for (Method method : methods) {
										method.invoke(t2, error);
									}
								}
							} else {
								// 注解存在设置错误数据
								for (Field field : fields) {
									ReflectionUtils.invokeSetterMethod(t2, field.getName(), error);
								}
							}
							errorsList.add(t2);
						}
					}

				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
