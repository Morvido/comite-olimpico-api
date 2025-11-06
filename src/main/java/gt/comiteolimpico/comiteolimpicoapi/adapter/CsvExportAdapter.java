package gt.comiteolimpico.comiteolimpicoapi.adapter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("csvExportAdapter")
public class CsvExportAdapter<T> implements ExportAdapter<T> {

    @Override
    public void export(List<T> data, OutputStream outputStream) throws Exception {
        if (data == null || data.isEmpty()) {
            return;
        }

        T first = data.get(0);
        Field[] fields = first.getClass().getDeclaredFields();

        try (Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(getHeaders(fields)))) {

            for (T item : data) {
                printer.printRecord(getValues(item, fields));
            }
        }
    }

    @Override
    public List<T> importData(InputStream inputStream) throws Exception {
        // Importación básica: no implementada completamente (opcional)
        // Puedes mejorarla con MapStruct o reflexión avanzada
        return new ArrayList<>();
    }

    private String[] getHeaders(Field[] fields) {
        return Arrays.stream(fields)
                .map(Field::getName)
                .toArray(String[]::new);
    }

    private Object[] getValues(T obj, Field[] fields) {
        return Arrays.stream(fields)
                .peek(field -> field.setAccessible(true))
                .map(field -> {
                    try {
                        return field.get(obj);
                    } catch (IllegalAccessException e) {
                        return "";
                    }
                })
                .toArray();
    }
}