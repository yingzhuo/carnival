package com.github.yingzhuo.carnival.easyexcel.core;

import com.alibaba.excel.annotation.ExcelProperty;
import com.github.yingzhuo.carnival.easyexcel.ExcelReader;
import com.github.yingzhuo.carnival.easyexcel.ReadingResult;
import com.github.yingzhuo.carnival.easyexcel.sheet.SheetDescriptor;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.Serializable;
import java.util.Date;

public class ExcelReaderTestCases {

    private final ExcelReader excelReader = new DefaultExcelReader();

    @Test
    public void test0() throws Exception {
        System.out.println(
                DateUtils.parseDate("2017-7-10", "yyyy-MM-dd")
        );
    }

    @Test
    public void test1() throws Throwable {
        final SheetDescriptor descriptor = SheetDescriptor.builder()
                .sheetNumber(0)
                .headerRowNumber(3)
                .modelClass(Person.class)
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


    public static final class Person implements Serializable {

        @ExcelProperty(value = "名字")
        private String name;

        @ExcelProperty(value = "年龄", converter = Converters.Int.class)
        private Integer age;

        @ExcelProperty(value = "工资", converter = Converters.Long.class)
        private Long salary;

        @ExcelProperty(value = "出生日期", converter = Converters.Date.class)
        private Date dob;

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", dob=" + dob +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Long getSalary() {
            return salary;
        }

        public void setSalary(Long salary) {
            this.salary = salary;
        }

        public Date getDob() {
            return dob;
        }

        public void setDob(Date dob) {
            this.dob = dob;
        }
    }

}
