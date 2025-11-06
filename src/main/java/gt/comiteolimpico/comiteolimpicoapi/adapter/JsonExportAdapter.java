package gt.comiteolimpico.comiteolimpicoapi.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@Component("jsonExportAdapter")
public class JsonExportAdapter<T> implements ExportAdapter<T> {

    private final ObjectMapper mapper;

    public JsonExportAdapter() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void export(List<T> data, OutputStream outputStream) throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, data);
    }

    @Override
    public List<T> importData(InputStream inputStream) throws Exception {
        T[] array = (T[]) mapper.readValue(inputStream, Object[].class);
        return Arrays.asList(array);
    }
}