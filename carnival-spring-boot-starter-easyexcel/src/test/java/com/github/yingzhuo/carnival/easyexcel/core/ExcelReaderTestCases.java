package com.github.yingzhuo.carnival.easyexcel.core;

import com.alibaba.excel.annotation.ExcelProperty;
import com.github.yingzhuo.carnival.easyexcel.ExcelReader;
import com.github.yingzhuo.carnival.easyexcel.ReadingResult;
import com.github.yingzhuo.carnival.easyexcel.rowskip.RowSkipStrategy;
import com.github.yingzhuo.carnival.easyexcel.sheet.SheetDescriptor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.Serializable;
import java.util.Date;

public class ExcelReaderTestCases {

    private final ExcelReader excelReader = new DefaultExcelReader();

    @Test
    public void test1() {
        final SheetDescriptor descriptor = SheetDescriptor.builder()
                .sheetNumber(1)
                .headerRowNumber(3)
                .modelClass(Person.class)
                .rowSkipStrategies(RowSkipStrategy.builder().byRowNumber(4).build())
                .build();

        final ReadingResult<Person> readingResult = excelReader.read(
                new FileSystemResource("/Users/yingzhuo/Downloads/test.xlsx"),
                descriptor
        );

        System.out.println("模型:");
        readingResult.getModels().forEach(System.out::println);

        System.out.println("----");
        System.out.println("错误:");
        readingResult.getErrors().forEach(System.out::println);
    }

    @Getter
    @Setter
    @ToString
    public static final class Person implements Serializable {

        @ExcelProperty(value = "名字")
        private String name;

        @ExcelProperty(value = "年龄", converter = Converters.Int.class)
        private Integer age;

        @ExcelProperty(value = "工资", converter = Converters.Long.class)
        private Long salary;

        @ExcelProperty(value = "出生日期", converter = Converters.Date.class)
        private Date dob;
    }

}
